package org.auth;

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

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HomeHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/error", new ErHandler());
        server.createContext("/homepage", new HomePageHandler());

        server.setExecutor(null);
        server.start();

        printUserPasswords(); // Виведення зареєстрованих користувачів при запуску сервера
    }

    public static void printUserPasswords() {
        System.out.println("Registered Users:");
        for (Map.Entry<String, String> entry : userPasswords.entrySet()) {
            System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

    static void addUser(String username, String password) {
        userPasswords.put(username.trim(), password.trim()); // Збереження пароля для користувача
    }


    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Bootstrap Server</title></head><body><h1>Welcome to the Bootstrap Server</h1><p>This is a simple example of using Bootstrap with a Java server.</p><div><h3>Choose an option</h3><a href=\"/register\">Register</a><br><a href=\"/login\">Login</a></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class RegisterHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response;
            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                String requestBody = Utils.convertStreamToString(t.getRequestBody());
                String[] params = requestBody.split("&");
                String[] usernameParams = params[0].split("=");
                String username = usernameParams[1];
                if (!username.isEmpty()) {
                    String password = generatePassword();
                    addUser(username, password);
                    printUserPasswords(); // Виведення зареєстрованих користувачів
                    response = "<!DOCTYPE html><html><head><title>Success</title></head><body><h1>Success</h1><div><p>You have successfully registered. Your password: " + password + "</p></div></body></html>";
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    t.getResponseHeaders().set("Location", "/error.html");
                    t.sendResponseHeaders(302, -1);
                }
            } else {
                response = "<!DOCTYPE html><html><head><title>Registration</title></head><body><h1>Registration</h1><div><form action=\"/register\" method=\"post\"><label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br><input type=\"submit\" value=\"Register\"></form></div></body></html>";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }}

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

                System.out.println("Input username: " + username);
                System.out.println("Input password: " + password);
                System.out.println("Stored password for the username: " + userPasswords.get(username));

                if (userPasswords.containsKey(username) && userPasswords.get(username).equals(password)) {
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

    static class ErHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Error</title></head><body><h1>Error</h1><div><p><span id=\"errorMessage\">Invalid username or password</span></p><form action=\"/login\" method=\"post\"><label for=\"loginUsername\">Username:</label><br><input type=\"text\" id=\"loginUsername\" name=\"username\"><br><label for=\"loginPassword\">Password:</label><br><input type=\"password\" id=\"loginPassword\" name=\"password\"><br><br><input type=\"submit\" value=\"Login\"></form></div></body></html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
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
}

