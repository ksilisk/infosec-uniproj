package com.ksilisk.infosec.validate;

import java.util.List;

public enum DefaultPasswordRestrictionValidator implements PasswordRestrictionValidator {
    INSTANCE;

    private final List<Character> marks =
            List.of('.', ',', '?', '!', ':', ';', '\'', '-', '–', '"', '/', '(', ')', '{', '}', '[', ']');

    @Override
    public boolean isValidPassword(String password) {
        boolean hasMark = false;
        boolean hasDigit = false;
        for (char ch : password.toCharArray()) {
            if (marks.contains(ch)) {
                hasMark = true;
            }
            if (ch >= '0' && ch <= '9') {
                hasDigit = true;
            }
        }
        return hasMark && !hasDigit;
    }
}
