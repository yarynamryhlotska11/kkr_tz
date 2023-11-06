package org.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import static org.auth.MainServer.*;

class RegisterHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "<!DOCTYPE html><html><head><title>Registration</title>" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                "<style>body {background-color: #f8e0e6;color: #555;}" +
                ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);} " +
                "input[type=\"text\"] {width: 100%;padding: 12px 20px;margin: 8px 0;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;}" +
                "input[type=\"submit\"] {width: 100%;background-color: #ff1493;color: white;padding: 14px 20px;margin: 8px 0;border: none;border-radius: 4px;cursor: pointer;}</style></head>" +
                "<body><div class=\"container\"><h1>Registration</h1><div><form action=\"/register\" method=\"post\">" +
                "<label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br>" +
                "<small id=\"loginHelp\" class=\"form-text text-muted\">The login must comply with the following rules:<br>" +
                "1 - contain only English letters<br>2 - have at least 1 capital letter<br>" +
                "3 - have at least 1 lowercase letter<br>4 - the length of the login must be more than 5 characters</small><br><br>" +
                "<input type=\"submit\" value=\"Register\"></form></div></div>" +
                "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";

        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            String requestBody = Utils.convertStreamToString(t.getRequestBody());
            String[] params = requestBody.split("&");
            if (params.length > 0) {
                String[] usernameParams = params[0].split("=");
                if (usernameParams.length > 1) {
                    String username = usernameParams[1].trim();
                    if (username.isEmpty() || !username.matches("^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{6,}$")) {
                        t.getResponseHeaders().set("Location", "/errorreg");
                        t.sendResponseHeaders(302, -1); // Redirect to error page for registration
                        return;
                    }

                    // Check if there is a registered user with the same name
                    if (userPasswords.containsKey(username.trim())) {
                        response = "<!DOCTYPE html><html><head><title>Error</title>" +
                                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                                "<style>body {background-color: #f8e0e6;color: #555;}" +
                                ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);} " +
                                "input[type=\"text\"] {width: 100%;padding: 12px 20px;margin: 8px 0;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;}" +
                                "input[type=\"submit\"] {width: 100%;background-color: #ff1493;color: white;padding: 14px 20px;margin: 8px 0;border: none;border-radius: 4px;cursor: pointer;}</style></head>" +
                                "<body><div class=\"container\"><h1>Registration</h1><div><p><span id=\"errorMessage\">Username already taken. Please choose another one.</span></p><form action=\"/register\" method=\"post\">" +
                                "<label for=\"registerUsername\">Username:</label><br><input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br>" +
                                "<input type=\"submit\" value=\"Register\"></form></div></div>" +
                                "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";

                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        return;
                    }

                    String password = generatePassword();
                    addUser(username, password);
                    printUserPasswords(); // Display registered users
                    response = "<!DOCTYPE html><html><head><title>Success</title>" +
                            "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                            "<style>body {background-color: #f8e0e6;color: #555;}" +
                            ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}" +
                            "a {color: #ff1493; text-decoration: none;}</style></head>" +
                            "<body><div class=\"container\"><h1>Success</h1><div>" +
                            "<p>You have successfully registered. Your password: " + password + "</p>" +
                            "<p>Redirecting to <a href=\"/login\">login page</a>...</p></div></div>" +
                            "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                            "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";
                    t.sendResponseHeaders(200, response.getBytes().length);
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
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}