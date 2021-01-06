import java.awt.event.ActionListener; // seems to be missing.

import java.net.InetSocketAddress;
import java.net.SocketAddress;

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
    // Thread t;
    private SocketAddress address = new InetSocketAddress("localhost", 6060);
    SocketChannel client;
    ServerInterface server;

    // RMICALLBACK
    Registry registry;
    String name1 = "Server";
    NotifyEventInterface callbackObj;
    NotifyEventInterface stub;



    public Controller(InitialView view, Model mod) {
        this.themodel = mod;
        this.theview = view;
       




        // Routine per chiudere bene la connessione
        theview.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(frame, "Do you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {

                if(client!=null)
                    if (client.isConnected())
                    {
                       RequestResponse.requestresponse(client,"LOGOUT", theview, themodel);
                            try {
                                server.unregisterForCallback(stub);
                            } catch (RemoteException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                    }
                            
                      
                     try
                     {
                     //Aspetto 1/2 secondo per dare il tempo di mandare il messaggio di QUI
                     Thread.currentThread().sleep(1000);
                     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                     }
                     catch(InterruptedException ex)
                     {

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
        this.theview.EffectiveAddCard(new EffectiveAddCard());
        this.theview.MoveCard(new MoveCard());
        this.theview.EffectiveMoveCard(new EffectiveMoveCard());
        this.theview.GroupChat(new GroupChat());
        this.theview.AddMember(new AddMember());

    }

    // Evento che interagisce con la registrazione
    class RegisterLis implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name, pass;

            try {

                name = theview.getUsername();
                pass = theview.getPassword();
                if (name != null || pass != null)

                    if (themodel.sendData(name, pass) == 400)
                        theview.error("400 Utente già registrato");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    // evento che interagisce con il longin
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

                String request = "LOGIN "+name+" "+pass;
                int code = RequestResponse.requestresponse(client, request, theview, themodel);
                System.out.println(code);
                if(code == -1)
                {
                    //Errore allora mi disconnetto e mi deregistro
                    server.unregisterForCallback(stub);               
                }
                
                // spawno un thread per gestire la connessione
                themodel.setName(theview.getUsername());
                theview.setlabel(theview.getUsername());


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    // evento logout
  
    class Logout implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
           
            int code = RequestResponse.requestresponse(client,"LOGOUT", theview, themodel);
            try {
                server.unregisterForCallback(stub);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
        }

    }

    // evento lista progetti
    class ListProjects implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
          
            int code = RequestResponse.requestresponse(client,"LISTPROJECTS", theview, themodel);
            
        }

    }

    // evento list users
    class ListUsers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
         
          
           
            String[] rest={};
           try {
              
            rest = new String[themodel.callbackob.listUsers().size()];
            rest = themodel.callbackob.listUsers().toArray(rest);
            } catch (RemoteException e) {
                
                e.printStackTrace();
            }
            theview.listProjects(rest, "LISTUSERS");
            
               
            
        }

    }

      // evento list online users
      class ListOnlineUsers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
         
          
           
            String[] rest={};
           try {
              
            rest = new String[themodel.callbackob.listOnlineUsers().size()];
            rest = themodel.callbackob.listOnlineUsers().toArray(rest);
            } catch (RemoteException e) {
                
                e.printStackTrace();
            }
            theview.listProjects(rest, "LISTONLINEUSERS");
            
               
            
        }

    }

    //Prepara alla creazione del Progetto
    class PrepareProject implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
           
            theview.CreateProject();

        }

    }

    //Evento creazione effettiva del progetto
    class EffectiveCreate implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
           
           String progetto;

           progetto = theview.getProgetto();
           String request= "CREATEPROJECT "+progetto;
           int code = RequestResponse.requestresponse(client,request, theview, themodel);
           if(code==1)
            {
                themodel.SetProjectName(progetto);
                theview.setProjectName(progetto);
            }
          
        }

    }

      //Evento creazione effettiva del progetto
      class EnterProject implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
           
           String progetto;

           progetto = theview.getProgetto();
           themodel.SetProjectName(progetto);
           String request = "ENTER "+progetto;
           int code = RequestResponse.requestresponse(client, request, theview, themodel);
     
        }

    }
    
    
    


    //evento vai indietro
    class goBack_prog implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
           
            theview.goback(false);
            theview.setFramedim(300, 300);
            theview.setvisiblepanel2(true);


        }

    }

    //evento CancelProject
    class CancelProject implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
            String name = themodel.getProjectName();
            String request = "CANCELPROJECT "+name;
            int code = RequestResponse.requestresponse(client,request, theview, themodel);
           

           
            
        }

    }

     //evento AddCardView
     class AddCard implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
          theview.AddCard();

        }
    }

    //Add effective card
    class EffectiveAddCard implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
            if(theview.getCardName().equals("") || theview.getDescription().equals(""))
                theview.error("Inserire qualcosa");
            else
            {
            String request ="ADDCARD "+ theview.getCardName()+"\n"+ theview.getDescription()+"\n"+theview.getProgetto()+"\n";
            RequestResponse.requestresponse(client, request, theview, themodel);
            }
        }
    }


    //Evento showCards
    class ShowCards implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
        
            String request = "SHOWCARDS "+themodel.getProjectName();
            RequestResponse.requestresponse(client, request, theview, themodel);

        }
    }

    //Evento ShoMembers
    class ShowMembers implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
            String request = "SHOWMEMBERS "+themodel.getProjectName();
            RequestResponse.requestresponse(client,request, theview, themodel);
           

        }
    }

    //Evento move card 
    class MoveCard implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
         theview.Movecard();

        }
    }
      //Evento move card 
      class  EffectiveMoveCard implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
         

        }
    }
   

    //Evento ShoCardProps
    class ShowCardProperty implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
        
            theview.ShowcardProps();

        }
    }


     //Evento GroupChat
     class AddMember implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
        
           theview.AddMemberPanel();
        }
    }



    //Evento GroupChat
    class GroupChat implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
        
           theview.ShowChat();

        }
    }



   




    
    
}
