import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DashboardPage {

    public static JPanel createPanel(Runnable onNext) {
        JPanel wrapper = new JPanel(new BorderLayout());
        
        // Main content area
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(11, 15, 25)); // Main dark background
        mainContent.setBorder(new EmptyBorder(30, 20, 20, 20));

        // 1. Top Bar (Menu ≡ | Dashboard | Bell 🔔)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setMaximumSize(new Dimension(360, 40));
        
        JLabel menuIcon = new JLabel("≡");
        menuIcon.setFont(new Font("Segoe UI", Font.BOLD, 28));
        menuIcon.setForeground(Color.WHITE);
        topBar.add(menuIcon, BorderLayout.WEST);

        JLabel title = new JLabel("Dashboard");
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

        // 2. Greeting Section
        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));
        greetingPanel.setOpaque(false);
        greetingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel helloLabel = new JLabel("Hello , Citizen 👋");
        helloLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        helloLabel.setForeground(Color.WHITE);
        greetingPanel.add(helloLabel);
        greetingPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel alertLabel = new JLabel("Stay alert. Stay safe.");
        alertLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        alertLabel.setForeground(new Color(180, 180, 180)); // Light gray
        greetingPanel.add(alertLabel);

        // Wrap to ensure left alignment in BoxLayout
        JPanel greetingWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        greetingWrapper.setOpaque(false);
        greetingWrapper.setMaximumSize(new Dimension(360, 60));
        greetingWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetingWrapper.add(greetingPanel);
        
        mainContent.add(greetingWrapper);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // 3. Grid of Cards (2x2)
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setMaximumSize(new Dimension(360, 250));

        JPanel reportCard = createGridCard("❕", "Report Crime", "Report a new cybercrime");
        reportCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { if(onNext != null) onNext.run(); }
            public void mouseEntered(java.awt.event.MouseEvent evt) { reportCard.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
        });
        gridPanel.add(reportCard);
        gridPanel.add(createGridCard("📄", "My Cases", "View your reported cases"));
        gridPanel.add(createGridCard("📍", "Track Case", "Track the status of your case"));
        gridPanel.add(createGridCard("🔔", "Notifications", "Important alerts and updates"));

        mainContent.add(gridPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // 4. Profile Card (Full Width)
        JPanel profileCard = createProfileCard();
        mainContent.add(profileCard);
        
        mainContent.add(Box.createVerticalGlue()); // Push content up

        // Add main content to center
        wrapper.add(mainContent, BorderLayout.CENTER);

        // 5. Bottom Navigation Bar
        BottomNavPanel bottomNav = new BottomNavPanel();
        wrapper.add(bottomNav, BorderLayout.SOUTH);

        return wrapper;
    }

    // Helper: Creates the 2x2 grid cards with rounded corners
    private static JPanel createGridCard(String icon, String title, String subtitle) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 25, 40)); // Lighter dark blue background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        iconLabel.setForeground(Color.WHITE);
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        // HTML for wrapping text
        JLabel subtitleLabel = new JLabel("<html><div style='width: 110px; color: #aaaaaa;'>" + subtitle + "</div></html>");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panel.add(subtitleLabel);

        return panel;
    }

    // Helper: Creates the full-width profile card
    private static JPanel createProfileCard() {
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setMaximumSize(new Dimension(360, 80));

        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconLabel.setForeground(new Color(100, 120, 255)); // Blueish color
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);
        textPanel.add(titleLabel);
        
        JLabel subLabel = new JLabel("Manage your profile and settings");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(new Color(170, 170, 170));
        textPanel.add(subLabel);

        panel.add(textPanel);
        return panel;
    }

    // Helper Class: Bottom Navigation Bar with floating action button (+)
    static class BottomNavPanel extends JPanel {
        public BottomNavPanel() {
            setOpaque(false);
            setLayout(null); // Null layout for precise overlapping positioning
            setPreferredSize(new Dimension(400, 80));

            // Home Nav Item
            JPanel homePanel = createNavItem("🏠", "Home", true);
            homePanel.setBounds(40, 25, 60, 55);
            add(homePanel);

            // Center Floating Action Button (+)
            JPanel plusBtn = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(120, 140, 255)); // Soft Purple/Blue
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth("+")) / 2;
                    int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString("+", x, y - 1);
                    g2.dispose();
                }
            };
            plusBtn.setOpaque(false);
            plusBtn.setBounds(168, 5, 50, 50); // Overlaps the top edge of the navbar
            add(plusBtn);

            // Profile Nav Item
            JPanel profilePanel = createNavItem("👤", "Profile", false);
            profilePanel.setBounds(285, 25, 60, 55);
            add(profilePanel);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(15, 20, 35)); // Navbar background color
            // Draw background starting at y=25 to allow the + button to overlap the transparent area above
            g2.fillRect(0, 25, getWidth(), getHeight() - 25);
            g2.dispose();
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
            lText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
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
