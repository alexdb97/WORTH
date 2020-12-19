import java.io.Serializable;
import java.util.ArrayList;

public class Scheda  implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String Nome;
    private String Descrizione;
    private ArrayList <String> Storia;
    
    public Scheda (String Nome)
    {
        this.Nome = Nome;
        this.Descrizione = null;
        this.Storia = new ArrayList <String> ();
    }
    
    public Scheda (String Nome, String Descrizione)
    {
        this.Nome = Nome;
        this.Descrizione = Descrizione;
        this.Storia = new ArrayList <String> ();
    }
    public String GetName()
    {
        return this.Nome;
    }
    public void AddHistory(String transfer)
    {
        this.Storia.add(transfer);
    }

    //Overriding the equality ()
    @Override
    public boolean equals(Object s)
    {
        Scheda sc = (Scheda) s;
        if(this.Nome == sc.GetName())
            return true;
        else
           return  false;
    }
}
