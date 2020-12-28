import java.io.IOException;
import java.rmi.RemoteException;

public class Model {

    private RegisterInterface serverobj;
    private String Name;

    private int  num ;

    public Model(RegisterInterface so) {
        this.serverobj = so;
    }

   public int sendData (String name, String Password) throws RemoteException, NullPointerException, IOException
    {

        this.Name = name;
        return serverobj.register(name, Password);
        

    }

    public void  setName (String name)
    {
        this.Name = name;
    }

    public String getName ()
    {
        return this.Name;
    }

    
}
