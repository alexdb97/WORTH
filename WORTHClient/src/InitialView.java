import java.awt.event.ActionListener; // seems to be missing.

import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.*;


     



public class InitialView {

    //INITIALVIEW
    public JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel UserName = new JLabel("UserName");
    private JLabel Password = new JLabel("Password");

    private JTextField un = new JTextField();
    private JTextField ps = new JTextField();
    private JButton Register = new JButton("Register");
    private JButton Login = new JButton("Login");
    //worthmenu
    private JLabel welcome = new JLabel("Welcome");
    private JButton createproject = new JButton("Create/Enter Project");
    private JButton listUsers = new JButton("Get list of Users");
    private JButton listOnlineUsers = new JButton("Get list of Users (online)");
    private JButton listProjects = new JButton("Get list of projects");
    private JButton logout = new JButton("logout");
     
    JPanel panel2 = new JPanel();
    //Pannello lista 
    private JPanel panel3 = new JPanel();
    private JButton goBack_prog = new JButton ("Go Back");
    private JList list = new JList();
    private JScrollPane scrollPane = new JScrollPane(list);

    //Pannello crea Progetti
    private JPanel panel4 = new JPanel();
    private JTextField progetto = new JTextField ();
    private JButton create = new JButton("create");
    private JButton enter = new JButton("enter");
    private JLabel desc = new JLabel("Insert project name");

    //Pannello InsideProject Comand Bar
    private JPanel panel5 = new JPanel ();
    private JButton CancelProject = new JButton ("Cancel Project");
    private JButton AddCard = new JButton("Add Card");
    private JButton ShowCard = new JButton("Show Card");
    private JButton MoveCard = new JButton("Move Card");
    private JButton ShowCardHistory = new JButton ("Show Card History");


    //Vediamo dopo i vari bottoni
    private JPanel panel6 = new JPanel ();
    
    
   
   
    


    




    public InitialView ()
    {
        
        frame.setResizable(false);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     
    

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

//GETTING FROM TEXTFIELDS

    public String getUsername ()
    {
        return un.getText();
    }

    public String getPassword ()
    {

        return ps.getText();

    }

    public String getProgetto ()
    {
        return progetto.getText();
    }


//NAVIGATION
   public void setvisiblepanel1 (boolean value)
   {
       this.panel.setVisible(value);

   }


   //WORTHMENU
   public void setvisiblepanel2 (boolean value)
   {
    
         //Setting WorthMenu
        frame.setTitle("WORTH MENU");
        //set layout to null
        panel2.setLayout(null);
        frame.add(this.panel2);
        welcome.setBounds(50,20,200,100);
        panel2.add(welcome);
        createproject.setBounds(40, 90, 220, 20);
        listUsers.setBounds(40,120,220, 20);
        listOnlineUsers.setBounds(40, 150, 220,20);
        listProjects.setBounds(40,180,220,20);
        logout.setBackground(Color.BLUE);
        logout.setForeground(Color.WHITE);
        logout.setBounds(40,10,220,20);
        panel2.add(logout);
        panel2.add(listProjects);
        panel2.add(listOnlineUsers);
        panel2.add(createproject);
        panel2.add(listUsers);
       this.panel2.setVisible(value);

   }

   //LISTPROJECTS
   public void listProjects (String [] rest, String kindlist )
   {
     
    for(int i=0;i<rest.length;i++)
    System.out.println(rest[i]);
   panel2.setVisible(false);
   frame.setTitle(kindlist);
   panel3.setLayout(null);
   panel3.setBounds(0, 0,300,300);
   list.setListData(rest);
   scrollPane.setBounds(0,50,285,210);
   scrollPane.setVisible(true);
  
   goBack_prog.setBounds(0, 0,300,50);
   panel3.add(goBack_prog);
   panel3.add(scrollPane);
   panel3.setVisible(true);
   frame.add(panel3);
 
   frame.setVisible(true);
   
   }

   public void CreateProject ()
   {
       panel2.setVisible(false);
       frame.setTitle("CREATE/ENTER PROJECT");
       panel4.setLayout(null);
       progetto.setBounds(20,120,100,20);
       create.setBounds(150,100,80, 20);
       enter.setBounds(150,140,80,20);
       desc.setBounds(100,70,150,20);
       goBack_prog.setBounds(0, 0,300,50);
       panel4.add(desc);
       panel4.add(enter);
       panel4.add(goBack_prog);
       panel4.add(create);
       panel4.add(progetto);
       frame.add(panel4);
       panel4.setVisible(true);    
   }

   public void InsideAProject (String Title)
   {
        frame.setSize(890, 600);
        frame.setTitle("Project Name");
        panel5.setLayout(null);
        panel5.setBounds(0,0,890,200);
        goBack_prog.setBounds(0, 0, 890,70);
        CancelProject.setBounds(20,100,150,50);
        AddCard.setBounds(190,100,150,50);
        ShowCard.setBounds(360,100,150,50);
        MoveCard.setBounds(530,100,150,50);
        ShowCardHistory.setBounds(700,100,150,50);
        panel5.setBackground(Color.CYAN);
        panel5.add(goBack_prog);
        panel5.add(AddCard);
        panel5.add(ShowCard);
        panel5.add(MoveCard);
        panel5.add(ShowCardHistory);
        panel5.add(CancelProject);
        panel6.setBounds(0,200,890,400);
      
        panel6.setVisible(true);
        panel5.setVisible(true);
        frame.setVisible(true);
        frame.add(panel5);
        frame.add(panel6);

   }





   public void  setFramedim(int x,int y)
   {
       this.frame.setSize(x,y);
   }

   public void setvisiblepanel5_6(boolean value)
   {
       this.panel5.setVisible(value);
       this.panel6.setVisible(value);
   }

   public void setvisiblepanel4(boolean value)
   {
       this.panel4.setVisible(value);
   }

   public void setvisiblepanel3( boolean value)
   {
       this.panel3.setVisible(value);
   }



//ACTIONS HANDLER


    void RegisterListner(ActionListener lis)
    {
        this.Register.addActionListener(lis);
    }

    void LoginListener (ActionListener lis)
    {
        this.Login.addActionListener(lis);
    }

    void logout (ActionListener lis)
    {
        this.logout.addActionListener(lis);
    }

    void ListProjects (ActionListener lis)
    {
        this.listProjects.addActionListener(lis);
    }

    void ListUsers (ActionListener lis)
    {
        this.listUsers.addActionListener(lis);
    }


    void ListOnlineUsers (ActionListener lis)
    {
        this.listOnlineUsers.addActionListener(lis);
    }

    void PrepareProject (ActionListener lis)
    {
        this.createproject.addActionListener(lis);
    }

    void EffectiveCreate (ActionListener lis)
    {
        this.create.addActionListener(lis);
    }

    void EnterProject (ActionListener lis)
    {
        this.enter.addActionListener(lis);
    }


    void GoBack (ActionListener lis)
    {

        //list.removeAll();
        this.goBack_prog.addActionListener(lis);
        
    }

    void error (String err)
    {
        JOptionPane.showMessageDialog(frame, err, "ErrorMessage",JOptionPane.ERROR_MESSAGE);
    }

    void setlabel(String name)
    {
        String text = "Welcome   "+name;
        welcome.setText(text);
    }
    



    
}
