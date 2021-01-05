import java.awt.event.ActionListener; // seems to be missing.




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



import java.awt.Color;
import java.awt.Font;



     


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
    private JButton ShowMembers = new JButton ("Show Members");
    private JButton AddMember = new JButton("Add Member");
    private JButton EnterChat = new JButton("Group Chat");
    private JLabel ProjectName = new JLabel();
    //Add Card Panel
    private JPanel panel7 = new JPanel ();
    private JLabel CardName = new JLabel("Card Name");
    private JLabel DescriptionLabel = new JLabel("Description");
    private JTextField NewScheda = new JTextField();
    private JTextArea Description = new JTextArea();
    private JButton  AddEffectiveCard = new JButton("Add");
    //Show Cards
    private JPanel panel8 = new JPanel ();
    private JList list3 = new JList ();
    private JScrollPane pane2 = new JScrollPane(list3);
   
    //MoveCard
    private JPanel panel9 = new JPanel ();
    private JLabel from = new JLabel("From");
    private JLabel to = new JLabel("to");
    private JTextField fromtxt = new JTextField();
    private JTextField totxt = new JTextField();
    private JButton changecard = new JButton ();


    //Show Card History

    //Show Members

    //EnterChat
    


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
       goBack_prog.setBackground(Color.RED);
       goBack_prog.setForeground(Color.WHITE);

        

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
    public String getCardName ()
    {
        return this.NewScheda.getText();
    }

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
        frame.setSize(1500, 600);
        frame.setTitle(Title);
        panel5.setLayout(null);
        panel5.setBounds(0,0,1500,200);
        goBack_prog.setBounds(0, 0,100,70);
        ProjectName.setBounds(200,0,890,70);
        ProjectName.setBackground(Color.BLUE);
        ProjectName.setFont(new Font("Serif", Font.BOLD, 25));
        ProjectName.setSize(200,50);
       
       
        this.setProjectName(Title);
        CancelProject.setBounds(20,100,150,50);
        AddCard.setBounds(190,100,150,50);
        ShowCard.setBounds(360,100,150,50);
        MoveCard.setBounds(530,100,150,50);
        ShowCardHistory.setBounds(700,100,150,50);
        AddMember.setBounds(870, 100,150, 50);
        ShowMembers.setBounds(1040,100,150,50);
        EnterChat.setBounds(1210,100,150,50);
        panel5.add(AddMember);
        panel5.add(ShowMembers);
        panel5.add(EnterChat);
        panel5.add(ProjectName);
        panel5.add(goBack_prog);
        panel5.add(AddCard);
        panel5.add(ShowCard);
        panel5.add(MoveCard);
        panel5.add(ShowCardHistory);
        panel5.add(CancelProject);
        panel6.setBounds(0,200,1500,400);
      
        panel6.setVisible(true);
        panel5.setVisible(true);
        frame.setVisible(true);
        frame.add(panel5);
        frame.add(panel6);

   }


   public void AddCard ()
   {

    panel5.setBackground(Color.LIGHT_GRAY);
    panel8.setVisible(false);
    panel6.setVisible(false);
    panel7.setBounds(0,200,1500,400);
    panel7.setLayout(null);
    panel7.setBackground(Color.GRAY);
    CardName.setBounds(480,220,200,50);
    CardName.setFont(new Font("Serif", Font.BOLD, 25));
    DescriptionLabel.setBounds(480,300,200,50);
    DescriptionLabel.setFont(new Font("Serif", Font.BOLD, 25));
    NewScheda.setBounds(700,220,200,30);
    Description.setBounds(700,300,200,200);
    Description.setLineWrap(true);
    AddEffectiveCard.setBounds(920,300,100,50);
    panel7.add(DescriptionLabel);
    panel7.add(Description);
    panel7.add(NewScheda);
    panel7.add(AddEffectiveCard);
    panel7.add(CardName);
    panel7.setVisible(true);
    frame.add(panel7);
   }

  
    
   //panel8   ShowlIST
   public void showList (String [] lista)
   {
      
       panel5.setBackground(Color.GRAY);
       panel6.setVisible(false);
       panel7.setVisible(false);
       panel8.setVisible(true);
       panel8.setLayout(null);
       panel8.setBounds(0,200,1500,400);
       list3.setListData(lista);
       panel8.setBackground(Color.LIGHT_GRAY);
       pane2.setBounds(100,300,1000, 200);
       panel8.add(pane2);
       frame.add(panel8);
       

   } 


   //panel9   MoveCard

   void Movecard ()
   {

   }

   

   public void setProjectName(String name)
   {
       ProjectName.setText(name);
   }

   public void  setFramedim(int x,int y)
   {
       this.frame.setSize(x,y);
   }

   public void goback(boolean value)
   {
        this.panel3.setVisible(value);
        this.panel4.setVisible(value);
       this.panel5.setVisible(value);
       this.panel6.setVisible(value);
       this.panel7.setVisible(value);
       this.panel8.setVisible(value);
   }

   public void setvisiblepanel4(boolean value)
   {
       this.panel4.setVisible(value);
   }
   



//ACTIONS HANDLER

   void ShowCardHistory (ActionListener lis)
   {
       this.ShowCardHistory.addActionListener(lis);
   }

   void ShowMembers (ActionListener lis)
   {
       this.ShowMembers.addActionListener(lis);
   }

   void ShowCardEvent (ActionListener lis)
   {
       this.ShowCard.addActionListener(lis);
   }

   void EffectiveAddCard (ActionListener lis)
   {
       this.AddEffectiveCard.addActionListener(lis);
   }


   void AddCardview (ActionListener lis)
   {
       this.AddCard.addActionListener(lis);
   }


   void CancelProject (ActionListener lis )
   {
       this.CancelProject.addActionListener(lis);
   }


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
