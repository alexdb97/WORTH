import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;


public class ChatTask implements Runnable {

    String groupip;

    public ChatTask(String ip, InitialView theview){
        this.groupip = ip;

    }

    @Override
    public void run() {

        // Metodo Run

        try {
        
            MulticastSocket ms = new MulticastSocket(6767);
            InetAddress ia = InetAddress.getByName(groupip);
            System.out.println(groupip);
            ms.joinGroup(ia);
            

            
            System.out.println("CiaoCORE");

           
            while(!Thread.currentThread().isInterrupted()) 
            {
                
              try
              {
               byte [] buffer = new byte [1024];
               DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
               //Timeout should set non blocking
               ms.setSoTimeout(200);
               ms.receive(dp);
               String s = new String (dp.getData());
               System.out.println(s);
              }
              catch(SocketTimeoutException ex)
              {
      
              }
      
             
            }
        
      
            ms.close();
            System.out.println(" MARIA IO ESCO");
         



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
