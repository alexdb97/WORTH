import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import Serializer.Serializer;



/**
*The Utente class that specify the class Utente For the project WORTH 
*
* @author  Di Biase Alesandro
* @version 1.0
* @since   2020-12-19
*/

public class Progetto implements Serializable {

    private static final long serialVersionUID = 1L;

        private String MAIN_DIR_PATH = "./MainDir";

      
        private String NomeProgetto;
        private ArrayList <Scheda> ToDo;
        private ArrayList <Scheda> InProgeress;
        private ArrayList <Scheda> ToBeRevised;
        private ArrayList <Scheda> Done;
       

        

        public  Progetto (String name) throws NullPointerException, IllegalArgumentException,IOException,ClassNotFoundException
        {
            if(name==null)
            throw new NullPointerException();
        

            this.NomeProgetto=name;
            this.ToDo = new ArrayList<Scheda>();
            this.InProgeress = new ArrayList<Scheda>();
            this.ToBeRevised = new ArrayList<Scheda>();
            this.Done= new ArrayList<Scheda>();

            File mainDir = new File(MAIN_DIR_PATH);
            String dirpath = this.MAIN_DIR_PATH+"/"+name;


            File progdir = new File(dirpath);
            //Controllare se esiste e crearla in caso negativo 
            if(progdir.exists()==false)
                progdir.mkdir();
            else{
                //SIGNIFICA CHE GIA ESISTE E QUINDI MI PRENDO I FILE DA DENTRO
                String [] list = progdir.list();
               
                for (String str : list) {
                     String path = dirpath+"/"+str;
                     
                   Scheda s = (Scheda) Serializer.read(path);
                   String cases = s.GetHistory().peek();

                   if(cases.equals("TODO"))
                        ToDo.add(s);
                   else if(cases.equals("INPROGRESS"))
                        InProgeress.add(s);
                   else if (cases.equals("TOBEREVISED"))
                        ToBeRevised.add(s);
                   else if (cases.equals("DONE"))
                        Done.add(s);
                    else throw new IllegalArgumentException();       

                }       
            }
        }


        public String GetName ()
        {
            return this.NomeProgetto;
        }


        public synchronized void insertScheda (String Nome, String Descrizione) throws IOException
        {

            Scheda newScheda = new Scheda (Nome,Descrizione);
            newScheda.AddHistory("TODO");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+Nome;
            System.out.println(schedapath);
            Serializer.write(newScheda,schedapath);
            this.ToDo.add(newScheda);
        }

       
       
       
        //Move from ToDo to InProgress
        public synchronized void Move_ToDo_InProgress (String s) throws IllegalArgumentException, NullPointerException,IOException
        {
            if(s==null)
                throw new NullPointerException();

            Scheda scheda = new Scheda(s);
            
            if(ToDo.contains(scheda))
            { 
            Scheda current = ToDo.remove(ToDo.indexOf(scheda));
            current.AddHistory("INPROGRESS");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+s;
            Serializer.write(current,schedapath);
            InProgeress.add(current);
            }
            else
                throw new IllegalArgumentException();
        }

        //Move from InProgre to Done
        public synchronized void Move_InProgress_Done(String s) throws IllegalArgumentException, NullPointerException,IOException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);
            if(InProgeress.contains(scheda))
            { 
            Scheda current = InProgeress.remove(InProgeress.indexOf(scheda));
            current.AddHistory("DONE");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+s;
            Serializer.write(current,schedapath);
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();
        }
        //Move from inprogress toberevised
        public synchronized void Move_InProgress_ToBeRevised(String s) throws NullPointerException,IllegalArgumentException,IOException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);

            if(InProgeress.contains(scheda))
            { 
            Scheda current = InProgeress.remove(InProgeress.indexOf(scheda));
            current.AddHistory("TOBEREVISED");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+s;
            Serializer.write(current,schedapath);
            ToBeRevised.add(current);
            }
            else
            throw new IllegalArgumentException();

        }



        //move from to be revised to done
        public synchronized  void Move_ToBeRevised_Done(String s) throws NullPointerException,IllegalArgumentException,IOException
        {
            if(s==null)
            throw new NullPointerException();

            Scheda scheda = new Scheda (s);

            if(ToBeRevised.contains(scheda))
            { 
            Scheda current = ToBeRevised.remove(ToBeRevised.indexOf(scheda));
            current.AddHistory("DONE");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+s;
            Serializer.write(current,schedapath);
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();
        }

        
       


     
    }

    


