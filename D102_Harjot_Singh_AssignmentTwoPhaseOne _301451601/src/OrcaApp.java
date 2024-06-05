import javax.swing.*;
import java.awt.*;

public class OrcaApp extends JFrame {

    private static final long serialVersionUID = 1L;

	public OrcaApp(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        
        // Set the layout manager to BorderLayout
        this.setLayout(new BorderLayout());
        
        // Instantiate our OrcaPanel
        OrcaPanel panel = new OrcaPanel(new Dimension(this.getSize().width - 40, this.getSize().height - 40));
        
        // Add padding around the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add the panel to the center of the frame
        this.add(panel, BorderLayout.CENTER);
        
        // Display the frame
        this.setVisible(true);
    }

    public static void main(String[] args) {
        OrcaApp app = new OrcaApp("Orca and Friends");
    }
}
