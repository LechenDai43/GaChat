package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ProfileSubPanel extends JPanel {
    private JLabel header, birthday_l, city_l, hometown_l, occupation_l, interest_l;
    private JLabel birthday_c, city_c, hometown_c, occupation_c;
    private JTextArea interest_c;
    protected JLabel editor;
    private JScrollPane interest_p;
    private int id;

    public ProfileSubPanel(){
        this.setBounds(220,150,240,400);
        this.setOpaque(false);
        this.setLayout(null);

        //set up the editor button
        {
            editor = new JLabel("Edit Profile");
            editor.setBounds(25,350,80,30);
            editor.setHorizontalAlignment(JLabel.CENTER);
            editor.setVerticalAlignment(JLabel.CENTER);
            this.add(editor);
        }

        //set header
        {
            header = new JLabel("Personal Information");
            header.setFont(new Font("TimesNewRome", Font.BOLD, 20));
            header.setHorizontalAlignment(JLabel.CENTER);
            header.setVerticalAlignment(JLabel.CENTER);
            header.setBounds(5,5,230,40);
            this.add(header);
        }

        //set up the labels
        {
            birthday_l = new JLabel("Birthday:");
            birthday_l.setHorizontalAlignment(JLabel.RIGHT);
            birthday_l.setVerticalAlignment(JLabel.CENTER);
            birthday_l.setBounds(5,55,100,30);
            this.add(birthday_l);
            city_l = new JLabel("Current City:");
            city_l.setHorizontalAlignment(JLabel.RIGHT);
            city_l.setVerticalAlignment(JLabel.CENTER);
            city_l.setBounds(5,90,100,30);
            this.add(city_l);
            hometown_l = new JLabel("Home Town:");
            hometown_l.setHorizontalAlignment(JLabel.RIGHT);
            hometown_l.setVerticalAlignment(JLabel.CENTER);
            hometown_l.setBounds(5,125,100,30);
            this.add(hometown_l);
            occupation_l = new JLabel("Occupation:");
            occupation_l.setHorizontalAlignment(JLabel.RIGHT);
            occupation_l.setVerticalAlignment(JLabel.CENTER);
            occupation_l.setBounds(5,160,100,30);
            this.add(occupation_l);
            interest_l = new JLabel("Interest:");
            interest_l.setHorizontalAlignment(JLabel.RIGHT);
            interest_l.setVerticalAlignment(JLabel.CENTER);
            interest_l.setBounds(5,195,100,30);
            this.add(interest_l);
        }

        //set up the contents of each information
        {
            birthday_c = new JLabel("N/A");
            birthday_c.setHorizontalAlignment(JLabel.LEFT);
            birthday_c.setVerticalAlignment(JLabel.CENTER);
            birthday_c.setBounds(110,55,130,30);
            this.add(birthday_c);
            city_c = new JLabel("N/A");
            city_c.setHorizontalAlignment(JLabel.LEFT);
            city_c.setVerticalAlignment(JLabel.CENTER);
            city_c.setBounds(110,90,130,30);
            this.add(city_c);
            hometown_c = new JLabel("N/A");
            hometown_c.setHorizontalAlignment(JLabel.LEFT);
            hometown_c.setVerticalAlignment(JLabel.CENTER);
            hometown_c.setBounds(110,125,130,30);
            this.add(hometown_c);
            occupation_c = new JLabel("N/A");
            occupation_c.setHorizontalAlignment(JLabel.LEFT);
            occupation_c.setVerticalAlignment(JLabel.CENTER);
            occupation_c.setBounds(110,160,130,30);
            this.add(occupation_c);
        }

        //set up the interest scroll panel
        {
            interest_c = new JTextArea();
            interest_c.setEditable(false);
            interest_c.setMargin(new Insets(0, 0, 5, 5));
            interest_c.setBackground(new Color(0xdd, 0xff, 0xee));
            interest_c.setText("N/A");
            interest_p = new JScrollPane(interest_c);
            interest_p.setBounds(110,200,130,195);
            interest_p.setBackground(new Color(0xdd, 0xff, 0xee));
            interest_p.setBorder(new EmptyBorder(1,1,1,1));
            this.add(interest_p);
        }
    }

    public void recieveID(int ID){
        id = ID;
        ArrayList<String> info_list = GaChatDataClient.getProfile(id);
        if(info_list.size() > 0){
            birthday_c.setText(info_list.get(0));
            city_c.setText(info_list.get(1));
            hometown_c.setText(info_list.get(2));
            occupation_c.setText(info_list.get(3));
        }
        ArrayList<String> interest_list = GaChatDataClient.getInterest(id);
        for(int i = 0; i < interest_list.size(); i ++){
            interest_c.append(interest_list.get(i) + "\n");
        }
    }

}
