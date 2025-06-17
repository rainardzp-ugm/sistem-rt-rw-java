package gui;

import controller.WargaManager;
import model.Warga;
import model.UserRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWargaFrame extends JFrame {
    private JTextField txtNama, txtNik, txtAlamat, txtBlok, txtNoTelepon, txtUsername;
    private JComboBox<String> cmbStatusRumah;
    private JPasswordField txtPassword;
    private JComboBox<UserRole> cmbRole;
    private JButton btnSimpan, btnBatal;
    private WargaManager wargaManager;
    private AdminFrame adminFrame;
    private Warga wargaToEdit;

    public EditWargaFrame(WargaManager wargaManager, AdminFrame adminFrame, String username) {
        this.wargaManager = wargaManager;
        this.adminFrame = adminFrame;
        this.wargaToEdit = wargaManager.getWargaByUsername(username);
        
        if (wargaToEdit == null) {
            JOptionPane.showMessageDialog(this, "Warga dengan username " + username + " tidak ditemukan", 
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        setTitle("Edit Data Warga");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        fillFormWithData();
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nama
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nama:"), gbc);
        
        gbc.gridx = 1;
        txtNama = new JTextField(20);
        panel.add(txtNama, gbc);
        
        // NIK
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("NIK:"), gbc);
        
        gbc.gridx = 1;
        txtNik = new JTextField(20);
        panel.add(txtNik, gbc);
        
        // Alamat
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Alamat:"), gbc);
        
        gbc.gridx = 1;
        txtAlamat = new JTextField(20);
        panel.add(txtAlamat, gbc);
        
        // Blok
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Blok:"), gbc);
        
        gbc.gridx = 1;
        txtBlok = new JTextField(20);
        panel.add(txtBlok, gbc);
        
        // No Telepon
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("No. Telepon:"), gbc);
        
        gbc.gridx = 1;
        txtNoTelepon = new JTextField(20);
        panel.add(txtNoTelepon, gbc);
        
        // Status Rumah
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Status Rumah:"), gbc);
        
        gbc.gridx = 1;
        cmbStatusRumah = new JComboBox<>(new String[]{"Milik Sendiri", "Kontrak", "Kost"});
        panel.add(cmbStatusRumah, gbc);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);
        
        gbc.gridy = 8;
        panel.add(new JLabel("(Kosongkan jika tidak ingin mengubah password)"), gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Role:"), gbc);
        
        gbc.gridx = 1;
        cmbRole = new JComboBox<>(UserRole.values());
        panel.add(cmbRole, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanPerubahan();
            }
        });
        
        btnBatal = new JButton("Batal");
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
        panel.add(buttonPanel, gbc);
        
        // Add scroll pane in case the form gets too large
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        add(scrollPane);
    }
    
    private void fillFormWithData() {
        txtNama.setText(wargaToEdit.getNama());
        txtNik.setText(wargaToEdit.getNik());
        txtAlamat.setText(wargaToEdit.getAlamat());
        txtBlok.setText(wargaToEdit.getBlok());
        txtNoTelepon.setText(wargaToEdit.getNoTelepon());
        cmbStatusRumah.setSelectedItem(wargaToEdit.getStatusRumah());
        txtUsername.setText(wargaToEdit.getUsername());
        // Password tidak diisi karena alasan keamanan
        cmbRole.setSelectedItem(wargaToEdit.getRole());
    }
    
    private void simpanPerubahan() {
        String nama = txtNama.getText().trim();
        String nik = txtNik.getText().trim();
        String alamat = txtAlamat.getText().trim();
        String blok = txtBlok.getText().trim();
        String noTelepon = txtNoTelepon.getText().trim();
        String statusRumah = (String) cmbStatusRumah.getSelectedItem();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        UserRole role = (UserRole) cmbRole.getSelectedItem();
        
        if (nama.isEmpty() || nik.isEmpty() || alamat.isEmpty() || 
            blok.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Field Nama, NIK, Alamat, Blok, dan Username harus diisi", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Gunakan konstruktor sesuai dengan Warga.java
        Warga wargaBaru = new Warga(
            nama, 
            nik, 
            alamat, 
            blok,
            noTelepon, 
            statusRumah,
            username, 
            password.isEmpty() ? wargaToEdit.getPassword() : password,
            role
        );
        
        // Jika username berubah, periksa apakah username baru sudah ada
        if (!username.equals(wargaToEdit.getUsername()) && 
            wargaManager.getWargaByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, 
                "Username sudah digunakan. Pilih username lain.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (wargaManager.editWarga(wargaToEdit.getUsername(), wargaBaru)) {
            JOptionPane.showMessageDialog(this, 
                "Data warga berhasil diubah", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            adminFrame.refreshTable();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengubah data warga", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}