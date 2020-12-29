import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
                    buffer.flip();

                    while(buffer.hasRemaining())
                        client.write(buffer);
                    
                    buffer = ByteBuffer.allocate(1024);
                    while(client.read(buffer)>0)
                        {
                            String str = new String(buffer.array()).trim();
                            System.out.println(str);
                        }
                    
                    
                    
                }
                try
                {
                Thread.sleep(10);
                }
                catch(InterruptedException ex)
                {
                    
                }
                
            }
            


            
            
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
         }




	}

    
    
}
