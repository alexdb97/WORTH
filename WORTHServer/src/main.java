
import java.io.File;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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

       

        //Punto di accesso del server Selettore con

        ServerSocketChannel ss = ServerSocketChannel.open();
        SocketAddress socadd = new InetSocketAddress(6060);
        ss.bind(socadd);
        ss.configureBlocking(false);

        Selector selector = Selector.open();
        selector.open();
        ss.register(selector, SelectionKey.OP_ACCEPT);

        while(true)
        {
            selector.select();
            Set <SelectionKey> readyKeys = selector.selectedKeys();
            Iterator <SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                iterator.remove();
                //rimuovi la chiave dal selected set, ma non dal registered set
                //si accettano nuove connessioni e si registrano sul selettore
                if(key.isAcceptable())
                {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    System.out.println("Accepted connection from"+client);
                    client.configureBlocking(false);
                    SelectionKey key2 = client.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    //Attach the bytebuffer ma io faro una classe apposita Attachment
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    key2.attach(buffer);

                }
                else if(key.isReadable())
                {
                    //operazioni di lettura

                    ByteBuffer buff =(ByteBuffer)  key.attachment();
                    



                }
                else if(key.isWritable())
                {
                    //operazioni di scrittura
                }
            }

        }

        
     

       
       
        



      
        


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
            
            Serializers.write(Ubase, path);
            return Ubase;
        }
   

    }

}

