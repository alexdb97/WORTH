import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.StringTokenizer;

public class GestioneRisposta {

    public static int ResponseHandler(String str, InitialView view, SocketChannel so) throws IOException
    {
        StringTokenizer strtok = new StringTokenizer(str," ");
        
           
                 if(strtok.hasMoreElements())
                 {
                    String code = strtok.nextToken();
                    if(code.equals("401"))
                    {
                        //errore nel login e nel passaggio dei parameti
                        view.error(str);
                        so.close();
                        return -1;
                    }
                    else if (code.equals("201"))
                    {
                        //login effettuata con sucesso
                        view.setvisiblepanel1(false);
                        view.setvisiblepanel2(true);
                        
                    }
                    else if(code.equals("202"))
                    {
                        //lista dei progetti
                        
                    }
                }
            
           return 0;
    
        }
    
}
