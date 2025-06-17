package auth;

import java.util.ArrayList;
import java.util.List;
import model.Warga;
import util.FileHandler;

public class LoginManager {
    private static final String DEFAULT_FILE_PATH = "src\\data\\warga.txt";
    private String filePath;
    private List<Warga> daftarWarga;

    public static String getDefaultFilePath() {
        return DEFAULT_FILE_PATH;
    }

    // Default constructor
    public LoginManager() {
        this(DEFAULT_FILE_PATH);
    }

    // Constructor with file path parameter
    public LoginManager(String filePath) {
        this.filePath = filePath;
        this.daftarWarga = FileHandler.bacaDataWarga(filePath);
        if (this.daftarWarga == null) {
            this.daftarWarga = new ArrayList<>();
        }
    }

    public Warga login(String username, String password) {
        for (Warga warga : daftarWarga) {
            if (warga.getUsername().equals(username) && warga.getPassword().equals(password)) {
                return warga;
            }
        }
        return null; // Return null if not found
    }
}
