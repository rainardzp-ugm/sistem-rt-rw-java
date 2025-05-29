package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Warga;
import model.UserRole;


// This class is a placeholder for file handling utilities.

public class FileHandler {

    private static final String NAMA_FILE = "warga.txt";

    // Fungsi menyimpan semua data warga ke file
    public static void simpanDataWarga(List<Warga> daftarWarga) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (Warga w : daftarWarga) {
                writer.write(w.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    // Fungsi membaca data dari file ke dalam list warga
    public static List<Warga> bacaDataWarga() {
        List<Warga> daftarWarga = new ArrayList<>();

        File file = new File(NAMA_FILE);
        if (!file.exists()) {
            return daftarWarga; // jika file belum ada, kembalikan list kosong
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                Warga warga = Warga.fromString(baris);
                if (warga != null) {
                    daftarWarga.add(warga);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data: " + e.getMessage());
        }

        return daftarWarga;
    }
}