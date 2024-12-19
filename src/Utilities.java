public class Utilities {

    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";

    /**
     * Prints colored text.
     *
     * @param text  Text to print
     * @param color Color constant (e.g., Utilities.BLUE)
     */
    public static void printColoredText(String text, String color) {
        System.out.println(color + text + RESET);
    }

    // Converts "y" or "n" (case-insensitive) to boolean
    public static boolean convertYesNoToBoolean(String input) {
        if (input == null) {
            return false;
        }
        String lowerCaseInput = input.trim().toLowerCase();
        if (lowerCaseInput.equals("y")) {
            return true;
        } else if (lowerCaseInput.equals("n")) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid input, must be 'y' or 'n'");
        }
    }

    // Converts "m" or "f" (case-insensitive) to "male" or "female"
    public static String convertMaleFemaleToGender(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Invalid gender input!");
        }
        String lowerCaseInput = input.trim().toLowerCase();
        if (lowerCaseInput.equals("m")) {
            return "male";
        } else if (lowerCaseInput.equals("f")) {
            return "female";
        } else {
            throw new IllegalArgumentException("Invalid input, must be 'm' or 'f'");
        }
    }
}
