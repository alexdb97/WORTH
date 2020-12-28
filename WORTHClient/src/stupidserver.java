import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class stupidserver {

    public static void main(String args[]) throws IOException
    {
        ServerSocket s = new ServerSocket(5000);

        while (true)
        {
            Socket ss = s.accept();
                OutputStream  out = ss.getOutputStream();
                 String str = "Ciao Piccolo";
                out.write(str.getBytes());
            
        }
        
        


    
    }
    
}
