package util;

import model.Warga;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<Warga> bacaDataWarga(String filePath) {
        List<Warga> daftarWarga = new ArrayList<>();

        try {
            File file = new File(filePath);

            // Jika file tidak ada, return empty list
            if (!file.exists()) {
                System.out.println("File " + filePath + " tidak ditemukan. Menunggu data dari file yang ada.");
                return daftarWarga; // Return empty list
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        Warga warga = Warga.fromString(line);
                        if (warga != null) {
                            daftarWarga.add(warga);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error membaca file: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error
        }

        return daftarWarga;
    }

    public static boolean simpanDataWarga(String filePath, List<Warga> daftarWarga) {
        try {
            File file = new File(filePath);

            // Cek apakah file exists, jika tidak return false
            if (!file.exists()) {
                System.err.println("File " + filePath + " tidak ditemukan!");
                return false;
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (Warga warga : daftarWarga) {
                    writer.println(warga.toString());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error menyimpan file: " + e.getMessage());
            return false;
        }
    }

    public static boolean tambahWarga(String filePath, Warga warga) {
        List<Warga> daftarWarga = bacaDataWarga(filePath);
        daftarWarga.add(warga);
        return simpanDataWarga(filePath, daftarWarga);
    }
}