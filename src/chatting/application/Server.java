package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server() {
        
//        setting frame
        f.setLayout(null);
        
//        this is upper panel
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        f.add(p1);
        
//        adding back or exit image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
//        adding profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(50, 10, 50, 50);
        p1.add(profile);
        
//        adding video call image
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
//        adding video call image
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 30, 30);
        p1.add(phone);
        
//        adding name
        JLabel name = new JLabel("Bunty");
        name.setBounds(120, 10, 100, 50);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 18));
        p1.add(name);
        
        
//        main chat pannel where chats were displayed 
        a1 = new JPanel();
        a1.setBounds(5, 75, 425, 525);
        f.add(a1);
        
        
//        adding Text field
        text = new JTextField();
        text.setBounds(5, 610, 310, 40);
        f.add(text);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        
//        Creating Send Button
        JButton send = new JButton("Send");
        send.setBounds(320, 610, 110, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Arial", Font.BOLD, 16));
        f.add(send);
        
//        frame setting
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.getContentPane().setBackground(Color.white);
        f.setVisible(true);
        
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
        
            JPanel p2 = formatLabel(out); 
        
            a1.setLayout(new BorderLayout());
        
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
        
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
        
            dout.writeUTF(out);
        
            text.setText("");
        
            f.repaint();
            f.invalidate();
            f.validate();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static JPanel formatLabel(String out) {
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel(out);
        output.setFont(new Font("Arial", Font.PLAIN, 16));
        output.setBackground(new Color(31, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);
        
//        adding timestamp
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }
    
    public static void main(String[] args) {
         
        new Server();  
        
        try {
            
            ServerSocket skt = new ServerSocket(5555);
            
            while(true) {
                
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String message = din.readUTF();
//                    adding message at received (left) side
                    JPanel panel = formatLabel(message);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                    
                }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
 