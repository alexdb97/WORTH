package Utility;

import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;



public class Utility {



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

     //Utility Function for sending to client
     public static void sendtoclient (Integer code, String description, SelectionKey key)
     {
         ByteBuffer buf = ByteBuffer.allocate(1024);
         String str = code.toString()+"\n"+description+"\n";
         buf.put(str.getBytes());
         key.attach(buf);
         key.interestOps(SelectionKey.OP_WRITE);
     }
 

    //Utility Function for Multcasting 
    public static void sendtoGroup(String description, String ipGroup)
    {
        try
        {
        InetAddress ia = InetAddress.getByName(ipGroup);
        byte [] buffer = new byte [1024];
        buffer = description.getBytes();
        DatagramSocket ms = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length,ia,6767);
        ms.send(dp);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    //Utility function used for filter SelectionKey
    public static String filter (String key)
    {
        String newkey=key;
        StringTokenizer strtok = new StringTokenizer(key," ");
        System.out.println(key);
        if(System.getProperty("os.name").startsWith("Windows"))
        {
        
        strtok.nextToken();
        newkey = strtok.nextToken()+" ";
        newkey = newkey + strtok.nextToken();
        }

        return newkey;
    }


   


}
