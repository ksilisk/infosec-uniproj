package com.ksilisk.infosec.entity;

public record User(String username, String password,
                   boolean isBlocked, String passwordRestriction, boolean isAdmin) {
}
