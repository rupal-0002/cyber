import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class AdminDashboardPage {

    public static JPanel createPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        // Main content area
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(11, 15, 25)); // Dark background
        mainContent.setBorder(new EmptyBorder(30, 20, 20, 20));

        // 1. Top Bar (≡ Admin Dashboard )
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setMaximumSize(new Dimension(360, 40));
        
        JLabel menuIcon = new JLabel("≡");
        menuIcon.setFont(new Font("Segoe UI", Font.BOLD, 28));
        menuIcon.setForeground(Color.WHITE);
        topBar.add(menuIcon, BorderLayout.WEST);

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        topBar.add(title, BorderLayout.CENTER);

        JLabel bellIcon = new JLabel("🔔"); 
        bellIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        bellIcon.setForeground(Color.WHITE);
        topBar.add(bellIcon, BorderLayout.EAST);
        
        mainContent.add(topBar);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2. Summary Cards Grid (2x2)
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setMaximumSize(new Dimension(360, 180));

        gridPanel.add(createSummaryCard("📚", "Total Cases", "1250"));
        gridPanel.add(createSummaryCard("📦", "Open Cases", "320"));
        gridPanel.add(createSummaryCard("⏱", "in Progress", "540"));
        gridPanel.add(createSummaryCard("✔", "Closed Cases", "390"));

        mainContent.add(gridPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // 3. Recent Cases Header
        JPanel recentHeader = new JPanel(new BorderLayout());
        recentHeader.setOpaque(false);
        recentHeader.setMaximumSize(new Dimension(360, 30));
        recentHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel recentLabel = new JLabel("Recent Cases");
        recentLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        recentLabel.setForeground(Color.WHITE);
        recentHeader.add(recentLabel, BorderLayout.WEST);

        JLabel viewAllLabel = new JLabel("View All");
        viewAllLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        viewAllLabel.setForeground(new Color(60, 90, 160)); // Muted blue
        recentHeader.add(viewAllLabel, BorderLayout.EAST);

        mainContent.add(recentHeader);
        mainContent.add(Box.createRigidArea(new Dimension(0, 15)));

        // 4. Cases List
        JPanel casesList = new JPanel();
        casesList.setLayout(new BoxLayout(casesList, BoxLayout.Y_AXIS));
        casesList.setOpaque(false);
        casesList.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color textFg = new Color(220, 220, 220);
        casesList.add(createCaseItem("CS20260001", "Online Fraud", "In Progress", new Color(100, 80, 40), textFg));
        casesList.add(Box.createRigidArea(new Dimension(0, 10)));
        casesList.add(createCaseItem("CS20260002", "Phishing", "Open", new Color(30, 50, 100), textFg));
        casesList.add(Box.createRigidArea(new Dimension(0, 10)));
        casesList.add(createCaseItem("CS20260003", "Identity Theft", "In Progress", new Color(100, 80, 40), textFg));
        casesList.add(Box.createRigidArea(new Dimension(0, 10)));
        casesList.add(createCaseItem("CS20260004", "Online Fraud", "Closed", new Color(30, 80, 50), textFg));
        casesList.add(Box.createRigidArea(new Dimension(0, 10)));
        casesList.add(createCaseItem("CS20240005", "UPI Fraud", "In Progress", new Color(100, 80, 40), textFg));

        mainContent.add(casesList);
        mainContent.add(Box.createVerticalGlue()); // Push content up

        // Wrap main content in a scroll pane just in case
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(11, 15, 25));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        wrapper.add(scrollPane, BorderLayout.CENTER);

        // 5. Bottom Navigation Bar
        BottomNavPanel bottomNav = new BottomNavPanel();
        wrapper.add(bottomNav, BorderLayout.SOUTH);

        return wrapper;
    }

    // Helper: (Summary card with rounded corners)
    private static JPanel createSummaryCard(String icon, String title, String value) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 25, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(10, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        iconLabel.setForeground(Color.WHITE);
        panel.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valLabel.setForeground(Color.WHITE);
        valLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        textPanel.add(valLabel);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    // Helper: Individual recent case item row
    private static JPanel createCaseItem(String id, String type, String status, Color statusBg, Color statusFg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(11, 15, 25));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 70, 90), 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(360, 60));

        // Left side: ID and Type
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel idLabel = new JLabel(id);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        idLabel.setForeground(Color.WHITE);
        
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        typeLabel.setForeground(new Color(150, 150, 150));

        infoPanel.add(idLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        infoPanel.add(typeLabel);

        panel.add(infoPanel, BorderLayout.WEST);

        // Right side: Status Badge (using custom paint for filled rounded rect)
        JPanel badgePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(statusBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        badgePanel.setOpaque(false);
        badgePanel.setLayout(new GridBagLayout()); // Center text
        badgePanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(statusFg);
        badgePanel.add(statusLabel);

        // Wrapper to prevent BorderLayout.EAST from vertically stretching the badge
        JPanel badgeWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
        badgeWrapper.setOpaque(false);
        badgeWrapper.add(badgePanel);

        panel.add(badgeWrapper, BorderLayout.EAST);

        return panel;
    }

    // Custom Navigation Bar for Admin
    static class BottomNavPanel extends JPanel {
        public BottomNavPanel() {
            setLayout(new GridLayout(1, 5));
            setPreferredSize(new Dimension(400, 65));
            setBackground(new Color(15, 20, 35)); // Dark nav background
            setBorder(new EmptyBorder(5, 5, 10, 5)); // Padding

            add(createNavItem("🎛️", "Dashboard", true));
            add(createNavItem("💼", "All Cases", false));
            add(createNavItem("🔍", "Evidence", false));
            add(createNavItem("📊", "Analytics", false));
            add(createNavItem("👤", "Profile", false));
        }

        private JPanel createNavItem(String icon, String text, boolean active) {
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setOpaque(false);

            JLabel lIcon = new JLabel(icon);
            lIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            lIcon.setForeground(active ? Color.WHITE : new Color(150, 150, 150));
            lIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lText = new JLabel(text);
            lText.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lText.setForeground(active ? Color.WHITE : new Color(150, 150, 150));
            lText.setAlignmentX(Component.CENTER_ALIGNMENT);

            p.add(Box.createVerticalGlue());
            p.add(lIcon);
            p.add(Box.createRigidArea(new Dimension(0, 4)));
            p.add(lText);
            p.add(Box.createVerticalGlue());
            return p;
        }
    }
}
