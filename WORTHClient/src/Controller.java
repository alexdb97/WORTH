import java.awt.event.ActionListener; 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.*;



public class Controller {

    private InitialView theview;
    private Model themodel;
    private Thread t;
    private SocketAddress address = new InetSocketAddress("localhost", 6060);
    SocketChannel client;
    ServerInterface server;

    // RMICALLBACK
    Registry registry;
    String name1 = "Server";
    NotifyEventInterface callbackObj;
    NotifyEventInterface stub;

    public Controller(InitialView view, Model mod) 
    {
        this.themodel = mod;
        this.theview = view;

        // Routine for closing the connection throught the exit button
        theview.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(frame, "Do you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {

                    if (client != null)
                        if (client.isConnected()) {
                            RequestResponse.requestresponse(client, "LOGOUT", theview, themodel);
                            try {
                                server.unregisterForCallback(stub);
                            } catch (RemoteException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }

                    try {
                        Thread.currentThread();
                        // Waiting an half second for sending LOGOUT to server
                        // and close safely the connection
                        Thread.sleep(1000);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });

        this.theview.RegisterListner(new RegisterLis());
        this.theview.LoginListener(new LoginListener());
        this.theview.ListProjects(new ListProjects());
        this.theview.GoBack(new goBack_prog());
        this.theview.logout(new Logout());
        this.theview.ListUsers(new ListUsers());
        this.theview.ListOnlineUsers(new ListOnlineUsers());
        this.theview.PrepareProject(new PrepareProject());
        this.theview.EffectiveCreate(new EffectiveCreate());
        this.theview.EnterProject(new EnterProject());
        this.theview.CancelProject(new CancelProject());
        this.theview.AddCardview(new AddCard());
        this.theview.ShowCardEvent(new ShowCards());
        this.theview.ShowMembers(new ShowMembers());
        this.theview.ShowCardProperty(new ShowCardProperty());
        this.theview.EffecctiveShowCardProps(new EffecctiveShowCardProps());
        this.theview.EffectiveAddCard(new EffectiveAddCard());
        this.theview.MoveCard(new MoveCard());
        this.theview.EffectiveMoveCard(new EffectiveMoveCard());
        this.theview.GroupChat(new GroupChat());
        this.theview.AddMember(new AddMember());
        this.theview.AddEffectiveMember(new AddEffectiveMember());
        this.theview.ShowChatListener(new GroupChat());
        this.theview.SendMessage(new SendMessage());

    }

    // Register Event
    class RegisterLis implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name, pass;

            try {

                name = theview.getUsername();
                pass = theview.getPassword();
                if (name != null || pass != null)

                    if (themodel.sendData(name, pass) == 400)
                        theview.error("400 Registered User");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    // Login Event
    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name, pass;

            try {

                name = theview.getUsername();
                pass = theview.getPassword();
                if (name != null || pass != null)
                    themodel.setName(name);

                registry = LocateRegistry.getRegistry(7070);
                server = (ServerInterface) registry.lookup(name1);
                System.out.println("Registering For Callback");
                client = SocketChannel.open(address);
                client.configureBlocking(false);

                callbackObj = new NotifyImpl();
                themodel.setcallback(callbackObj);
                stub = (NotifyEventInterface) UnicastRemoteObject.exportObject(callbackObj, 0);
                server.registerForCallback(stub);

                String request = "LOGIN\n"+name+"\n"+pass+"\n";
                int code = RequestResponse.requestresponse(client, request, theview, themodel);
               
                if (code == -1) {
                    // If code = -1 Error occur then unregister
                    server.unregisterForCallback(stub);
                }

                //New Thread for handling the chat
                themodel.setName(theview.getUsername());
                themodel.setInsideProject(false);
                theview.setlabel(theview.getUsername());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    //Logout Event
    class Logout implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            int code = RequestResponse.requestresponse(client, "LOGOUT\n", theview, themodel);
            try {
                server.unregisterForCallback(stub);
            } catch (RemoteException e) {
               
                e.printStackTrace();
            }

        }

    }

    //ListProjects Event
    class ListProjects implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            int code = RequestResponse.requestresponse(client, "LISTPROJECTS\n", theview, themodel);

        }

    }

    //ListUsers Event
    class ListUsers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String[] rest = {};
            try {

                rest = new String[themodel.callbackob.listUsers().size()];
                rest = themodel.callbackob.listUsers().toArray(rest);
            } catch (RemoteException e) {

                e.printStackTrace();
            }
            theview.listProjects(rest, "LISTUSERS");

        }

    }

    //ListOnlineUsers Event
    class ListOnlineUsers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String[] rest = {};
            try {

                rest = new String[themodel.callbackob.listOnlineUsers().size()];
                rest = themodel.callbackob.listOnlineUsers().toArray(rest);
            } catch (RemoteException e) {

                e.printStackTrace();
            }
            theview.listProjects(rest, "LISTONLINEUSERS");

        }

    }

    
    class PrepareProject implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            theview.CreateProject();

        }

    }

    //EffectiveCreate Event
    class EffectiveCreate implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String progetto;

            progetto = theview.getProgetto();
            String request = "CREATEPROJECT\n" + progetto + "\n";
            int code = RequestResponse.requestresponse(client, request, theview, themodel);
            if (code == 1) {
                themodel.SetProjectName(progetto);
                theview.setProjectName(progetto);
                t = new Thread(new ChatTask(themodel.Getip(), theview));
                t.start();
                themodel.setInsideProject(true);
            }

        }

    }

    //EnterProject Event 
    class EnterProject implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String progetto;

            progetto = theview.getProgetto();
            themodel.SetProjectName(progetto);
            String request = "ENTER\n" + progetto + "\n";
            int code = RequestResponse.requestresponse(client, request, theview, themodel);

            // Se il codice = 1 allora posso avviare il thread
            if (code == 1) {
                themodel.setInsideProject(true);
                t = new Thread(new ChatTask(themodel.Getip(),theview));
                t.start();
            }

        }

    }

    //GoBack Event
    class goBack_prog implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            theview.goback(false);
            theview.setFramedim(300, 300);
            theview.setvisiblepanel2(true);

            // Interrompo il Thread
            if (themodel.getInsideProject()) {
                t.interrupt();
            }

        }

    }

    //Cancel Project
    class CancelProject implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name = themodel.getProjectName();
            String request = "CANCELPROJECT\n" + name;
            int code = RequestResponse.requestresponse(client, request, theview, themodel);
         

        }

    }

    //AddCardView Event
    class AddCard implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            theview.AddCard();

        }
    }

    // Add effective card Event
    class EffectiveAddCard implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            if (theview.getCardName().equals("") || theview.getDescription().equals(""))
                theview.error("Inserire qualcosa");
            else {
                String request = "ADDCARD\n" + theview.getCardName() + "\n" + theview.getDescription() + "\n"
                        + theview.getProgetto() + "\n";
                RequestResponse.requestresponse(client, request, theview, themodel);
            }
        }
    }

    //showCards Event
    class ShowCards implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String request = "SHOWCARDS\n" + themodel.getProjectName() + "\n";
            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    //ShoMembers Event 
    class ShowMembers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String request = "SHOWMEMBERS\n" + themodel.getProjectName() + "\n";
            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    //Move Card Event
    class MoveCard implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            theview.Movecard();

        }
    }

    //MoveCrad Effect
    class EffectiveMoveCard implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String request = "MOVECARD\n" + theview.GetMoveCardName() + "\n" + theview.GetFrom() + "\n"
                    + theview.GetTo() + "\n" + themodel.getProjectName() + "\n";
            System.out.println(request);
            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    // Evento ShoCardProps
    class ShowCardProperty implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            theview.ShowcardProps();

        }
    }

    // Evento ShoCardProps
    class EffecctiveShowCardProps implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String request = "SHOWCARD\n" + theview.getNameProps() + "\n" + themodel.getProjectName() + "\n";

            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    // Evento AddMember
    class AddMember implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            theview.AddMemberPanel();
        }
    }

    // TODO
    // Evento AddEffectiveMember
    class AddEffectiveMember implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            String Member = theview.GetNewMember();

            String request = "ADDMEMBER\n" + Member + "\n" + themodel.getProjectName() + "\n";
            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    // Evento GroupChat
    class GroupChat implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            theview.ShowChat();

        }
    }

    // Evento GroupChat
    class SendMessage implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

          
            String sendmess = themodel.getName()+": "+theview.GetSendBox();
            try{
                  
            InetAddress ia = InetAddress.getByName(themodel.Getip());
            byte [] Buffer = new byte [1024];
            Buffer = sendmess.getBytes();
            DatagramSocket sc = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(Buffer,Buffer.length,ia,6767);
            sc.send(dp);
            theview.RefreshSendBox();

            }
            catch (UnknownHostException e) {
                
                e.printStackTrace();
            }
            catch (SocketException e)
            {
                e.printStackTrace();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

      



    



   
    
}
