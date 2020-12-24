import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import Rmi.*;

public class ServerMain {

     static String MAIN_DIR_PATH =  "./MainDir";

    public static void main (String [] args) 
    {
        try 
        {
        ArrayList <Progetto> LisProject= new ArrayList<Progetto>();
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
        r.rebind("EGISTER", stub);
      
        
      
        


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
    public static void FirstSetup(ArrayList <Progetto> listp) throws IOException,ClassNotFoundException
    {
        File mainDir = new File(MAIN_DIR_PATH);
        if(mainDir.exists()==false)
        {
            mainDir.mkdir();
           
        }

       String [] list = mainDir.list();
       for (String str : list) {
            
            listp.add(new Progetto(str));
            
       }
    }
}

