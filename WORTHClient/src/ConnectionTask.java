import java.io.IOException;
import java.net.InetSocketAddress;

import java.net.SocketAddress;
import java.nio.ByteBuffer;


import java.nio.channels.SocketChannel;
import java.util.ArrayList;


public class ConnectionTask  implements Runnable{

    InitialView  view;
    ArrayList <Event> evlist;

    public ConnectionTask (InitialView v, ArrayList <Event> list)
    {
        view = v;
        evlist = list;
    }
    
    
    
    @Override
	public void run() {
        
         try
         {
            SocketAddress address = new InetSocketAddress("localhost", 6060);
            SocketChannel client = SocketChannel.open(address);
            client.configureBlocking(false);

            
            while(true)
            {
               
               
                if(evlist.isEmpty()==false)
                {

                    Event ev = evlist.remove(0);
                    String operation = ev.getOperation();
                    System.out.println(operation);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(operation.getBytes());
                    if(ev.getParam1()!=null)
                        buffer.put((" "+ev.getParam1()).getBytes());
                    if(ev.getParam2()!=null)
                        buffer.put((" "+ev.getParam2()).getBytes());
                    
                
                    buffer.flip();

                    //RICHIESTA
                    while(buffer.hasRemaining())
                        client.write(buffer);
                    
                    try
                    {
                        Thread.currentThread().sleep(50);
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                 

                       
                    //RISPOSTA
                    buffer = ByteBuffer.allocate(1024);
                    String response="";
                    int len;
                 
                   
                    while((len =client.read(buffer))>0)
                        {
                            System.out.println(len);
                            response = new String(buffer.array()).trim();
                            System.out.println(response);
                            int code = GestioneRisposta.ResponseHandler(response, view, client);
                            // se il codice restituito è -1 
                            //significa che si è chiusa una connessione e quindi si esce dal thread    
                            if(code == -1)
                                    return;
                        }
                   
                    
                }
              try 
              {
                Thread.currentThread().sleep(40);
              }
              catch(InterruptedException ex)
              {
                  ex.printStackTrace();
              }
            }
            


            
            
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
         }




	}

    
    
}
