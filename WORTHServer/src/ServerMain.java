
import java.io.File;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;


import javax.naming.NameNotFoundException;


import Utility.Utility;
import jdk.jshell.execution.Util;

import com.google.gson.*;




@SuppressWarnings("unchecked")
public class ServerMain {

     static String MAIN_DIR_PATH =  "./MainDir";


    public static void main (String [] args ) 
    {
        try 
        {
        
        //Strutture dati che danno supporto al Server
        ConcurrentHashMap <String,String> KeysUserMap = new ConcurrentHashMap<String,String>();
        ConcurrentHashMap <String,String> Userbase= null;
        ConcurrentHashMap <String,Progetto> LisProject = new ConcurrentHashMap<String,Progetto>();

        //Generatore di IP
        GeneratorIp ipGenerator = new GeneratorIp();

        
        
        Userbase = FirstSetup(LisProject,Userbase,ipGenerator);
        

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

        System.out.println("Register service Active!");

     


        //selector

        ServerSocketChannel ss = ServerSocketChannel.open();
        SocketAddress socadd = new InetSocketAddress(6060);
        ss.bind(socadd);
        ss.configureBlocking(false);

        Selector selector = Selector.open();
    
        ss.register(selector, SelectionKey.OP_ACCEPT);

        

        while(true)
        {
            selector.select();
            Set <SelectionKey> readyKeys = selector.selectedKeys();
           
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                iterator.remove();
               
                
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
                    
                   
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer =  (ByteBuffer) key.attachment();

                    int len = client.read(buffer);

                    if(len>=0)
                    {
                            String command = new String(buffer.array()).trim();
                            System.out.println(command);

                            StringTokenizer strtok = new StringTokenizer(command,"\n");
                            String nextok="";
                            
                            if(strtok.hasMoreTokens())
                                nextok = strtok.nextToken();
                            


                            //logout() 
                            if(nextok.equals("LOGOUT"))
                            {
                                
                                
                                String namelogout = KeysUserMap.get(Utility.filter(key.toString()));
                                KeysUserMap.remove(Utility.filter(key.toString()));
                                LoginMap.replace(namelogout,true,false);
                                server1.update(LoginMap);
                                Utility.sendtoclient(300,"Logout",key);
                            

                            }
                         
                            //login(nickname,password)
                            else if(nextok.equals("LOGIN"))
                            {
                                if(strtok.countTokens()!=2)
                                {
                                    
                                    Utility.sendtoclient(401,"Error, Arguments are wrong", key);     
                                }
                                else
                                {

                                String name = strtok.nextToken();
                                String password = strtok.nextToken();
                                System.out.println(name+" "+password+"  "+Userbase.containsKey(name));

                                if(Userbase.containsKey(name))
                                {
                                    System.out.println(Userbase.get(name));

                                //checking of the password and login 
                                if(Userbase.get(name).equals(password)&&(LoginMap.get(name).equals(false)))
                                    {
                                        
                                      
                                        LoginMap.replace(name,false,true);
                                        KeysUserMap.putIfAbsent(Utility.filter(key.toString()),name);
                                        server1.update(LoginMap);
                                        Utility.sendtoclient(201,"Login", key);

                                    }
                                else
                                    {
                                        //Wrong password or The User is already logged
                                        Utility.sendtoclient(401,"Wrong password or The User is already logged", key);
                                        
                                    }
                                }
                                else 
                                {
                                    //The User does not exist
                                    Utility.sendtoclient(401,"The User does not exist ", key);
                                } 
                             }      
                            }

                            //LISTPROJECTS
                            //listprojects()
                            else if (nextok.equals("LISTPROJECTS"))
                            {
                                //sending through GSON
                                Gson gson = new Gson();
                                Set<String> listprog = LisProject.keySet();
                                String send = gson.toJson(listprog);
                                Utility.sendtoclient(202,send,key);
                            }
                            //createProject()
                            else if(nextok.equals("CREATEPROJECT"))
                            {
                                String projectname; 
                                if(strtok.hasMoreTokens())
                                {
                                projectname = strtok.nextToken();
                               
                                }
                                else{
                                    
                                     Utility.sendtoclient(404,"Error, Arguments are wrong", key);
                                     break;
                                }

                                System.out.println(projectname);
                              

                                if(LisProject.containsKey(projectname)==false)
                                    {
                                    Progetto p = new Progetto(projectname,ipGenerator.GetnextIp());
                                    LisProject.put(projectname, p);
                                    //Aggiungo il proprietario come mebro
                                    System.out.println(KeysUserMap);
                                   
                                    String name = KeysUserMap.get(Utility.filter(key.toString()));
                                    System.out.println(name);
                                    p.AddMember(name,true);
                                    Utility.sendtoclient(203,projectname+"\n"+p.GetIpGroup()+"\n",key);
                                    }
                                else 
                                    {
                                        //ERRORE PROGETTO GIA ESISTENTE
                                        Utility.sendtoclient(404,"Errore Progetto gia' esistente ", key);
                                        break;
                                    }
                            }
                            //ENTER PROJECT
                            else if (nextok.equals("ENTER"))
                            {
                                String projectname=""; 

                                if(strtok.hasMoreElements())
                                {   
                                    projectname = strtok.nextToken();
                                

                                    System.out.println(projectname);
                                    if(LisProject.containsKey(projectname)){
                                        Progetto pi = LisProject.get(projectname);
                                            if(pi.ContainsMember( KeysUserMap.get(Utility.filter(key.toString())))){
                                                
                                                Utility.sendtoclient(208,projectname+"\n"+pi.GetIpGroup()+"\n",key);
                                            }
                                            else{
                                                //NOTMEMBER
                                                Utility.sendtoclient(404,"Errore non sei membro del progetto", key);
                                            }
                                    }
                                    else {
                                        //PROJECT DOESN'T EXIST
                                        Utility.sendtoclient(404,"Errore il progetto non esiste", key);
                                    }
                                }
                                else 
                                {
                                    //ERRORE NEI TOKENS
                                    Utility.sendtoclient(404,"Errore nel passaggio dei parametri", key);
                                }


                            }
                            //ShowMembers()
                            else if(nextok.equals("SHOWMEMBERS"))
                            {

                                String projectname=""; 

                                if(strtok.hasMoreElements())
                                {   
                                    projectname = strtok.nextToken();
                                   
                                }

                                //Gia ho controllato che fosse membro e che il progetto esista quindi vado
                                Gson gson = new Gson ();
                                Progetto pi = LisProject.get(projectname);
                                String response =gson.toJson(pi.GetMembers());
                                System.out.println(response);
                                Utility.sendtoclient(206, response, key);
                                
                            }
                            else if(nextok.equals("CANCELPROJECT"))
                            {
                                String projectname=""; 
                                
                                if(strtok.hasMoreTokens())
                                {
                                projectname = strtok.nextToken();
                              
                              

                                //FACCIO I CONTROLLI OPPORTUNEI
                                if(LisProject.containsKey(projectname))
                                {
                                Progetto p = LisProject.get(projectname);
                                    
                                    if(p.CheckCardsForDelete())
                                    {
                                    //Questo per il riuso degli ip
                                     String ip = p.RemoveProgetto();
                                     ipGenerator.pushIp(ip);
                                     LisProject.remove(projectname);
                                     Utility.sendtoclient(205,"OK ", key);
                                     Utility.sendtoGroup("CANCELLED",p.GetIpGroup());
                                     System.out.println(ip);
                                      
                                    }
                                    else 
                                    {
                                        Utility.sendtoclient(404,"Not all cards are in  DONE State ", key);
                                    } 
                                }
                                else
                                {
                                   
                                    Utility.sendtoclient(403,"The Project Does not exist", key);
                                }

                            }
                        }
                            //addScheda scheda descrizione
                            //da gestire gli errori
                           else if (nextok.equals("ADDCARD"))
                           {
                                String cardname=""; 
                                String descrizione="";
                                String projectname="";
                             
                                if(strtok.hasMoreTokens())
                                {
                                    cardname = strtok.nextToken();
                                    descrizione = strtok.nextToken();
                                    projectname = strtok.nextToken();
                                }
                              
                            
                            //Inserisco semplicemente la carta 
                            Progetto pi = LisProject.get(projectname);
                            try 
                            {
                                pi.insertScheda(cardname, descrizione);
                                Utility.sendtoclient(204,"OK card added", key);
                                Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was added to the TODO list",pi.GetIpGroup());
                            }
                            catch (IllegalArgumentException ex)
                            {
                              
                                Utility.sendtoclient(404,"The card is already inside the project", key);
                            }

                            

                           }
                           else if (nextok.equals("SHOWCARDS"))
                           {
                               
                            String projectname=""; 
                                
                            if(strtok.hasMoreTokens())
                            {
                            projectname = strtok.nextToken();
                            if(strtok.hasMoreTokens())
                                projectname = projectname + strtok.nextToken();
                            }

                            Progetto pi =LisProject.get(projectname);
                            Gson gson = new Gson();
                            String response = gson.toJson(pi.GetSchede());
                            Utility.sendtoclient(206,response,key);          
                           
                           }
                           else if (nextok.equals("SHOWCARD"))
                           {
                            
                            String cardname="";
                            String projectname="";

                            if(strtok.hasMoreTokens())
                            {
                                cardname = strtok.nextToken();
                                projectname = strtok.nextToken();
                            }

                            Progetto pi = LisProject.get(projectname);
                            System.out.println(cardname);
                        
                           
                          
                            try
                            {
                                
                                Gson gson = new Gson ();
                                String desc = pi.GetDescription(cardname);
                                String send= gson.toJson(pi.GetHistory(cardname));
                                 
                                Utility.sendtoclient(207,(send+"\n"+desc+"\n"),key);
                                
                            }
                            catch(IllegalArgumentException ex)
                            {
                                Utility.sendtoclient(404,"Questa scheda non è pesente!", key);    
                            }
                        }
                           else if (nextok.equals("MOVECARD"))
                           {
                            String cardname=""; 
                            String  from="";
                            String  to="";
                            String projectname = "";
                         
                            if(strtok.hasMoreTokens())
                            {
                                cardname = strtok.nextToken();
                                from = strtok.nextToken();
                                to = strtok.nextToken();
                                projectname = strtok.nextToken();
                            }

                         


                             Progetto pi = LisProject.get(projectname);
                       

                                    if((from.equals("TODO"))&&(to.equals("INPROGRESS")))
                                        {
                                            try
                                            {
                                               
                                                pi.Move_ToDo_InProgress(cardname);
                                                Utility.sendtoclient(204,"OK Card Moved", key);
                                                Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was moved from TODO list to INPROGRESS list",pi.GetIpGroup());
                                            }
                                            catch(IllegalArgumentException ex)
                                            {
                                                Utility.sendtoclient(404,"This card is not present", key);
                                            }


                                        }
                                    else if (from.equals("INPROGRESS")&&to.equals("DONE"))
                                    {
                                        try
                                            {
                                                pi.Move_InProgress_Done(cardname);
                                                Utility.sendtoclient(204,"OK Card Moved", key);
                                                Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was moved from INPROGRESS list to DONE list",pi.GetIpGroup());
                                            }
                                            catch(IllegalArgumentException ex)
                                            {
                                                Utility.sendtoclient(404,"This card is not present", key);
                                            }

                                    }
                                    else if(from.equals("INPROGRESS")&&to.equals("TOBEREVISED"))
                                    {
                                        try
                                        {
                                            pi.Move_InProgress_ToBeRevised(cardname);
                                            Utility.sendtoclient(204,"OK Card Moved", key);
                                            Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was moved from INPROGRESS list to TOBEREVISED list",pi.GetIpGroup());
                                        }
                                        catch(IllegalArgumentException ex)
                                        {
                                            Utility.sendtoclient(404,"This card is not present", key);
                                            
                                        }


                                    }
                                    else if (from.equals("TOBEREVISED")&&to.equals("INPROGRESS"))
                                    {
                                        try
                                        {
                                            pi.Move_ToBeRevised_InProgress(cardname);
                                            Utility.sendtoclient(204,"OK Card Moved", key);
                                            Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was moved from TOBEREVISED list to INPROGRESS list",pi.GetIpGroup());
                                            
                                        }
                                        catch(IllegalArgumentException ex)
                                        {
                                            Utility.sendtoclient(404,"This card is not present", key);
                                            
                                        }


                                    }
                                    else if (from.equals("TOBEREVISED")&&to.equals("DONE"))
                                    {
                                        try
                                        {
                                            pi.Move_ToBeRevised_Done(cardname);
                                            Utility.sendtoclient(204,"OK Card Moved", key);
                                            Utility.sendtoGroup("SYSTEM: card \""+cardname+"\" was moved from TOBEREVISED list to DONE list",pi.GetIpGroup());
                                        }
                                        catch(IllegalArgumentException ex)
                                        {
                                            Utility.sendtoclient(404,"This card is not present", key);
                                        }

                                    }
                                    else
                                    {
                                        Utility.sendtoclient(404,"Error, Arguments are wrong", key);
                                    }

                               
                           }
                           else if (nextok.equals("ADDMEMBER"))
                           {
                                String projectname="";
                                String newMember = "";
                                
                            if(strtok.hasMoreTokens())
                            {
                                newMember = strtok.nextToken();
                                projectname = strtok.nextToken();
                            

                                System.out.println(newMember);
                                System.out.println(projectname);

                                if(Userbase.containsKey(newMember))
                                {
                                    Progetto pi = LisProject.get(projectname);
                                    
                                    if(!pi.ContainsMember(newMember))
                                    {

                                        pi.AddMember(newMember,false);
                                        Utility.sendtoclient(204,"OK Client Added", key);
                                        Utility.sendtoGroup("SYSTEM:  \""+newMember+"\" joined ",pi.GetIpGroup());
                                        

                                    }
                                    else
                                    {
                                        Utility.sendtoclient(404,"User is already in the group", key);
                                    }


                                }
                                else
                                {
                                    Utility.sendtoclient(404,"Not registered User", key);
                                }
                               

                            }

                        }
                           else 
                           {
                               //non si puo mandare altro
                           }

                            
                            

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
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(OutOfRangeException ex)
        {
            ex.printStackTrace();
        }
        catch(AlreadyBoundException ex)
        {
            ex.printStackTrace();
        }

       

    }
    
    //Setup Server
    private static ConcurrentHashMap <String,String>  FirstSetup(ConcurrentHashMap <String,Progetto> listp,ConcurrentHashMap <String,String> Ubase, GeneratorIp ipgen) throws IOException,ClassNotFoundException
    {
        File mainDir = new File(MAIN_DIR_PATH);
        if(mainDir.exists()==false)
        {
            mainDir.mkdir();
           
        }

        //All Projects are loaded on the memory
       String [] list = mainDir.list();
       for (String str : list) {
            System.out.println(str);
            try
            {
            listp.put(str, new Progetto(str,ipgen.GetnextIp()));
            }
            catch(OutOfRangeException ex)
            {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }

        //UserBase is loaded on the memory 
        String path = "./UserBase";
        File  filebase = new File(path);
        if(filebase.exists())
        {
            Ubase = (ConcurrentHashMap <String,String> ) Utility.read(path);
            //DA ELIINARE
            System.out.println("CArico in memoria"+Ubase.toString());
            return Ubase;
        }
        else
        {
            Ubase = new ConcurrentHashMap<String,String>();
            
            Utility.write(Ubase, path);
            return Ubase;
        }
   

    }

   
   

    


}

