import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ReportCrimePage {

    public static JPanel createPanel(Runnable onNext) {
        JPanel wrapper = new JPanel(new BorderLayout());
        // Main content area
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(11, 15, 25)); // Dark background
        mainContent.setBorder(new EmptyBorder(30, 20, 20, 20));

        // 1. Top Bar (<- Report a Cybercrime)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setMaximumSize(new Dimension(360, 40));
        
        JLabel backIcon = new JLabel("←");
        backIcon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        backIcon.setForeground(Color.WHITE);
        topBar.add(backIcon, BorderLayout.WEST);

        JLabel title = new JLabel("Report a Cybercrime");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        // Padding to center the title properly despite the back button
        title.setBorder(new EmptyBorder(0, 0, 0, 22)); 
        topBar.add(title, BorderLayout.CENTER);
        
        mainContent.add(topBar);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2. Crime Details Header
        JPanel headerWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerWrap.setOpaque(false);
        headerWrap.setMaximumSize(new Dimension(340, 25));
        headerWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel headerLabel = new JLabel("Crime Details:");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerWrap.add(headerLabel);
        mainContent.add(headerWrap);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // 3. Type of Crime
        addLabelToPanel(mainContent, "Type of Crime");
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Select type", "Financial Fraud", "Identity Theft", "Cyberbullying"});
        typeCombo.setMaximumSize(new Dimension(340, 40));
        typeCombo.setBackground(new Color(11, 15, 25));
        typeCombo.setForeground(new Color(150, 150, 150));
        typeCombo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 70, 90), 1, true),
            new EmptyBorder(5, 5, 5, 5)
        ));
        typeCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(typeCombo);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // 4. Date & Time
        addLabelToPanel(mainContent, "Date & Time");
        JTextField dateField = new JTextField("DD/MM/YYYY, --:-- --");
        dateField.setCaretColor(Color.WHITE);
        JPanel datePanel = createInputPanel(dateField, "📅");
        datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(datePanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // 5. Description
        addLabelToPanel(mainContent, "Description");
        JTextArea descArea = new JTextArea("Provide a brief description of the incident");
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(11, 15, 25));
        descArea.setForeground(new Color(150, 150, 150));
        descArea.setCaretColor(Color.WHITE);
        descArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 70, 90), 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        descArea.setMaximumSize(new Dimension(340, 100));
        descArea.setPreferredSize(new Dimension(340, 100));
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(descArea);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // 6. Upload Evidence
        JPanel uploadPanel = new JPanel(new BorderLayout());
        uploadPanel.setBackground(new Color(15, 20, 35));
        uploadPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 70, 90), 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        uploadPanel.setMaximumSize(new Dimension(340, 60));
        uploadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setOpaque(false);
        
        JLabel uploadTitle = new JLabel("Upload Evidence");
        uploadTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        uploadTitle.setForeground(new Color(200, 200, 220));
        
        JLabel uploadSub = new JLabel("Add files, screenshots or documents");
        uploadSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        uploadSub.setForeground(new Color(120, 120, 140));
        
        textContainer.add(uploadTitle);
        textContainer.add(Box.createRigidArea(new Dimension(0, 3)));
        textContainer.add(uploadSub);
        
        JLabel uploadIcon = new JLabel("📤"); // Upload icon
        uploadIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        uploadIcon.setForeground(new Color(150, 150, 150));
        
        uploadPanel.add(textContainer, BorderLayout.CENTER);
        uploadPanel.add(uploadIcon, BorderLayout.EAST);
        
        mainContent.add(uploadPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // 7. Next Button
        JButton nextBtn = new JButton("Next");
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setBackground(new Color(40, 70, 220)); // Bright blue
        nextBtn.setFocusPainted(false);
        nextBtn.setMaximumSize(new Dimension(340, 45));
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextBtn.setBorder(new LineBorder(new Color(40, 70, 220), 1, true));
        nextBtn.addActionListener(e -> { if(onNext != null) onNext.run(); });
        mainContent.add(nextBtn);

        mainContent.add(Box.createVerticalGlue()); // Push content up

        // Add main content
        wrapper.add(mainContent, BorderLayout.CENTER);

        // 8. Bottom Navigation Bar (Reused from Dashboard)
        BottomNavPanel bottomNav = new BottomNavPanel();
        wrapper.add(bottomNav, BorderLayout.SOUTH);

        return wrapper;
    }

    // Helper: Creates a left-aligned label for form inputs
    private static void addLabelToPanel(JPanel parentPanel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(200, 200, 200));
        
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrap.setOpaque(false);
        wrap.setMaximumSize(new Dimension(340, 20));
        wrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrap.add(label);
        
        parentPanel.add(wrap);
        parentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    }

    // Helper: Creates the text fields with a border and optional right icon
    private static JPanel createInputPanel(JTextField textField, String rightIcon) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(new Color(11, 15, 25));
        panel.setMaximumSize(new Dimension(340, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 70, 90), 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        textField.setBorder(null);
        textField.setBackground(new Color(11, 15, 25));
        textField.setForeground(new Color(150, 150, 150));
        panel.add(textField, BorderLayout.CENTER);

        if (rightIcon != null) {
            JLabel icon = new JLabel(rightIcon);
            icon.setForeground(new Color(150, 150, 150));
            icon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            panel.add(icon, BorderLayout.EAST);
        }
        
        return panel;
    }

    // Custom Bottom Navigation Bar
    static class BottomNavPanel extends JPanel {
        public BottomNavPanel() {
            setOpaque(false);
            setLayout(null); // Null layout for overlapping
            setPreferredSize(new Dimension(400, 80));

            JPanel homePanel = createNavItem("🏠", "Home", true);
            homePanel.setBounds(40, 25, 60, 55);
            add(homePanel);

            JPanel plusBtn = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(120, 140, 255)); // Soft Blue
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
            plusBtn.setBounds(168, 5, 50, 50); // Overlaps top edge
            add(plusBtn);

            JPanel profilePanel = createNavItem("👤", "Profile", false);
            profilePanel.setBounds(285, 25, 60, 55);
            add(profilePanel);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(15, 20, 35));
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
