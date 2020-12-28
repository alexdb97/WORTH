

public class ConnectionTask  implements Runnable{

    InitialView  view;

    public ConnectionTask (InitialView v)
    {
        view = v;
    }
    
    
    
    @Override
	public void run() {
        
        int i=0;
        while (true)
        {
            try
            {
            Thread.currentThread().sleep(4000);
            view.setlabel(i);
           
            i++;

            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }


	}

    
    
}
