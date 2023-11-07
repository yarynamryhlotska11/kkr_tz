import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.auth.ErRegHandler;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;


public class ErRegHandlerTest {

    @Test
    public void testHandle() throws IOException {
        ErRegHandler erRegHandler = new ErRegHandler();
        HttpExchange t = new TestHttpExchange();
        erRegHandler.handle(t);
    }

    static class TestHttpExchange extends HttpExchange {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        @Override
        public void sendResponseHeaders(int rCode, long responseLength) throws IOException {
            // Do nothing
        }

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
        public OutputStream getResponseBody() {
            return os;
        }

        @Override
        public Headers getRequestHeaders() {
            return null;
        }

        @Override
        public Headers getResponseHeaders() {
            return null;
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
            // Do nothing
        }

        @Override
        public InputStream getRequestBody() {
            return null;
        }
    }
}
