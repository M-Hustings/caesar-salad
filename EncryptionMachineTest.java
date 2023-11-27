import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class EncryptionMachineTest {

    @Test
    void testValidEncryption() {
        // Assuming encryptWord and shiftChar are package-private for testing
        String result = EncryptionMachine.encryptWord("abc");
        assertEquals("def", result, "Encryption with shift 3 failed for 'abc'");
    }

    @Test
    void testWrapAroundEncryption() {
        String result = EncryptionMachine.encryptWord("xyz");
        assertEquals("abc", result, "Wrap around encryption failed for 'xyz'");
    }

    @Test
    void testInvalidCharacterEncryption() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EncryptionMachine.encryptWord("123");
        });

        String expectedMessage = "Invalid character:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEmptyStringEncryption() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EncryptionMachine.encryptWord("");
        });

        assertEquals("Word cannot be empty", exception.getMessage());
    }

    @Test
    void testSpaceCharacterEncryption() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EncryptionMachine.encryptWord("hello world");
        });

        assertEquals("Words cannot contain spaces", exception.getMessage());
    }

    @Test
    void testUserInputHandling() {
        String input = "secret\n1\nhello\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        EncryptionMachine.main(new String[0]);

        assertTrue(out.toString().contains("Your encrypted key is:"));
        assertTrue(out.toString().contains("\"hello\" has been encrypted to:"));
    }

    @Test
    void testNumberFormatExceptionForWordCount() {
        String input = "secret\nnotANumber\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        EncryptionMachine.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("That was not a number..."));
        assertTrue(output.contains("Stopping Caesar Cipher Program."));
    }

    @Test
    void testNegativeWordCount() {
        String input = "secret\n-1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        EncryptionMachine.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Word count must be positive"));
        assertTrue(output.contains("Stopping Caesar Cipher Program."));
    }

    @Test
    void testHandleErrorForInvalidKey() {
        String input = "123\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        EncryptionMachine.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Invalid character:"));
        assertTrue(output.contains("Stopping Caesar Cipher Program."));
    }

    @Test
    void testHandleErrorForSpaceInKey() {
        String input = "hello world\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        EncryptionMachine.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Words cannot contain spaces"));
        assertTrue(output.contains("Stopping Caesar Cipher Program."));
    }
}