package atm.machine.create;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;
import atm.machine.*;

public class User extends JFrame implements ActionListener {
    JButton next;

    JTextField textName ,textEmail,textPhone;
    Random ran = new Random();
    long first4 =(ran.nextLong() % 9000L) +1000L;
    String first = " " + Math.abs(first4);
    User(){

        JLabel label1 = new JLabel("APPLICATION FORM NO."+ first);
        label1.setBounds(160,20,600,40);
        label1.setFont(new Font("Raleway",Font.BOLD,38));
        add(label1);

        JLabel label2 = new JLabel("Page 1");
        label2.setFont(new Font("Ralway",Font.BOLD, 22));
        label2.setBounds(330,70,600,30);
        add(label2);

        JLabel label3 = new JLabel("Personal Details");
        label3.setFont(new Font("Raleway", Font.BOLD,22));
        label3.setBounds(290,90,600,30);
        add(label3);

        JLabel labelName = new JLabel("Name :");
        labelName.setFont(new Font("Raleway", Font.BOLD, 20));
        labelName.setBounds(100,190,100,30);
        add(labelName);

        textName = new JTextField();
        textName.setFont(new Font("Raleway",Font.BOLD, 14));
        textName.setBounds(300,190,400,30);
        add(textName);


        JLabel labelEmail = new JLabel("Email address :");
        labelEmail.setFont(new Font("Raleway", Font.BOLD, 20));
        labelEmail.setBounds(100,390,200,30);
        add(labelEmail);

        textEmail = new JTextField();
        textEmail.setFont(new Font("Raleway",Font.BOLD, 14));
        textEmail.setBounds(300,390,400,30);
        add(textEmail);

        JLabel labelPhone = new JLabel("Phone Number :");
        labelPhone.setFont(new Font("Raleway", Font.BOLD, 20));
        labelPhone.setBounds(100,290,200,30);
        add(labelPhone);

        textPhone = new JTextField();
        textPhone.setFont(new Font("Raleway",Font.BOLD, 14));
        textPhone.setBounds(300,290,400,30);
        add(textPhone);

        next = new JButton("Next");
        next.setFont(new Font("Raleway",Font.BOLD, 14));
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setBounds(620,450,80,30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(new Color(222,255,228));
        setLayout(null);
        setSize(850,550);
        setLocation(360,140);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        temp c=null;
        String formno = first;
        String name = textName.getText();
        String email = textEmail.getText();
        String Phone =textPhone.getText();

        try{
            c = new temp();
            if (textName.getText().equals("") || textEmail.getText().equals("") || textPhone.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Fill all the fields");
            } else if (textPhone.getText().length()>10 || textPhone.getText().length()<10) {
                JOptionPane.showMessageDialog(null,"Phone number should only 10 digits","Alert",JOptionPane.ERROR_MESSAGE);

            } else {
//                this is direct sql injections
//                String q="insert into users (user_id,name,email,phone) values('"+formno+"','"+name+"','"+email+"','"+Phone+"')";
//                c.statement.executeQuery(q);

//                this is optimize way
                String q = "insert into users (user_id,name,email,phone) values(?,?,?,?)";
                 PreparedStatement prpst= c.connection.prepareStatement(q);
                 prpst.setString(1,formno);
                prpst.setString(2,name);
                prpst.setString(3,email);
                prpst.setString(4,Phone);
                prpst.executeUpdate();
                prpst.close();
                new Accountdetails(formno);
                setVisible(false);
            }

        }
        catch (Exception E){
            E.printStackTrace();
        }
        finally {
            c.closeConnection();

        }
    }

    public static void main(String[] args) {
        new User();
    }
}