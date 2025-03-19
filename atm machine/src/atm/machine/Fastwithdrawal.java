package atm.machine;

import atm.machine.card.PleaseWaitLoading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Fastwithdrawal extends JFrame implements ActionListener {
   JButton btn1000,btn1500,btncancle,btn5000,btn500,btn2000,box,box1;
   int userid;
   boolean simless;
//   this class accept two values simless or userid
    public Fastwithdrawal(int userid,boolean simless){
//       this line gives container name
        super("FAST WITHDRAWAL");
        this.userid=userid;
        this.simless=simless;
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

        // 1000rs withdrawn button
        btn1000=new JButton("1000");
        btn1000.setForeground(Color.cyan);
        btn1000.setFont(new Font("Arial",Font.BOLD,20));
        btn1000.setBackground(Color.gray);
        btn1000.setBounds(180,300,80,30);
        btn1000.addActionListener(this);
        add(btn1000);

        // 500rs withdrawn button
        btn500=new JButton("500");
        btn500.setForeground(Color.cyan);
        btn500.setFont(new Font("Arial",Font.BOLD,20));
        btn500.setBackground(Color.gray);
        btn500.setBounds(180,350,80,30);
        btn500.addActionListener(this);
        add(btn500);

        //   5000rs withdrawn button
        btn5000=new JButton("5000");
        btn5000.setForeground(Color.cyan);
        btn5000.setFont(new Font("Arial",Font.BOLD,20));
        btn5000.setBackground(Color.gray);
        btn5000.setBounds(430,300,80,30);
        btn5000.addActionListener(this);
        add(btn5000);

        //   1500rs withdrawn button
        btn1500=new JButton("1500");
        btn1500.setForeground(Color.cyan);
        btn1500.setFont(new Font("Arial",Font.BOLD,20));
        btn1500.setBackground(Color.gray);
        btn1500.setBounds(430,350,80,30);
        btn1500.addActionListener(this);
        add(btn1500);

        //   2000rs withdrawn button
        btn2000=new JButton("2000");
        btn2000.setForeground(Color.cyan);
        btn2000.setFont(new Font("Arial",Font.BOLD,20));
        btn2000.setBackground(Color.gray);
        btn2000.setBounds(430,400,80,30);
        btn2000.addActionListener(this);
        add(btn2000);

        //       cancle transaction button
        btncancle=new JButton("Cancle");
        btncancle.setForeground(Color.white);
        btncancle.setFont(new Font("Arial",Font.BOLD,20));
        btncancle.setBackground(Color.gray);
        btncancle.setBounds(425,460,100,30);
        btncancle.addActionListener(this);
        add(btncancle);

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
            if (e.getSource()==btncancle){
              System.exit(0);
            }else if (e.getSource()==btn2000){
                int amount =2000;
                withdrawn(amount);
            }
            else if (e.getSource()==btn1500){
                int amount =1500;
                withdrawn(amount);
            }
            else if (e.getSource()==btn5000){
                int amount =5000;
                withdrawn(amount);
            }
            else if (e.getSource()==btn1000){
                int amount =1000;
                withdrawn(amount);
            } else if (e.getSource()==btn500){
                int amount =500;
                withdrawn(amount);
            }
        }catch (Exception E){
            System.out.println(E);
        }
    }

    void withdrawn(int amou) {
        temp c=null;
        try {
            c = new temp();
            String k = "SELECT EMAIL FROM USERS WHERE USER_ID=?";
            String kk = "SELECT ACCOUNT_ID FROM ACCOUNTS WHERE USER_ID=?";
            PreparedStatement pttt = c.connection.prepareStatement(k);
            PreparedStatement ptt = c.connection.prepareStatement(kk);
            pttt.setInt(1, userid);
            ptt.setInt(1, userid);
            ResultSet sss = pttt.executeQuery();
            ResultSet acc = ptt.executeQuery();
            if (sss.next() && acc.next()) {
                String email = sss.getString("email");
                int accountid = acc.getInt("account_id");
                if (simless) {
                    PleaseWaitLoading loading= new PleaseWaitLoading();
                    setVisible(false);
                    SwingWorker<Void, Void> otpWorker = new SwingWorker<>() {
                        @Override
                        protected Void  doInBackground() throws Exception {
                            Otp o = new Otp();
                            o.sendOtp(email, userid);
                            return null;
                        }

                        @Override
                        protected void done() {
//                           this opens enterotp class
                            new EnterOtp(accountid, amou, email,false,"");
                            loading.dispose();
                        }
                    };
                    otpWorker.execute();

                } else {
                    new EnterPin(accountid,amou,email,false,0);
                    setVisible(false);
                }
            } else {
                System.out.println("email user id is incorrect");
            }
            pttt.close();
            ptt.close();
            sss.close();
            acc.close();
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            c.closeConnection();
        }
    }
    public static void main(String[] args) {
        new Fastwithdrawal(0,false);
    }
}
