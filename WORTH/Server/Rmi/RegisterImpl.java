package Rmi;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.concurrent.ConcurrentHashMap;

import Serializer.Serializer;


public class RegisterImpl extends RemoteServer implements RegisterInterface, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1234567L;
    private ConcurrentHashMap<String, Utente> UserBase;

    public RegisterImpl () 
    {
        try 
        {
        File fileb = new File("./Userbase");
        if(fileb.exists()==false)
            {
            
            this.UserBase= new ConcurrentHashMap<String,Utente>();
            Serializer.write(UserBase, "./Userbase");  

            }
        else
        {
            //VEDERE ATTENTAMENTE
            Object obj = Serializer.read("./Userbase");
            System.out.println(obj.getClass());
            this.UserBase = (ConcurrentHashMap <String,Utente>) Serializer.read("./Userbase");  
        }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

    public int register(String Nickname, String Password) throws RemoteException, NullPointerException
    {
        if(Nickname==null|| Password==null)
            throw new NullPointerException();
        if(UserBase.contains(Nickname)==false)
        {
            
            UserBase.putIfAbsent(Nickname, new Utente(Nickname, Password));
            try
            {
            Serializer.write(this.UserBase, "./Userbase");  
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            } 
            return 200;
        }
        else 
            return 400;
    }
    
}