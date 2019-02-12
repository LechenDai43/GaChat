package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogInPanel{
    private JLabel unp /*the label of username/email text field*/,
            pwp /*the label of password text field*/,
            title /*the heading to welcome user to use GaChat*/,
            error /*to show error massage if any*/;
    private JTextField untf /*the text field to fill the username/email*/;
    private JPasswordField pwtf; /*the password field to fill the password*/
    private JButton rgtbtn /*the button to head into the register panel*/,
            lgnbtn /*the button to process the LogIn() function*/;
    private JFrame frame /*the frame that holds LogInPanel, RegisterPanel, and RootPanel*/;

    public LogInPanel(JFrame frm){
        frame = frm;
         //pass the content pane of the frame to the init function to make up the content pane
        // #local $container
        Container container = frame.getContentPane();
        this.init(container);
    }

    /**
     * this function receive the content pane of the frame and make it up
     * this function add the following things onto the panel
     * 1. the welcome massage which to greet users to using GaChat
     *    #global $title
     * 2. the username entering which is the place for user to enter their username or email for logging in, and which including a label and a textfield
     *    #global $unp $untf
     * 3. the password entering which allows the user to enter their password and includes a label and a passwordfield
     *    #global $pwp $pwtf
     * 4. the error massage which is hidden at the beginning, and if there are any problems about logging in, the error massage will show up to tell the user what is wrong
     *    #gloabl $error
     * 5. the logging in button which will direct the frame to the root panel or trigger the error massage if there are some errors
     *    a LogInListener() is attached on this button
     *    #global $lgnbtn
     * 6. the register button which allows a new user to go the register panel to register a new account
     *    a RegisterAccountListener() is attached on this button
     *    #global $rgtbtn
     * @param container the content panel of the frame
     */
    private void init(Container container) {
        //set up the attributes about the frame
        {
            frame.setSize(400, 325);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
            frame.setTitle("Log in GaChat");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = frame.getSize();
            frame.setLocation((int) ((screenSize.getWidth() - size.getWidth()) / 2), (int) ((screenSize.getHeight() - size.getHeight()) / 2));
        }
        //set the property of the container
        {
            container.setBackground(new Color(0xbb, 0xbb, 0xff));
        }

        //set the welcome massage
        {
            title = new JLabel("Welcome to GaChat");
            title.setFont(new Font("TimesRoman", Font.BOLD, 20));
            title.setBounds(50, 20, 300, 50);
            title.setHorizontalAlignment(JLabel.CENTER);
            title.setVerticalAlignment(JLabel.CENTER);
            container.add(title);
        }

        //set the username entering
        {
            unp = new JLabel("E-mail/Username:");
            unp.setBounds(10, 80, 120, 30);
            unp.setHorizontalAlignment(JLabel.RIGHT);
            unp.setVerticalAlignment(JLabel.CENTER);
            container.add(unp);
            untf = new JTextField();
            untf.setBounds(150, 85, 200, 20);
            container.add(untf);
        }

        //set the password entering
        {
            pwp = new JLabel("Password:");
            pwp.setBounds(10, 120, 120, 30);
            pwp.setHorizontalAlignment(JLabel.RIGHT);
            pwp.setVerticalAlignment(JLabel.CENTER);
            container.add(pwp);
            pwtf = new JPasswordField();
            pwtf.setBounds(150, 125, 200, 20);
            container.add(pwtf);
        }

        //set error massage
        {
            error = new JLabel();
            error.setBounds(30, 170, 320, 20);
            error.setHorizontalAlignment(JLabel.LEFT);
            error.setVerticalAlignment(JLabel.CENTER);
            error.setVisible(false);
            container.add(error);
        }

        //set log in button
        {
            lgnbtn = new JButton("Log In");
            lgnbtn.setBounds(250, 200, 100, 30);
            lgnbtn.addMouseListener(new LogInListener());
            container.add(lgnbtn);
        }

        //set register button
        {
            rgtbtn = new JButton("Register");
            rgtbtn.setBounds(50, 200, 100, 30);
            rgtbtn.addActionListener(new RegisterAccountListener());
            container.add(rgtbtn);
        }
    }

    /**
     *  this function is to receive logging in information from the panel and pass them to the GaChatData to check the if the logging in information is correct
     *  #global $untf->getText() $pwtf->getPassword()
     *  if the information has any problems, error massage will be shown
     *  #global $error
     *  if the information does not have problem, the frame will head into the root pabel
     *  #global $frame #functional $rootPanel
     *  this function is called by LogInListener by mouse click on the log in button
     */
    private void logIn() {
        //get the username/email and the password to pass them to GaChatData for checking with the database
        String username = untf.getText();
        String password = new String(pwtf.getPassword());
        String log_status = GaChatDataClient.checkLogin(username, password);

        //get the feedback information from GaChatData
        //if the feedback is not an empty string, then some errors occurs, the error massage need to be shown
        if(!log_status.equals("")){
            error.setText(log_status);
            error.setVisible(true);
            error.setHorizontalAlignment(JLabel.CENTER);
        }
        //if the feedback is an empty string, then the information are all correct, the root panel will be shown
        else{
            int id = GaChatDataClient.getLogInID(username);
            RootPanel rootPanel = new RootPanel(frame,id, this);
        }
    }

    /**
     * this function is to repaint the frame
     * this function first set the attributes of the frame back what attributes have been set in init() method
     * then, this function will remove all the components from the content panel of the frame and set the attributes of the content panel
     * finally, this function add all the components of the LogInPanel back to the content panel
     * this function is called outside of LogInPanel in order to go back the logging in window
     * this function is called by RegisterPanel->
     */
    protected void repack(){
        //set up the frame
        {
            frame.setSize(400, 325);
            frame.setLayout(null);
            frame.setTitle("Log in GaChat");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = frame.getSize();
            frame.setLocation((int) ((screenSize.getWidth() - size.getWidth()) / 2), (int) ((screenSize.getHeight() - size.getHeight()) / 2));
        }

        //set up the content panel of the frame
        Container container = frame.getContentPane();
        {
            container.removeAll();
            container.setBackground(new Color(0xbb, 0xbb, 0xff));
        }

        //add the components back the container
        {
            container.add(unp);
            container.add(pwp);
            container.add(title);
            error.setVisible(false);
            container.add(error);
            untf.setText("");
            container.add(untf);
            pwtf.setText("");
            container.add(pwtf);
            container.add(rgtbtn);
            container.add(lgnbtn);
        }
    }

    /**
     * this function is to create a new register panel to register new account
     */
    private void register(){
        RegisterPanel rp = new RegisterPanel(frame,this);
    }

    /**
     * this class is attached on the log in button
     * #global $lgnbtn
     * this class will call logIn() function for logging in information whenever the button is clicked
     */
    private class LogInListener implements MouseListener {
        @Override
        /**
         * this function calls logIn() function
         */
        public void mouseClicked(MouseEvent e) {
            logIn();
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

    /**
     * this class is attached on the register button
     * #global $rgtbtn
     * this class will create a RegisterPanel() to let the user register a new account whenever the register button is clicked
     */
    private class RegisterAccountListener implements ActionListener {

        public RegisterAccountListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            register();
        }
    }
}
