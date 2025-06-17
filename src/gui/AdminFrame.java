package gui;

import auth.LoginManager;
import controller.WargaManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.UserRole;
import model.Warga;
import util.FileHandler;

public class AdminFrame extends JFrame {
    private Warga admin;
    private LoginFrame loginFrame;
    private JTable wargaTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, addButton, editButton, deleteButton, logoutButton;
    private JLabel totalWargaLabel, totalAdminLabel;
    
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    private static final Color WARNING_COLOR = new Color(245, 158, 11);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color BACKGROUND = new Color(249, 250, 251);
    private static final Color CARD_BACKGROUND = Color.WHITE;

    public AdminFrame(Warga admin, LoginFrame loginFrame) {
        this.admin = admin;
        this.loginFrame = loginFrame;
        initializeComponents();
        setupEventHandlers(); // ADD THIS LINE - Call setupEventHandlers after components are initialized
        setFrameProperties();
        loadWargaData();
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
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Left side - Title and welcome
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Dashboard Admin");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel welcomeLabel = new JLabel("Selamat datang, " + admin.getNama());
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
        
        // Stats cards
        mainPanel.add(createStatsPanel(), BorderLayout.NORTH);
        
        // Table panel
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Total Warga Card
        totalWargaLabel = new JLabel("0");
        JPanel wargaCard = createStatsCard("Total Warga", totalWargaLabel, SUCCESS_COLOR);
        
        // Total Admin Card
        totalAdminLabel = new JLabel("0");
        JPanel adminCard = createStatsCard("Total Admin", totalAdminLabel, PRIMARY_COLOR);
        
        // Action Card
        JPanel actionCard = createActionCard();
        
        statsPanel.add(wargaCard);
        statsPanel.add(adminCard);
        statsPanel.add(actionCard);
        
        return statsPanel;
    }
    
    private JPanel createStatsCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            new ModernCardBorder(),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(valueLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(titleLabel);
        
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createActionCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            new ModernCardBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("Aksi Cepat", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        buttonPanel.setOpaque(false);
        
        addButton = createModernButton("Tambah Warga", SUCCESS_COLOR);
        editButton = createModernButton("Edit Warga", PRIMARY_COLOR);
        deleteButton = createModernButton("Hapus Warga", ERROR_COLOR);
        refreshButton = createModernButton("Refresh Data", WARNING_COLOR);
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BACKGROUND);
        tablePanel.setBorder(new ModernCardBorder());
        
        // Table header
        JPanel tableHeaderPanel = new JPanel(new BorderLayout());
        tableHeaderPanel.setBackground(CARD_BACKGROUND);
        tableHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel tableTitle = new JLabel("Data Warga RT/RW");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setForeground(TEXT_PRIMARY);
        
        tableHeaderPanel.add(tableTitle, BorderLayout.WEST);
        
        // Table
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nama", "NIK", "Alamat", "Blok", "No. Telepon", "Status Rumah", "Username", "Role"}
        );
        
        wargaTable = new JTable(tableModel);
        setupModernTable();
        
        JScrollPane scrollPane = new JScrollPane(wargaTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scrollPane.getViewport().setBackground(CARD_BACKGROUND);
        
        tablePanel.add(tableHeaderPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private void setupModernTable() {
        wargaTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        wargaTable.setRowHeight(40);
        wargaTable.setShowGrid(false);
        wargaTable.setIntercellSpacing(new Dimension(0, 0));
        wargaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wargaTable.setBackground(CARD_BACKGROUND);
        
        // Header styling
        JTableHeader header = wargaTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        header.setReorderingAllowed(false);
        
        // Cell renderer for alternating row colors
        wargaTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(99, 102, 241, 50));
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(row % 2 == 0 ? CARD_BACKGROUND : new Color(248, 250, 252));
                    c.setForeground(TEXT_PRIMARY);
                }
                
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                
                return c;
            }
        });
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(248, 250, 252));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel footerLabel = new JLabel("© 2024 Sistem RT/RW - Dashboard Admin");
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
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
        if (refreshButton != null) {
            refreshButton.addActionListener(__ -> {
                loadWargaData();
                showNotification("Data berhasil direfresh!", SUCCESS_COLOR);
            });
        }

        if (addButton != null) {
            addButton.addActionListener(__ -> showAddWargaDialog());
        }
        
        if (editButton != null) {
            editButton.addActionListener(__ -> editSelectedWarga());
        }
        
        if (deleteButton != null) {
            deleteButton.addActionListener(__ -> deleteSelectedWarga());
        }

        if (logoutButton != null) {
            logoutButton.addActionListener(__ -> {
                int option = JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", 
                    JOptionPane.YES_NO_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    this.setVisible(false);
                    loginFrame.showFrame();
                }
            });
        }
    }

    private void loadWargaData() {
        tableModel.setRowCount(0);
        List<Warga> daftarWarga = FileHandler.bacaDataWarga(LoginManager.getDefaultFilePath());
        
        int totalWarga = 0;
        int totalAdmin = 0;
        
        for (Warga warga : daftarWarga) {
            Object[] rowData = {
                warga.getNama(),
                warga.getNik(),
                warga.getAlamat(),
                warga.getBlok(),
                warga.getNoTelepon(),
                warga.getStatusRumah(),
                warga.getUsername(),
                warga.getRole() == UserRole.ADMIN ? "Admin" : "Warga"
            };
            tableModel.addRow(rowData);
            
            if (warga.getRole() == UserRole.ADMIN) {
                totalAdmin++;
            } else {
                totalWarga++;
            }
        }
        
        totalWargaLabel.setText(String.valueOf(totalWarga));
        totalAdminLabel.setText(String.valueOf(totalAdmin));
    }

    private void showAddWargaDialog() {
        JDialog dialog = new JDialog(this, "Tambah Warga Baru", true);
        dialog.setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(CARD_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Form fields
        JTextField namaField = createModernTextField();
        JTextField nikField = createModernTextField();
        JTextField alamatField = createModernTextField();
        JTextField blokField = createModernTextField();
        JTextField teleponField = createModernTextField();
        JTextField statusRumahField = createModernTextField();
        JTextField usernameField = createModernTextField();
        JPasswordField passwordField = createModernPasswordField();

        // Add components
        String[] labels = {"Nama:", "NIK:", "Alamat:", "Blok:", "No. Telepon:", "Status Rumah:", "Username:", "Password:"};
        JTextField[] fields = {namaField, nikField, alamatField, blokField, teleponField, statusRumahField, usernameField, passwordField};
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(TEXT_PRIMARY);
            mainPanel.add(label, gbc);
            
            gbc.gridx = 1;
            fields[i].setPreferredSize(new Dimension(250, 35));
            mainPanel.add(fields[i], gbc);
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        JButton saveButton = createModernButton("Simpan", SUCCESS_COLOR);
        JButton cancelButton = createModernButton("Batal", ERROR_COLOR);

        saveButton.addActionListener(__ -> {
            if (namaField.getText().trim().isEmpty() || 
                nikField.getText().trim().isEmpty() ||
                usernameField.getText().trim().isEmpty() ||
                passwordField.getPassword().length == 0) {
                showNotification("Harap isi semua field yang wajib!", ERROR_COLOR);
                return;
            }

            Warga wargaBaru = new Warga(
                namaField.getText().trim(),
                nikField.getText().trim(),
                alamatField.getText().trim(),
                blokField.getText().trim(),
                teleponField.getText().trim(),
                statusRumahField.getText().trim(),
                usernameField.getText().trim(),
                new String(passwordField.getPassword()),
                UserRole.WARGA
            );

            if (FileHandler.tambahWarga(LoginManager.getDefaultFilePath(), wargaBaru)) {
                showNotification("Warga baru berhasil ditambahkan!", SUCCESS_COLOR);
                loadWargaData();
                dialog.dispose();
            } else {
                showNotification("Gagal menambahkan warga baru!", ERROR_COLOR);
            }
        });

        cancelButton.addActionListener(__ -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private JTextField createModernTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }
    
    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }
    
    private void showNotification(String message, Color color) {
        JOptionPane.showMessageDialog(this, message, 
            color == SUCCESS_COLOR ? "Sukses" : "Peringatan", 
            color == SUCCESS_COLOR ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    private void setFrameProperties() {
        setTitle("Dashboard Admin - Sistem RT/RW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
    }
    
    // Custom modern card border
    class ModernCardBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Shadow
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fillRoundRect(x + 2, y + 2, width - 2, height - 2, 12, 12);
            
            // Border
            g2.setColor(new Color(229, 231, 235));
            g2.drawRoundRect(x, y, width - 1, height - 1, 12, 12);
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }

    private void editSelectedWarga() {
        int selectedRow = wargaTable.getSelectedRow();
        if (selectedRow == -1) {
            showNotification("Pilih warga yang akan diedit terlebih dahulu!", ERROR_COLOR);
            return;
        }
        
        String username = (String) wargaTable.getValueAt(selectedRow, 6); // Index 6 adalah kolom username
        
        // Buat instance WargaManager dan EditWargaFrame
        WargaManager wargaManager = new WargaManager(LoginManager.getDefaultFilePath());
        EditWargaFrame editFrame = new EditWargaFrame(wargaManager, this, username);
        editFrame.setVisible(true);
    }

    private void deleteSelectedWarga() {
        int selectedRow = wargaTable.getSelectedRow();
        if (selectedRow == -1) {
            showNotification("Pilih warga yang akan dihapus terlebih dahulu!", ERROR_COLOR);
            return;
        }
        
        String username = (String) wargaTable.getValueAt(selectedRow, 6); // Index 6 adalah kolom username
        String nama = (String) wargaTable.getValueAt(selectedRow, 0); // Index 0 adalah kolom nama
        
        int option = JOptionPane.showConfirmDialog(
            this,
            "Anda yakin ingin menghapus warga " + nama + " (" + username + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            WargaManager wargaManager = new WargaManager(LoginManager.getDefaultFilePath());
            if (wargaManager.hapusWarga(username)) {
                showNotification("Warga berhasil dihapus!", SUCCESS_COLOR);
                loadWargaData(); // Refresh data
            } else {
                showNotification("Gagal menghapus warga!", ERROR_COLOR);
            }
        }
    }

    public void refreshTable() {
        loadWargaData();
    }
}
