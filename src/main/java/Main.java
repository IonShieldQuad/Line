import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JPanel rootPanel;
    private LineDisplay lineDisplay;
    private JTextField fieldX1;
    private JTextField fieldY1;
    private JTextField fieldX2;
    private JTextField fieldY2;
    private JButton addButton;
    private JButton clearButton;
    private JCheckBox antialiasingCheckBox;
    private JCheckBox altCheckBox;
    private JTextField scaleField;
    private JButton updateButton;
    
    public Main() {
        clearButton.addActionListener(e -> {
            lineDisplay.clearLines();
            lineDisplay.repaint();
        });
        addButton.addActionListener(e -> {
            try {
                lineDisplay.addLine(new Line(Integer.parseInt(fieldX1.getText()), Integer.parseInt(fieldY1.getText()), Integer.parseInt(fieldX2.getText()), Integer.parseInt(fieldY2.getText())));
            } catch (NumberFormatException ignored) {}
            update();
        });
        antialiasingCheckBox.addActionListener(e -> update());
        altCheckBox.addActionListener(e -> update());
        updateButton.addActionListener(e -> update());
    }
    
    private void update() {
        try {
            lineDisplay.setAntialiasing(antialiasingCheckBox.isSelected());
            lineDisplay.setAltAlpha(altCheckBox.isSelected());
            lineDisplay.setScale(Math.max(Integer.parseInt(scaleField.getText()), 1));
            lineDisplay.repaint();
        }
        catch (NumberFormatException ignored) {}
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Line");
        Main gui = new Main();
        frame.setContentPane(gui.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
