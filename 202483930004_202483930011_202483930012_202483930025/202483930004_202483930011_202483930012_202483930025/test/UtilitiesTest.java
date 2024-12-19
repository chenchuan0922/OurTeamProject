import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilitiesTest {

    // Tests for convertYesNoToBoolean method
    @Test
    void testConvertYesNoToBoolean_ValidInputs() {
        assertTrue(Utilities.convertYesNoToBoolean("y"));
        assertFalse(Utilities.convertYesNoToBoolean("n"));
        assertTrue(Utilities.convertYesNoToBoolean("Y"));
        assertFalse(Utilities.convertYesNoToBoolean("N"));
        assertTrue(Utilities.convertYesNoToBoolean("  y  "));
        assertFalse(Utilities.convertYesNoToBoolean("  n  "));
    }

    @Test
    void testConvertYesNoToBoolean_InvalidInputs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertYesNoToBoolean("yes");
        });
        assertEquals("Invalid input, must be 'y' or 'n'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertYesNoToBoolean("no");
        });
        assertEquals("Invalid input, must be 'y' or 'n'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertYesNoToBoolean("maybe");
        });
        assertEquals("Invalid input, must be 'y' or 'n'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertYesNoToBoolean("");
        });
        assertEquals("Invalid input, must be 'y' or 'n'", exception.getMessage());
    }

    // Tests for convertMaleFemaleToGender method
    @Test
    void testConvertMaleFemaleToGender_ValidInputs() {
        assertEquals("male", Utilities.convertMaleFemaleToGender("m"));
        assertEquals("female", Utilities.convertMaleFemaleToGender("f"));
        assertEquals("male", Utilities.convertMaleFemaleToGender("M"));
        assertEquals("female", Utilities.convertMaleFemaleToGender("F"));
        assertEquals("male", Utilities.convertMaleFemaleToGender("  m  "));
        assertEquals("female", Utilities.convertMaleFemaleToGender("  f  "));
    }

    @Test
    void testConvertMaleFemaleToGender_InvalidInputs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertMaleFemaleToGender("male");
        });
        assertEquals("Invalid input, must be 'm' or 'f'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertMaleFemaleToGender("female");
        });
        assertEquals("Invalid input, must be 'm' or 'f'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertMaleFemaleToGender("unknown");
        });
        assertEquals("Invalid input, must be 'm' or 'f'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertMaleFemaleToGender("");
        });
        assertEquals("Invalid input, must be 'm' or 'f'", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Utilities.convertMaleFemaleToGender(null);
        });
        assertEquals("Invalid gender input!", exception.getMessage());
    }
}
