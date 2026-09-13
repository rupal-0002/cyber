import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class OfficerDashboardPage {

    // Complaint data model
    public static class Complaint {
        String caseId;
        String crimeType;
        String complainant;
        String date;
        String priority;
        String status;
        String remarks;

        public Complaint(String caseId, String crimeType, String complainant, String date, String priority, String status, String remarks) {
            this.caseId = caseId;
            this.crimeType = crimeType;
            this.complainant = complainant;
            this.date = date;
            this.priority = priority;
            this.status = status;
            this.remarks = remarks;
        }
    }

    private static List<Complaint> assignedCases = new ArrayList<>();

    static {
        assignedCases.add(new Complaint("CS20261042", "Phishing Scam", "Rahul Sharma", "12 Sep 2026", "High", "In Progress", "Bank account transaction traces requested from nodal officer."));
        assignedCases.add(new Complaint("CS20261055", "Identity Theft", "Priya Verma", "10 Sep 2026", "Medium", "Pending", "Awaiting suspect social media IP access logs."));
        assignedCases.add(new Complaint("CS20261063", "UPI Payment Fraud", "Amit Patel", "08 Sep 2026", "High", "In Progress", "Freezing order sent to merchant payment gateway."));
        assignedCases.add(new Complaint("CS20261019", "Ransomware Attack", "TechCorp Ltd", "02 Sep 2026", "Critical", "Resolved", "Decryption key verified; attacker wallet blacklisted."));
        assignedCases.add(new Complaint("CS20260980", "Cyber Harassment", "Sneha Roy", "28 Aug 2026", "Medium", "Closed", "Suspect identified and notice served under IT Act."));
    }

    // Scrollable Panel that strictly tracks viewport width to prevent horizontal overflow and ensure centering
    static class CenteredScrollablePanel extends JPanel implements Scrollable {
        public CenteredScrollablePanel() {
            super();
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 32;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true; // Crucial: forces panel width to match viewport exactly
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    public static JPanel createPanel(Runnable onLogout) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(11, 15, 25));

        final int CONTENT_WIDTH = 330;

        // Main content area strictly tracking viewport width
        CenteredScrollablePanel mainContent = new CenteredScrollablePanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(11, 15, 25));
        mainContent.setBorder(new EmptyBorder(16, 12, 24, 12));

        // 1. Top Bar (Balanced and Centered)
        JPanel topBar = new JPanel(new BorderLayout(8, 0));
        topBar.setOpaque(false);
        topBar.setMaximumSize(new Dimension(CONTENT_WIDTH, 42));
        topBar.setPreferredSize(new Dimension(CONTENT_WIDTH, 42));
        topBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Left badge / sub-info
        JLabel unitBadge = new JLabel("Unit #402");
        unitBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        unitBadge.setForeground(new Color(110, 150, 230));
        unitBadge.setPreferredSize(new Dimension(65, 30));
        topBar.add(unitBadge, BorderLayout.WEST);

        // Center Title
        JLabel title = new JLabel("Officer Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        topBar.add(title, BorderLayout.CENTER);

        // Right: Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        logoutBtn.setForeground(new Color(255, 125, 125));
        logoutBtn.setBackground(new Color(38, 20, 30));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setPreferredSize(new Dimension(68, 28));
        logoutBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(175, 45, 60), 1, true),
            new EmptyBorder(4, 8, 4, 8)
        ));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                wrapper,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION && onLogout != null) {
                onLogout.run();
            }
        });
        topBar.add(logoutBtn, BorderLayout.EAST);

        mainContent.add(topBar);
        mainContent.add(Box.createRigidArea(new Dimension(0, 14)));

        // 2. Summary Metric Cards (2x2)
        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        metricsPanel.setOpaque(false);
        metricsPanel.setMaximumSize(new Dimension(CONTENT_WIDTH, 120));
        metricsPanel.setPreferredSize(new Dimension(CONTENT_WIDTH, 120));
        metricsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel totalVal = new JLabel("0");
        JLabel inProgressVal = new JLabel("0");
        JLabel pendingVal = new JLabel("0");
        JLabel resolvedVal = new JLabel("0");

        metricsPanel.add(createMetricCard("Assigned", totalVal, new Color(110, 165, 255)));
        metricsPanel.add(createMetricCard("In Progress", inProgressVal, new Color(255, 195, 75)));
        metricsPanel.add(createMetricCard("Pending", pendingVal, new Color(255, 120, 135)));
        metricsPanel.add(createMetricCard("Resolved", resolvedVal, new Color(90, 230, 145)));

        mainContent.add(metricsPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 14)));

        // 3. Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout(6, 0));
        searchPanel.setOpaque(false);
        searchPanel.setMaximumSize(new Dimension(CONTENT_WIDTH, 36));
        searchPanel.setPreferredSize(new Dimension(CONTENT_WIDTH, 36));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(45, 60, 95), 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        searchLabel.setForeground(new Color(130, 155, 195));
        searchPanel.add(searchLabel, BorderLayout.WEST);

        JTextField searchField = new JTextField();
        searchField.setBackground(new Color(11, 15, 25));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchField.setBorder(null);
        searchPanel.add(searchField, BorderLayout.CENTER);

        mainContent.add(searchPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        // 4. Filter Chips Row (Centered)
        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        filterRow.setOpaque(false);
        filterRow.setMaximumSize(new Dimension(CONTENT_WIDTH, 30));
        filterRow.setPreferredSize(new Dimension(CONTENT_WIDTH, 30));
        filterRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] filters = {"All", "In Progress", "Pending", "Resolved"};
        JButton[] filterButtons = new JButton[filters.length];
        final String[] activeFilter = {"All"};

        // Container for complaint cards
        JPanel casesListContainer = new JPanel();
        casesListContainer.setLayout(new BoxLayout(casesListContainer, BoxLayout.Y_AXIS));
        casesListContainer.setOpaque(false);
        casesListContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // View update runner
        Runnable updateView = () -> {
            int total = assignedCases.size();
            int inProg = 0, pend = 0, res = 0;
            for (Complaint c : assignedCases) {
                if ("In Progress".equalsIgnoreCase(c.status)) inProg++;
                else if ("Pending".equalsIgnoreCase(c.status)) pend++;
                else if ("Resolved".equalsIgnoreCase(c.status) || "Closed".equalsIgnoreCase(c.status)) res++;
            }
            totalVal.setText(String.valueOf(total));
            inProgressVal.setText(String.valueOf(inProg));
            pendingVal.setText(String.valueOf(pend));
            resolvedVal.setText(String.valueOf(res));

            String query = searchField.getText().trim().toLowerCase();
            casesListContainer.removeAll();

            int matchesCount = 0;
            for (Complaint c : assignedCases) {
                if (!"All".equalsIgnoreCase(activeFilter[0])) {
                    if (!c.status.equalsIgnoreCase(activeFilter[0])) {
                        if ("Resolved".equalsIgnoreCase(activeFilter[0]) && "Closed".equalsIgnoreCase(c.status)) {
                            // count closed under resolved
                        } else {
                            continue;
                        }
                    }
                }
                if (!query.isEmpty()) {
                    boolean matches = c.caseId.toLowerCase().contains(query)
                        || c.crimeType.toLowerCase().contains(query)
                        || c.complainant.toLowerCase().contains(query)
                        || c.remarks.toLowerCase().contains(query);
                    if (!matches) continue;
                }

                matchesCount++;
                casesListContainer.add(createComplaintCard(wrapper, c, CONTENT_WIDTH, () -> {
                    for (int i = 0; i < filters.length; i++) {
                        filterButtons[i].setBackground(filters[i].equals(activeFilter[0]) ? new Color(60, 100, 230) : new Color(20, 25, 40));
                        filterButtons[i].setForeground(filters[i].equals(activeFilter[0]) ? Color.WHITE : new Color(160, 170, 190));
                    }
                }));
                casesListContainer.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            if (matchesCount == 0) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setOpaque(false);
                emptyPanel.setBorder(new EmptyBorder(25, 10, 25, 10));
                emptyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel emptyLabel = new JLabel("No complaints found.");
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                emptyLabel.setForeground(new Color(130, 140, 160));
                emptyPanel.add(emptyLabel);
                casesListContainer.add(emptyPanel);
            }

            casesListContainer.revalidate();
            casesListContainer.repaint();
        };

        for (int i = 0; i < filters.length; i++) {
            String fName = filters[i];
            JButton fBtn = new JButton(fName);
            fBtn.setFont(new Font("Segoe UI", Font.BOLD, 10));
            fBtn.setFocusPainted(false);
            fBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            fBtn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(45, 60, 95), 1, true),
                new EmptyBorder(4, 9, 4, 9)
            ));
            if (fName.equals("All")) {
                fBtn.setBackground(new Color(60, 100, 230));
                fBtn.setForeground(Color.WHITE);
            } else {
                fBtn.setBackground(new Color(20, 25, 40));
                fBtn.setForeground(new Color(160, 170, 190));
            }

            fBtn.addActionListener(e -> {
                activeFilter[0] = fName;
                for (JButton b : filterButtons) {
                    b.setBackground(new Color(20, 25, 40));
                    b.setForeground(new Color(160, 170, 190));
                }
                fBtn.setBackground(new Color(60, 100, 230));
                fBtn.setForeground(Color.WHITE);
                updateView.run();
            });

            filterButtons[i] = fBtn;
            filterRow.add(fBtn);
        }

        mainContent.add(filterRow);
        mainContent.add(Box.createRigidArea(new Dimension(0, 14)));

        // 5. Section Header: "Assigned Complaints" (Centered)
        JPanel sectionHeader = new JPanel(new BorderLayout());
        sectionHeader.setOpaque(false);
        sectionHeader.setMaximumSize(new Dimension(CONTENT_WIDTH, 22));
        sectionHeader.setPreferredSize(new Dimension(CONTENT_WIDTH, 22));
        sectionHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sectionTitle = new JLabel("Assigned Complaints");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sectionTitle.setForeground(Color.WHITE);
        sectionHeader.add(sectionTitle, BorderLayout.WEST);

        mainContent.add(sectionHeader);
        mainContent.add(Box.createRigidArea(new Dimension(0, 8)));

        // 6. Complaints List
        mainContent.add(casesListContainer);
        mainContent.add(Box.createVerticalGlue());

        // Live search listener
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateView.run();
            }
        });

        // Initialize view
        updateView.run();

        // Scroll pane wrapper
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(11, 15, 25));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    // Helper: Metric Card with clean centering
    private static JPanel createMetricCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 26, 42));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel tLbl = new JLabel(title);
        tLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tLbl.setForeground(new Color(160, 175, 200));
        tLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(accent);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(tLbl);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(valueLabel);

        return card;
    }

    // Helper: Complaint Card using strict BorderLayout structure
    private static JPanel createComplaintCard(Component parent, Complaint complaint, int cardWidth, Runnable onStateChanged) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 26, 42));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(35, 48, 75), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));
        card.setMaximumSize(new Dimension(cardWidth, 145));
        card.setPreferredSize(new Dimension(cardWidth, 145));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 1. Top Row: Case ID & Status Badge
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel idLabel = new JLabel(complaint.caseId);
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idLabel.setForeground(new Color(100, 170, 255));
        topRow.add(idLabel, BorderLayout.WEST);

        JPanel badge = createStatusBadge(complaint.status);
        topRow.add(badge, BorderLayout.EAST);
        card.add(topRow, BorderLayout.NORTH);

        // 2. Middle Body: Crime Type + Complainant/Date + Remarks
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.setOpaque(false);

        JLabel typeLabel = new JLabel(complaint.crimeType);
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel metaLabel = new JLabel("By: " + complaint.complainant + "  •  " + complaint.date);
        metaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        metaLabel.setForeground(new Color(150, 165, 190));
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String remarksText = complaint.remarks.isEmpty() ? "No notes." : complaint.remarks;
        JLabel notesLabel = new JLabel("<html><div style='width: " + (cardWidth - 45) + "px; color: #8fa0b5; font-size: 10px;'>" + remarksText + "</div></html>");
        notesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        midPanel.add(typeLabel);
        midPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        midPanel.add(metaLabel);
        midPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        midPanel.add(notesLabel);

        card.add(midPanel, BorderLayout.CENTER);

        // 3. Bottom Row: Priority & Update Button
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);

        JLabel priorityLabel = new JLabel("Priority: " + complaint.priority);
        priorityLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        if ("Critical".equalsIgnoreCase(complaint.priority) || "High".equalsIgnoreCase(complaint.priority)) {
            priorityLabel.setForeground(new Color(255, 120, 120));
        } else {
            priorityLabel.setForeground(new Color(255, 200, 100));
        }
        bottomRow.add(priorityLabel, BorderLayout.WEST);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(new Color(60, 100, 230));
        updateBtn.setFocusPainted(false);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 100, 230), 1, true),
            new EmptyBorder(4, 14, 4, 14)
        ));
        updateBtn.addActionListener(e -> showUpdateDialog(parent, complaint, () -> {
            onStateChanged.run();
            Component root = SwingUtilities.getRoot(parent);
            if (root != null) root.repaint();
        }));
        bottomRow.add(updateBtn, BorderLayout.EAST);

        card.add(bottomRow, BorderLayout.SOUTH);

        return card;
    }

    // Status Badge Helper
    private static JPanel createStatusBadge(String status) {
        Color bg;
        Color fg;
        if ("In Progress".equalsIgnoreCase(status)) {
            bg = new Color(80, 65, 20);
            fg = new Color(255, 205, 85);
        } else if ("Pending".equalsIgnoreCase(status)) {
            bg = new Color(75, 25, 35);
            fg = new Color(255, 130, 140);
        } else if ("Resolved".equalsIgnoreCase(status) || "Closed".equalsIgnoreCase(status)) {
            bg = new Color(20, 65, 35);
            fg = new Color(90, 230, 145);
        } else {
            bg = new Color(30, 45, 80);
            fg = new Color(130, 180, 255);
        }

        JPanel badge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setLayout(new GridBagLayout());
        badge.setBorder(new EmptyBorder(2, 8, 2, 8));

        JLabel lbl = new JLabel(status);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(fg);
        badge.add(lbl);

        return badge;
    }

    // Interactive Dialog to update complaint status & investigation details
    private static void showUpdateDialog(Component parent, Complaint complaint, Runnable onStateChanged) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(window, "Update Complaint - " + complaint.caseId, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(340, 390);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(16, 22, 36));
        panel.setBorder(new EmptyBorder(18, 16, 18, 16));

        // Header Title
        JLabel title = new JLabel("Update Case Status & Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel caseMeta = new JLabel(complaint.caseId + " • " + complaint.crimeType);
        caseMeta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        caseMeta.setForeground(new Color(130, 150, 190));
        caseMeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(caseMeta);
        panel.add(Box.createRigidArea(new Dimension(0, 14)));

        // Status Combo
        JLabel statusLabel = new JLabel("Investigation Status:");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(statusLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        String[] statuses = {"In Progress", "Pending", "Resolved", "Closed"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setSelectedItem(complaint.status);
        statusCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusCombo.setBackground(new Color(25, 35, 55));
        statusCombo.setMaximumSize(new Dimension(300, 32));
        statusCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(statusCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 14)));

        // Remarks / Notes Field
        JLabel remarksLabel = new JLabel("Investigation Remarks / Actions:");
        remarksLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        remarksLabel.setForeground(Color.WHITE);
        remarksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(remarksLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextArea remarksArea = new JTextArea(4, 20);
        remarksArea.setText(complaint.remarks);
        remarksArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        remarksArea.setBackground(new Color(25, 35, 55));
        remarksArea.setForeground(Color.WHITE);
        remarksArea.setCaretColor(Color.WHITE);
        remarksArea.setLineWrap(true);
        remarksArea.setWrapStyleWord(true);
        remarksArea.setBorder(new EmptyBorder(6, 6, 6, 6));

        JScrollPane areaScroll = new JScrollPane(remarksArea);
        areaScroll.setBorder(new LineBorder(new Color(45, 60, 95), 1, true));
        areaScroll.setMaximumSize(new Dimension(300, 85));
        areaScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(areaScroll);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));

        // Action Buttons Row (Cancel, Save)
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.setMaximumSize(new Dimension(300, 35));
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cancelBtn.setBackground(new Color(30, 38, 55));
        cancelBtn.setForeground(new Color(200, 200, 210));
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        saveBtn.setBackground(new Color(60, 100, 230));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(e -> {
            complaint.status = (String) statusCombo.getSelectedItem();
            complaint.remarks = remarksArea.getText().trim();
            dialog.dispose();
            if (onStateChanged != null) {
                onStateChanged.run();
            }
            JOptionPane.showMessageDialog(
                parent,
                "Complaint " + complaint.caseId + " has been successfully updated.",
                "Case Updated",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);
        panel.add(btnRow);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
