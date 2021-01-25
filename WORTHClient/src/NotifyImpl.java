
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class NotifyImpl extends RemoteObject implements NotifyEventInterface{


  
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ConcurrentHashMap<String, Boolean> usermap;


    // crea una nuova callback client
    public NotifyImpl () throws RemoteException
    { super ();
      this.usermap = new ConcurrentHashMap<String,Boolean>();
    }


  
    public void notifyEvent(ConcurrentHashMap <String,Boolean> value ) throws RemoteException {
        
        this.usermap = value;

    }

    public  ArrayList <String> listOnlineUsers () throws RemoteException
    
    {   
        ArrayList <String> list = new ArrayList<String>();
        Iterator <Entry<String,Boolean>> it = this.usermap.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry <String,Boolean> pair = (Map.Entry<String,Boolean> )  it.next();
            Boolean b =  pair.getValue();
            if(b.equals(true))
                list.add( pair.getKey());
            
        }
        return list;
    }

    public  ArrayList <String> listUsers () throws RemoteException
    {
        
        ArrayList <String> list = new ArrayList<String>();
        Iterator  <Entry<String,Boolean>>  it = this.usermap.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry <String,Boolean> pair = (Map.Entry <String,Boolean> ) it.next();
            list.add(  pair.getKey());
            
        }
        return list;

    }

  

    
}
