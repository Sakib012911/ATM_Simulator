package atm.machine;

import atm.machine.card.Deposite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EnterPin extends JFrame implements ActionListener {
    JLabel lable1;
    JButton btn2,box,box1;
    JPasswordField pin;
    int accountid;
    int amount;
    String email;
    boolean isdeposite;
    int userid;
    public EnterPin(int  accounid,int amount,String email,boolean isdeposite,int userid){
        super("CARD Transaction");
        this.accountid=accounid;
        this.amount=amount;
        this.email=email;
        this.isdeposite=isdeposite;
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

        JLabel labelname=new JLabel("ENTER PIN");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(260,330,300,50);
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
        if (enterpin.length() < 4 || enterpin.length() > 6 || enterpin.length()==5) {
            JOptionPane.showMessageDialog(null, "Enter Only 6 or 4 digits Pin", "Alert", JOptionPane.ERROR_MESSAGE);
        } else {

            // Show transaction page
            if (!isdeposite){
                new TransactionProcess();
                setVisible(false); // Hide current frame
            }

            // Create a 2-second delay using Timer
            Timer timer = new Timer(10, _ -> {
                boolean result = verifyPin(enterpin, accountid);
                if (result) {
                    if (isdeposite){
                        new Deposite(userid);
                        setVisible(false);
                    }
                    else {
                        WithDrawal w = new WithDrawal();
                        if (w.withdrawnMethod(accountid, amount)) {
                            // Run email sending in a separate thread
                            SwingWorker<Void, Void> emailWorker = new SwingWorker<>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    Email eee = new Email();
                                    eee.WithdrawnEmail(amount, email);// Send email without freezing UI
                                    new PlayMusic();
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
                        }
                    else {
                        JOptionPane.showMessageDialog(null, "INSUFFICIENT FUNDS", "Warning", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "INCORRECT PIN", "WRONG PIN", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            });

            timer.setRepeats(false); // Run only once
            timer.start();
        }
    }


    public static void main(String[] args) {
        new EnterPin(0,0,"",false,0);
    }
    public  boolean verifyPin(String enteredPin,int accountid){
        Conn c=null;
        try  {
            c=new Conn();
            String query = "SELECT pin  FROM accounts WHERE account_id = ?";

            PreparedStatement ps = c.connection.prepareStatement(query);
            ps.setInt(1, accountid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPin = rs.getString("pin");
                if (storedPin.equals(enteredPin)) {
                    return true;
                }
            }else {
                System.out.println("pin verify error");
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.closeConnection();

        }
        return false;
    }
}
