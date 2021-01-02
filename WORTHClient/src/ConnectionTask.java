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

            
            //Ci siamo registrati al servizio di Callback
            System.out.println("Cerco il server");
            Registry registry = LocateRegistry.getRegistry(7070);
            String name1 = "Server";
            ServerInterface server = (ServerInterface) registry.lookup(name1);
            // si registra la callback
            System.out.println("Registering For Callback");
            NotifyEventInterface callbackObj = new NotifyImpl();
          
            NotifyEventInterface stub = (NotifyEventInterface) UnicastRemoteObject.exportObject(callbackObj,0);
            server.registerForCallback(stub);



            SocketAddress address = new InetSocketAddress("localhost", 6060);
            SocketChannel client = SocketChannel.open(address);
            client.configureBlocking(false);

            while (true) {

                if (evlist.isEmpty() == false) {

                    Event ev = evlist.remove(0);
                    String operation = ev.getOperation();
                    System.out.println(operation);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(operation.getBytes());
                    if (ev.getParam1() != null)
                        buffer.put((" " + ev.getParam1()).getBytes());
                    if (ev.getParam2() != null)
                        buffer.put((" " + ev.getParam2()).getBytes());

                    buffer.flip();

                    // RICHIESTA
                    while (buffer.hasRemaining())
                        client.write(buffer);

                    try {
                        Thread.currentThread().sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // RISPOSTA
                    buffer = ByteBuffer.allocate(1024);
                    String response = "";
                    int len;

                    while ((len = client.read(buffer)) > 0) {
                        System.out.println(len);
                        response = new String(buffer.array()).trim();
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
                    }

                 System.out.println(callbackObj.listUsers().toString());
                   



                }
                try {
                    Thread.currentThread().sleep(40);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (NotBoundException e) {
         
            e.printStackTrace();
        }




	}

    
    
}
