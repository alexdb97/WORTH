
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface  RegisterInterface  extends Remote{


    public int register(String Nickname, String Password) throws RemoteException, NullPointerException,IOException;


}
