import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatTask implements Runnable {

    String groupip;

    public ChatTask(String ip, InitialView theview){
        this.groupip = ip;

    }

    @Override
    public void run() {

        // Metodo Run

        try {
        
            InetAddress ia = InetAddress.getByName(groupip);
            MulticastSocket ms = new MulticastSocket(6767);
            ms.joinGroup(ia, );





        }
        catch (UnknownHostException e) {
           
            e.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
    
    
    
}
