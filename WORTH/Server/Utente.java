package Rmi;



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


    public Utente (String  nickname, String password)
    {
        
        this.Nickname=nickname;
        this.Password= password;
      
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

    
}
