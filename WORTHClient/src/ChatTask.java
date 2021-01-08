import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


public class ChatTask implements Runnable {

    String groupip;
    InitialView view;

    public ChatTask(String ip, InitialView theview){
        this.groupip = ip;
        view = theview;

    }

    @Override
    public void run() {

       

        try {
        
            //Initializing Multicast Socket
            MulticastSocket ms = new MulticastSocket(6767);
            InetAddress ia = InetAddress.getByName(groupip);
            System.out.println(groupip);
            ms.joinGroup(ia);
            

            
           

           
            while(!Thread.currentThread().isInterrupted()) 
            {
                
              try
              {
               byte [] buffer = new byte [1024];
               DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
               //Timeout should set non blocking
               ms.setSoTimeout(1000);
               ms.receive(dp);
               byte [] data= new byte[dp.getLength()];
               System.arraycopy(dp.getData(), dp.getOffset(), data, 0, dp.getLength());
               String s = new String (data,StandardCharsets.UTF_8);

               //Safe Routine for  terminating the Thread operation if
               // someone cancel the project
               if(s.equals("CANCELLED"))
                    {
                        view.goback(false);
                        view.setFramedim(300, 300);
                        view.setvisiblepanel2(true);
                        break;
                    }
               System.out.print(s);
               view.SetReceiveBox(s);
              }
              catch(SocketTimeoutException ex)
              {
      
              }
      
             
            }
        
      
            ms.close();
           
         



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
