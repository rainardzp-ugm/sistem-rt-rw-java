package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Warga;

public class FileHandler {

    public static List<Warga> bacaDataWarga(String filePath) {
        List<Warga> daftarWarga = new ArrayList<>();

        try {
            File file = new File(filePath);

            // Jika file tidak ada, buat file kosong
            if (!file.exists()) {
                // Create directory structure if it doesn't exist
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                
                file.createNewFile();
                System.out.println("File " + filePath + " tidak ditemukan. File baru dibuat.");
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
            
            // Create directory structure if it doesn't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
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
        
        // Check if username already exists
        for (Warga w : daftarWarga) {
            if (w.getUsername().equals(warga.getUsername())) {
                System.err.println("Username " + warga.getUsername() + " sudah ada!");
                return false;
            }
        }
        
        daftarWarga.add(warga);
        return simpanDataWarga(filePath, daftarWarga);
    }
}
