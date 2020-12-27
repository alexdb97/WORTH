

import java.io.File;
import java.io.IOException;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;


import Serializers.Serializers;


public class RegisterImpl extends RemoteServer implements RegisterInterface{

    /**
     *
     */
    private static final long serialVersionUID = 1234567L;
    

    public RegisterImpl () 
    {
       
    }
    

    public int register(String Nickname, String Password) throws RemoteException, NullPointerException
    {
        System.out.println(Nickname+" "+Password);
	return 3;
    }


}