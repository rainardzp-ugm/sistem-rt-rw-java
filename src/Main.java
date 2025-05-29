import auth.LoginManager;
import model.Warga;
import model.UserRole;
import util.FileHandler;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LoginManager loginManager = new LoginManager();

    public static void main(String[] args) {
        System.out.println("=== SISTEM MANAJEMEN RT/RW ===");
        System.out.println("Initializing system...");

        // Test 1: Inisialisasi data sample jika file kosong
        inisialisasiDataSample();

        // Test 2: Test login system
        testLoginSystem();

        // Test 3: Menu utama
        menuUtama();
    }

    private static void inisialisasiDataSample() {
        System.out.println("\n1. Testing Data Initialization...");

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

            // Cek apakah file ada sebelum menambah data
            File file = new File(LoginManager.getDefaultFilePath());
            if (!file.exists()) {
                System.out.println("❌ File data/warga.txt tidak ditemukan!");
                System.out.println("Pastikan folder data/ dan file warga.txt sudah ada.");
                return;
            }

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

    private static void testLoginSystem() {
        System.out.println("\n2. Testing Login System...");

        // Test login admin
        Warga loginResult = loginManager.login("admin", "admin123");
        if (loginResult != null) {
            System.out.println("✓ Login admin berhasil: " + loginResult.getNama() + " (" + loginResult.getRole() + ")");
        } else {
            System.out.println("✗ Login admin gagal!");
        }

        // Test login warga
        loginResult = loginManager.login("budi", "budi123");
        if (loginResult != null) {
            System.out.println("✓ Login warga berhasil: " + loginResult.getNama() + " (" + loginResult.getRole() + ")");
        } else {
            System.out.println("✗ Login warga gagal!");
        }

        // Test login invalid
        loginResult = loginManager.login("invalid", "invalid");
        if (loginResult == null) {
            System.out.println("✓ Login invalid berhasil ditolak");
        } else {
            System.out.println("✗ Login invalid tidak ditolak!");
        }
    }

    private static void menuUtama() {
        System.out.println("\n3. Menu Login Interaktif");
        Warga userLogin = null;

        while (userLogin == null) {
            System.out.println("\n=== LOGIN SISTEM RT/RW ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            userLogin = loginManager.login(username, password);

            if (userLogin == null) {
                System.out.println("❌ Username atau password salah!");
                System.out.println("\nAccount yang tersedia untuk testing:");
                System.out.println("- admin / admin123 (Admin)");
                System.out.println("- budi / budi123 (Warga)");
                System.out.println("- siti / siti123 (Warga)");
                continue;
            }

            System.out.println("✅ Login berhasil!");
            System.out.println("Selamat datang, " + userLogin.getNama() + " (" + userLogin.getRole() + ")");

            if (userLogin.getRole() == UserRole.ADMIN) {
                menuAdmin(userLogin);
            } else {
                menuWarga(userLogin);
            }

            // Reset untuk login ulang
            userLogin = null;
            System.out.println("\nAnda telah logout.");
        }
    }

    private static void menuAdmin(Warga admin) {
        while (true) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Lihat Data Warga");
            System.out.println("2. Tambah Warga");
            System.out.println("3. Logout");
            System.out.print("Pilih menu (1-3): ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    lihatDataWarga();
                    break;
                case "2":
                    tambahWargaBaru();
                    break;
                case "3":
                    return; // Logout
                default:
                    System.out.println("❌ Pilihan tidak valid!");
            }
        }
    }

    private static void menuWarga(Warga warga) {
        while (true) {
            System.out.println("\n=== MENU WARGA ===");
            System.out.println("1. Lihat Profil");
            System.out.println("2. Update Nomor Telepon");
            System.out.println("3. Logout");
            System.out.print("Pilih menu (1-3): ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    lihatProfil(warga);
                    break;
                case "2":
                    updateNomorTelepon(warga);
                    break;
                case "3":
                    return; // Logout
                default:
                    System.out.println("❌ Pilihan tidak valid!");
            }
        }
    }

    private static void lihatDataWarga() {
        System.out.println("\n=== DATA WARGA RT/RW ===");
        List<Warga> daftarWarga = FileHandler.bacaDataWarga(LoginManager.getDefaultFilePath());

        if (daftarWarga.isEmpty()) {
            System.out.println("Tidak ada data warga.");
            return;
        }

        System.out.printf("%-15s %-20s %-15s %-10s %-15s %-10s%n",
                "Nama", "NIK", "Alamat", "Blok", "No. Telepon", "Role");
        System.out.println("=".repeat(90));

        for (Warga w : daftarWarga) {
            System.out.printf("%-15s %-20s %-15s %-10s %-15s %-10s%n",
                    w.getNama(), w.getNik(), w.getAlamat(),
                    w.getBlok(), w.getNoTelepon(), w.getRole());
        }
    }

    private static void tambahWargaBaru() {
        System.out.println("\n=== TAMBAH WARGA BARU ===");

        System.out.print("Nama: ");
        String nama = scanner.nextLine();

        System.out.print("NIK: ");
        String nik = scanner.nextLine();

        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();

        System.out.print("Blok: ");
        String blok = scanner.nextLine();

        System.out.print("No. Telepon: ");
        String noTelepon = scanner.nextLine();

        System.out.print("Status Rumah: ");
        String statusRumah = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Warga wargaBaru = new Warga(nama, nik, alamat, blok, noTelepon,
                statusRumah, username, password, UserRole.WARGA);

        if (FileHandler.tambahWarga(LoginManager.getDefaultFilePath(), wargaBaru)) {
            System.out.println("✅ Warga baru berhasil ditambahkan!");
        } else {
            System.out.println("❌ Gagal menambahkan warga baru!");
        }
    }

    private static void lihatProfil(Warga warga) {
        System.out.println("\n=== PROFIL ANDA ===");
        System.out.println("Nama: " + warga.getNama());
        System.out.println("NIK: " + warga.getNik());
        System.out.println("Alamat: " + warga.getAlamat());
        System.out.println("Blok: " + warga.getBlok());
        System.out.println("No. Telepon: " + warga.getNoTelepon());
        System.out.println("Status Rumah: " + warga.getStatusRumah());
        System.out.println("Role: " + warga.getRole());
    }

    private static void updateNomorTelepon(Warga warga) {
        System.out.println("\n=== UPDATE NOMOR TELEPON ===");
        System.out.println("Nomor telepon saat ini: " + warga.getNoTelepon());
        System.out.print("Nomor telepon baru: ");
        String nomorBaru = scanner.nextLine();

        warga.setNoTelepon(nomorBaru);

        // Update file
        List<Warga> daftarWarga = FileHandler.bacaDataWarga(LoginManager.getDefaultFilePath());
        for (int i = 0; i < daftarWarga.size(); i++) {
            if (daftarWarga.get(i).getUsername().equals(warga.getUsername())) {
                daftarWarga.set(i, warga);
                break;
            }
        }

        if (FileHandler.simpanDataWarga(LoginManager.getDefaultFilePath(), daftarWarga)) {
            System.out.println("✅ Nomor telepon berhasil diupdate!");
        } else {
            System.out.println("❌ Gagal mengupdate nomor telepon!");
        }
    }
}