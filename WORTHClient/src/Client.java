import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public  class Client {

    public static void main (String [] args)
    {
        RegisterInterface serverObject;
        Remote RemoteObject;

        try
        {
            Registry r = LocateRegistry.getRegistry(8080);
            RemoteObject = r.lookup("REGISTER");
            serverObject = (RegisterInterface) RemoteObject;
            serverObject.register("Alessandro", "1234");
            

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
