package gui;

import auth.LoginManager;
import model.Warga;
import model.UserRole;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private LoginManager loginManager;
    
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(236, 254, 255);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color CARD_BACKGROUND = Color.WHITE;

    public LoginFrame() {
        loginManager = new LoginManager();
        initializeComponents();
        setFrameProperties();
    }

    private void initializeComponents() {
        // Main background with gradient
        JPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        
        // Login card
        JPanel loginCard = createLoginCard();
        
        backgroundPanel.add(loginCard);
        
        setContentPane(backgroundPanel);
    }
    
    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        // Use BorderLayout for better component organization
        card.setLayout(new BorderLayout(0, 10));
        card.setBackground(CARD_BACKGROUND);
        card.setPreferredSize(new Dimension(380, 580));
        
        // Add shadow and rounded corners
        card.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // North panel - Logo and title
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setOpaque(false);
        
        // Logo/Icon
        JPanel logoPanel = createLogoPanel();
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title
        JLabel titleLabel = new JLabel("Selamat Datang", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sistem Manajemen RT/RW", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        northPanel.add(Box.createVerticalStrut(10));
        northPanel.add(logoPanel);
        northPanel.add(Box.createVerticalStrut(20));
        northPanel.add(titleLabel);
        northPanel.add(Box.createVerticalStrut(5));
        northPanel.add(subtitleLabel);
        northPanel.add(Box.createVerticalStrut(20));
        
        // Center panel - Form fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        usernameField = createModernTextField("Username");
        passwordField = createModernPasswordField("Password");
        
        JPanel usernamePanel = createFieldPanel("Username", usernameField);
        JPanel passwordPanel = createFieldPanel("Password", passwordField);
        
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(usernamePanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createVerticalStrut(25));
        
        // Login button
        loginButton = createModernButton("MASUK", PRIMARY_COLOR);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        
        // Status label
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setOpaque(false);
        statusPanel.add(statusLabel);
        
        centerPanel.add(statusPanel);
        
        // South panel - Info panel
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setOpaque(false);
        
        JPanel infoPanel = createInfoPanel();
        southPanel.add(infoPanel);
        
        // Add all panels to card
        card.add(northPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(southPanel, BorderLayout.SOUTH);
        
        setupEventHandlers();
        
        return card;
    }
    
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setPreferredSize(new Dimension(80, 80));
        logoPanel.setMaximumSize(new Dimension(80, 80));
        
        // Create a modern geometric icon
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = 60;
                int x = (getWidth() - size) / 2;
                int y = 0;
                
                // Draw modern geometric logo
                g2.setColor(PRIMARY_COLOR);
                g2.fillRoundRect(x, y, size, size, 15, 15);
                
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                
                // Draw house-like shape
                int[] xPoints = {x + 15, x + 30, x + 45};
                int[] yPoints = {y + 25, y + 15, y + 25};
                g2.drawPolyline(xPoints, yPoints, 3);
                
                // Draw base
                g2.drawRect(x + 20, y + 25, 20, 20);
                g2.drawRect(x + 25, y + 30, 4, 8);
                g2.drawRect(x + 31, y + 30, 4, 4);
                
                g2.dispose();
            }
        };
        
        iconPanel.setPreferredSize(new Dimension(80, 80));
        logoPanel.add(iconPanel, BorderLayout.CENTER);
        
        return logoPanel;
    }
    
    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.dispose();
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setBackground(new Color(249, 250, 251));
        field.setForeground(TEXT_PRIMARY);
        
        // Placeholder effect
        addPlaceholderEffect(field, placeholder);
        
        return field;
    }
    
    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.dispose();
            }
        };
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        field.setBackground(new Color(249, 250, 251));
        field.setForeground(TEXT_PRIMARY);
        
        return field;
    }
    
    private JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(280, 70));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_SECONDARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        fieldPanel.setBorder(new RoundedBorder(10));
        fieldPanel.setPreferredSize(new Dimension(280, 40));
        fieldPanel.setMaximumSize(new Dimension(280, 40));
        fieldPanel.add(field, BorderLayout.CENTER);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(fieldPanel);
        
        return panel;
    }
    
    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, bgColor,
                    0, getHeight(), bgColor.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Button text
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(280, 45));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        infoPanel.setPreferredSize(new Dimension(280, 100));
        infoPanel.setMaximumSize(new Dimension(280, 100));
        infoPanel.setBackground(SECONDARY_COLOR);
        
        JLabel infoTitle = new JLabel("Akun Demo", JLabel.CENTER);
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        infoTitle.setForeground(TEXT_PRIMARY);
        infoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel adminInfo = new JLabel("Admin: admin / admin123", JLabel.CENTER);
        JLabel warga1Info = new JLabel("Warga: budi / budi123", JLabel.CENTER);
        JLabel warga2Info = new JLabel("Warga: siti / siti123", JLabel.CENTER);
        
        Font infoFont = new Font("Segoe UI", Font.PLAIN, 11);
        adminInfo.setFont(infoFont);
        warga1Info.setFont(infoFont);
        warga2Info.setFont(infoFont);
        
        adminInfo.setForeground(TEXT_SECONDARY);
        warga1Info.setForeground(TEXT_SECONDARY);
        warga2Info.setForeground(TEXT_SECONDARY);
        
        adminInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        warga1Info.setAlignmentX(Component.CENTER_ALIGNMENT);
        warga2Info.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(infoTitle);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(adminInfo);
        infoPanel.add(warga1Info);
        infoPanel.add(warga2Info);
        
        return infoPanel;
    }
    
    private void addPlaceholderEffect(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(TEXT_SECONDARY);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_SECONDARY);
                }
            }
        });
    }
    
    private void setupEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        if (username.equals("Username")) username = "";
        
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Username dan password tidak boleh kosong!", ERROR_COLOR);
            return;
        }

        Warga user = loginManager.login(username, password);

        if (user != null) {
            showStatus("Login berhasil! Selamat datang, " + user.getNama(), SUCCESS_COLOR);
            
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openMainFrame(user);
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            showStatus("Username atau password salah!", ERROR_COLOR);
            passwordField.setText("");
        }
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private void openMainFrame(Warga user) {
        this.setVisible(false);
        
        if (user.getRole() == UserRole.ADMIN) {
            new AdminFrame(user, this).setVisible(true);
        } else {
            new WargaFrame(user, this).setVisible(true);
        }
    }

    private void setFrameProperties() {
        setTitle("Login - Sistem RT/RW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(450, 650);
        setLocationRelativeTo(null);
    }

    public void showFrame() {
        usernameField.setText("Username");
        usernameField.setForeground(TEXT_SECONDARY);
        passwordField.setText("");
        statusLabel.setText(" ");
        setVisible(true);
    }

    // Custom gradient background panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            // Improved soft gradient
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(240, 245, 255),
                0, getHeight(), new Color(230, 232, 255)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Add some subtle pattern
            g2d.setColor(new Color(255, 255, 255, 40));
            for (int i = 0; i < getHeight(); i += 20) {
                g2d.drawLine(0, i, getWidth(), i);
            }
        }
    }
    
    // Custom rounded border
    class RoundedBorder extends AbstractBorder {
        private int radius;
        
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(229, 231, 235));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }
    
    // Custom shadow border
    class ShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Shadow
            for (int i = 0; i < 5; i++) {
                g2.setColor(new Color(0, 0, 0, 10 - i*2));
                g2.drawRoundRect(x + i, y + i, width - 2*i - 1, height - 2*i - 1, 20, 20);
            }
            
            // White outline for the card
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, width - 1, height - 1, 20, 20);
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new LoginFrame().setVisible(true);
            }
        });
    }
}
