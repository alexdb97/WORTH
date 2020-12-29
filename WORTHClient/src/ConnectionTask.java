import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ConnectionTask  implements Runnable{

    InitialView  view;

    public ConnectionTask (InitialView v)
    {
        view = v;
    }
    
    
    
    @Override
	public void run() {
        
         try
         {
            SocketAddress address = new InetSocketAddress("localhost", 6060);
            SocketChannel client = SocketChannel.open(address);
            client.configureBlocking(false);
            Selector selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);

            while(selector.isOpen())
            {
                selector.select();
                Set <SelectionKey> readyKeys = selector.selectedKeys();
                Iterator <SelectionKey> iterator = readyKeys.iterator();
                while(iterator.hasNext())
                {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isConnectable())
                    {

                    }
                    else if(key.isReadable())
                    {

                    }
                    else if(key.isWritable());
                    {
                        
                    }
                }
            }

            
            
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
         }




	}

    
    
}
