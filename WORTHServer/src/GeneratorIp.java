import java.net.Inet4Address;
import java.util.Stack;

public class GeneratorIp {


    int x1;
    int x2;
    int x3;
    int x4;

    Stack <String> IpReuse = new Stack <String> ();


    public GeneratorIp ()
    {
        this.x1 = 224;
        this.x2 = 0;
        this.x3 = 0;
        this.x4 = 0;
    }



    //Forse bisognerebbe fare dei controlli
    public void pushIp (String ip)
    {
        this.IpReuse.push(ip);
    }


    //Genera il prossimo ip
    public String GetnextIp () throws OutOfRangeException
    {
        String nextip;

        if(IpReuse.isEmpty())
        {

            if(x1<240)
                {
                    x4++;
                    x4 = x4%256;
                        if(x4==0)
                            {
                                x3++;
                                x3=x3%256;
                                    if(x3==0)
                                        {
                                            x2++;
                                            x2=x2%256;
                                                if(x2==0)
                                                    {
                                                        x1++;
                                                    }
                                        }
                            }
                }
        else 
            throw  new OutOfRangeException ("OutOfRange");

        if(x1==240)
            throw new OutOfRangeException();
            
        nextip = x1+"."+x2+"."+x3+"."+x4;
        }
        else
        {
            nextip = IpReuse.pop();
        }

        
        return nextip;
    }


   

    
}


