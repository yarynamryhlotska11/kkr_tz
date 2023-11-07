
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.auth.HomePageHandler;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageHandlerTest {

    @Test
    public void testHandle() throws IOException {
        HomePageHandler homePageHandler = new HomePageHandler();
        TestHttpExchange t = new TestHttpExchange();
        homePageHandler.handle(t);
        assertEquals(200, t.getResponseCode());
    }

    static class TestHttpExchange extends HttpExchange {
        private int responseCode;
        private OutputStream os;
        private Headers headers;

        TestHttpExchange() {
            this.responseCode = -1;
            this.os = new OutputStream() {
                @Override
                public void write(int b) {
                    // Do nothing
                }
            };
            this.headers = new Headers();
        }

        @Override
        public Headers getRequestHeaders() {
            return null;
        }

        @Override
        public void sendResponseHeaders(int rCode, long responseLength) throws IOException {
            this.responseCode = rCode;
        }

        @Override
        public InetSocketAddress getRemoteAddress() {
            return null;
        }

        @Override
        public OutputStream getResponseBody() {
            return os;
        }

        @Override
        public Headers getResponseHeaders() {
            return headers;
        }

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
        public void close() {
        }

        @Override
        public InputStream getRequestBody() {
            return null;
        }

        public int getResponseCode() {
            return responseCode;
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
    }
}
