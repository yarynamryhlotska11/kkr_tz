package org.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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
        server.createContext("/erempty", new ErEmptyHandler());
        server.createContext("/error", new ErHandler());



        server.setExecutor(null);
        server.start();

        printUserPasswords(); // Виведення зареєстрованих користувачів при запуску сервера
    }

    public static void printUserPasswords() {
        System.out.println("Registered Users:");
        for (Map.Entry<String, String> entry : userPasswords.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue() + ", Salt: " + userSalts.get(entry.getKey()));
        }
    }

    // Генерувати сіль
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }

    // Повернення хешу пароля з використанням солі
    private static String hashPassword(String password, String salt) {
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
        String salt = generateSalt(); // Генерація солі
        String hashedPassword = hashPassword(password, salt); // Хешування пароля з сіллю
        userPasswords.put(username.trim(), hashedPassword.trim()); // Збереження хешованого пароля для користувача
        userSalts.put(username.trim(), salt); // Збереження солі для користувача
    }

    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Bootstrap Server</title></head><body><div><h3>Choose an option</h3><a href=\"/register\">Register</a><br><a href=\"/login\">Login</a></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    static class RegisterHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                String requestBody = Utils.convertStreamToString(t.getRequestBody());
                String[] params = requestBody.split("&");
                if (params.length > 0) {
                    String[] usernameParams = params[0].split("=");
                    if (usernameParams.length > 1) {
                        String username = usernameParams[1].trim();
                        if (username.isEmpty()) {
                            t.getResponseHeaders().set("Location", "/erempty");
                            t.sendResponseHeaders(302, -1); // Redirect to error page if field is empty
                            return;
                        } else if (!username.matches("^[a-zA-Z]*$")) {
                            t.getResponseHeaders().set("Location", "/errorreg");
                            t.sendResponseHeaders(302, -1); // Redirect to error page for registration
                            return;
                        }
                        String password = generatePassword();
                        addUser(username, password);
                        printUserPasswords(); // Display registered users
                        response = "<!DOCTYPE html><html><head><title>Success</title><meta http-equiv=\"refresh\" content=\"60; url=/login\"></head><body><h1>Success</h1><div><p>You have successfully registered. Your password: " + password + "</p><p>Redirecting to login page...</p></div></body></html>";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        t.getResponseHeaders().set("Location", "/er");
                        t.sendResponseHeaders(302, -1); // Redirect to error page if field is empty
                        return;
                    }
                } else {
                    t.getResponseHeaders().set("Location", "/er");
                    t.sendResponseHeaders(302, -1); // Redirect to error page if field is empty
                    return;
                }
            } else {
                response = "<!DOCTYPE html><html><head><title>Registration</title></head><body><h1>Registration</h1><div><form action=\"/register\" method=\"post\"><label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br><input type=\"submit\" value=\"Register\"></form></div></body></html>";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    static class ErEmptyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Error</title></head><body><h1>Error</h1><div><p><span id=\"errorMessage\">Field must not be empty</span></p><form action=\"/register\" method=\"post\"><label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br><input type=\"submit\" value=\"Register\"></form></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ErRegHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Error</title></head><body><h1>Error</h1><div><p><span id=\"errorMessage\">Username must be filled and contain only English characters</span></p><form action=\"/register\" method=\"post\"><label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br><input type=\"submit\" value=\"Register\"></form></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }



    static String generatePassword() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code); // Повернення паролю у форматі рядка
    }


    static class LoginHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            if (t.getRequestMethod().equals("POST")) {
                String requestBody = Utils.convertStreamToString(t.getRequestBody());
                Map<String, String> parameters = Utils.parseQuery(requestBody);

                String username = parameters.get("username").trim(); // Обрізання зайвих пробілів
                String password = parameters.get("password").trim(); // Обрізання зайвих пробілів

                if (userPasswords.containsKey(username) && userPasswords.get(username).equals(hashPassword(password, userSalts.get(username)))) {
                    t.getResponseHeaders().set("Location", "/homepage");
                    t.sendResponseHeaders(302, -1); // Перенаправлення на домашню сторінку
                } else {
                    t.getResponseHeaders().set("Location", "/error");
                    t.sendResponseHeaders(302, -1); // Перенаправлення на сторінку помилки
                }
            } else {
                // Обробка GET-запиту для сторінки входу
                // Генерація простої форми входу
                String response = "<!DOCTYPE html><html><head><title>Login</title></head><body><h1>Login</h1><div><form action=\"/login\" method=\"post\"><label for=\"loginUsername\">Username:</label><br><input type=\"text\" id=\"loginUsername\" name=\"username\"><br><label for=\"loginPassword\">Password:</label><br><input type=\"password\" id=\"loginPassword\" name=\"password\"><br><br><input type=\"submit\" value=\"Login\"></form></div></body></html>";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }


    static class HomePageHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Home Page</title></head><body><h1>Home Page</h1><div><p>Welcome to the home page</p></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    static class ErHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Error</title></head><body><h1>Error</h1><div><p><span id=\"errorMessage\">Invalid username or password</span></p><form action=\"/login\" method=\"post\"><label for=\"loginUsername\">Username:</label><br><input type=\"text\" id=\"loginUsername\" name=\"username\"><br><label for=\"loginPassword\">Password:</label><br><input type=\"password\" id=\"loginPassword\" name=\"password\"><br><br><input type=\"submit\" value=\"Login\"></form></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}

