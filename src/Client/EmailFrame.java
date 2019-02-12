package Client;

/**
 * javax.mail.MessagingException: 554 Shunned connection - only the QUIT command will be accepted.
 */

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.*;

public class EmailFrame extends JFrame {
    private String from;
    private String to;
    private JTextField title, receiver;
    private JLabel ltitle,lreveiver, lbody;
    private JTextArea body;
    private JButton send;

    public EmailFrame(String from){
        this.from = from;
        init();
    }

    public EmailFrame(String from, String to){
        this.from = from;
        this.to = to;
        init();
    }

    private void init(){
        Container container = this.getContentPane();
        //set properties of the frame
        {
            this.setSize(500, 650);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(null);
            this.setTitle(from + "- ");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = this.getSize();
            this.setLocation((int) ((screenSize.getWidth() - size.getWidth()) / 2), (int) ((screenSize.getHeight() - size.getHeight()) / 2));
        }

        //set attributes of the content panel
        {
            container.setBackground(Color.white);
        }

        //set up the title bar
        {
            ltitle = new JLabel("Title:");
            ltitle.setBounds(10,10,460,20);
            title = new JTextField();
            title.setBounds(10, 35, 460, 25);
            title.addKeyListener(new ReceiverListener(this));
            container.add(ltitle);
            container.add(title);
        }

        //set up the receiver
        {
            lreveiver = new JLabel("Send to:");
            lreveiver.setBounds(10,70,460,20);
            receiver = new JTextField();
            receiver.setBounds(10,95,460,25);
            container.add(lreveiver);
            container.add(receiver);
        }

        //set up send button
        {
            send = new JButton("Send");
            send.setBounds(20,550,100,20);
            send.addMouseListener(new SendListener());
            container.add(send);
        }

        //set up the body
        {
            lbody = new JLabel("Your content:");
            lbody.setBounds(10,120,460,20);
            body = new JTextArea();
            body.setBorder(new EmptyBorder(3,3,3,3));
            body.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(body);
            scrollPane.setBounds(10,145,460,400);
            container.add(lbody);
            container.add(scrollPane);
        }



    }

    private class SendListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            to = receiver.getText();
            String[] list;
            if(to.contains(",")){
                list = to.split(",");
            }else if(to.contains(",")){
                list = to.split(";");
            }else{
                list = to.split(" ");
            }
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host","localhost");
            Session session = Session.getDefaultInstance(properties);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setSubject(title.getText());
                message.setText(body.getText());
                for(String one_to : list){
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(one_to));
                }
                Transport.send(message);
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class ReceiverListener implements KeyListener {
        private EmailFrame ef;

        public ReceiverListener(EmailFrame emailFrame) {
            ef = emailFrame;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            String n_t = from + "-" + title.getText();
            ef.setTitle(n_t);
        }
    }
}
