package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel{
    private JLabel title /*the label to welcomes new user to register new account*/,
            username_l /*the label of username text field*/,
            email_l /*the label of email address text field*/,
            password_l /*the label of password text field*/,
            confirm_l /*the label of confirmation of password text field*/,
            username_e /*the error massage related to username, if any*/,
            email_e /*the error massage related to email address, if any*/,
            password_e /*the error massage related to password, if any*/,
            confirm_e /*the error massafe related to the confirmation of password, if any*/;
    private JTextField username_t /*the text field to enter username*/,
            email_t /*the text field to enter the email address*/;
    private JPasswordField password_t /*the password field to enter password*/,
            confirm_t /*the password field to confirm the password*/;
    private JButton submit /*the button to submit all the new information to create a new account*/,
            cancel /*the button to cancel the registration and go back to the logging in window*/;
    private JFrame frame /*the frame that holds LogInPanel, RegisterPanel, and RootPanel*/;
    private LogInPanel panel /*the logging in window*/;

    public RegisterPanel(JFrame frm, LogInPanel lip) {
        frame = frm;
        panel = lip;
        Container container = frame.getContentPane();
        this.initi(container);
    }

    /**
     * this function receives the content panel of the frame as parameter
     * this function set up the attributes of the frame and the content panel of the frame
     * this function initiates and adds the following components to the content panel:
     * 1. the title massage which welcomes the user to register for a new account
     *    #global $title
     * 2.the email information field which requires a email address for the new account
     *   #global $emial_l $email_t $email_e
     * 3.
     * @param container
     */
    private void initi(Container container) {
        //set up the attributes of the frame
        {
            frame.setTitle("Register for new account");
            frame.setSize(450,450);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = frame.getSize();
            frame.setLocation((int)((screenSize.getWidth()-size.getWidth())/2),(int)((screenSize.getHeight()-size.getHeight())/2));
        }

        //set up the attributes of the content panel
        {
            container.removeAll();
            container.setBackground(new Color(0xbb, 0xbb, 0xff));
        }

        //set up the title jlabel
        {
            title = new JLabel("Welcome to Register GaChat Account");
            title.setFont(new Font("TimesNewRome", Font.PLAIN, 20));
            title.setHorizontalAlignment(JLabel.CENTER);
            title.setVerticalAlignment(JLabel.CENTER);
            title.setBounds(20, 10, 410, 40);
            container.add(title);
        }

        //set up the email field
        {
            email_l = new JLabel("Enter your e-mail address:");
            email_l.setBounds(50,60,400,20);
            container.add(email_l);
            email_t = new JTextField();
            email_t.setBounds(50,85,330,25);
            container.add(email_t);
            email_e = new JLabel("this is an error about email");
            email_e.setForeground(Color.red);
            email_e.setFont(new Font("TimesNewRome", Font.ITALIC,9));
            email_e.setBounds(50,115,400,10);
            email_e.setVisible(false);
            container.add(email_e);
        }

        //set up the username field
        {
            username_l = new JLabel("Enter your username:");
            username_l.setBounds(50,130,400,20);
            container.add(username_l);
            username_t = new JTextField();
            username_t.setBounds(50,155,330,25);
            container.add(username_t);
            username_e = new JLabel("this is an error about username");
            username_e.setForeground(Color.red);
            username_e.setFont(new Font("TimesNewRome", Font.ITALIC,9));
            username_e.setBounds(50,185,400,10);
            username_e.setVisible(false);
            container.add(username_e);
        }

        //set up the password field
        {
            password_l = new JLabel("Enter your password (6~30):");
            password_l.setBounds(50,200,400,20);
            container.add(password_l);
            password_t = new JPasswordField();
            password_t.setBounds(50,225,330,25);
            container.add(password_t);
            password_e = new JLabel("this is an error about password");
            password_e.setForeground(Color.red);
            password_e.setFont(new Font("TimesNewRome", Font.ITALIC,9));
            password_e.setBounds(50,255,400,10);
            password_e.setVisible(false);
            container.add(password_e);
        }

        //set up the confirmation of the password
        {
            confirm_l = new JLabel("Re-Enter your password:");
            confirm_l.setBounds(50,270,400,20);
            container.add(confirm_l);
            confirm_t = new JPasswordField();
            confirm_t.setBounds(50,295,330,25);
            container.add(confirm_t);
            confirm_e = new JLabel("this is an error about password confrimation");
            confirm_e.setForeground(Color.red);
            confirm_e.setFont(new Font("TimesNewRome", Font.ITALIC,9));
            confirm_e.setBounds(50,325,400,10);
            confirm_e.setVisible(false);
            container.add(confirm_e);
        }

        //set upt the submit button
        {
            submit = new JButton("Register");
            submit.addActionListener(new RegisterSubmissionListener());
            submit.setBounds(75,350,100,30);
            container.add(submit);
        }

        //set up the cancel button
        {
            cancel = new JButton("Cancel");
            cancel.addActionListener(new CancelRegisterListener());
            cancel.setBounds(275,350,100,30);
            container.add(cancel);
        }
    }

    /**
     * this function is for canceling the registration and going back the LogInPanel
     */
    private void cancelRegister(){
        panel.repack();
    }

    /**
     * this function create a new root panel
     * @param id the user_id of the new account
     */
    private void openRootPanel(int id) {
        RootPanel panel = new RootPanel(frame,id, this.panel);
    }

    /**
     * this class is for clicking the submit button
     * #global $submit
     */
    private class RegisterSubmissionListener implements ActionListener {

        @Override
        /**
         * this function will first check the validness of all the information entered by the user
         * if any of the information is invalid, the error massage will shown and the registration process will stop
         * if there are no problems, the information will be submitted into the database to create a new account
         */
        public void actionPerformed(ActionEvent e) {
            //the key boolean to remark if there are any errors
            boolean doProcess = true;

            //check if there are any problem with the email address
            if(GaChatDataClient.checkEmailDup(email_t.getText())){
                email_e.setText("This e-mail has been registered.");
                email_e.setVisible(true);
                doProcess = false;
            }else if(! (email_t.getText().contains("@") || email_t.getText().startsWith("@") || email_t.getText().endsWith("@")) ){
                email_e.setText("This e-mail address is invalid.");
                email_e.setVisible(true);
                doProcess = false;
            }else if(email_t.getText().length() == 0) {
                email_e.setText("E-mail address cannot be empty.");
                email_e.setVisible(true);
                doProcess = false;
            }else{
                email_e.setVisible(false);
            }

            //check if there are any problems with the username
            if(GaChatDataClient.checkUserNameDup(username_t.getText())){
                username_e.setText("This username has been used.");
                username_e.setVisible(false);
                doProcess = false;
            }else if (username_t.getText().length() == 0){
                username_e.setText("Username cannot be empty.");
                username_e.setVisible(false);
                doProcess = false;
            }else{
                username_e.setVisible(false);
            }

            //check if there are any problems with the password
            if(password_t.getPassword().length < 6){
                password_e.setText("This password is too short.");
                password_e.setVisible(true);
                doProcess = false;
            }else if(password_t.getPassword().length > 30){
                password_e.setText("This password is too long.");
                password_e.setVisible(true);
                doProcess = false;
            }else{
                password_e.setVisible(false);
            }

            //check if there are any problem with the confirmation of the password
            String ps = new String(password_t.getPassword());
            String cp = new String(confirm_t.getPassword());
            if( ! ps.equals(cp)){
                confirm_e.setText("The passwords do not match.");
                confirm_e.setVisible(true);
                doProcess = false;
            }else{
                confirm_e.setVisible(false);
            }

            //if there are no problem, then go ahead add the new account to the database and head into root panel
            if(doProcess){
                int id = GaChatDataClient.addUser(email_t.getText(), username_t.getText(), new String(password_t.getPassword()));
                if(id != -1){
                    openRootPanel(id);

                }else{
                    System.out.println(id);
                }
            }
        }
    }

    /**
     * this class is attached to the cancel button
     * #global $cancel
     */
    private class CancelRegisterListener implements ActionListener {

        @Override
        /**
         * this function will call the function cancelRegister() whenever the cancel button is clicked
         */
        public void actionPerformed(ActionEvent e) {
            cancelRegister();
        }
    }
}
