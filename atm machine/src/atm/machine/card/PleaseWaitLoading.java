package atm.machine.card;

import javax.swing.*;
import java.awt.*;

public class PleaseWaitLoading extends JFrame {
    JLabel lable1,t,y;
    JButton box,box1;
    public PleaseWaitLoading(){
        super("ATM SIMULATOR");
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



        t=new JLabel("Please Wait...");
        t.setForeground(Color.white);
        t.setFont(new Font("AvantGrade",Font.BOLD,23));
        t.setBounds(270,315,500,50);
        add(t);


//     this line adds  atm machine background image
        ImageIcon loading=new ImageIcon(ClassLoader.getSystemResource("icon/15.gif"));
        Image atmm= loading.getImage().getScaledInstance(240,20,Image.SCALE_DEFAULT);
        ImageIcon atm12=new ImageIcon(atmm);
        JLabel atmimagee=new JLabel(atm12);
        atmimagee.setBounds(220,380,240,20);
        atmimagee.setOpaque(false);
        add(atmimagee);

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

    public static void main(String[] args) {
        new PleaseWaitLoading();
    }
}
