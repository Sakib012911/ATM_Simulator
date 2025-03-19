package atm.machine.simless;

import atm.machine.Conn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Simlesslogin extends JFrame implements ActionListener {
    JLabel lable1;
    JButton btn2,box,box1;
    JTextField phone;

    public Simlesslogin(){
        super("Simless Transaction");

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

//        this shows enter mobile number text
        JLabel labelname=new JLabel("Enter Mobile No");
        labelname.setForeground(Color.white);
        labelname.setFont(new Font("Raleway",Font.BOLD,30));
        labelname.setBounds(230,330,300,50);
        add(labelname);

//       this is text box field
        phone=new JTextField();
        phone.setFont(new Font("Raleway",Font.BOLD,14));
        phone.setBounds(265,380,150,30);
        add(phone);

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

//        container layout
        setLayout(null);
        setSize(718,750);
        setLocation(425,60);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Conn c=null;
        String phonee=phone.getText();
//        checks number is less than 10 or greater than 10 digit
        if (phone.getText().length()>10 || phone.getText().length()<10){
            JOptionPane.showMessageDialog(null,"Phone number should only 10 digits","Alert",JOptionPane.ERROR_MESSAGE);
        }
        else {
//            this creats connections with  db and fetch userid from users table and
//            pass to the simlesstransaction class
            try {
                c=new Conn();
                String q="SELECT* FROM USERS WHERE PHONE=?";
                PreparedStatement ptt=c.connection.prepareStatement(q);
                 ptt.setString(1,phonee);
                ResultSet ss= ptt.executeQuery();
                if (ss.next()){
                     int userId = ss.getInt("user_id"); // Fetch user ID
                     new Simlesstransaction(userId);
                     setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(null,"INVALID NUMBER","Alert",JOptionPane.ERROR_MESSAGE);
                }
                    ptt.close();
                    ss.close();
            }catch (Exception E){
                E.printStackTrace();
            }finally {
                c.closeConnection();
            }
        }

    }

    public static void main(String[] args) {
        new Simlesslogin();
    }

}
