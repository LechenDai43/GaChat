package Client;

import org.jdesktop.swingx.border.DropShadowBorder;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import static java.awt.Color.*;

public class RootPanel {
    private  int id;
    private ImageIcon old_ii,new_ii;
    private JLabel name, icon, profile, friends, status, forum, gender1, gender2, age1, age2, email1,email2, logout;
    private JTextArea sign;
    private JPanel sub, right;
    private ProfileSubPanel psp;
    private ProfileRightPanel prp;
    private JFrame frame;
    private String user;
    private EmailIcon email;
    private LogInPanel panel;

    public RootPanel(JFrame frm, int Id, LogInPanel lip) {
        id = Id;
        panel = lip;
        user = GaChatDataClient.getUsernameById(id);
        frame = frm;
        frame.setTitle(user + "-GaChat");
        frame.setSize(950,600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        frame.setLocation((int)((screenSize.getWidth()-size.getWidth())/2),(int)((screenSize.getHeight()-size.getHeight())/2));
        Container container = frame.getContentPane();
        container.removeAll();
        container.setBackground(new Color(0xdd,0xff,0xee));
        this.init(container);
        autoClick();
    }

    private void autoClick(){
        MouseEvent me = new MouseEvent(profile,MouseEvent.MOUSE_CLICKED,1000,0,10,10,1,false);
        TagListener tl = new TagListener();
        tl.mouseClicked(me);
    }

    private void testIcon(){
        try {
            File imgfile = new File(".\\148705-essential-collection.png");
            BufferedImage img;
            img = ImageIO.read(imgfile);
            Image image = img.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH);
            old_ii = new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init(Container container) {
        //get information from database
        getUserBasicInformation();

        //set up the image icon
        {
            /*remove this afterword*/
            testIcon(); //remove this afterword
            icon = new JLabel();
            icon.setBounds(10, 10, 200, 200);
            Image im = old_ii.getImage();
            Image my_im = im.getScaledInstance(icon.getWidth(), icon.getHeight(), Image.SCALE_SMOOTH);
            new_ii = new ImageIcon(my_im);
            icon.setIcon(new_ii);
            container.add(icon);
        }

        //set up signature
        {
            sign.setFont(new Font("TimesNewRome", Font.ITALIC, 13));
            sign.setBounds(10, 205, 200, 100);
            sign.setBackground(new Color(0xdd, 0xff, 0xee));
            sign.setEditable(false);
            sign.setMargin(new Insets(5, 5, 5, 5));
            sign.setBorder(BorderFactory.createCompoundBorder(
                    sign.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            container.add(sign);
        }

        //set the name label
        {
            name = new JLabel();
            name.setText(user);
            name.setBounds(220, 10, 180, 70);
            name.setHorizontalAlignment(JLabel.CENTER);
            name.setVerticalAlignment(JLabel.CENTER);
            name.setFont(new Font("TimeNewRome", Font.BOLD, 20));
            container.add(name);
        }

        //set the information label
        {
            gender1 = new JLabel("Gender:");
            gender1.setBounds(220, 70, 65, 20);
            gender1.setHorizontalAlignment(JLabel.RIGHT);
            age1 = new JLabel("Age:");
            age1.setBounds(220, 90, 65, 20);
            age1.setHorizontalAlignment(JLabel.RIGHT);
            email1 = new JLabel("E-mail:");
            email1.setBounds(220, 110, 65, 20);
            email1.setHorizontalAlignment(JLabel.RIGHT);
            container.add(gender1);
            container.add(age1);
            container.add(email1);
        }

        //set the information content
        {
        gender2.setBounds(290,70,100,20);
        gender2.setHorizontalAlignment(JLabel.LEFT);
        age2.setBounds(290,90,100,20);
        age2.setHorizontalAlignment(JLabel.LEFT);
        email2.setBounds(290,110,160,20);
        email2.setHorizontalAlignment(JLabel.LEFT);
        container.add(gender2);
        container.add(age2);
        container.add(email2);
        }

        //set the select menu
        {
            profile = new JLabel("Profile");
            profile.setHorizontalAlignment(JLabel.CENTER);
            profile.setVerticalAlignment(JLabel.CENTER);
            profile.setBounds(20, 320, 180, 30);
            profile.setBorder(new MatteBorder(2, 2, 2, 2, black));
            profile.addMouseListener(new TagListener());
            container.add(profile);
            friends = new JLabel("Friends");
            friends.setHorizontalAlignment(JLabel.CENTER);
            friends.setVerticalAlignment(JLabel.CENTER);
            friends.setBounds(20, 370, 180, 30);
            friends.setBorder(new MatteBorder(2, 2, 2, 2, black));
            friends.addMouseListener(new TagListener());
            container.add(friends);
            status = new JLabel("Status");
            status.setHorizontalAlignment(JLabel.CENTER);
            status.setVerticalAlignment(JLabel.CENTER);
            status.setBounds(20, 420, 180, 30);
            status.setBorder(new MatteBorder(2, 2, 2, 2, black));
            status.addMouseListener(new TagListener());
            container.add(status);
            forum = new JLabel("Forum");
            forum.setHorizontalAlignment(JLabel.CENTER);
            forum.setVerticalAlignment(JLabel.CENTER);
            forum.setBounds(20, 470, 180, 30);
            forum.setBorder(new MatteBorder(2, 2, 2, 2, black));
            forum.addMouseListener(new TagListener());
            container.add(forum);
        }

        //set email icon
        {
            email.setBounds(405, 10, 45, 45);
            email.setIcon();
            container.add(email);
        }

        //set up the log out button
        {
            logout = new JLabel("Log Out");
            logout.setBounds(405, 60, 50, 20);
            logout.setFont(new Font("TimesNewRome", 0, 12));
            logout.setHorizontalAlignment(JLabel.CENTER);
            logout.setVerticalAlignment(JLabel.CENTER);
            logout.setBorder(new MatteBorder(1, 1, 1, 1, darkGray));
            logout.addMouseListener(new LogOutListener());
            container.add(logout);
        }

        //set up the size of the sub and the right panel
        {
            sub = new JPanel();
            sub.setBounds(220,150,240,400);
            sub.setOpaque(false);
            container.add(sub);
            right = new JPanel();
            right.setBounds(470,10,450,540);
            right.setOpaque(false);
            container.add(right);
        }

        //initialize all the sub and right panel
        {
            psp = new ProfileSubPanel();
            prp = new ProfileRightPanel();
        }
    }

    private void logOut() {
        panel.repack();
    }

    private void getUserBasicInformation() {
        sign = new JTextArea();
        gender2 = new JLabel();
        age2 = new JLabel();
        email2 = new JLabel();
        String forsign = "";
        String from = "";

        ArrayList<String> al = GaChatDataClient.getBasicProfile(id);
        gender2.setText(al.get(0));
        age2.setText(al.get(1));
        from = al.get(2);
        email2.setText(from);
        forsign = al.get(3);

        email = new EmailIcon(from);
        StringTokenizer tokenizer = new StringTokenizer(forsign);
        String real_sign = "";
        int count = 0;
        while(tokenizer.hasMoreElements()){
            String token = tokenizer.nextToken();
            if(count + token.length() < 33){
                real_sign += token + " ";
                count += token.length() + 1;
            }else{
                real_sign += "\n" + token + " ";
                count = token.length() + 1;
            }
        }
        sign.setText(real_sign);
    }

    private void switchToProfile() {
        psp.recieveID(id);
        sub = psp;
        frame.getContentPane().add(sub);
        prp.addSchoolJobById(id);
        right = prp;
        frame.getContentPane().add(right);
    }

    private class TagListener implements MouseListener {
        private Border original = new MatteBorder(2,2,2,2, black),
                       updated = new MatteBorder(2,2,2,2, red);
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            profile.setBorder(original);
            friends.setBorder(original);
            status.setBorder(original);
            forum.setBorder(original);
            String text = source.getText();
            if(text.equals("Profile")){
                profile.setBorder(updated);
                switchToProfile();
            }else if(text.equals("Friends")){
                friends.setBorder(updated);

            }else if(text.equals("Status")){
                status.setBorder(updated);

            }else{
                forum.setBorder(updated);

            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(2,2,2,2, red),
                    new EtchedBorder(EtchedBorder.LOWERED,gray,darkGray)));

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(original);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBounds(source.getX() - 3, source.getY() - 3, source.getWidth() + 6, source.getHeight() + 6);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBounds(source.getX() + 3, source.getY() + 3, source.getWidth() - 6, source.getHeight() - 6);

        }
    }

    private class LogOutListener implements MouseListener {
        private Border original = new MatteBorder(1, 1, 1, 1, darkGray),
                update = new DropShadowBorder(Color.black, 2, 0.5f,2,true,true,true,true),
                clicked = new MatteBorder(1,1,1,1,new Color(0,0,100));
        @Override
        public void mouseClicked(MouseEvent e) {
            logOut();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(new CompoundBorder(update,clicked));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(update);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(update);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel source = (JLabel)e.getSource();
            source.setBorder(original);
        }
    }
}
