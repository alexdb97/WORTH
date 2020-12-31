import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.StringTokenizer;

public class GestioneRisposta {

    public static int ResponseHandler(String str, InitialView view, SocketChannel so) throws IOException
    {
        StringTokenizer strtok = new StringTokenizer(str," ");
        
           
                 if(strtok.hasMoreElements())
                 {
                    String errcode = strtok.nextToken();
                    if(errcode.equals("401"))
                    {
                        //errore nel login e nel passaggio dei parameti
                        view.error(str);
                        so.close();
                        return -1;
                    }
                }
            
           return 0;
    
        }
    
}
