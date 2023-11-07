import org.auth.MainServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainServerTest {

    @Test
    public void testHashPassword() {
        String password = "testpassword";
        String salt = "testsalt";
        String hashedPassword = MainServer.hashPassword(password, salt);
        assertNotNull(hashedPassword);
    }
    @Test
    public void testGeneratePassword() {
        String generatedPassword = MainServer.generatePassword();
        assertNotNull(generatedPassword);
        assertEquals(6, generatedPassword.length());
    }

}
