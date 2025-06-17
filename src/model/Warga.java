package model;

public class Warga {
    private String nama;
    private String nik;
    private String alamat;
    private String blok;
    private String noTelepon;
    private String statusRumah;
    private String username;
    private String password;
    private UserRole role;

    public Warga(String nama, String nik, String alamat, String blok, String noTelepon,
                 String statusRumah, String username, String password, UserRole role) {
        this.nama = nama;
        this.nik = nik;
        this.alamat = alamat;
        this.blok = blok;
        this.noTelepon = noTelepon;
        this.statusRumah = statusRumah;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter & Setter
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getBlok() { return blok; }
    public void setBlok(String blok) { this.blok = blok; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getStatusRumah() { return statusRumah; }
    public void setStatusRumah(String statusRumah) { this.statusRumah = statusRumah; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    @Override
    public String toString() {
        return nama + "|" + nik + "|" + alamat + "|" + blok + "|" + noTelepon + "|" +
                statusRumah + "|" + username + "|" + password + "|" + role;
    }

    public static Warga fromString(String data) {
        String[] bagian = data.split("\\|");
        if (bagian.length == 9) {
            return new Warga(
                    bagian[0], // nama
                    bagian[1], // NIK
                    bagian[2], // alamat
                    bagian[3], // blok
                    bagian[4], // noTelepon
                    bagian[5], // statusRumah
                    bagian[6], // username
                    bagian[7], // password
                    UserRole.valueOf(bagian[8]) // role
            );
        }
        return null; // jika format tidak valid
    }
}
