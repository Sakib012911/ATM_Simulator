package atm.machine.card;

import atm.machine.Conn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MiniStatement extends JFrame implements ActionListener {
    int accountid;
    String cardno;
    JButton btn1;
    MiniStatement(int accountid,String cardno){
        this.accountid=accountid;
        this.cardno=cardno;
        setTitle("Mini Statement");
        setLayout(null);
        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(400, 500);
        setLocation(40, 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Mini Statement");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(100, 20, 200, 30);
        add(title);

        JLabel card = new JLabel("Card No: " + cardno.substring(0,4)+" "+"xxxx xxxx"+" "+cardno.substring(12,16));
        card.setBounds(20, 60, 300, 20);
        add(card);

        JTextArea statement = new JTextArea();
        statement.setBounds(20, 100, 350, 243);
        statement.setEditable(false);
        add(statement);

        btn1=new JButton("Cancle");
        btn1.setForeground(Color.cyan);
        btn1.setFont(new Font("Arial",Font.BOLD,20));
        btn1.setBackground(Color.gray);
        btn1.setBounds(230,350,130,30);
        btn1.addActionListener(this);
        add(btn1);
        Conn c=null;
        try {
            c=new Conn();
            String query = "SELECT amount, transaction_type, transaction_date FROM transactions WHERE account_id =? ORDER BY transaction_id DESC";
            PreparedStatement pt=c.connection.prepareStatement(query);
            pt.setInt(1,accountid);
            ResultSet rs = pt.executeQuery();
            StringBuilder data = new StringBuilder("Date\t"+"Time"+"\tAmount\tType\n");
            while (rs.next()) {
                data.append(rs.getString("transaction_date")).append("\t")
                        .append(rs.getInt("amount")).append("\t")
                        .append(rs.getString("transaction_type")).append("\n");
            }

            statement.setText(data.toString());
            pt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.closeConnection();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btn1){
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new MiniStatement(4,"1409962930670052");
    }
}
