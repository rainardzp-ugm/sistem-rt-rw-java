package controller;

import java.util.ArrayList;
import java.util.List;
import model.UserRole;
import model.Warga;
import util.FileHandler;

public class WargaManager {
    private final String filePath;
    private List<Warga> daftarWarga;

    public WargaManager(String filePath) {
        this.filePath = filePath;
        this.daftarWarga = new ArrayList<>();
        loadData();
    }
    
    // Tambahkan method untuk mengedit data warga
    public boolean editWarga(String username, Warga wargaBaru) {
        for (int i = 0; i < daftarWarga.size(); i++) {
            Warga warga = daftarWarga.get(i);
            if (warga.getUsername().equals(username)) {
                // Mempertahankan username dan password jika tidak diubah
                if (wargaBaru.getPassword() == null || wargaBaru.getPassword().isEmpty()) {
                    wargaBaru.setPassword(warga.getPassword());
                }
                if (wargaBaru.getUsername() == null || wargaBaru.getUsername().isEmpty()) {
                    wargaBaru.setUsername(warga.getUsername());
                }
                
                // Update warga
                daftarWarga.set(i, wargaBaru);
                saveData();
                return true;
            }
        }
        return false;
    }
    
    // Tambahkan method untuk menghapus warga
    public boolean hapusWarga(String username) {
        for (int i = 0; i < daftarWarga.size(); i++) {
            if (daftarWarga.get(i).getUsername().equals(username)) {
                daftarWarga.remove(i);
                saveData();
                return true;
            }
        }
        return false;
    }
    
    // Method untuk mendapatkan warga berdasarkan username
    public Warga getWargaByUsername(String username) {
        for (Warga warga : daftarWarga) {
            if (warga.getUsername().equals(username)) {
                return warga;
            }
        }
        return null;
    }
    
    public void loadData() {
        daftarWarga = FileHandler.bacaDataWarga(filePath);
        
        // Jika data kosong, tambahkan data default
        if (daftarWarga.isEmpty()) {
            System.out.println("Data kosong, menambahkan data default Admin");
            
            // Menambahkan admin default
            Warga admin = new Warga(
                "Admin RT", 
                "3201234567890001", 
                "Jl. Admin", 
                "Blok A", 
                "08123456789", 
                "Milik Sendiri", 
                "admin", 
                "admin123", 
                UserRole.ADMIN
            );
            
            daftarWarga.add(admin);
            saveData();
        }
    }

    public void saveData() {
        FileHandler.simpanDataWarga(filePath, daftarWarga);
    }
    
    public List<Warga> getDaftarWarga() {
        return daftarWarga;
    }
}
