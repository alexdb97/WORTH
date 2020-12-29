import java.awt.event.ActionListener; // seems to be missing.
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;

     



public class InitialView {

    //INITIALVIEW
    private  JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel UserName = new JLabel("UserName");
    private JLabel Password = new JLabel("Password");
    private JTextField un = new JTextField();
    private JTextField ps = new JTextField();
    private JButton Register = new JButton("Register");
    private JButton Login = new JButton("Login");
    //worthmenu
    private JLabel welcome = new JLabel("Welcome");
    private JButton createproject = new JButton("Create a new Project");
    private JButton listUsers = new JButton("Get list of Users");
    private JButton listOnlineUsers = new JButton("Get list of Users (online)");
    private JButton listProjects = new JButton("Get list of projects");
    JPanel panel2 = new JPanel();

    private ArrayList <Event> eventlist;




    public InitialView (ArrayList <Event> lis)
    {
        
        frame.setResizable(false);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     
        
     //Routine per chiudere bene la connessione
     frame.addWindowListener( new WindowAdapter()
     {
             public void windowClosing(WindowEvent e)
             {
                 Event ev = new Event("LOGOUT");
                 lis.add(ev);
                 System.out.println(lis);

                 JFrame frame = (JFrame)e.getSource();

                     int result = JOptionPane.showConfirmDialog(
                                         frame,
                         "Are you sure you want to exit the application?",
                         "Exit Application",
                             JOptionPane.YES_NO_OPTION);
                     
                    

                     if (result == JOptionPane.YES_OPTION)
                     {
                     try
                     {
                     Thread.currentThread().sleep(50);
                     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                     }
                     catch(InterruptedException ex)
                     {

                     }
                     }
             }
         });


        

        frame.setTitle("WORTH");
        frame.add(panel);
      
       

        panel.setVisible(true);
       

        

        //Setting InitialView
        UserName.setBounds(20,50,100,20);
        Password.setBounds(20,80,100,20);
        un.setBounds(100,50,100,20);
        ps.setBounds(100,80,100,20);
        Register.setBounds(100,130,100,20);
        Login.setBounds(100,160,100,20);
        panel.setLayout(null);
        panel.add(Register);
        panel.add(Login);
        panel.add(UserName);
        panel.add(Password);
        panel.add(un);
        panel.add(ps);


      



        frame.setVisible(true);
    }

    public String getUsername ()
    {
        return un.getText();
    }

    public String getPassword ()
    {
        return ps.getText();

    }

   public void setvisiblepanel1 (boolean value)
   {
       this.panel.setVisible(value);

   }
   public void setvisiblepanel2 (boolean value)
   {
    
       
         //Setting WorthMenu
         frame.setTitle("WORTH MENU");
         //set layout to null
         panel2.setLayout(null);
         frame.add(this.panel2);
         welcome.setBounds( 100,0,100,100);
        panel2.add(welcome);
        createproject.setBounds(40, 70, 220, 20);
        listUsers.setBounds(40,100,220, 20);
        listOnlineUsers.setBounds(40, 130, 220,20);
        listProjects.setBounds(40,160,220,20);
        panel2.add(listProjects);
        panel2.add(listOnlineUsers);
        panel2.add(createproject);
        panel2.add(listUsers);
 
      
       this.panel2.setVisible(value);

   }

    void RegisterListner(ActionListener lis)
    {
        this.Register.addActionListener(lis);
    }

    void LoginListener (ActionListener lis)
    {
        this.Login.addActionListener(lis);
    }

    void ListProjects (ActionListener lis)
    {
        this.listProjects.addActionListener(lis);
    }

    void error (String err)
    {
        JOptionPane.showMessageDialog(frame, err, "ErrorMessage",JOptionPane.ERROR_MESSAGE);
    }

    void setlabel(String name)
    {
        String text = "Welcome"+name;
        welcome.setText(text);
    }
    



    
}
