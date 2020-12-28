

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
          

            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }


	}

    
    
}
