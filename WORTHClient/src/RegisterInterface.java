
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface  RegisterInterface  extends Remote{

      /**
     *
     */
      static final long serialVersionUID = 1234567L;

    public int register(String Nickname, String Password) throws RemoteException, NullPointerException,IOException;


}
