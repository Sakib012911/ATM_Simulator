package atm.machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class EnterOtp extends JFrame implements ActionListener {
    JLabel lable1;
    JButton btn2;
    JTextField otp;
    int accountid;
    int amount;
    String email;
    boolean issetpin;
    String pin;
    public EnterOtp(int  accounid,int amount,String email,boolean issetpin,String pin){
        super("Simless Transaction");
        this.accountid=accounid;
        this.amount=amount;
        this.email=email;
        this.issetpin=issetpin;
        this.pin=pin;

//        this line shows star bank text
        lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        JLabel labelname=new JLabel("ENTER OTP");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(260,330,300,50);
        add(labelname);

        otp=new JTextField();
        otp.setFont(new Font("Raleway",Font.BOLD,14));
        otp.setBounds(265,380,150,30);
        add(otp);

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
        int ootp=Integer.parseInt(otp.getText());

        if (otp.getText().length()>6 || otp.getText().length()<6){
            JOptionPane.showMessageDialog(null,"Enter Only 6 digits Otp","Alert",JOptionPane.ERROR_MESSAGE);
        }
        else {
            new TransactionProcess();
            setVisible(false);
            Timer timer=new Timer(10,_->{
                Otp o=new Otp();
                boolean result=o.verifyOtp(ootp,accountid);
                if (result){
                    if (issetpin){
                        temp c=null;
                        try {
                            c=new temp();
                            String ss="Update accounts set pin=? where account_id=?";
                            PreparedStatement ptt=c.connection.prepareStatement(ss);
                            ptt.setString(1,pin);
                            ptt.setInt(2,accountid);
                            ptt.executeUpdate();
                            ptt.close();
                            JOptionPane.showMessageDialog(null,"PIN SET SUCCESSFULLY","Message",JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }catch (Exception EEE){
                            EEE.printStackTrace();
                        }finally {
                            c.closeConnection();
                        }
                    }
                    else {
                        WithDrawal w = new WithDrawal();
                        if (w.withdrawnMethod(accountid, amount)) {
                            SwingWorker<Void, Void> emailWorker = new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    Email eee = new Email();
                                    eee.WithdrawnEmail(amount, email);
                                    new PlayMusic();// Send email without freezing UI
                                    return null;
                                }

                                @Override
                                protected void done() {
                                    // After email is sent, show success message
                                    JOptionPane.showMessageDialog(null, "Amount Withdrawn");
                                    System.exit(0);
                                }
                            };
                            emailWorker.execute();
                        } else {
                            JOptionPane.showMessageDialog(null, "INSUFFICENT FUNDS", "Warning", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    }
                } else  {
                    JOptionPane.showMessageDialog(null,"Time out","TIME OUT",JOptionPane.WARNING_MESSAGE);
                }
            });
            timer.setRepeats(false); // Run only once
            timer.start();

        }

    }

    public static void main(String[] args) {
        new EnterOtp(0,0,"",false,"");
    }
}
