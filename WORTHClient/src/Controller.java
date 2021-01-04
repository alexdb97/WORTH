import java.awt.event.ActionListener; // seems to be missing.
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.google.gson.Gson;

import java.awt.event.*;

public class Controller {

    private InitialView theview;
    private Model themodel;
    Thread t;
    private ArrayList<Event> eventlist;

    public Controller(InitialView view, Model mod, ArrayList<Event> list) {
        this.themodel = mod;
        this.theview = view;
        this.eventlist = list;
        this.theview.RegisterListner(new RegisterLis());
        this.theview.LoginListener(new LoginListener());
        this.theview.ListProjects(new ListProjects());
        this.theview.GoBack(new goBack_prog());
        this.theview.logout(new Logout());
        this.theview.ListUsers(new ListUsers());

    }

    // Evento che interagisce con la registrazione
    class RegisterLis implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name, pass;

            try {

                name = theview.getUsername();
                pass = theview.getPassword();
                if (name != null || pass != null)

                    if (themodel.sendData(name, pass) == 400)
                        theview.error("400 Utente già registrato");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    // evento che interagisce con il longin
    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            String name, pass;

            try {

                name = theview.getUsername();
                pass = theview.getPassword();
                if (name != null || pass != null)
                    themodel.setName(name);

                // Inserisco nella coda degli eventi il LOGIN
                Event evento = new Event("LOGIN", theview.getUsername(), theview.getPassword());
                eventlist.add(evento);
                // spawno un thread per gestire la connessione
                themodel.setName(theview.getUsername());
                theview.setlabel(theview.getUsername());


                t = new Thread(new ConnectionTask(theview, eventlist, themodel));
                t.start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    // evento logout
    // evento lista progetti
    class Logout implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            Event event = new Event("LOGOUT", null, null);
            eventlist.add(event);

        }

    }

    // evento lista progetti
    class ListProjects implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            Event event = new Event("LISTPROJECTS", null, null);
            eventlist.add(event);
        }

    }

    // evento list users
    // evento lista progetti
    class ListUsers implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
           
            Event event = new Event("LISTUSERS",null,null);
            eventlist.add(event);    
        }

    }

    
    


    //evento vai indietro
    class goBack_prog implements ActionListener {

        public void actionPerformed(ActionEvent evt)
        {
           
            theview.setvisiblepanel3(false);
            theview.setvisiblepanel2(true);

        }

    }
    
}
