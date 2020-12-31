import java.util.ArrayList;

public class Event {
    
    private String Operation;
    private String arg1;
    private String arg2;
    

    public Event (String Op, String arg1, String arg2)
    {
        this.Operation = Op;
        this.arg1 = arg1 ;
        this.arg2 = arg2;
      
    }

    public String getOperation ()
    {
        return this.Operation;
    }

    public String getParam1 ()
    {
        return this.arg1;
    }

    public String getParam2 ()
    {
        return this.arg2;
    }

    
}
