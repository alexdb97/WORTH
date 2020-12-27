
import java.io.File;

import java.io.IOException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;





public class main {

     static String MAIN_DIR_PATH =  "./MainDir";


    public static void main (String [] args ) 
    {
        try 
        {
        HashMap <String,Progetto> LisProject = new HashMap<String,Progetto>();

        FirstSetup(LisProject);

        //Servizio RMI
        //Creazione del servizio 
        RegisterImpl register = new RegisterImpl();
        //Esportazione dell'oggetto
        RegisterInterface stub = (RegisterInterface) UnicastRemoteObject.exportObject(register,0);
        //Creazione di un registry sulla porta prestabilita
        LocateRegistry.createRegistry(8080);
        Registry r = LocateRegistry.getRegistry(8080);
        //Pubblicazione del registry 
        r.rebind("REGISTER", stub);
        System.out.println("Servizio di registrazione Attivo!");
        
       
        listProjects(LisProject); 
        



      
        


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
    //FUNZIONE PER IL SETUP INIZIALE DEL SERVER 
    public static void FirstSetup(HashMap <String,Progetto> listp) throws IOException,ClassNotFoundException
    {
        File mainDir = new File(MAIN_DIR_PATH);
        if(mainDir.exists()==false)
        {
            mainDir.mkdir();
           
        }

       String [] list = mainDir.list();
       for (String str : list) {
            System.out.println(str);
            listp.put(str, new Progetto(str));
            
       }
   

    }

    public static void listProjects (HashMap <String,Progetto> list)
       {
          
       }
}

