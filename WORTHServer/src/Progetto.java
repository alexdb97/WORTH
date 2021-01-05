import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;

import Serializers.Serializers;



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

        private ArrayList <String> Members;
       

         /**
   * Costructor Of the Class Progetto, it finds if the Project is present in the folder MainDir, in a positive case
   * it takes all the structure and load on the memory, this is for fault tollerance prevention. In a negative case it means
   * that is the first start of the server.
   * @return nothing.
   * @throws  NullPointerException, IllegalArgumentException, IOException, ClassNotFoundException.
   * 
   * 
   */
     public  Progetto (String name) throws NullPointerException, IllegalArgumentException,IOException,ClassNotFoundException
        {
            if(name==null)
            throw new NullPointerException();
        

            this.NomeProgetto=name;
            this.ToDo = new ArrayList<Scheda>();
            this.InProgeress = new ArrayList<Scheda>();
            this.ToBeRevised = new ArrayList<Scheda>();
            this.Done= new ArrayList<Scheda>();
            

      
            String dirpath = this.MAIN_DIR_PATH+"/"+name;


            File progdir = new File(dirpath);
            //Controllare se esiste e crearla in caso negativo 
            if(progdir.exists()==false)
            {
                progdir.mkdir();
                this.Members = new ArrayList<String>();
            }
            else{
                //SIGNIFICA CHE GIA ESISTE E QUINDI MI PRENDO I FILE DA DENTRO
                String [] list = progdir.list();
               
                for (String str : list) {

                     String path = dirpath+"/"+str;
                     if(!path.equals(dirpath+"/Members"))
                     {
                     
                   Scheda s = (Scheda) Serializers.read(path);
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

            String path1 = this.MAIN_DIR_PATH+"/"+name+"/Members";
            this.Members= (ArrayList <String>) Serializers.read(path1);     
            }

          
        }


           /**
   * Getter method for getting the Name of the Project
   * @return String.
   */
        public String GetName ()
        {
            return this.NomeProgetto;
        }

        
           /**
   * Method for inserting a new istance of Scheda inside the TODO list, it's a 
   * syncronized method for garantee security
   * in multithread case
   * @throws IOException.
   * 
   */
        public synchronized void insertScheda (String Nome, String Descrizione) throws IOException
        {

            Scheda newScheda = new Scheda (Nome,Descrizione);
            newScheda.AddHistory("TODO");
            String schedapath = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/"+Nome;
            System.out.println(schedapath);
            Serializers.write(newScheda,schedapath);
            this.ToDo.add(newScheda);
        }

       
       
       
        
    /**
   * Method for moving one "Scheda" from TODO list to InProgress list.it's a syncronized method for garantee security
   * in multithread case.
   * @throws IOException,IllegalArgumentException,NullPointerException.
   * 
   */
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
            Serializers.write(current,schedapath);
            InProgeress.add(current);
            }
            else
                throw new IllegalArgumentException();
        }

    /**
   * Method for moving one "Scheda" from InProgress list to Done list.it's a syncronized method for garantee security
   * in multithread case.
   * @throws IOException,IllegalArgumentException,NullPointerException.
   * 
   */
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
            Serializers.write(current,schedapath);
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();
        }
    
         /**
   * Method for moving one "Scheda" from InProgress list to ToBeRevised list.it's a syncronized method for garantee security
   * in multithread case.
   * @throws IOException,IllegalArgumentException,NullPointerException.
   * 
   */
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
            Serializers.write(current,schedapath);
            ToBeRevised.add(current);
            }
            else
            throw new IllegalArgumentException();

        }



          /**
   * Method for moving one "Scheda" from ToBeRevised list to Done list.it's a syncronized method for garantee security
   * in multithread case.
   * @throws IOException,IllegalArgumentException,NullPointerException.
   * 
   */
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
            Serializers.write(current,schedapath);
            Done.add(current);
            }
            else
            throw new IllegalArgumentException();
        }

        
        public int RemoveProgetto()
        {
            String path = this.MAIN_DIR_PATH+"/"+this.NomeProgetto;
            File progfile = new File(path);
            System.out.println(progfile.getAbsolutePath());
            deleteFolder(progfile);

            return 1;
        }

        
        public  synchronized int AddMember (String Name,boolean fondatore) throws NullPointerException, IllegalArgumentException,
                IOException
        {
            if(Name==null)
                throw new NullPointerException();

            if(fondatore==false)
                if(this.Members.contains(Name))
                    throw new IllegalArgumentException("Utente gia registrato");
            
            this.Members.add(Name);
            String path = this.MAIN_DIR_PATH+"/"+this.NomeProgetto+"/Members";
            Serializers.write(this.Members,path);

            return 1;

        }

        public synchronized boolean ContainsMember (String Name) throws NullPointerException
        {
            if(Name==null)
                throw new NullPointerException();
            
            if(this.Members.contains(Name))
                return true;
            else
                return false;
        }

        //funzione che serve per la delete del progetto ma la faro dopo
        public synchronized boolean CheckCardsForDelete ()
        {
            if(ToDo.isEmpty()&&InProgeress.isEmpty()&&ToBeRevised.isEmpty())
                return true;
            else
                return false;
        }

        //funzione per prendere i membri di un progetto
        public synchronized ArrayList <String>  GetMembers ()
        {
            return this.Members;
        }

        


        private static void deleteFolder(File file){

            for (File subFile : file.listFiles()) {
                System.out.println("Delete of :"+subFile.getName());
               if(subFile.isDirectory()) {
                  deleteFolder(subFile);
               } else {
                  subFile.delete();
               }
            }
            file.delete();
         }

         

        
       


     
    }

    


