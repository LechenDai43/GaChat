package Client;

import javax.swing.*;
import java.awt.*;

public class EditorFrame extends JFrame {
    private int id;
    private JLabel name, gender, age, birthday, city, hometown, interest, occupation, sign, icon, icon_l;
    private JTextField gender_tf, age_tf, birthday_tf, city_tf, hometown_tf, occupation_tf;
    private JTextArea interest_ta, sign_ta;

    public EditorFrame(int Id){
        id = Id;
        String username = GaChatDataClient.getUsernameById(id);
        Container container = this.getContentPane();

        //set up the frame
        {
            this.setTitle("Edit Profile - " + username);
            this.setSize(660,500);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = this.getSize();
            this.setLocation((int)((screenSize.getWidth()-size.getWidth())/2),(int)((screenSize.getHeight()-size.getHeight())/2));
            this.setResizable(false);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.setLayout(null);
        }

        //set the container of the frame
        {
            container.setSize(660,500);
            container.setLayout(null);
            container.setBackground(Color.white);
        }

        //add labels onto the panel
        {
            name = new JLabel(username);
            name.setBounds(5,5,650,40);
            name.setFont(new Font("TimesNewRome", Font.BOLD, 20));
            name.setHorizontalAlignment(JLabel.CENTER);
            name.setVerticalAlignment(JLabel.CENTER);
            name.setOpaque(false);

            gender = new JLabel("Gender:");
            gender.setBounds(5,250,75,20);
            gender.setHorizontalAlignment(JLabel.RIGHT);
            gender.setOpaque(false);

            age = new JLabel("Age:");
            age.setBounds(5,280,75,20);
            age.setHorizontalAlignment(JLabel.RIGHT);
            age.setOpaque(false);

            birthday = new JLabel("Birth Date:");
            birthday = new JLabel("Age:");
            birthday.setBounds(5,280,75,20);
            birthday.setHorizontalAlignment(JLabel.RIGHT);
            birthday.setOpaque(false);
        }
    }
}
