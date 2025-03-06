package pt.isel.mpd.swingdemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SwingDemo extends JFrame {
    
    private void initComponents() {
      
        Font boldFont = new Font("Serif", Font.BOLD, 24);
        var textField = new JTextField();
        var button1 = new JButton("Button 1");
        var button2 = new JButton("Button 2");
        
        var content = getContentPane();
        content.setLayout(new BorderLayout());
        var panel = new JPanel(new BorderLayout());
        panel.add(button1, BorderLayout.WEST);
        panel.add(button2, BorderLayout.EAST);
        textField.setFont(boldFont);
        textField.setHorizontalAlignment(JTextField.CENTER);
        
        button1.setFont(boldFont);
        button2.setFont(boldFont);
        
        ActionListener butListener = e -> {
            
            if (textField.getText().isEmpty()) {
                textField.setText(((JButton) e.getSource()).getText());
            }
            else {
                textField.setText("");
            }
        };
        button1.addActionListener(butListener);
        button2.addActionListener(butListener);
        content.add(panel, BorderLayout.SOUTH);
        content.add(textField, BorderLayout.CENTER);
    }
    
    public SwingDemo() {
        super("SwingDemo");
        initComponents();
        setPreferredSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    
  
}
