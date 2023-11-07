package org.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ErHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "<!DOCTYPE html><html><head><title>Error</title>" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                "<style>body {background-color: #f8e0e6;color: #555;}" +
                ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);} " +
                "input[type=\"text\"], input[type=\"password\"] {width: 100%;padding: 12px 20px;margin: 8px 0;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;}" +
                "input[type=\"submit\"] {width: 100%;background-color: #ff1493;color: white;padding: 14px 20px;margin: 8px 0;border: none;border-radius: 4px;cursor: pointer;}</style></head>" +
                "<body><div class=\"container\"><h1>Login</h1><div><p><span id=\"errorMessage\">Invalid username or password</span></p><form action=\"/login\" method=\"post\">" +
                "<label for=\"loginUsername\">Username:</label><br><input type=\"text\" id=\"loginUsername\" name=\"username\"><br>" +
                "<label for=\"loginPassword\">Password:</label><br><input type=\"password\" id=\"loginPassword\" name=\"password\"><br><br>" +
                "<input type=\"submit\" value=\"Login\"></form></div></div>" +
                "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";

        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}