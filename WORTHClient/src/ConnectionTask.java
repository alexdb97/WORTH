import java.io.IOException;
import java.net.InetSocketAddress;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import java.nio.channels.SocketChannel;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class ConnectionTask implements Runnable {

    InitialView view;
    ArrayList<Event> evlist;
    Model model;
  

    public ConnectionTask(InitialView v, ArrayList<Event> list, Model m) {
        model = m;
        view = v;
        evlist = list;
     
    }

    @Override
    public void run() {

        try {

            
            
                // Ci siamo registrati al servizio di Callback
                System.out.println("Cerco il server");
                Registry registry = LocateRegistry.getRegistry(7070);
                String name1 = "Server";
                ServerInterface server = (ServerInterface) registry.lookup(name1);
                // si registra la callback
                System.out.println("Registering For Callback");
                NotifyEventInterface callbackObj = new NotifyImpl();
                model.setcallback(callbackObj);
                NotifyEventInterface stub = (NotifyEventInterface) UnicastRemoteObject.exportObject(callbackObj, 0);
                server.registerForCallback(stub);



            SocketAddress address = new InetSocketAddress("localhost", 6060);
            SocketChannel client = SocketChannel.open(address);
            client.configureBlocking(false);

            while (true) {
 
                try
                {
                Thread.sleep(10);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }

                if (evlist.isEmpty() == false) {

                    Event ev = evlist.remove(0);
                    String operation = ev.getOperation();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(operation.getBytes());
                    if (ev.getParam1() != null)
                        buffer.put((" " + ev.getParam1()).getBytes());
                    if (ev.getParam2() != null)
                        buffer.put((" " + ev.getParam2()).getBytes());

                   

                    System.out.println(operation);
                    buffer.flip();

                    // RICHIESTA
                    while (buffer.hasRemaining())
                        client.write(buffer);
                    

                    buffer.clear();
                 
                   


                    // RISPOSTA
                    ByteBuffer bufferrisposta = ByteBuffer.allocate(1024);
                    String response = "";
                   

                    while (( client.read(bufferrisposta))>=0) {
                   
                        response = new String(bufferrisposta.array()).trim();
                        System.out.println(response);
                        int code = GestioneRisposta.ResponseHandler(response, view, client);
                        // se il codice restituito è -1
                        // significa che si è chiusa una connessione e quindi si esce dal thread
                        // Mi devo anche deregistrare dalla RMI callback



                        if (code == -1)
                            {
                                server.unregisterForCallback(stub);
                                return;
                            }
                        else if(code==1)
                        {
                            break;
                        }
                        else
                        {

                        }

                    }
                 }

                
                
                
               
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NotBoundException e) {
            
            e.printStackTrace();
        }
       




	}

    
    
}
