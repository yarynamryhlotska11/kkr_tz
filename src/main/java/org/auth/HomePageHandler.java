package org.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

 class HomePageHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "<!DOCTYPE html><html><head><title>Home Page</title>" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                "<style>body {background-color: #f8e0e6;color: #555;}" +
                ".container {background-color: #fff;padding: 20px;border-radius: 5px;margin-top: 50px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}</style></head>" +
                "<body><div class=\"container\"><h1>Home Page</h1><div><p>Welcome to the home page</p></div></div>" +
                "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script></body></html>";

        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}