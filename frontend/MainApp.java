import java.awt.*;
import javax.swing.*;

public class MainApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainApp().show());
    }

    public void show() {
        frame = new JFrame("Cyber Crime Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize panels and chain them together in the required order
        JPanel splashPanel = CyberCrimeApp.createPanel(() -> cardLayout.show(cardPanel, "Login"));
        JPanel loginPanel = LoginPage.createPanel(role -> {
            if ("Admin".equals(role)) {
                cardLayout.show(cardPanel, "AdminDashboard");
            } else {
                cardLayout.show(cardPanel, "Dashboard");
            }
        });
        JPanel dashboardPanel = DashboardPage.createPanel(() -> cardLayout.show(cardPanel, "ReportCrime"));
        JPanel reportPanel = ReportCrimePage.createPanel(() -> cardLayout.show(cardPanel, "Dashboard"));
        JPanel adminPanel = AdminDashboardPage.createPanel();

        cardPanel.add(splashPanel, "Splash");
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(reportPanel, "ReportCrime");
        cardPanel.add(adminPanel, "AdminDashboard");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
