package auth;

import model.Warga;
import util.FileHandler;

import java.util.List;

public class LoginManager {
    private List<Warga> daftarWarga;

    public LoginManager(){
        this.daftarWarga = FileHandler.bacaDataWarga(filePath);
    }

    public Warga login(String username, String password) {
        for (Warga warga : daftarWarga){
            if (warga.getUsername().equals(username) && warga.getPassword().equals(password)) {
                return warga;
            }
        }
        return null; // Jika tidak ditemukan, kembalikan null
    }
}
