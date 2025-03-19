package atm.machine;

import atm.machine.card.CardTransaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewBalance extends JFrame implements ActionListener {
    JLabel lable1,t,y;
    public int balance;
    JButton btn2,box,box1;
    int userid;
    int accountid;
    String cardno;
    public ViewBalance(int balance,int userid,int accountid,String cardno){
        super("ATM SIMULATOR");
        this.balance=balance;
        this.userid=userid;
        this.accountid=accountid;
        this.cardno=cardno;
        String bb=Integer.toString(balance);

        box1=new JButton("");
        box1.setBackground(Color.GREEN);
        box1.setBounds(56,60,65,30);
        add(box1);
        box=new JButton("");
        box.setBackground(Color.GREEN);
        box.setBounds(575,60,65,30);
        add(box);
//        this line shows star bank text
        lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);



        t=new JLabel("Available Balance");
        t.setForeground(Color.white);
        t.setFont(new Font("AvantGrade",Font.BOLD,23));
        t.setBounds(250,315,500,50);
        add(t);

        JLabel y = new JLabel(bb);
        y.setForeground(Color.WHITE);
        y.setFont(new Font("AvantGarde", Font.BOLD, 19));
        y.setBounds(330, 350, 400, 50);
        add(y);

        //  confirm button
        btn2=new JButton("Back");
        btn2.setForeground(Color.black);
        btn2.setFont(new Font("Arial",Font.BOLD,26));
        btn2.setBackground(Color.gray);
        btn2.setBounds(265,450,150,30);
        btn2.addActionListener(this);
        add(btn2);

        ImageIcon atmbackground=new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image atm= atmbackground.getImage().getScaledInstance(700,750,Image.SCALE_DEFAULT);
        ImageIcon atm1=new ImageIcon(atm);
        JLabel atmimage=new JLabel(atm1);
        atmimage.setBounds(0,0,700,750);
        add(atmimage);

//        this is main layout container
        setLayout(null);
        setSize(718, 750);
        setLocation(425, 60);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btn2){
            new CardTransaction(userid,accountid,cardno);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ViewBalance(0,0,0,"");
    }
}
