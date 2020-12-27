

import java.io.File;
import java.io.IOException;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.concurrent.ConcurrentHashMap;

import Serializers.Serializers;


public class RegisterImpl extends RemoteServer implements RegisterInterface{

    /**
     *
     */
    private static final long serialVersionUID = 1234567L;
    private ConcurrentHashMap <String,String> Ubase;

    public RegisterImpl (ConcurrentHashMap <String,String>Userbase) 
    {
        
     
       this.Ubase = Userbase;
        

    }
    

    public int register(String Nickname, String Password) throws RemoteException, NullPointerException
    {
        System.out.println(Nickname+" "+Password);
        if(this.Ubase.containsKey(Nickname)==false)
            {
            this.Ubase.putIfAbsent(Nickname, Password);
            return 200;
            }
        else
            {
                return 400;
            }

	
    }


}