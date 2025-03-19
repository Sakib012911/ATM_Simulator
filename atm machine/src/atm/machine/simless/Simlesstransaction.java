package atm.machine.simless;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import atm.machine.*;
public class Simlesstransaction extends JFrame implements ActionListener {
   JButton btn1,btn2,btn3,btn4,box,box1;
   int userid;
    public Simlesstransaction(int userid){
        super("SEMLESS TRANSACTION");
        this.userid=userid;

        box1=new JButton("");
        box1.setBackground(Color.GREEN);
        box1.setBounds(56,60,65,30);
        add(box1);
        box=new JButton("");
        box.setBackground(Color.GREEN);
        box.setBounds(575,60,65,30);
        add(box);
        //        this line shows star bank text
      JLabel  lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        // fast withdrawal page button
       btn1=new JButton("Fast Withdrawal");
        btn1.setForeground(Color.cyan);
        btn1.setFont(new Font("Arial",Font.BOLD,20));
        btn1.setBackground(Color.gray);
        btn1.setBounds(180,350,188,30);
        btn1.addActionListener(this);
        add(btn1);

        //   withdrawal button which opens withdrawal page
        btn2=new JButton("Withdrawal");
        btn2.setForeground(Color.cyan);
        btn2.setFont(new Font("Arial",Font.BOLD,20));
        btn2.setBackground(Color.gray);
        btn2.setBounds(180,400,190,30);
        btn2.addActionListener(this);
        add(btn2);

        //   balance inquery button
         btn3=new JButton("Balance");
        btn3.setForeground(Color.cyan);
        btn3.setFont(new Font("Arial",Font.BOLD,20));
        btn3.setBackground(Color.gray);
        btn3.setBounds(400,350,120,30);
        btn3.addActionListener(this);
        add(btn3);

        //    cancle transaction button
        btn4=new JButton("Cancle");
        btn4.setForeground(Color.white);
        btn4.setFont(new Font("Arial",Font.BOLD,20));
        btn4.setBackground(Color.gray);
        btn4.setBounds(425,460,100,30);
        btn4.addActionListener(this);
        add(btn4);

//     this line adds  atm machine background image
        ImageIcon atmbackground=new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image atm= atmbackground.getImage().getScaledInstance(700,750,Image.SCALE_DEFAULT);
        ImageIcon atm1=new ImageIcon(atm);
        JLabel atmimage=new JLabel(atm1);
        atmimage.setBounds(0,0,700,750);
        add(atmimage);


//        this is main layout container
        setLayout(null);
        setSize(718,750);
        setLocation(425,60);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource()==btn4){
                System.exit(0);
            }
            else if (e.getSource()==btn1){
                new Fastwithdrawal(userid,true);
                setVisible(false);
            }
            else if (e.getSource()==btn2){
                new WithDrawal(userid,true);
                setVisible(false);
            }  else if (e.getSource()==btn3){
                /* this create connection with db and checks balance form accounts table using userid
                */
            temp c=null;
            try {
                c=new temp();
                String s="select balance from accounts where user_id=?";
                PreparedStatement pp=c.connection.prepareStatement(s);
                pp.setInt(1,userid);
                ResultSet ss=pp.executeQuery();
                if (ss.next()) {
                    int avlbalance = ss.getInt("balance");
                    JOptionPane.showMessageDialog(null,"Available Balance :"+avlbalance,"Balance",JOptionPane.INFORMATION_MESSAGE);
                }
                pp.close();
                ss.close();
            }catch (Exception EE){
                EE.printStackTrace();
            }finally {
                c.closeConnection();
            }
            }
        }catch (Exception E){
            System.out.println(E);
        }
    }

    public static void main(String[] args) {
        new Simlesstransaction(0);
    }
}
