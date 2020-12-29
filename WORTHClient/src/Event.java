import java.util.ArrayList;

public class Event {
    
    private String Operation;
    private ArrayList <String> paramaters;

    public Event (String Op, String ... values)
    {
        this.Operation = Op;
        for(String  si:values )
        {
            paramaters.add(si);
        }
    }

    public String getOperation ()
    {
        return this.Operation;
    }

    public ArrayList <String> getParam ()
    {
        return this.paramaters;
    }

    
}
