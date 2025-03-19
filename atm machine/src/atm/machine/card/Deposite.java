package atm.machine.card;

import atm.machine.Conn;
import atm.machine.PlayMusic;
import atm.machine.TransactionProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Deposite extends JFrame implements ActionListener {
    JLabel lable1,label2;
    JButton btn2,box,box1;
    JTextField amount;
    int userid;
//    int accountid;
    public Deposite(int  userid){
        super("DEPOSITE AMOUNT");
        this.userid=userid;
        box1=new JButton("");
        box1.setBackground(Color.GREEN);
        box1.setBounds(56,60,65,30);
        add(box1);

        box=new JButton("");
        box.setBackground(Color.GREEN);
        box.setBounds(575,60,65,30);
        add(box);


//        this.accountid=accountid;
//        this line shows star bank text
        lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        label2=new JLabel("500 to 1,00,000 Amount");
        label2.setForeground(Color.cyan);
        label2.setFont(new Font("AvantGrade",Font.BOLD,25));
        label2.setBounds(220,250,450,50);
        add(label2);

        JLabel labelname=new JLabel("ENTER AMOUNT");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(220,330,300,50);
        add(labelname);

        amount=new JTextField();
        amount.setFont(new Font("Raleway",Font.BOLD,14));
        amount.setBounds(265,380,150,30);
        add(amount);

        //  confirm button
        btn2=new JButton("Confirm");
        btn2.setForeground(Color.black);
        btn2.setFont(new Font("Arial",Font.BOLD,26));
        btn2.setBackground(Color.gray);
        btn2.setBounds(255,450,180,30);
        btn2.addActionListener(this);
        add(btn2);

//     this line adds  atm machine background image
        ImageIcon atmbackground=new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image atm= atmbackground.getImage().getScaledInstance(700,750,Image.SCALE_DEFAULT);
        ImageIcon atm1=new ImageIcon(atm);
        JLabel atmimage=new JLabel(atm1);
        atmimage.setBounds(0,0,700,750);
        add(atmimage);


        setLayout(null);
        setSize(718,750);
        setLocation(425,60);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int amountdeposite = Integer.parseInt(amount.getText());
        if (amountdeposite < 500 || amountdeposite > 100000) {
            JOptionPane.showMessageDialog(null, "Enter Only 500 to 100000 Rs", "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
            // Show transaction page
//            new EnterPin();
            new TransactionProcess();
            setVisible(false); // Hide current frame
            new Thread(() -> {
                new PlayMusic();
            }).start();
            // Create a 1-second delay using Timer
            Timer timer = new Timer(6000, _ -> {
                Conn c=null;
                try{
                       c=new Conn();
                       String s="select* from accounts where user_id=?";
                       PreparedStatement p=c.connection.prepareStatement(s);
                       p.setInt(1,userid);
                       ResultSet ss=p.executeQuery();
                       if (ss.next()){
                           int oldbalance=ss.getInt("balance");
                           int accounid=ss.getInt("account_id");
                           int newbalance=oldbalance+amountdeposite;
                           String k="update accounts set balance=? where user_id=?";
                           PreparedStatement y=c.connection.prepareStatement(k);
                           y.setInt(1,newbalance);
                           y.setInt(2,userid);
                           y.executeUpdate();
                           y.close();
                           String t = "insert into transactions (account_id,transaction_type,amount) values(?,?,?)";
                           PreparedStatement pt = c.connection.prepareStatement(t);
                           pt.setInt(1, accounid);
                           pt.setString(2,"Deposite");
                           pt.setInt(3,amountdeposite);
                           pt.executeUpdate();
                           pt.close();
                           JOptionPane.showMessageDialog(null,"Amount Deposite Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                           System.exit(0);
                       }else {
                           JOptionPane.showMessageDialog(null,"Something went wrong");
                       }
                       p.close();
                       ss.close();
                   }catch (Exception E){
                       E.printStackTrace();
                   }finally {
                    c.closeConnection();
                }
            });

            timer.setRepeats(false); // Run only once
            timer.start();
        }
    }


    public static void main(String[] args) {
        new Deposite(0);
    }
}
