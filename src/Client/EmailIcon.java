package Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import org.jdesktop.swingx.border.DropShadowBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmailIcon extends JLabel {
    private ImageIcon icon;
    private String from,to;

    public EmailIcon(String from){
        this.from = from;
        this.addMouseListener(new EmailMouseListener());
    }

    public EmailIcon(String from, String to){
        this.from = from;
        this.to = to;
        this.addMouseListener(new EmailMouseListener());
    }

    public void setIcon(){
        try {
            File imgfile = new File(".\\Mail-de-confirmation-exemple-de-logo.png");
            BufferedImage img;
            img = ImageIO.read(imgfile);
            Image image = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class EmailMouseListener implements MouseListener {
        private Border original = new EmptyBorder(2,2,2,2),
                       update = new DropShadowBorder(Color.black, 2, 0.5f,2,true,true,true,true),
                       clicked = new MatteBorder(1,1,1,1,new Color(0,0,100));

        @Override
        public void mouseClicked(MouseEvent e) {
            EmailFrame e_frame = new EmailFrame(from);
            e_frame.setVisible(true);
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
