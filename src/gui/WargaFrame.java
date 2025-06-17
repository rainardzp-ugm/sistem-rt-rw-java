package gui;

import model.Warga;
import util.FileHandler;
import auth.LoginManager;
import model.UserRole;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class WargaFrame extends JFrame {
    private Warga warga;
    private LoginFrame loginFrame;
    private JLabel namaLabel, nikLabel, alamatLabel, blokLabel, teleponLabel, statusRumahLabel, roleLabel;
    private JButton updateTeleponButton, logoutButton;
    
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(34, 197, 94);
    private static final Color SECONDARY_COLOR = new Color(59, 130, 246);
    private static final Color WARNING_COLOR = new Color(245, 158, 11);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color BACKGROUND = new Color(249, 250, 251);
    private static final Color CARD_BACKGROUND = Color.WHITE;

    public WargaFrame(Warga warga, LoginFrame loginFrame) {
        this.warga = warga;
        this.loginFrame = loginFrame;
        initializeComponents();
        setFrameProperties();
        loadProfileData();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main content
        add(createMainPanel(), BorderLayout.CENTER);
        
        // Footer
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // Left side - Title and welcome
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Profil Warga");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel welcomeLabel = new JLabel("Selamat datang, " + warga.getNama());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(255, 255, 255, 180));
        
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(welcomeLabel);
        
        // Right side - Logout button
        logoutButton = createModernButton("Logout", ERROR_COLOR);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create scroll pane for better layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        // Profile card
        JPanel profileCard = createProfileCard();
        contentPanel.add(profileCard, BorderLayout.CENTER);
        
        // Action panel
        JPanel actionPanel = createActionPanel();
        contentPanel.add(actionPanel, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createProfileCard() {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            new ModernCardBorder(),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        // Avatar section
        JPanel avatarPanel = createAvatarPanel();
        
        // Profile info section
        JPanel infoPanel = createProfileInfoPanel();
        
        card.add(avatarPanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createAvatarPanel() {
        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new BoxLayout(avatarPanel, BoxLayout.Y_AXIS));
        avatarPanel.setOpaque(false);
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Avatar circle
        JPanel avatarCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Circle background
                g2.setColor(PRIMARY_COLOR);
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                // User initial
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
                FontMetrics fm = g2.getFontMetrics();
                String initial = warga.getNama().substring(0, 1).toUpperCase();
                int x = (getWidth() - fm.stringWidth(initial)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 5;
                g2.drawString(initial, x, y);
                
                g2.dispose();
            }
        };
        avatarCircle.setPreferredSize(new Dimension(80, 80));
        avatarCircle.setMaximumSize(new Dimension(80, 80));
        avatarCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Name label
        JLabel nameLabel = new JLabel(warga.getNama(), JLabel.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Role badge
        String roleText = warga.getRole() == UserRole.ADMIN ? "ADMINISTRATOR" : "WARGA RT/RW";
        JLabel roleBadge = new JLabel(roleText, JLabel.CENTER);
        roleBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        roleBadge.setForeground(Color.WHITE);
        roleBadge.setOpaque(true);
        roleBadge.setBackground(PRIMARY_COLOR);
        roleBadge.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        roleBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        avatarPanel.add(avatarCircle);
        avatarPanel.add(Box.createVerticalStrut(15));
        avatarPanel.add(nameLabel);
        avatarPanel.add(Box.createVerticalStrut(8));
        avatarPanel.add(roleBadge);
        
        return avatarPanel;
    }
    
    private JPanel createProfileInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Initialize labels
        namaLabel = new JLabel();
        nikLabel = new JLabel();
        alamatLabel = new JLabel();
        blokLabel = new JLabel();
        teleponLabel = new JLabel();
        statusRumahLabel = new JLabel();
        roleLabel = new JLabel();

        // Profile fields
        String[] labels = {"Nama Lengkap", "NIK", "Alamat", "Blok", "No. Telepon", "Status Rumah", "Role"};
        JLabel[] valueLabels = {namaLabel, nikLabel, alamatLabel, blokLabel, teleponLabel, statusRumahLabel, roleLabel};

        for (int i = 0; i < labels.length; i++) {
            JPanel rowPanel = createInfoRow(labels[i], valueLabels[i]);
            infoPanel.add(rowPanel);
            
            if (i < labels.length - 1) {
                infoPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        return infoPanel;
    }
    
    private JPanel createInfoRow(String label, JLabel valueLabel) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(12, 0, 12, 0)
        ));
        rowPanel.setPreferredSize(new Dimension(0, 50));
        
        // Left side - Label
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelText.setForeground(TEXT_SECONDARY);
        labelText.setPreferredSize(new Dimension(120, 25));
        
        // Right side - Value
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valueLabel.setForeground(TEXT_PRIMARY);
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        rowPanel.add(labelText, BorderLayout.WEST);
        rowPanel.add(valueLabel, BorderLayout.CENTER);
        
        return rowPanel;
    }
    
    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        updateTeleponButton = createModernButton("Update Nomor Telepon", WARNING_COLOR);
        
        actionPanel.add(updateTeleponButton);
        
        setupEventHandlers();
        
        return actionPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(248, 250, 252));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel footerLabel = new JLabel("© 2024 Sistem RT/RW - Profil Warga");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(TEXT_SECONDARY);
        
        footerPanel.add(footerLabel, BorderLayout.WEST);
        
        return footerPanel;
    }
    
    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, bgColor,
                    0, getHeight(), bgColor.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.repaint();
            }
        });
        
        return button;
    }

    private void setupEventHandlers() {
        updateTeleponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateTeleponDialog();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(WargaFrame.this, 
                    "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", 
                    JOptionPane.YES_NO_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    WargaFrame.this.setVisible(false);
                    loginFrame.showFrame();
                }
            }
        });
    }

    private void loadProfileData() {
        // Load all profile data
        namaLabel.setText(warga.getNama());
        nikLabel.setText(warga.getNik());
        alamatLabel.setText(warga.getAlamat());
        blokLabel.setText(warga.getBlok());
        teleponLabel.setText(warga.getNoTelepon());
        statusRumahLabel.setText(warga.getStatusRumah());
        
        // Style role label with color coding
        if (warga.getRole() == UserRole.ADMIN) {
            roleLabel.setText("Administrator");
            roleLabel.setForeground(PRIMARY_COLOR);
        } else {
            roleLabel.setText("Warga");
            roleLabel.setForeground(TEXT_PRIMARY);
        }
        
        // Debug print to console
        System.out.println("Loading profile data:");
        System.out.println("Nama: " + warga.getNama());
        System.out.println("NIK: " + warga.getNik());
        System.out.println("Alamat: " + warga.getAlamat());
        System.out.println("Blok: " + warga.getBlok());
        System.out.println("Telepon: " + warga.getNoTelepon());
        System.out.println("Status Rumah: " + warga.getStatusRumah());
        System.out.println("Role: " + warga.getRole());
    }

    private void showUpdateTeleponDialog() {
        // Create modern dialog
        JDialog dialog = new JDialog(this, "Update Nomor Telepon", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(CARD_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Current number info
        JLabel currentLabel = new JLabel("Nomor telepon saat ini:");
        currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        currentLabel.setForeground(TEXT_SECONDARY);
        currentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel currentNumber = new JLabel(warga.getNoTelepon());
        currentNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        currentNumber.setForeground(PRIMARY_COLOR);
        currentNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // New number input
        JLabel newLabel = new JLabel("Nomor telepon baru:");
        newLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newLabel.setForeground(TEXT_SECONDARY);
        newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField newNumberField = new JTextField(20);
        newNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        newNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        newNumberField.setMaximumSize(new Dimension(300, 40));
        newNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        JButton saveButton = createModernButton("Simpan", PRIMARY_COLOR);
        JButton cancelButton = createModernButton("Batal", ERROR_COLOR);
        
        saveButton.addActionListener(e -> {
            String nomorBaru = newNumberField.getText().trim();
            if (!nomorBaru.isEmpty()) {
                warga.setNoTelepon(nomorBaru);

                List<Warga> daftarWarga = FileHandler.bacaDataWarga(LoginManager.getDefaultFilePath());
                for (int i = 0; i < daftarWarga.size(); i++) {
                    if (daftarWarga.get(i).getUsername().equals(warga.getUsername())) {
                        daftarWarga.set(i, warga);
                        break;
                    }
                }

                if (FileHandler.simpanDataWarga(LoginManager.getDefaultFilePath(), daftarWarga)) {
                    JOptionPane.showMessageDialog(dialog, "Nomor telepon berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    loadProfileData();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gagal mengupdate nomor telepon!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Nomor telepon tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add components
        mainPanel.add(currentLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(currentNumber);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(newLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(newNumberField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void setFrameProperties() {
        setTitle("Profil Warga - Sistem RT/RW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
    }
    
    // Custom card border
    private static class ModernCardBorder extends AbstractBorder {
        private final int shadowSize = 5;
        private final float shadowOpacity = 0.1f;
        private final Color shadowColor = Color.BLACK;
        private final int borderRadius = 15;
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Shadow
            g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), (int) (shadowOpacity * 255)));
            for (int i = 0; i < shadowSize; i++) {
                g2.drawRoundRect(x + i, y + i, width - (i * 2) - 1, height - (i * 2) - 1, borderRadius, borderRadius);
            }
            
            // Border
            g2.setColor(new Color(229, 231, 235));
            g2.drawRoundRect(x, y, width - 1, height - 1, borderRadius, borderRadius);
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize + 2, shadowSize + 2, shadowSize + 2, shadowSize + 2);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.top = shadowSize + 2;
            insets.left = shadowSize + 2;
            insets.bottom = shadowSize + 2;
            insets.right = shadowSize + 2;
            return insets;
        }
    }
}
