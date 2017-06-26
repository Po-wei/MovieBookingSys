import java.awt.*;
import javax.swing.*;

public class Alert extends JFrame{

    Alert(String message){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        setContentPane(panel);

        JTextArea textArea = new JTextArea(30, 50);
        textArea.setLineWrap(true);
        textArea.setText(message);
        panel.add(textArea);
        //JTextPane textPane = new JTextPane();
        //textPane.setText(message);
        //panel.add(textPane);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setLocation(width/2, height/2);

        //setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}

