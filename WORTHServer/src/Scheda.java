
import java.io.Serializable;

import java.util.Stack;





public class Scheda  implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String Nome;
    private String Descrizione;
    private Stack <String> Storia;
   



    
    public Scheda (String Nome)
    {
        this.Nome = Nome;
        this.Descrizione = null;
        this.Storia = new Stack <String> ();

    }
    
    public Scheda (String Nome, String Descrizione)
    {
        this.Nome = Nome;
        this.Descrizione = Descrizione;
        this.Storia = new Stack <String> ();

    }
    public String GetName()
    {
        return this.Nome;
    }

    public void AddHistory(String transfer) throws NullPointerException
    {
        if(transfer == null)
            throw new NullPointerException();
       this.Storia.push(transfer);
    }

    public String getDescription ()
    {
        return this.Descrizione;
    }


    public Stack <String> GetHistory ()
    {
      
        return this.Storia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Nome== null) ? 0 : Nome.hashCode());
        return result;
    }


    //Overriding the equality ()
    @Override
    public boolean equals(Object s)
    {
        Scheda sc = (Scheda) s;
        if(this.Nome.equals(sc.GetName()))
            return true;
        else
           return  false;
    }
}
