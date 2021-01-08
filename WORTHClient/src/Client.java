import java.rmi.ConnectException;
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
            //RMI inizialization
            Registry r = LocateRegistry.getRegistry(7070);
            RemoteObject = r.lookup("REGISTER");
            serverObject = (RegisterInterface) RemoteObject;
            
            
            InitialView nview = new InitialView();
            Model mod = new Model(serverObject);
            Controller ctr = new Controller(nview, mod);
            
            
        }
        
        catch (ConnectException ex)
        {
            System.out.println("Start the server FIRST!");
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
