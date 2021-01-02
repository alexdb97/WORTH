import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public  class Client {

    public static void main (String [] args)
    {
       

        
        RegisterInterface serverObject;
        Remote RemoteObject;
        ArrayList<Event> list = new ArrayList<Event>();
        
   

        try
        {
            //RMI inizialization
            Registry r = LocateRegistry.getRegistry(7070);
            RemoteObject = r.lookup("REGISTER");
            serverObject = (RegisterInterface) RemoteObject;
            
            
            InitialView nview = new InitialView(list);
            Model mod = new Model(serverObject);
            Controller ctr = new Controller(nview, mod,list);
            
            
            

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
