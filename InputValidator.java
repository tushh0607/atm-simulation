import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * InputValidator.java
 * Utility class with static helper methods to safely read user input
 * and validate PIN formats. Prevents the program from crashing on
 * invalid input (e.g. typing letters instead of numbers).
 */
public class InputValidator {

    // Private constructor - this is a utility class, no need to create objects of it
    private InputValidator() {
    }

    /**
     * Safely reads an integer from the Scanner.
     * Keeps asking until valid input is given.
     */
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Safely reads a double (used for money amounts) from the Scanner.
     * Keeps asking until valid input is given.
     */
    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid amount (numbers only).");
                scanner.nextLine();
            }
        }
    }

    /**
     * Reads a PIN as plain text and validates that it is exactly 4 digits.
     * Keeps asking until a valid 4-digit PIN is entered.
     */
    public static String readValidPin(Scanner scanner, String prompt) {
        String pin;
        while (true) {
            System.out.print(prompt);
            pin = scanner.nextLine().trim();
            if (isValidPin(pin)) {
                return pin;
            }
            System.out.println("Invalid PIN format! PIN must be exactly 4 digits.");
        }
    }

    /**
     * Checks whether a given string is a valid 4-digit PIN.
     */
    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }
}