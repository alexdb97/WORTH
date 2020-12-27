
import java.io.File;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Serializers.Serializers;





public class main {

     static String MAIN_DIR_PATH =  "./MainDir";


    public static void main (String [] args ) 
    {
        try 
        {
        
        ConcurrentHashMap <String,String> Userbase= null;
        HashMap <String,Progetto> LisProject = new HashMap<String,Progetto>();

        Userbase = FirstSetup(LisProject,Userbase);
        
        
        //Servizio RMI
        //Creazione del servizio 
        RegisterImpl register = new RegisterImpl(Userbase);
        //Esportazione dell'oggetto
        RegisterInterface stub = (RegisterInterface) UnicastRemoteObject.exportObject(register,0);
        //Creazione di un registry sulla porta prestabilita
        LocateRegistry.createRegistry(8080);
        Registry r = LocateRegistry.getRegistry(8080);
        //Pubblicazione del registry 
        r.rebind("REGISTER", stub);
        System.out.println("Servizio di registrazione Attivo!");

       
        System.out.println(Userbase.get("Alessandro"));
       
     

       
       
        



      
        


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
    public static ConcurrentHashMap <String,String>  FirstSetup(HashMap <String,Progetto> listp,ConcurrentHashMap <String,String> Ubase) throws IOException,ClassNotFoundException
    {
        File mainDir = new File(MAIN_DIR_PATH);
        if(mainDir.exists()==false)
        {
            mainDir.mkdir();
           
        }

        //Prendo tutti i progetti e li carico in memoria
       String [] list = mainDir.list();
       for (String str : list) {
            System.out.println(str);
            listp.put(str, new Progetto(str));
        }

        //Prendo la UserBase e la carico in memoria
        String path = "./UserBase";
        File  filebase = new File(path);
        if(filebase.exists())
        {
            Ubase = (ConcurrentHashMap <String,String> ) Serializers.read(path);
            System.out.println("CArico in memoria"+Ubase.toString());
            return Ubase;
        }
        else
        {
            Ubase = new ConcurrentHashMap<String,String>();
            Ubase.put("Franca", "1234");
            Serializers.write(Ubase, path);
            return Ubase;
        }
   

    }

}

