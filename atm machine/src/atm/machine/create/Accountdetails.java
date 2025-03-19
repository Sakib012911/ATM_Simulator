package atm.machine.create;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;
import atm.machine.*;

public class Accountdetails extends JFrame implements ActionListener {

        JRadioButton r1,r2,r3,r4;
        JButton s,c;
        String formno;
     public Accountdetails(String formno){

            this.formno = formno;

        JLabel l12 = new JLabel("Form No : ");
        l12.setFont(new Font("Raleway", Font.BOLD,14));
        l12.setBounds(700,10,100,30);
        add(l12);

        JLabel l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD,14));
        l13.setBounds(760,10,60,30);
        add(l13);
            JLabel l1 = new JLabel("Page 2:");
            l1.setFont(new Font("Raleway",Font.BOLD,22));
            l1.setBounds(280,40,400,40);
            add(l1);

            JLabel l2 = new JLabel("Account Details");
            l2.setFont(new Font("Raleway",Font.BOLD,22));
            l2.setBounds(280,70,400,40);
            add(l2);

            JLabel l3 = new JLabel("Account Type:");
            l3.setFont(new Font("Raleway",Font.BOLD,18));
            l3.setBounds(100,140,200,30);
            add(l3);

            r1 = new JRadioButton("Saving Account");
            r1.setFont(new Font("Raleway",Font.BOLD,16));
            r1.setBackground(new Color(215,252,252));
            r1.setBounds(100,180,150,30);
            add(r1);

            r2 = new JRadioButton("Fixed Deposit Account");
            r2.setFont(new Font("Raleway",Font.BOLD,16));
            r2.setBackground(new Color(215,252,252));
            r2.setBounds(350,180,300,30);
            add(r2);

            r3 = new JRadioButton("Current Account");
            r3.setFont(new Font("Raleway",Font.BOLD,16));
            r3.setBackground(new Color(215,252,252));
            r3.setBounds(100,220,250,30);
            add(r3);

            r4 = new JRadioButton("Recurring Deposit Account");
            r4.setFont(new Font("Raleway",Font.BOLD,16));
            r4.setBackground(new Color(215,252,252));
            r4.setBounds(350,220,250,30);
            add(r4);

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(r1);
            buttonGroup.add(r2);
            buttonGroup.add(r3);
            buttonGroup.add(r4);

            JLabel l4 = new JLabel("Card Number:");
            l4.setFont(new Font("Raleway",Font.BOLD,18));
            l4.setBounds(100,300,200,30);
            add(l4);

            JLabel l5 = new JLabel("(Your 16-digit Card Number)");
            l5.setFont(new Font("Raleway",Font.BOLD,12));
            l5.setBounds(100,330,200,20);
            add(l5);

            JLabel l6 = new JLabel("XXXX-XXXX-XXXX-4841");
            l6.setFont(new Font("Raleway",Font.BOLD,18));
            l6.setBounds(330,300,250,30);
            add(l6);

            JLabel l7 = new JLabel("(It would appear on atm card/cheque Book and Statements)");
            l7.setFont(new Font("Raleway",Font.BOLD,12));
            l7.setBounds(330,330,500,20);
            add(l7);

            JLabel l8 = new JLabel("PIN:");
            l8.setFont(new Font("Raleway",Font.BOLD,18));
            l8.setBounds(100,370,200,30);
            add(l8);

            JLabel l9 = new JLabel("XXXX");
            l9.setFont(new Font("Raleway",Font.BOLD,18));
            l9.setBounds(330,370,200,30);
            add(l9);

            JLabel l10 = new JLabel("(4-digit Password)");
            l10.setFont(new Font("Raleway",Font.BOLD,12));
            l10.setBounds(100,400,200,20);
            add(l10);


            JCheckBox c7 = new JCheckBox("I here by declare that the above entered details correct to the best of my knlowledge.",true);
            c7.setBackground(new Color(215,252,252));
            c7.setFont(new Font("Raleway",Font.BOLD,12));
            c7.setBounds(100,430,600,20);
            add(c7);




            s = new JButton("Submit");
            s.setFont(new Font("Raleway", Font.BOLD,14));
            s.setBackground(Color.BLACK);
            s.setForeground(Color.WHITE);
            s.setBounds(250,470,100,30);
            s.addActionListener(this);
            add(s);

            c = new JButton("Cancel");
            c.setFont(new Font("Raleway", Font.BOLD,14));
            c.setBackground(Color.BLACK);
            c.setForeground(Color.WHITE);
            c.setBounds(420,470,100,30);
            c.addActionListener(this);
            add(c);

            getContentPane().setBackground(new Color(215,252,252));
            setSize(850,550);
            setLayout(null);
            setLocation(400,150);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String atype = null;
            if (r1.isSelected()){
                atype = "Saving Account";
            } else if (r2.isSelected()) {
                atype ="Fixed Deposit Account";
            }else if (r3.isSelected()){
                atype ="Current Account";
            }else if (r4.isSelected()){
                atype = "Recurring Deposit Account";
            }

            Random ran = new Random();
            long first7 = (ran.nextLong() % 90000000L) + 1409963000000000L;
            String cardno = "" + Math.abs(first7);
            String card="xxxx";
            card+="-"+"xxxx";
            card+="-"+"xxxx";
            card+="-"+cardno.substring(12);

            long first5 = (ran.nextLong() % 900000000L) + 100000000L;
            String accno = "" + Math.abs(first5);

//            long first4=(ran.nextLong()%900L)+100L;
//            String accid=""+Math.abs(first4);
            temp c1=null;
            try {
                c1 = new temp();
                if (e.getSource()==s){
                    if (atype.equals("")){
                        JOptionPane.showMessageDialog(null,"Fill all the fields");
                    }else {
                        String q1 = "insert into accounts (user_id,account_number,acc_type,card_no) values(?,?,?,?)";
                        PreparedStatement pt= c1.connection.prepareStatement(q1);
//                        pt.setString(1,accid);
                        pt.setString(1,formno);
                        pt.setString(2,accno);
                        pt.setString(3,atype);
                        pt.setString(4,cardno);
                        pt.executeUpdate();
                        pt.close();
                        JOptionPane.showMessageDialog(null,"Card Number : "+card+"\n Account No : "+accno,  "Account Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (e.getSource()==c) {
                    String q= "delete from users where user_id='"+formno+"'";
                    c1.statement.executeUpdate(q);
                    System.exit(0);
                }

            }catch (Exception E){
                E.printStackTrace();
            }
            finally {
                c1.closeConnection();
                System.exit(0);
            }
        }
        public static void main(String []args){
         new Accountdetails("");
        }
}
