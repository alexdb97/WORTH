import java.util.StringTokenizer;

public class FilterKey {


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
