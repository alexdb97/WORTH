package Serializers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Serializer {



    public static int write(Object ob, String Path) throws IOException
    {

        FileOutputStream fout = new FileOutputStream(Path);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(ob);
        oos.close();
        return 1;
    } 

    public static Object read(String Path) throws IOException, ClassNotFoundException
    {
        FileInputStream fin = new FileInputStream(Path);
        ObjectInputStream oos = new ObjectInputStream(fin);
        Object ob = oos.readObject();
       
        oos.close();
        return ob;
    }

   


}
