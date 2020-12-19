import java.util.function.ObjLongConsumer;

/**
*The Utente class that specify the class Utente For the project WORTH 
*
* @author  Di Biase Alesandro
* @version 1.0
* @since   2020-12-19
*/

public class Utente  implements Cloneable{

    private String Nickname;
    private String Password;
    private Boolean Online;

    public Utente (String  nickname, String password)
    {
        
        this.Nickname=nickname;
        this.Password= password;
        this.Online = false;
    }


    
   /**
   * This is the Setter method for setting Online status.
   * @param value Used.
   * @return Boolean.
   * @exception NullPointerException On param null.
   * @see NullPinterException
   */
    public Boolean SetOnline(Boolean value) throws NullPointerException
    {
        if(value == null)
            throw new NullPointerException();
        
        this.Online = value;
        return this.Online;
    }
     /**
   * This is the Getter method for getting Online status.
   * @return Boolean.
   */

    public Boolean GetOnlineState ()
    {
        return this.Online;
    }

    /**
   * This is the Getter method for getting Nickname.
   * @return String.
   */
    public String GetName()
    {
        return this.Nickname;
    }
     /**
   * This is the Getter method for getting Password.
   * @return String.
   */
    public String GetPasword()
    {
        return this.Password;
    }

   @Override
    protected  Object clone () throws CloneNotSupportedException
    {
        Utente obj = (Utente) super.clone();
        obj.Password=null;
        return obj;
    }
    
}
