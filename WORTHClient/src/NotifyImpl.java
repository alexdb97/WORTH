

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class NotifyImpl extends RemoteObject implements NotifyEventInterface{


    ConcurrentHashMap <String,Boolean> usermap;


    // crea una nuova callback client
    public NotifyImpl (ConcurrentHashMap <String,Boolean> map) throws RemoteException
    { super ();
      this.usermap = map;
    }

  
    public void notifyEvent(ConcurrentHashMap <String,Boolean> value ) throws RemoteException {
        
        this.usermap = value;

    }

    public ConcurrentHashMap <String,Boolean> GetMap () throws RemoteException
    
    {
        return this.usermap;
    }

  

    
}
