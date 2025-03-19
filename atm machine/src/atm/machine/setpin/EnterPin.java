package atm.machine.setpin;

import atm.machine.Conn;
import atm.machine.EnterOtp;
import atm.machine.Otp;
import atm.machine.TransactionProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EnterPin extends JFrame implements ActionListener {
    JLabel lable1;
    JButton btn2,box,box1;
    int accountid;
    JPasswordField pin;
    int userid;
    String email;
    String enterpin;
    public EnterPin(int userid,int  accounid){
        super("Enter Pin");
        this.accountid=accounid;
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
        lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        JLabel labelname=new JLabel("ENTER NEW PIN");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(230,330,300,50);
        add(labelname);

        pin=new JPasswordField();
        pin.setFont(new Font("Raleway",Font.BOLD,14));
        pin.setBounds(265,380,150,30);
        pin.setEchoChar('x');
        add(pin);

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
        String enterpin = new String(pin.getPassword());
        if (enterpin.length() < 4 || enterpin.length() > 6 || enterpin.length() == 5) {
            JOptionPane.showMessageDialog(null, "Enter Only 6 or 4 digits Pin", "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
                 TransactionProcess tr=new TransactionProcess();
                setVisible(false); // Hide current frame

            // Create a 1-mile second delay using Timer
            Timer timer = new Timer(10, _ -> {
                            // Run email sending in a separate thread
                            SwingWorker<Void, Void> emailWorker = new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    Conn c=null;
                                    try{
                                        c=new Conn();
                                        String s="Select email from users where user_id=?";
                                        PreparedStatement pt=c.connection.prepareStatement(s);
                                        pt.setInt(1,userid);
                                        ResultSet rr=pt.executeQuery();
                                        if (rr.next()){
                                            email=rr.getString("email");
                                        }else {
                                            System.out.println("something in set enterpin page");
                                        }
                                        pt.close();
                                        rr.close();
                                    }catch (Exception EE){
                                        EE.printStackTrace();
                                    }finally {
                                        c.closeConnection();
                                    }
                                    Otp o = new Otp();
                                    o.sendOtp(email, userid);
                                    return null;
                                }

                                @Override
                                protected void done() {
                                   new EnterOtp(accountid,0,email,true,enterpin);
                                  tr.dispose();
                                }
                            };
                            emailWorker.execute();
            });

            timer.setRepeats(false); // Run only once
            timer.start();
        }
    }


    public static void main(String[] args) {
        new EnterPin(0,0);
    }
}
