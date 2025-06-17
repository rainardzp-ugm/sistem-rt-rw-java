import auth.LoginManager;
import model.Warga;
import model.UserRole;
import util.FileHandler;
import gui.LoginFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEM MANAJEMEN RT/RW ===");
        System.out.println("Initializing system...");

        // Inisialisasi data sample jika diperlukan
        inisialisasiDataSample();

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    private static void inisialisasiDataSample() {
        System.out.println("Checking data initialization...");

        // Ensure data directory exists first
        File dataDir = new File("src\\data");
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                System.out.println("✓ Directory src\\data created successfully");
            } else {
                System.out.println("❌ Failed to create directory src\\data");
                return;
            }
        }

        // Ensure data file exists
        File dataFile = new File(LoginManager.getDefaultFilePath());
        if (!dataFile.exists()) {
            try {
                if (dataFile.createNewFile()) {
                    System.out.println("✓ File warga.txt created successfully");
                } else {
                    System.out.println("❌ Failed to create file warga.txt");
                    return;
                }
            } catch (IOException e) {
                System.out.println("❌ Error creating file: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        List<Warga> daftarWarga = FileHandler.bacaDataWarga(LoginManager.getDefaultFilePath());

        if (daftarWarga.isEmpty()) {
            System.out.println("File kosong atau tidak ada data, menambahkan data sample...");

            // Tambah admin default
            Warga admin = new Warga(
                    "Admin RT", "1234567890123456", "Jl. RT No. 1", "A1",
                    "081234567890", "Milik Sendiri", "admin", "admin123", UserRole.ADMIN
            );

            // Tambah warga sample
            Warga warga1 = new Warga(
                    "Budi Santoso", "3201012345670001", "Jl. Mawar No. 5", "B2",
                    "081234567891", "Milik Sendiri", "budi", "budi123", UserRole.WARGA
            );

            Warga warga2 = new Warga(
                    "Siti Aminah", "3201012345670002", "Jl. Melati No. 3", "A3",
                    "081234567892", "Kontrak", "siti", "siti123", UserRole.WARGA
            );

            if (FileHandler.tambahWarga(LoginManager.getDefaultFilePath(), admin) &&
                    FileHandler.tambahWarga(LoginManager.getDefaultFilePath(), warga1) &&
                    FileHandler.tambahWarga(LoginManager.getDefaultFilePath(), warga2)) {
                System.out.println("✓ Data sample berhasil ditambahkan!");
            } else {
                System.out.println("✗ Gagal menambahkan data sample!");
            }
        } else {
            System.out.println("✓ Data sudah ada (" + daftarWarga.size() + " warga terdaftar)");
        }
    }
}
