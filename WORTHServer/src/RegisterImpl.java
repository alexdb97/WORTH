


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
    private ConcurrentHashMap <String,Boolean> Logmap;
    private ServerImpl server1 ;

    public RegisterImpl (ConcurrentHashMap <String,String>Userbase,ConcurrentHashMap <String,Boolean> logmap, ServerImpl serv1 ) 
    {
        
       this.server1 = serv1;
       this.Logmap = logmap;
       this.Ubase = Userbase;
     
        

    }
    

    public int register(String Nickname, String Password) throws RemoteException, NullPointerException, IOException
    {
        System.out.println(Nickname+" "+Password);
        if(this.Ubase.containsKey(Nickname)==false)
            {
            String path = "./UserBase";
            this.Ubase.putIfAbsent(Nickname, Password);
            Logmap.putIfAbsent(Nickname,false);
            server1.update(Logmap);
            Serializers.write(Ubase,path);
            return 200;
            }
        else
            {
                return 400;
            }

	
    }


}