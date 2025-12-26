import javax.swing.UIManager;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;
import view.LoginFrame;

public class App {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}