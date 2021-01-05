import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.google.gson.*;

public class GestioneRisposta {

    public static int ResponseHandler(String str, InitialView view, Model model,SocketChannel so) throws IOException
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
                        System.out.println(rest);
                        view.listProjects(gson.fromJson(rest, String[].class) , "LISTPROJECTS");
                   
                        return 1;

                        
                    }
                    else if(code.equals("300"))
                    {
                        view.setvisiblepanel1(true);
                        view.setvisiblepanel2(false);
                        so.close();
                        return -1;
                    }
                    else if (code.equals("402"))
                    {
                        //Errore Progetto gia esistente
                        view.error(str);
                        return -1;
                    }
                    else if(code.equals("203"))
                    {
                        //Progetto creato con successo
                        System.out.println(str);
                        view.setvisiblepanel4(false);
                        view.InsideAProject(strtok.nextToken(""));
                        return 1;

                    }
                    else if (code.equals("204")) 
                    {
                        //Progetto Rimosso con successo
                        view.goback(false);
                        view.setFramedim(300, 300);
                        view.setvisiblepanel2(true);
                     
                        return 1;
                    }
                    else if (code.equals("205"))
                    {
                        //ENTRATO
                        view.setvisiblepanel4(false);
                        view.InsideAProject(strtok.nextToken(""));
                        return 1;

                    }
                    else if(code.equals("206"))
                    {
                        //Lista Membri
                        String rest = strtok.nextToken("");
                        Gson gson = new Gson();
                        System.out.println(rest);
                        view.showList(gson.fromJson(rest, String[].class));
                        return 1;
                       
                    }
                    else if(code.equals("440"))
                    {
                        view.error(str);
                        view.goback(false);
                        view.setFramedim(300, 300);
                        view.setvisiblepanel2(true);
                        return 1;
                    }
                }
            
           return 0;
    
        }
    
}
