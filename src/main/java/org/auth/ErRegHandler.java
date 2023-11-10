package org.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ErRegHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "<!DOCTYPE html><html><head><title>Error</title>" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                "<style>body {background-color: #f8e0e6;color: #555;}" +
                ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);} " +
                "input[type=\"text\"] {width: 100%;padding: 12px 20px;margin: 8px 0;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;}" +
                "input[type=\"submit\"] {width: 100%;background-color: #ff1493;color: white;padding: 14px 20px;margin: 8px 0;border: none;border-radius: 4px;cursor: pointer;}" +
                "a {color: #ff1493; text-decoration: none;}" +
                ".contact-info {position: absolute; bottom: 10px; left: 10px;}" +
                "</style></head>" +
                "<body><div class=\"container\"><h1>Registration</h1><div><p><span id=\"errorMessage\">Username must meet all requirements</span></p>" +
                "<small id=\"loginHelp\" class=\"form-text text-muted\">The login must comply with the following rules:<br>" +
                "1 - contain only English letters<br>2 - have at least 1 capital letter<br>" +
                "3 - have at least 1 lowercase letter<br>4 - the length of the login must be more than 5 characters</small>" +
                "<form action=\"/register\" method=\"post\"><label for=\"registerUsername\">Username:</label><br>" +
                "<input type=\"text\" id=\"registerUsername\" name=\"username\"><br><br><input type=\"submit\" value=\"Register\"></form></div>" +
                "<div class=\"contact-info\"><p>For support, please contact yaryna.mryhlotska.it.2021@lpnu.ua</p></div></div>" +
                "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
