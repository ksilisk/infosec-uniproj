package com.ksilisk.infosec.entity;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private boolean isBlocked;
    private boolean hasPasswordRestriction;
    private boolean isAdmin;

    public User(String username, String password,
                boolean isBlocked, boolean hasPasswordRestriction, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked;
        this.hasPasswordRestriction = hasPasswordRestriction;
        this.isAdmin = isAdmin;
    }

    public User() {
        this("", "", false, false, false);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public void setHasPasswordRestriction(boolean hasPasswordRestriction) {
        this.hasPasswordRestriction = hasPasswordRestriction;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public boolean getHasPasswordRestriction() {
        return hasPasswordRestriction;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password) &&
                this.isBlocked == that.isBlocked &&
                this.hasPasswordRestriction == that.hasPasswordRestriction &&
                this.isAdmin == that.isAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isBlocked, hasPasswordRestriction, isAdmin);
    }

    @Override
    public String toString() {
        return "User[" +
                "username=" + username + ", " +
                "password=" + password + ", " +
                "isBlocked=" + isBlocked + ", " +
                "hasPasswordRestriction=" + hasPasswordRestriction + ", " +
                "isAdmin=" + isAdmin + ']';
    }

}
