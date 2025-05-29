package model;

public enum UserRole {
    ADMIN,
    WARGA;

    public static UserRole fromString(String roleStr) {
        if (roleStr.equalsIgnoreCase("admin")) {
            return ADMIN;
        } else {
            return WARGA;
        }
    }
}
