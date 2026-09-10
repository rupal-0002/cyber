import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CyberCrimeApp {

    public static JPanel createPanel(Runnable onNext) {
        // Main Panel with dark background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(11, 15, 25)); // Deep dark blue background
        mainPanel.setBorder(new EmptyBorder(40, 20, 20, 20));

        // 1. Top Logo Shield
        JLabel logoLabel = createCenteredLabel("🛡️", new Font("Segoe UI", Font.PLAIN, 60), Color.WHITE);
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 2. Main Titles
        JLabel title1 = createCenteredLabel("CYBER CRIME", new Font("Segoe UI", Font.BOLD, 28), Color.WHITE);
        mainPanel.add(title1);
        
        JLabel title2 = createCenteredLabel("MANAGEMENT SYSTEM", new Font("Segoe UI", Font.PLAIN, 16), new Color(220, 220, 220));
        mainPanel.add(title2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // 3. Subtitle
        JLabel subtitle = createCenteredLabel("Secure. Monitor. Protect.", new Font("Segoe UI", Font.PLAIN, 20), new Color(30, 110, 255));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // 4. Description Text
        JLabel desc1 = createCenteredLabel("A comprehensive platform to report , monitor", new Font("Segoe UI", Font.PLAIN, 13), new Color(220, 220, 220));
        JLabel desc2 = createCenteredLabel("and manage cyber crimes efficiently", new Font("Segoe UI", Font.PLAIN, 13), new Color(220, 220, 220));
        mainPanel.add(desc1);
        mainPanel.add(desc2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 5. Center Graphic Area Placeholder
        JPanel imagePlaceholder = new JPanel();
        imagePlaceholder.setOpaque(false);
        imagePlaceholder.setPreferredSize(new Dimension(300, 200));
        imagePlaceholder.setMaximumSize(new Dimension(300, 200));
        imagePlaceholder.setBorder(new LineBorder(new Color(30, 110, 255), 1, true));
        JLabel imgText = new JLabel("<html><center>Add your main glowing<br>shield image here</center></html>");
        imgText.setForeground(new Color(30, 110, 255));
        imagePlaceholder.add(imgText);
        mainPanel.add(imagePlaceholder);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // 6. Help Box Panel
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new GridBagLayout());
        helpPanel.setBackground(new Color(11, 15, 25)); // Match background
        helpPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 120), 1, true), // Grey rounded border
                new EmptyBorder(15, 15, 15, 15) // Inner padding
        ));
        helpPanel.setMaximumSize(new Dimension(350, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel helpTitle = new JLabel("Need Help Immediately ?");
        helpTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        helpTitle.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        helpPanel.add(helpTitle, gbc);

        JButton callBtn = new JButton("CALL NOW \u2192");
        callBtn.setBackground(new Color(40, 70, 220)); // Blue button
        callBtn.setForeground(Color.WHITE);
        callBtn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        callBtn.setFocusPainted(false);
        callBtn.setBorder(new EmptyBorder(8, 12, 8, 12));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.0;
        helpPanel.add(callBtn, gbc);

        JLabel helpDesc = new JLabel("If you are facing an emergency, contact our helpline.");
        helpDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        helpDesc.setForeground(new Color(180, 180, 180));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.insets = new Insets(8, 0, 0, 0);
        helpPanel.add(helpDesc, gbc);

        mainPanel.add(helpPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // 7. Get Started Button
        JButton getStartedBtn = new JButton("🛡️ Get Started   \u2192");
        getStartedBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        getStartedBtn.setForeground(Color.WHITE);
        getStartedBtn.setBackground(new Color(40, 70, 220)); // Blue button
        getStartedBtn.setFocusPainted(false);
        getStartedBtn.setMaximumSize(new Dimension(350, 50));
        getStartedBtn.setPreferredSize(new Dimension(350, 50));
        getStartedBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStartedBtn.setBorder(new LineBorder(new Color(60, 90, 255), 1, true));
        getStartedBtn.addActionListener(e -> { if(onNext != null) onNext.run(); });
        mainPanel.add(getStartedBtn);
        
        mainPanel.add(Box.createVerticalGlue()); // Pushes the footer to the bottom

        // 8. Footer Text
        JLabel footer = createCenteredLabel("🛡️ Working together for a safer digital society", new Font("Segoe UI", Font.PLAIN, 12), new Color(100, 100, 120));
        mainPanel.add(footer);
        return mainPanel;
    }

    // Helper method to create centered text labels
    private static JLabel createCenteredLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
