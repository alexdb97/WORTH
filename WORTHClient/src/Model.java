import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class Model {

    private RegisterInterface serverobj;
    private String Name;
    private ConcurrentHashMap <String,Boolean> LoginMap;
    public NotifyEventInterface callbackob;
    private String CurrentProject;
    private String [] ProjectBuffer=null;
    

    public Model(RegisterInterface so) {
        this.serverobj = so;
        LoginMap = new ConcurrentHashMap<String,Boolean>();
        
    }



    public int ContainsProject (String word)
    {
        if(ProjectBuffer!=null)
        {
        for (String str : ProjectBuffer) {
            if(str.equals(word))
            {
                return 1;
            }
            
        }
        return 0;
     }
     else
     {

        return 0;
     }

    }


    public void setProjectBuffer (String [] array)
    {
        this.ProjectBuffer = array;
    }


 public void setcallback (NotifyEventInterface callbackob)
  {
      this.callbackob= callbackob;
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
