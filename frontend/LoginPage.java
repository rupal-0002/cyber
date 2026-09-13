import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginPage {

    public static JPanel createPanel(java.util.function.Consumer<String> onLogin) {
        // Main background panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(11, 15, 25));
        mainPanel.setBorder(new EmptyBorder(50, 20, 20, 20));

        // 1. Top Header Area
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(new Color(11, 15, 25));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo placeholder
        JLabel logoLabel = new JLabel("🛡️");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 45));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        // Title Texts
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(11, 15, 25));
        
        JLabel title1 = new JLabel("CYBER CRIME");
        title1.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title1.setForeground(Color.WHITE);
        JLabel title2 = new JLabel("MANAGEMENT SYSTEM");
        title2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        title2.setForeground(new Color(220, 220, 220));
        
        titlePanel.add(title1);
        titlePanel.add(title2);
        headerPanel.add(titlePanel);

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 2. White Login Card 
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        cardPanel.setOpaque(false); // Make transparent so custom drawing shows
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new EmptyBorder(30, 25, 30, 25));
        cardPanel.setMaximumSize(new Dimension(340, 500));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome Back Text
        JLabel welcomeLabel = createCenteredLabel("Welcome Back!", new Font("Segoe UI", Font.BOLD, 24), new Color(15, 25, 50));
        cardPanel.add(welcomeLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel signinLabel = createCenteredLabel("Sign in to your account to continue", new Font("Segoe UI", Font.PLAIN, 13), new Color(140, 140, 140));
        cardPanel.add(signinLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Username AND Email Field
        addLabelToPanel(cardPanel, "Username / Email");
        JTextField userField = new JTextField();
        cardPanel.add(createInputPanel(userField, "👤", null));
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password Field
        addLabelToPanel(cardPanel, "Password");
        JPasswordField passField = new JPasswordField();
        cardPanel.add(createInputPanel(passField, "🔒", "👁"));
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Remember me & Forgot Password Options
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setMaximumSize(new Dimension(290, 25));
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JCheckBox rememberBox = new JCheckBox("Remember me");
        rememberBox.setBackground(Color.WHITE);
        rememberBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rememberBox.setForeground(new Color(50, 50, 50));
        rememberBox.setFocusPainted(false);
        optionsPanel.add(rememberBox, BorderLayout.WEST);

        JLabel forgotLabel = new JLabel("Forgot Password?");
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        forgotLabel.setForeground(new Color(60, 90, 220)); // Blue color
        optionsPanel.add(forgotLabel, BorderLayout.EAST);
        
        cardPanel.add(optionsPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Login As Dropdown
        addLabelToPanel(cardPanel, "Login as");
        String[] roles = {"-- Select Role --", "Admin", "Officer", "Citizen"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBackground(Color.WHITE);
        roleCombo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(2, 5, 2, 5)
        ));
        roleCombo.setMaximumSize(new Dimension(290, 35));
        roleCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(roleCombo);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Login Button
        JButton loginBtn = new JButton("→ Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(60, 100, 230)); // Bright blue
        loginBtn.setFocusPainted(false);
        loginBtn.setMaximumSize(new Dimension(290, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBorder(new LineBorder(new Color(60, 100, 230), 1, true));
        loginBtn.addActionListener(e -> {
            String selected = (String) roleCombo.getSelectedItem();
            if (selected != null && !selected.startsWith("--")) {
                if (onLogin != null) {
                    onLogin.accept(selected);
                }
            } else {
                JOptionPane.showMessageDialog(cardPanel, "Please select a role to login.", "Role Required", JOptionPane.WARNING_MESSAGE);
            }
        });
        cardPanel.add(loginBtn);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Separator (--- or continue with ---)
        JPanel sepPanel = new JPanel();
        sepPanel.setLayout(new BoxLayout(sepPanel, BoxLayout.X_AXIS));
        sepPanel.setBackground(Color.WHITE);
        sepPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        sep1.setMaximumSize(new Dimension(60, 5));
        sep1.setForeground(new Color(220, 220, 220));
        
        JLabel orLabel = new JLabel("   or continue with   ");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orLabel.setForeground(new Color(160, 160, 160));
        
        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        sep2.setMaximumSize(new Dimension(60, 5));
        sep2.setForeground(new Color(220, 220, 220));
        
        sepPanel.add(sep1);
        sepPanel.add(orLabel);
        sepPanel.add(sep2);
        cardPanel.add(sepPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Guest Login Button
        JButton guestBtn = new JButton(" Login as Guest (Public User)");
        guestBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        guestBtn.setForeground(new Color(60, 70, 90));
        guestBtn.setBackground(Color.WHITE);
        guestBtn.setFocusPainted(false);
        guestBtn.setMaximumSize(new Dimension(290, 40));
        guestBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        guestBtn.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        guestBtn.addActionListener(e -> { if(onLogin != null) onLogin.accept("Citizen"); });
        cardPanel.add(guestBtn);

        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalGlue()); // Push footer to bottom

        // 3. Footer (Don't have an account?)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        footerPanel.setBackground(new Color(11, 15, 25));
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel footerText = new JLabel("Don't have an account?");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footerText.setForeground(new Color(160, 160, 160));
        
        JLabel registerText = new JLabel("Register Here");
        registerText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerText.setForeground(Color.WHITE);
        
        footerPanel.add(footerText);
        footerPanel.add(registerText);
        mainPanel.add(footerPanel);
        return mainPanel;
    }

    // Helper: Creates a left-aligned label and wraps it in a panel to constrain width
    private static void addLabelToPanel(JPanel parentPanel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(50, 50, 50));
        
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrap.setBackground(Color.WHITE);
        wrap.setMaximumSize(new Dimension(290, 20));
        wrap.add(label);
        
        parentPanel.add(wrap);
        parentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    // Helper: Creates the text fields with an icon on the left (and optional right icon)
    private static JPanel createInputPanel(JTextField textField, String leftIcon, String rightIcon) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(290, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        if (leftIcon != null) {
            JLabel icon = new JLabel(leftIcon);
            icon.setForeground(new Color(150, 150, 150));
            icon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            panel.add(icon, BorderLayout.WEST);
        }
        
        textField.setBorder(null); // Remove default border
        textField.setBackground(Color.WHITE);
        panel.add(textField, BorderLayout.CENTER);

        if (rightIcon != null) {
            JLabel icon = new JLabel(rightIcon);
            icon.setForeground(new Color(150, 150, 150));
            icon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            panel.add(icon, BorderLayout.EAST);
        }
        
        return panel;
    }

    // Helper: Center aligned labels
    private static JLabel createCenteredLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
