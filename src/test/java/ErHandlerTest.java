import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.auth.ErHandler;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErHandlerTest {
    @Test
    public void testHandle() throws IOException {
        ErHandler erHandler = new ErHandler();
        HttpExchange t = new HttpExchange() {
            Headers requestHeaders = new Headers();
            Headers responseHeaders = new Headers();
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            @Override
            public Headers getRequestHeaders() {
                return requestHeaders;
            }

            @Override
            public Headers getResponseHeaders() {
                return responseHeaders;
            }

            @Override
            public OutputStream getResponseBody() {
                return responseBody;
            }

            @Override
            public void sendResponseHeaders(int rCode, long responseLength) throws IOException {
                responseHeaders.set("Content-Type", "text/html; charset=UTF-8");
            }

            // Додати імплементацію для всіх абстрактних методів
            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public String getRequestMethod() {
                return null;
            }

            @Override
            public HttpContext getHttpContext() {
                return null;
            }

            @Override
            public void close() {}

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public int getResponseCode() {
                return 0;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public void setStreams(InputStream i, OutputStream o) {

            }

            @Override
            public HttpPrincipal getPrincipal() {
                return null;
            }

            @Override
            public InputStream getRequestBody() {
                return null;
            }
        };

        erHandler.handle(t);

        ByteArrayOutputStream os = (ByteArrayOutputStream) t.getResponseBody();
        String result = os.toString();
        String expectedResponse = "<!DOCTYPE html><html><head><title>Error</title>" +
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

        assertEquals(expectedResponse, result);
    }
}