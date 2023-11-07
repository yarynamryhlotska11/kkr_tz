import org.auth.NumberSequenceSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NumberSequenceSystemTest {
    private NumberSequenceSystem numberSequenceSystem;

    @BeforeEach
    public void setUp() {
        numberSequenceSystem = new NumberSequenceSystem();
    }

    @Test
    public void testGenerateRandomSequence() {
        numberSequenceSystem.generateRandomSequence(5, 100);
        List<Integer> sequence = numberSequenceSystem.getSequence();
        assertNotNull(sequence);
        assertEquals(5, sequence.size());
    }

    @Test
    public void testValidateUserInput() {
        List<Integer> userInput = Arrays.asList(1, 2, 3, 4, 5);
        numberSequenceSystem.generateRandomSequence(5, 100);
        List<Integer> sequence = numberSequenceSystem.getSequence();
        assertTrue(numberSequenceSystem.validateUserInput(sequence));
        assertFalse(numberSequenceSystem.validateUserInput(userInput));
    }

    @Test
    public void testGrantAccess() {
        List<Integer> userInput1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> userInput2 = Arrays.asList(5, 4, 3, 2, 1);
        numberSequenceSystem.generateRandomSequence(5, 100);
        List<Integer> sequence = numberSequenceSystem.getSequence();
        assertTrue(numberSequenceSystem.grantAccess(sequence, sequence));
        assertFalse(numberSequenceSystem.grantAccess(sequence, userInput1));
        assertFalse(numberSequenceSystem.grantAccess(sequence, userInput2));
    }

    @Test
    public void testClearSequence() {
        numberSequenceSystem.generateRandomSequence(5, 100);
        List<Integer> sequence = numberSequenceSystem.getSequence();
        assertNotNull(sequence);
        numberSequenceSystem.clearSequence();
        sequence = numberSequenceSystem.getSequence();
        assertNotNull(sequence);
        assertTrue(sequence.isEmpty());
    }
}
