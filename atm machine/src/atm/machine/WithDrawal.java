package atm.machine;

import atm.machine.card.PleaseWaitLoading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithDrawal extends JFrame implements ActionListener{
        JLabel lable1;
        JButton btn2,box,box1;
        JTextField amount;
        int userid;
        boolean simless;
        public WithDrawal(){};
        public WithDrawal(int userid,boolean simless){
            super("Withdrawan");
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
            lable1=new JLabel("STAR BANK");
            lable1.setForeground(Color.darkGray);
            lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
            lable1.setBounds(250,130,450,50);
            add(lable1);

            lable1=new JLabel("500 to 10,000 Amount");
            lable1.setForeground(Color.cyan);
            lable1.setFont(new Font("AvantGrade",Font.BOLD,25));
            lable1.setBounds(220,250,450,50);
            add(lable1);

            JLabel labelname=new JLabel("Amount");
            labelname.setForeground(Color.white);
            labelname.setFont(new Font("Raleway",Font.BOLD,30));
            labelname.setBounds(280,330,300,50);
            add(labelname);

            amount=new JTextField();
            amount.setFont(new Font("Raleway",Font.BOLD,14));
            amount.setBounds(265,380,150,30);
            add(amount);

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
            temp c=null;
            int amou=Integer.parseInt(amount.getText());
            if (amou>10000){
                JOptionPane.showMessageDialog(null,"Only  10000 Withdrawan at a time","Alert",JOptionPane.ERROR_MESSAGE);
            } else if (amou<500) {
                JOptionPane.showMessageDialog(null,"Amount should Above 500","Alert",JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    c=new temp();
                    String k="SELECT EMAIL FROM USERS WHERE USER_ID=?";
                    String kk="SELECT ACCOUNT_ID FROM ACCOUNTS WHERE USER_ID=?";
                    PreparedStatement pttt=c.connection.prepareStatement(k);
                    PreparedStatement ptt=c.connection.prepareStatement(kk);
                    pttt.setInt(1,userid);
                    ptt.setInt(1,userid);
                    ResultSet sss= pttt.executeQuery();
                    ResultSet acc=ptt.executeQuery();
                        if (sss.next() && acc.next()){
                            String email=sss.getString("email");
                            int accountid=acc.getInt("account_id");
                            if (simless){
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
                                        // After email is sent, show success message
                                        new EnterOtp(accountid, amou, email,false,"");
                                        loading.dispose();
                                    }
                                };
                                otpWorker.execute();
                            }
                          else {
//                               enter pin;
                                new EnterPin(accountid,amou,email,false,0);
                                setVisible(false);
                            }
                                }
                                else {
                                    System.out.println("email user id is incorrect");
                                }
                    pttt.close();
                    ptt.close();
                    sss.close();
                    acc.close();
                }catch (Exception E){
                    E.printStackTrace();
                }finally {
                    c.closeConnection();
                }
            }

        }

    public boolean withdrawnMethod(int accid,int amount){
            temp c=null;
            try{
                c=new temp();
                String s="select balance from accounts where account_id=?";
                PreparedStatement pp=c.connection.prepareStatement(s);
                pp.setInt(1,accid);
                ResultSet ss=pp.executeQuery();
                if (ss.next()){
                    int avlbalance=ss.getInt("balance");
                    if (avlbalance<amount){
                        return false;
                    }else {
                        int newbalance = avlbalance - amount;
                        String k = "update accounts set balance=? where account_id=?";
                        PreparedStatement p = c.connection.prepareStatement(k);
                        p.setInt(1, newbalance);
                        p.setInt(2, accid);
                        p.executeUpdate();

                        String t = "insert into transactions (account_id,transaction_type,amount) values(?,?,?)";
                        PreparedStatement pt = c.connection.prepareStatement(t);
                        pt.setInt(1, accid);
                        pt.setString(2,"Withdrawn");
                        pt.setInt(3,amount);
                        pt.executeUpdate();

                        p.close();
                        pt.close();
                    }
                }
                pp.close();
                ss.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                c.closeConnection();
            }
            return true;
    }

    public static void main(String[] args) {
        new WithDrawal(1,false);
    }

    }

