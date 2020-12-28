import java.awt.event.ActionListener; // seems to be missing.
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;








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
    private JButton showProjects = new JButton("Show menu of a single project");
    JPanel panel2 = new JPanel();


    public InitialView ()
    {
        frame.setResizable(false);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("WORTH");
        frame.add(panel);
      
       

        panel.setVisible(true);
        panel2.setVisible(false);

        

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


        //Setting WorthMenu
        welcome.setBounds(0,20,100,20);
        createproject.setBounds(20,100,100,50);
        showProjects.setBounds(20,150,100,50);
        panel2.add(welcome);
        panel2.add(createproject);
        panel2.add(showProjects);




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
       frame.setSize(300,300);
       frame.add(this.panel2);
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

    void error (String err)
    {
        JOptionPane.showMessageDialog(frame, err, "ErrorMessage",JOptionPane.ERROR_MESSAGE);
    }
    



    
}
