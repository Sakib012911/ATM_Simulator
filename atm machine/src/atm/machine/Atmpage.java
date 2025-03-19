package atm.machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import atm.machine.simless.*;
import atm.machine.card.*;
public class Atmpage extends JFrame implements ActionListener {
    JLabel lable1;
    JButton btn1,btn2,btncancle,box1,box;
   public Atmpage(){
//        this gives name  to the heading
        super("Atm Machine Simulator");

       box1=new JButton("");
       box1.setBackground(Color.RED);
       box1.setBounds(56,60,65,30);
       add(box1);

       box=new JButton("");
       box.setBackground(Color.RED);
       box.setBounds(575,60,65,30);
       add(box);
//        this line shows star bank text
        lable1=new JLabel("STAR BANK");
        lable1.setForeground(Color.darkGray);
        lable1.setFont(new Font("AvantGrade",Font.BOLD,35));
        lable1.setBounds(250,130,450,50);
        add(lable1);

        //  Semless transactions button
        btn1=new JButton("Cardless");
        btn1.setForeground(Color.white);
        btn1.setFont(new Font("Arial",Font.BOLD,26));
        btn1.setBackground(Color.gray);
        btn1.setBounds(250,350,180,30);
        btn1.addActionListener(this);
        add(btn1);

        //  card transactions button
        btn2=new JButton("Insert Card");
        btn2.setForeground(Color.white);
        btn2.setFont(new Font("Arial",Font.BOLD,26));
        btn2.setBackground(Color.gray);
        btn2.setBounds(250,400,180,30);
        btn2.addActionListener(this);
        add(btn2);
//        cancle button
       btncancle=new JButton("Cancle");
       btncancle.setForeground(Color.white);
       btncancle.setFont(new Font("Arial",Font.BOLD,24));
       btncancle.setBackground(Color.gray);
       btncancle.setBounds(400,480,120,30);
       btncancle.addActionListener(this);
       add(btncancle);

//     this line adds  atm machine background image
        ImageIcon atmbackground=new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image atm= atmbackground.getImage().getScaledInstance(700,750,Image.SCALE_DEFAULT);
        ImageIcon atm1=new ImageIcon(atm);
        JLabel atmimage=new JLabel(atm1);
        atmimage.setBounds(0,0,700,750);
        add(atmimage);

//       JPanel redBoxPanel = new JPanel() {
//           @Override
//           protected void paintComponent(Graphics g) {
//               super.paintComponent(g);
//               g.setColor(Color.RED);
//               g.fillRect(50, 50, 500, 300); // x, y, width, height (Red box)
//           }
//       };
//       redBoxPanel.setBounds(400, 120, 120, 300); // Position the red box on the right side
//       redBoxPanel.setOpaque(false);
//       add(redBoxPanel);

//        this is main layout container
        setLayout(null);
        setSize(718,750);
        setLocation(425,60);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource()==btn1){
                new Simlesslogin();
                setVisible(false);
            } else if (e.getSource()==btn2) {
                new MainAtm();
                setVisible(false);
            }else if (e.getSource()==btncancle) {
                System.exit(0);
            }
        }catch (Exception E){
            System.out.println(E);
        }
    }

    public static void main(String[] args) {
        new Atmpage();
    }
}
