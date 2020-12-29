
import java.io.File;

import java.io.IOException;
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
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import Serializers.Serializers;






public class main {

     static String MAIN_DIR_PATH =  "./MainDir";


    public static void main (String [] args ) 
    {
        try 
        {
        
        ConcurrentHashMap <String,String> Userbase= null;
        ConcurrentHashMap <String,Progetto> LisProject = new ConcurrentHashMap<String,Progetto>();

        Userbase = FirstSetup(LisProject,Userbase);
        
        
        //Servizio RMI
        //Creazione del servizio 
        RegisterImpl register = new RegisterImpl(Userbase);
        //Esportazione dell'oggetto
        RegisterInterface stub = (RegisterInterface) UnicastRemoteObject.exportObject(register,0);
        //Creazione di un registry sulla porta prestabilita
        LocateRegistry.createRegistry(7070);
        Registry r = LocateRegistry.getRegistry(7070);
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
                    System.out.println("Accepted connection from");
                    client.configureBlocking(false);
                    SelectionKey key2 = client.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    //Attach the bytebuffer ma io faro una classe apposita Attachment
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    key2.attach(buffer);

                }
                else if(key.isReadable())
                {
                    
                    //operazioni di lettura
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer =  (ByteBuffer) key.attachment();
                   
                    int len = client.read(buffer);

                    if(len>=0)
                    {
                            String command = new String(buffer.array()).trim();
                            System.out.println(command);

                            StringTokenizer strtok = new StringTokenizer(command," ");
                            String nextok="";
                            
                            if(strtok.hasMoreTokens())
                                nextok = strtok.nextToken();
                            


                            //logout()
                            if(nextok.equals("LOGOUT"))
                            {
                                //chiudo la connessione TCP aperta
                                key.channel().close();
                            }
                            //login(nickname,password)
                            else if(nextok.equals("LOGIN"))
                            {
                                if(strtok.countTokens()!=2)
                                {
                                    //ERRORE NEL PASSAGGIO DEI PARAMETRI dei parametri
                                    // definire che codive di errore 
                                    key.channel().close();
                                    break;
                                    
                                }

                                String name = strtok.nextToken();
                                String password = strtok.nextToken();
                                System.out.println(name+" "+password+"  "+Userbase.containsKey(name));

                                if(Userbase.containsKey(name))
                                {
                                    System.out.println(Userbase.get(name));

                                if(Userbase.get(name).equals(password))
                                    {
                                        //LOGIN AVVENUTO CON SUCCESSO 
                                        System.out.println("SEI DENTRO AMICO");
                                    }
                                else
                                    {
                                        //ERRORE NEL LOGIN 
                                        key.channel().close();
                                        break;
                                    }
                                }
                                else 
                                {
                                    //UTENTE NON ESISTE
                                    key.channel().close();;
                                }       

                            }
                            //listprojects()
                            else if (nextok.equals("LISTPROJECTS"))
                            {
                                Set<String> listprog = LisProject.keySet();
                                System.out.println(listprog.toString());
                                buffer.put((listprog.toString()).getBytes());
                                key.attach(buffer);
                            }
                            //createProject()
                            else if(nextok.equals("CREATEPROJECT"))
                            {
                                if(strtok.countTokens()!=1)
                                {
                                    //errore nel passaggio dei parametri errore
                                    // non chiudo la connessione però
                                    break;
                                }

                                String projectname = strtok.nextToken();
                          
                              

                                if(LisProject.containsKey(projectname)==false)
                                    {
                                    Progetto p = new Progetto(projectname);
                                    LisProject.put(projectname, p);

                                    ByteBuffer buf = ByteBuffer.allocate(1024);
                                    String str = " 200 OK OPERAZIONE EFFETTUATA CON SUCCESSO";
                                    buf.put(str.getBytes());
                                    key.attach(buf);
                                    }
                                else 
                                    {
                                        //ERRORE PROGETTO GIA ESISTENTE
                                        ByteBuffer buf = ByteBuffer.allocate(1024);
                                        String str = " 401 ERR PROGETTO GIA ESISTENTE";
                                        buf.put(str.getBytes());
                                        key.attach(buf);

                                        break;
                                    }
                            }
                            else if(nextok.equals("SHOWMEMBERS"))
                            {
                                
                            }

                            
                            //addMember member
                            //addScheda scheda descrizione
                            //changeScheda from to
                            //shocards 
                            //cardhistory projectname card
                            //cancelproject name
                            
                                
                            

                    }
                    //Chiusura improvvisa della connessione nella read restituisce -1
                    else 
                    {
                        key.channel().close();
                    }
                    
                    

                    

                }
                else if(key.isWritable())
                {
                    //operazioni di scrittura
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer =  (ByteBuffer) key.attachment();
                    buffer.flip();

                    if(buffer.hasRemaining())  
                        client.write(buffer);
                    
                    key.attach(ByteBuffer.allocate(1024));


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
    public static ConcurrentHashMap <String,String>  FirstSetup(ConcurrentHashMap <String,Progetto> listp,ConcurrentHashMap <String,String> Ubase) throws IOException,ClassNotFoundException
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

