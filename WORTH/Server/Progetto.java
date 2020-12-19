import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
*The Utente class that specify the class Utente For the project WORTH 
*
* @author  Di Biase Alesandro
* @version 1.0
* @since   2020-12-19
*/

public class Progetto {

        private String MAIN_DIR_PATH = "../MainDir";

        private String NomeProgetto;
        private ArrayList <Scheda> ToDo;
        private ArrayList <Scheda> InProgeress;
        private ArrayList <Scheda> ToBeRevised;
        private ArrayList <Scheda> Done;
 n

        public Progetto (String name) throws NullPointerException, IllegalArgumentException
        {
            File mainDir = new File(MAIN_DIR_PATH);

            String dirpath = MAIN_DIR_PATH+"/"+name;
            File progdir = new File(dirpath);
            //CONTROLLARE SE ESISTE E CREARLA
            if(progdir.exists()==false)
            {
                progdir.mkdir();
            }
            else
            {
                throw new IllegalArgumentException();
            }


            if(name==null)
                throw new NullPointerException();
            
            this.NomeProgetto=name;
            this.ToDo = new ArrayList<Scheda>();
            this.InProgeress = new ArrayList<Scheda>();
            this.ToBeRevised = new ArrayList<Scheda>();
            this.Done= new ArrayList<Scheda>();
        }


        public String GetName ()
        {
            return this.NomeProgetto;
        }


        public synchronized void insertScheda (String Nome, String Descrizione)
        {
            Scheda newScheda = new Scheda (Nome,Descrizione);
            newScheda.AddHistory("TODO");
            this.ToDo.add(newScheda);
        }

       
       
       
        //Move from ToDo to InProgress
        public synchronized void Move_ToDo_InProgress (String s) throws IllegalArgumentException, NullPointerException
        {
            if(s==null)
                throw new NullPointerException();

            Scheda scheda = new Scheda(s);
            
            if(ToDo.contains(scheda))
            { 
            Scheda current = ToDo.remove(ToDo.indexOf(scheda));
            current.AddHistory("INPROGRESS");
            InProgeress.add(current);
            }
            else
                throw new IllegalArgumentException();
        }

        //Move from InProgre to Done
        public synchronized void Move_InProgress_Done(String s) throws IllegalArgumentException, NullPointerException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);
            if(InProgeress.contains(scheda))
            { 
            Scheda current = InProgeress.remove(InProgeress.indexOf(scheda));
            current.AddHistory("DONE");
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();
        }
        //Move from inprogress toberevised
        public void Move_InProgress_ToBeRevised(String s) throws NullPointerException,IllegalArgumentException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);

            if(InProgeress.contains(scheda))
            { 
            Scheda current = InProgeress.remove(InProgeress.indexOf(scheda));
            current.AddHistory("TOBEREVISED");
            ToBeRevised.add(current);
            }
            else
            throw new IllegalArgumentException();

        }



        //move from to be revised to done
        public void Move_ToBeRevised_Done(String s) throws NullPointerException,IllegalArgumentException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);

            if(ToBeRevised.contains(scheda))
            { 
            Scheda current = ToBeRevised.remove(ToBeRevised.indexOf(scheda));
            current.AddHistory("TOBEREVISED");
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();

        }



       public void  iterate ()
       {
         for (Scheda s : Done) {
             System.out.println(s.GetName());
             
       }
    }

    

}
