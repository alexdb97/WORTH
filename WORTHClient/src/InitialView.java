import java.awt.event.ActionListener; // seems to be missing.

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;



     


public class InitialView {

    //INITIALVIEW
    public JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel UserName = new JLabel("UserName");
    private JLabel Password = new JLabel("Password");
    

    private JTextField un = new JTextField();
    private JPasswordField ps = new JPasswordField();
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
    private JButton ShowCards = new JButton("Show Cards");
    private JButton MoveCard = new JButton("Move Card");
    private JButton ShowCardProperty = new JButton ("Show Card Prop");
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
    //Show Lists
    private JPanel panel8 = new JPanel ();
    private JList list3 = new JList ();
    private JScrollPane pane2 = new JScrollPane(list3);
   
    
    //MoveCard
    private JPanel panel9 = new JPanel ();
    private JLabel from = new JLabel("From");
    private JLabel to = new JLabel("to");
    private JTextField fromtxt = new JTextField();
    private JTextField totxt = new JTextField();
    private JButton changecard = new JButton ("Move");
    private JLabel card_name  = new JLabel ("Card Name");
    private JTextField card_nametxt = new JTextField ();

    //GUI
    //Show Card Property
    private JPanel panel10 = new JPanel ();
    private JList list4 = new JList ();
    private JScrollPane pane3 = new JScrollPane(list4);
    private JLabel name = new JLabel ("Insert Name of the card") ; 
    private JLabel description = new JLabel ("Description");
    private JLabel State = new JLabel ("State");
    private JLabel History = new JLabel ("History");
    private JTextField nametxt = new JTextField(); 
    private JTextField descriptiontxt = new JTextField();
    private JTextField statetxt = new JTextField();
    private JTextField Historytxt = new JTextField();
    private JButton ShowCardProps = new JButton ("Show Card Proprety");
    
    //ADD MEMBER |TO DO
    private JLabel Nameof = new JLabel ("New Member name");
    private JButton AddMembereffective = new JButton ("Add");
    private JTextField NameMember = new JTextField ();
    private JPanel panel11 = new JPanel ();
    //GROUP CHAT 
    private JTextArea  receiveBox = new JTextArea ();
    private JScrollPane panescroll = new JScrollPane(receiveBox);
    private JPanel panel12 = new JPanel ();
    private JTextArea sendBox = new JTextArea ();
    private JButton sendbutton = new JButton("Send");
    

   


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


    public String GetSendBox()
    {
        return this.sendBox.getText();
    }

    public String GetNewMember ()
    {
        return this.NameMember.getText();
    }

    public String getCardName ()
    {
        return this.NewScheda.getText();
    }

    public String getDescription()
    {
        return (this.Description.getText()).replaceAll("\r\n|\r|\n"," ");
    }

    public String getUsername ()
    {
        return un.getText();
    }
    public String getNameProps ()
    {
        return this.nametxt.getText();
    }

    public String getPassword ()
    {

        return ps.getText();
    }

    public String GetMoveCardName ()
    {
        return card_nametxt.getText();
    }

    public String GetFrom ()
    {
        return fromtxt.getText();
    }
    
    public String GetTo ()
    {
        return totxt.getText();
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
        panel2.setBackground(Color.WHITE);
        frame.add(this.panel2);
        welcome.setBounds(50,20,200,100);
        panel2.add(welcome);
        createproject.setBounds(40, 90, 220, 20);
        createproject.setBackground(Color.GRAY);
        createproject.setForeground(Color.WHITE);
        listUsers.setBounds(40,120,220, 20);
        listUsers.setBackground(Color.GRAY);
        listUsers.setForeground(Color.WHITE);
        listOnlineUsers.setBounds(40, 150, 220,20);
        listOnlineUsers.setBackground(Color.GRAY);
        listOnlineUsers.setForeground(Color.WHITE);
        listProjects.setBounds(40,180,220,20);
        listProjects.setBackground(Color.GRAY);
        listProjects.setForeground(Color.WHITE);
        logout.setBackground(Color.DARK_GRAY);
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
       create.setBackground(Color.GRAY);
       create.setForeground(Color.WHITE);
       enter.setBounds(150,140,80,20);
       enter.setBackground(Color.GRAY);
       enter.setForeground(Color.WHITE);
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

   //INSIDE PROJECT
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
        CancelProject.setBackground(Color.DARK_GRAY);
        CancelProject.setForeground(Color.WHITE);
        CancelProject.setFont(new Font("Serif", Font.BOLD, 15));
        AddCard.setBounds(190,100,150,50);
        AddCard.setBackground(Color.GRAY);
        AddCard.setForeground(Color.WHITE);
        AddCard.setFont(new Font("Serif", Font.BOLD, 15));
        ShowCards.setBounds(360,100,150,50);
        ShowCards.setBackground(Color.GRAY);
        ShowCards.setForeground(Color.WHITE);
        ShowCards.setFont(new Font("Serif", Font.BOLD, 15));
        MoveCard.setBackground(Color.GRAY);
        MoveCard.setForeground(Color.WHITE);
        MoveCard.setFont(new Font("Serif", Font.BOLD, 15));
        MoveCard.setBounds(530,100,150,50);

        ShowCardProperty.setBackground(Color.GRAY);
        ShowCardProperty.setForeground(Color.WHITE);
        ShowCardProperty.setFont(new Font("Serif", Font.BOLD, 15));
        ShowCardProperty.setBounds(700,100,150,50);

        AddMember.setBounds(870, 100,150, 50);
        AddMember.setBackground(Color.GRAY);
        AddMember.setForeground(Color.WHITE);
        AddMember.setFont(new Font("Serif", Font.BOLD, 15));

        ShowMembers.setBounds(1040,100,150,50);
        ShowMembers.setBackground(Color.GRAY);
        ShowMembers.setForeground(Color.WHITE);
        ShowMembers.setFont(new Font("Serif", Font.BOLD, 15));

        EnterChat.setBounds(1210,100,150,50);
        EnterChat.setBackground(Color.GRAY);
        EnterChat.setForeground(Color.WHITE);
        EnterChat.setFont(new Font("Serif", Font.BOLD, 15));

        panel5.add(AddMember);
        panel5.add(ShowMembers);
        panel5.add(EnterChat);
        panel5.add(ProjectName);
        panel5.add(goBack_prog);
        panel5.add(AddCard);
        panel5.add(ShowCards);
        panel5.add(MoveCard);
        panel5.add(ShowCardProperty);
        panel5.add(CancelProject);
        panel6.setBounds(0,200,1500,400);
      
        panel6.setVisible(true);
        panel5.setVisible(true);
        frame.setVisible(true);
        frame.add(panel5);
        frame.add(panel6);

   }


   //ADD CARD
   public void AddCard ()
   {

    panel5.setBackground(Color.LIGHT_GRAY);
    panel8.setVisible(false);
    panel6.setVisible(false);
    panel9.setVisible(false);
    panel10.setVisible(false);
    panel11.setVisible(false);
    panel12.setVisible(false);
    panel7.setBounds(0,200,1500,400);
    panel7.setLayout(null);
    panel7.setBackground(Color.WHITE);
    CardName.setBounds(480,220,200,50);
    CardName.setFont(new Font("Serif", Font.BOLD, 25));
    DescriptionLabel.setBounds(480,300,200,50);
    DescriptionLabel.setFont(new Font("Serif", Font.BOLD, 25));
    NewScheda.setBounds(700,220,200,30);
    Description.setBounds(700,300,200,200);
    Description.setLineWrap(true);
    Description.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    AddEffectiveCard.setBounds(920,300,100,50);
    AddEffectiveCard.setBackground(Color.GRAY);
    AddEffectiveCard.setForeground(Color.WHITE);
    AddEffectiveCard.setFont(new Font("Serif", Font.BOLD, 15));
    panel7.add(DescriptionLabel);
    panel7.add(Description);
    panel7.add(NewScheda);
    panel7.add(AddEffectiveCard);
    panel7.add(CardName);
    panel7.setVisible(true);
    frame.add(panel7);
   }

  
    
   //  ShowlIST: CARDS,MEMBERS
   public void showList (String [] lista)
   {
       
       frame.add(panel8);
       panel8.setBackground(Color.WHITE);
       panel9.setVisible(false);
       panel11.setVisible(false);
       panel12.setVisible(false);
       panel6.setVisible(false);
       panel7.setVisible(false);
       panel10.setVisible(false);
       panel8.setVisible(true);
       panel8.setLayout(null);
       pane2.setBounds(200,220,1000,200);
       panel8.setBounds(0,200,1500,400);
       list3.setListData(lista);
       panel8.add(pane2);
       frame.invalidate();
       frame.validate();
       frame.repaint();
   } 


   //panel9   MoveCard

   void Movecard ()
   {
       panel6.setVisible(false);
       panel8.setVisible(false);
       panel7.setVisible(false);
       panel10.setVisible(false);
       panel11.setVisible(false);
       panel12.setVisible(false);
       panel9.setVisible(true);
       panel9.setBounds(0,200,1500,400);
       panel9.setLayout(null);
       panel9.setBackground(Color.WHITE);
       from.setBounds(480,300,200,50);
       from.setFont(new Font("Serif", Font.BOLD, 25));
       to.setFont(new Font("Serif", Font.BOLD, 25));
       to.setBounds(480,400,200,50);
       fromtxt.setBounds(720,300,200,30);
       totxt.setBounds(720,400,200,30);
       changecard.setBounds(600,500,150,50);
     
       changecard.setBackground(Color.GRAY);
       changecard.setForeground(Color.WHITE);
       changecard.setFont(new Font("Serif", Font.BOLD, 15));
       card_name.setBounds(480,200,200,50);
       card_name.setFont(new Font("Serif", Font.BOLD, 25));
       card_nametxt.setBounds(720,220,200,30);
       panel9.add(card_name);
       panel9.add(card_nametxt);
       panel9.add(changecard);
       panel9.add(from);
       panel9.add(to);
       panel9.add(fromtxt);
       panel9.add(totxt);
       frame.add(panel9);



   }

   //Panel10 Cards Proprety
   void ShowcardProps()
   {
    panel6.setVisible(false);
    panel8.setVisible(false);
    panel7.setVisible(false);
    panel9.setVisible(false);
    panel11.setVisible(false);
    panel12.setVisible(false);
    panel10.setVisible(true);
    panel10.setLayout(null);
    panel10.setBounds(0,200,1500,400);
    panel10.setBackground(Color.WHITE);
    pane3.setBounds(20,240,200,300);
    History.setBounds(30,200,100,40);
    History.setFont(new Font("Serif", Font.BOLD, 25));
    name.setBounds(500,200,300,50);
    name.setFont(new Font("Serif", Font.BOLD, 25));
    description.setBounds(500,300,200,50);
    description.setFont(new Font("Serif", Font.BOLD, 25));
    State.setBounds(500,400,200,50);
    State.setFont(new Font("Serif", Font.BOLD, 25));
    nametxt.setBounds(500,240,200,30);
    statetxt.setEditable(false);
    descriptiontxt.setEditable(false);
    descriptiontxt.setBounds(500,340,200,50);
    statetxt.setBounds(500,440,200,50);
    ShowCardProps.setBounds(760,340,150,50);
    ShowCardProps.setBackground(Color.GRAY);
    ShowCardProps.setForeground(Color.WHITE);
    ShowCardProps.setFont(new Font("Serif", Font.BOLD, 15));
    panel10.add(ShowCardProps);
    panel10.add(name);
    panel10.add(description);
    panel10.add(descriptiontxt);
    panel10.add(nametxt);
    panel10.add(statetxt);
    panel10.add(State);
    panel10.add(History);
    panel10.add(pane3);
    frame.add(panel10);
    frame.invalidate();
    frame.validate();
    frame.repaint();
}

//Show effectiveCardProprety
 void show_card_property (String [] rest,String description, String state)
 {
     list4.setListData(rest);
     descriptiontxt.setText(description);
     statetxt.setText(state);
     frame.invalidate();
     frame.validate();
     frame.repaint();

 }


    //Add member Panel
    void AddMemberPanel ()
    {
        panel6.setVisible(false);
        panel8.setVisible(false);
        panel7.setVisible(false);
        panel9.setVisible(false);
        panel10.setVisible(false);
        panel12.setVisible(false);
        panel11.setVisible(true);
        panel11.setBounds(0,200,1500,400);
        panel11.setLayout(null);
        panel11.setBackground(Color.WHITE);
        Nameof.setBounds(500,240,400,50);
        Nameof.setFont(new Font("Serif", Font.BOLD, 25));
        NameMember.setBounds(830,240,200,50);
        AddMembereffective.setBounds(700,400,150,50);
        AddMembereffective.setBackground(Color.GRAY);
        AddMembereffective.setForeground(Color.WHITE);
        AddMembereffective.setFont(new Font("Serif", Font.BOLD, 15));
        panel11.add(Nameof);
        panel11.add(NameMember);
        panel11.add(AddMembereffective);
        frame.add(panel11);
        frame.invalidate();
        frame.validate();
        frame.repaint();

    }

   


    //ShowChat 
    void ShowChat()
    {
        panel6.setVisible(false);
        panel8.setVisible(false);
        panel7.setVisible(false);
        panel9.setVisible(false);
        panel10.setVisible(false);
        panel12.setVisible(false);
        panel11.setVisible(false);
        panel12.setVisible(true);
        panel12.setBounds(0,200,1500,400);
        panel12.setLayout(null);
        panel12.setBackground(Color.WHITE);
        receiveBox.setBounds(200,220,1000,200);
        receiveBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        receiveBox.setEditable(false);
        sendBox.setFont(new Font("Serif", Font.BOLD, 15));
        sendBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sendBox.setLineWrap(true);
        panel12.add(receiveBox);
        sendBox.setBounds(200,450,1000,100);
        sendbutton.setBounds(1250,470,150,50);
        sendbutton.setBackground(Color.GRAY);
        sendbutton.setForeground(Color.WHITE);
        sendbutton.setFont(new Font("Serif", Font.BOLD, 15));
        panel12.add(sendbutton);
        panel12.add(sendBox);
        frame.add(panel12);
        frame.invalidate();
        frame.validate();
        frame.repaint();

        

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
       this.panel9.setVisible(value);
       this.panel10.setVisible(value);
       this.panel11.setVisible(value);
       this.panel12.setVisible(value);
       this.panel12.setVisible(value);
       NewScheda.setText("");
       Description.setText("");
       fromtxt.setText("");
       totxt.setText("");
       card_nametxt.setText("");
       nametxt.setText("");
       descriptiontxt.setText("");
       statetxt.setText("");
       Historytxt.setText("");
       NameMember.setText("");
       receiveBox.setText("");
       sendBox.setText("");
       list3.removeAll();
       list4.removeAll();
   }




   public void setvisiblepanel4(boolean value)
   {
       this.panel4.setVisible(value);
   }
   



//ACTIONS HANDLER


   void SendMessage (ActionListener lis)
   {
    this.sendbutton.addActionListener(lis);
   }



   void ShowChatListener (ActionListener lis)
   {
       this.EnterChat.addActionListener(lis);
   }

    void AddMember (ActionListener lis)
   {
       this.AddMember.addActionListener(lis);
   }

   void GroupChat (ActionListener lis)
   {
       this.EnterChat.addActionListener(lis);
   }


   void MoveCard (ActionListener lis)
   {
       this.MoveCard.addActionListener(lis);
   }

   void EffectiveMoveCard (ActionListener lis)
   {
       this.changecard.addActionListener(lis);
   }

   void EffecctiveShowCardProps (ActionListener lis)
   {
       this.ShowCardProps.addActionListener(lis);
   }

   void AddEffectiveMember (ActionListener lis)
   {
       this.AddMembereffective.addActionListener(lis);
   }


   void ShowCardProperty (ActionListener lis)
   {
      this.ShowCardProperty.addActionListener(lis);
   }

   void ShowMembers (ActionListener lis)
   {
       this.ShowMembers.addActionListener(lis);
   }

   void ShowCardEvent (ActionListener lis)
   {
       this.ShowCards.addActionListener(lis);
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
