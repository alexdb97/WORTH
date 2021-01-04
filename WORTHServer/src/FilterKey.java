import java.util.StringTokenizer;

public class FilterKey {


    public static String filter (String key)
    {
        String newkey="";
        StringTokenizer strtok = new StringTokenizer(key, " ");
        strtok.nextToken();
        newkey = strtok.nextToken()+" ";
        newkey = newkey + strtok.nextToken();
        return newkey;


    }
    
}
