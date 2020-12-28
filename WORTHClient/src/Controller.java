import java.awt.event.ActionListener; // seems to be missing.
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Stack;

import javax.lang.model.util.ElementScanner14;

import java.awt.event.*;

public class Controller {

    private InitialView theview;
    private Model themodel;
    Thread t;
    


    public Controller (InitialView view, Model mod)
    {
        this.themodel = mod;
        this.theview = view;

        this.theview.RegisterListner(new RegisterLis());
        this.theview.LoginListener(new LoginListener());
    }




    //Evento che interagisce con la registrazione
    class RegisterLis implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
            String name, pass;
            
            try
            {

                name = theview.getUsername();
                pass = theview.getPassword();
                if(name!=null || pass!=null)

              if(themodel.sendData(name, pass)==400)
                theview.error("400 Utente già registrato");
              
              
            }
            catch(Exception ex)
            {ex.printStackTrace();}

            
        }

    }

    //evento che interagisce con il longin
    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
            String name, pass;
            
            try
            {

                name = theview.getUsername();
                pass = theview.getPassword();
                if(name!=null || pass!=null)
               

                theview.setvisiblepanel1(false);
                theview.setvisiblepanel2(true);


                t = new Thread(new ConnectionTask(theview));
                t.start();



            }
            catch(Exception ex)
            {ex.printStackTrace();}

            
        }

    }
    
}
