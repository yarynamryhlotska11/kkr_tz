package org.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainServer {
    static Map<String, String> userPasswords = new HashMap<>();
    static Map<String, String> userSalts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HomeHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/homepage", new HomePageHandler());
        server.createContext("/errorreg", new ErRegHandler());
        server.createContext("/error", new ErHandler());
        server.setExecutor(null);
        server.start();
    }

    public static void printUserPasswords() {
        System.out.println("Registered Users:");
        for (Map.Entry<String, String> entry : userPasswords.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue() + ", Salt: " + userSalts.get(entry.getKey()));
        }
    }

    static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }

    // Return the password hash using the salt
    static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String toHash = password + salt;
            byte[] encodedHash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            return new String(encodedHash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void addUser(String username, String password) {
        String salt = generateSalt(); /// Salt generation
        String hashedPassword = hashPassword(password, salt); // Hashing password with salt
        userPasswords.put(username.trim(), hashedPassword.trim()); // Store the hashed password for the user
        userSalts.put(username.trim(), salt); // Save the salt for the user
    }

    static String generatePassword() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code); // Return the password in string format
    }
}

