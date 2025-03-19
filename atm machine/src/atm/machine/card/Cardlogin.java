package atm.machine.card;

import atm.machine.temp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cardlogin extends JFrame implements ActionListener {
    JTextField card;
    JButton btn2,box,box1;
    public Cardlogin(){
        super("Card Login");
        box1=new JButton("");
        box1.setBackground(Color.GREEN);
        box1.setBounds(56,60,65,30);
        add(box1);

        box=new JButton("");
        box.setBackground(Color.GREEN);
        box.setBounds(575,60,65,30);
        add(box);
        //        this line shows star bank text
       JLabel lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        JLabel labelname=new JLabel("Insert Card ");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(260,330,300,50);
        add(labelname);

        card=new JTextField();
        card.setFont(new Font("Raleway",Font.BOLD,14));
        card.setBounds(265,380,150,30);
        add(card);

        //  confirm button
        btn2=new JButton("Confirm");
        btn2.setForeground(Color.black);
        btn2.setFont(new Font("Arial",Font.BOLD,26));
        btn2.setBackground(Color.gray);
        btn2.setBounds(265,450,150,30);
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

        if (card.getText().length()<16 || card.getText().length()>16){
            JOptionPane.showMessageDialog(null,"CARD number should only 16 digits","Alert",JOptionPane.ERROR_MESSAGE);
        }else {
            String cardno=card.getText();
               PleaseWaitLoading cardloading= new PleaseWaitLoading();
                setVisible(false);
                Timer timer=new Timer(1500,_->{
                    temp c=null;
                    try {
                        c = new temp();
                        String q = "SELECT* FROM ACCOUNTS WHERE CARD_NO=?";
                        PreparedStatement ptt = c.connection.prepareStatement(q);
                        ptt.setString(1, cardno);
                        ResultSet ss = ptt.executeQuery();
                        if (ss.next()) {
                            int userId = ss.getInt("user_id"); // Fetch user ID
                            int accountid=ss.getInt("account_id");
                            String  cardd=ss.getNString("card_no");
                            new CardTransaction(userId,accountid,cardd);
                            cardloading.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "INVALID CARD NUMBER", "Alert", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        ptt.close();
                        ss.close();
                    } catch (Exception E) {
                        E.printStackTrace();
                    } finally {
                        c.closeConnection();
                    }
                });
                    timer.setRepeats(false);
                    timer.start();
        }
    }

    public static void main(String[] args) {
        new Cardlogin();
    }
}
