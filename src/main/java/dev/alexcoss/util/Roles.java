package dev.alexcoss.util;

public enum Roles {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
