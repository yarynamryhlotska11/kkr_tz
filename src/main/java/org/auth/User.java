package org.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private String sixDigitCode;

    public User(String username) {
        this.username = username;
        this.password = generatePassword();
        this.sixDigitCode = generateSixDigitCode();
    }

    private String generatePassword() {
        Random random = new Random();
        int password = 100000 + random.nextInt(900000);
        return String.valueOf(password);
    }

    private String generateSixDigitCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSixDigitCode() {
        return sixDigitCode;
    }
}
