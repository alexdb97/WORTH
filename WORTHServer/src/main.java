
import java.io.File;

import java.io.IOException;
import java.lang.reflect.Array;
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
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import Serializers.Serializers;
import com.google.gson.*;





public class main {

     static String MAIN_DIR_PATH =  "./MainDir";


    public static void main (String [] args ) 
    {
        try 
        {
        
        //Strutture dati che danno supporto al Server
        ConcurrentHashMap <String,String> KeysUserMap = new ConcurrentHashMap<String,String>();
        ConcurrentHashMap <String,String> Userbase= null;
        ConcurrentHashMap <String,Progetto> LisProject = new ConcurrentHashMap<String,Progetto>();


        Userbase = FirstSetup(LisProject,Userbase);
        

        //Servizio di callback per client
        ConcurrentHashMap <String,Boolean> LoginMap = new ConcurrentHashMap<String,Boolean>();
        //mi prendo i nomi che sono gia presenti nella base di dati
         Set <String> names = Userbase.keySet();
         for (String objt : names) {
             LoginMap.put(objt,false);
                
         }
        //faccio una semplice stampa per vedere se l'ho caricato

        //DA ELIMINARE 
        System.out.println(LoginMap.toString());



        //registrazione presso il registry
        ServerImpl server1 = new ServerImpl ();
        ServerInterface stub2 = (ServerInterface) UnicastRemoteObject.exportObject(server1,39000);
        String namereg = "Server";
        LocateRegistry.createRegistry(5000);
        
       

        //dovro metterlo anche qua il servizio di callback

        //Servizio RMI
        //Creazione del servizio 
        //passo anche la loginmap e il servizio di Callback
        RegisterImpl register = new RegisterImpl(Userbase,LoginMap,server1);
        //Esportazione dell'oggetto
        RegisterInterface stub = (RegisterInterface) UnicastRemoteObject.exportObject(register,0);
        //Creazione di un registry sulla porta prestabilita
        LocateRegistry.createRegistry(7070);
        Registry r = LocateRegistry.getRegistry(7070);
        //Pubblicazione del registry 
        r.rebind("REGISTER", stub);
        r.bind(namereg,stub2);

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
                            


                            //logout() LOGOUT FUNZIONA
                            if(nextok.equals("LOGOUT"))
                            {
                                ByteBuffer buff = ByteBuffer.allocate(1024);
                                buff.put("300 Logout".getBytes());
                                
                                System.out.print(FilterKey.filter(key.toString()));
                                String namelogout = KeysUserMap.get(FilterKey.filter(key.toString()));
                                KeysUserMap.remove(FilterKey.filter(key.toString()));
                                
                                System.out.println();
                                System.out.println(KeysUserMap);
                                //System.out.println(LoginMap);
                                System.out.println(namelogout);
                                LoginMap.replace(namelogout,true,false);
                                server1.update(LoginMap);
                               
                                key.attach(buff);
                                key.interestOps(SelectionKey.OP_WRITE);

                            }
                            //LA LOGIN DOVREBBE FUNZIONARE IL PROBLEMA E CHE DOBBIAMO  FARE RMI
                            //login(nickname,password)
                            else if(nextok.equals("LOGIN"))
                            {
                                if(strtok.countTokens()!=2)
                                {
                                    //ERRORE NEL PASSAGGIO DEI PARAMETRI dei parametri
                                    // definire che codive di errore
                            
        
                                        
                                         //ERRORE NEL LOGIN 
                                         ByteBuffer buff = ByteBuffer.allocate(1024);
                                         buff.put("401 Errore ".getBytes());
                                         System.out.println("401 Errore Parametri");
                                         key.attach(buff);
                                         key.interestOps(SelectionKey.OP_WRITE);
                                        
                                    
                                }
                                else
                                {

                                String name = strtok.nextToken();
                                String password = strtok.nextToken();
                                System.out.println(name+" "+password+"  "+Userbase.containsKey(name));

                                if(Userbase.containsKey(name))
                                {
                                    System.out.println(Userbase.get(name));

                                if(Userbase.get(name).equals(password)&&(LoginMap.get(name).equals(false)))
                                    {
                                        //LOGIN AVVENUTO CON SUCCESSO 
                                        System.out.println("SEI DENTRO AMICO");
                                        LoginMap.replace(name,false,true);
                                        KeysUserMap.putIfAbsent(FilterKey.filter(key.toString()),name);
                                        server1.update(LoginMap);
                                        System.out.println(KeysUserMap);
                                        //SEGNALO IL CORRETTO LOGIN
                                         ByteBuffer buff = ByteBuffer.allocate(1024);
                                         buff.put("201 Login".getBytes());
                                         key.attach(buff);
                                        key.interestOps(SelectionKey.OP_WRITE);

                                    }
                                else
                                    {
                                        //ERRORE NEL LOGIN 
                                        ByteBuffer buff = ByteBuffer.allocate(1024);
                                        buff.put("401 Errore Login Password o Utente gia' loggato".getBytes());
                                        System.out.println("401 Errore Login Password o Utente gia' loggato");
                                        key.attach(buff);
                                        key.interestOps(SelectionKey.OP_WRITE);
                                        
                                    }
                                }
                                else 
                                {
                                    //UTENTE NON ESISTE
                                    //ERRORE NEL LOGIN 
                                    ByteBuffer buff = ByteBuffer.allocate(1024);
                                    buff.put("401 Errore Login non esisti".getBytes());
                                    System.out.println("401 Errore Login non esisti");
                                    key.attach(buff);
                                    key.interestOps(SelectionKey.OP_WRITE);
                                
                                
                                } 
                            }      

                            }

                            //LISTPROJECTS
                            //listprojects()
                            else if (nextok.equals("LISTPROJECTS"))
                            {
                                Gson gson = new Gson();
                                Set<String> listprog = LisProject.keySet();
                                ByteBuffer buff = ByteBuffer.allocate(1024);
                                String send = gson.toJson(listprog);
                                buff.put( ("202 "+send).getBytes());
                                System.out.println(("202 "+gson.toJson(listprog)));
                                key.attach(buff);
                                key.interestOps(SelectionKey.OP_WRITE);
                              
                            }
                            //createProject()
                            else if(nextok.equals("CREATEPROJECT"))
                            {
                                

                                String projectname = strtok.nextToken("");
                                System.out.println(projectname);
                              

                                if(LisProject.containsKey(projectname)==false)
                                    {
                                    Progetto p = new Progetto(projectname);
                                    LisProject.put(projectname, p);

                                    ByteBuffer buf = ByteBuffer.allocate(1024);
                                    String str = " 203 OK OPERAZIONE EFFETTUATA CON SUCCESSO";
                                    buf.put(str.getBytes());
                                    key.attach(buf);
                                    key.interestOps(SelectionKey.OP_WRITE);
                                    }
                                else 
                                    {
                                        //ERRORE PROGETTO GIA ESISTENTE
                                        ByteBuffer buf = ByteBuffer.allocate(1024);
                                        String str = " 402 ERR PROGETTO GIA' ESISTENTE";
                                        buf.put(str.getBytes());
                                        key.attach(buf);
                                        key.interestOps(SelectionKey.OP_WRITE);
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
                    key.interestOps(SelectionKey.OP_READ);


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
            //DA ELIINARE
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

