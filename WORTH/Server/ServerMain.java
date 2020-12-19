import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMain {

     static String MAIN_DIR_PATH =  "../MainDir";

    public static void main (String [] args)
    {
        FirstSetup();


        HashMap <String,Utente> UserBase = new HashMap<String,Utente>();

        Utente u1 = new Utente ("Alessandro","1234");
        UserBase.putIfAbsent("Alessandro",u1);
       // System.out.println(UserBase);
       
        try
        {
         HashMap <String,Utente> b2 = new HashMap<String,Utente>(UserBase);
        // System.out.println(b2);

        
         UserBase.get("Alessandro").SetOnline(true);
         /*
         System.out.println(UserBase.get("Alessandro").GetOnlineState());
         System.out.println(b2.get("Alessandro").GetOnlineState());
         System.out.println(b2.get("Alessandro").GetPasword());
         */
         Progetto p = new Progetto("RESISTO");
         p.insertScheda("Splash_screen", "Creare una spashscreen con 3 bottoni e una label");
         
         p.insertScheda("ArtWork", "Ciaone_Proprio");
         p.insertScheda("ArtHome","Descrizione");

         //MOve from one to another
         p.Move_ToDo_InProgress("Splash_screen");
         p.Move_InProgress_Done("Splash_screen");
         

         p.iterate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public static void FirstSetup()
    {
        File mainDir = new File(MAIN_DIR_PATH);
        if(mainDir.exists()==false)
        {
            mainDir.mkdir();
        }
    }
}

