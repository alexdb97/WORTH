import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.StringTokenizer;
import com.google.gson.*;

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
                        return 1;
                        
                    }
                    else if(code.equals("202"))
                    {
                        String rest = strtok.nextToken("");
                        Gson gson = new Gson();
                        System.out.println(gson.fromJson(rest, String[].class));
                        view.listProjects(gson.fromJson(rest,String[].class));
                        return 1;

                        
                    }
                    else if(code.equals("300"))
                    {
                        view.setvisiblepanel1(true);
                        view.setvisiblepanel2(false);
                        so.close();
                        return -1;
                    }
                }
            
           return 0;
    
        }
    
}
