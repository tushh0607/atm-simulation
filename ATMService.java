import java.util.ArrayList;
import java.util.Scanner;

/**
 * ATMService.java
 * Contains all the business logic for the ATM:
 * login, menu navigation, and every banking operation.
 */
public class ATMService {

    private Account account;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final String LINE_SEPARATOR = "----------------------------------------";

    public ATMService(Account account, ArrayList<Transaction> transactions, Scanner scanner) {
        this.account = account;
        this.transactions = transactions;
        this.scanner = scanner;
    }

    /**
     * Main entry point for running the ATM: shows welcome screen,
     * handles login, and then loops the main menu until Exit.
     */
    public void run() {
        printWelcomeScreen();

        if (login()) {
            showMenu();
        } else {
            System.out.println("\nToo many incorrect attempts. Your card has been blocked.");
            System.out.println("Please contact your bank branch for assistance.");
        }
    }

    private void printWelcomeScreen() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("      WELCOME TO XYZ BANK ATM");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Handles PIN-based login. Allows a maximum of 3 attempts.
     */
    private boolean login() {
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            String enteredPin = InputValidator.readValidPin(scanner, "Enter your 4-digit PIN: ");

            if (enteredPin.equals(account.getPin())) {
                System.out.println("\nLogin successful! Welcome, " + account.getAccountHolderName() + ".");
                return true;
            } else {
                int remainingAttempts = MAX_LOGIN_ATTEMPTS - attempt;
                if (remainingAttempts > 0) {
                    System.out.println("Incorrect PIN. Attempts remaining: " + remainingAttempts);
                } 
            }
        }
        return false;
    }

    /**
     * Displays the main menu and routes the user's choice to the
     * correct operation. Repeats until the user chooses to Exit.
     */
    private void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + LINE_SEPARATOR);
            System.out.println("               MAIN MENU");
            System.out.println(LINE_SEPARATOR);
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Fast Cash");
            System.out.println("5. Mini Statement");
            System.out.println("6. Account Information");
            System.out.println("7. Change PIN");
            System.out.println("8. Exit");
            System.out.println(LINE_SEPARATOR);

            int choice = InputValidator.readInt(scanner, "Enter your choice (1-8): ");

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    fastCash();
                    break;
                case 5:
                    miniStatement();
                    break;
                case 6:
                    accountInformation();
                    break;
                case 7:
                    changePin();
                    break;
                case 8:
                    running = false;
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 8.");
            }
        }
    }

    // ---------------------- Check Balance ----------------------

    private void checkBalance() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.printf("Available Balance: Rs. %.2f%n", account.getBalance());
        System.out.println(LINE_SEPARATOR);
    }

    // ---------------------- Deposit ----------------------

    private void deposit() {
        double amount = InputValidator.readDouble(scanner, "\nEnter amount to deposit: Rs. ");

        if (amount <= 0) {
            System.out.println("Invalid amount! Deposit amount must be positive.");
            return;
        }

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        recordTransaction("Deposit", amount, newBalance);

        System.out.printf("Deposit successful! New Balance: Rs. %.2f%n", newBalance);
    }

    // ---------------------- Withdraw ----------------------

    private void withdraw() {
        double amount = InputValidator.readDouble(scanner, "\nEnter amount to withdraw: Rs. ");
        processWithdrawal(amount);
    }

    /**
     * Core withdrawal logic, reused by both withdraw() and fastCash().
     */
    private void processWithdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount! Withdrawal amount must be positive.");
            return;
        }

        if (amount > account.getBalance()) {
            System.out.println("Insufficient balance! Available balance: Rs. " + account.getBalance());
            return;
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        recordTransaction("Withdraw", amount, newBalance);

        System.out.printf("Withdrawal successful! Please collect your cash.%n");
        System.out.printf("Remaining Balance: Rs. %.2f%n", newBalance);
    }

    // ---------------------- Fast Cash ----------------------

    private void fastCash() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("             FAST CASH");
        System.out.println(LINE_SEPARATOR);
        System.out.println("1. Rs. 500");
        System.out.println("2. Rs. 1000");
        System.out.println("3. Rs. 2000");
        System.out.println("4. Rs. 5000");
        System.out.println("5. Other Amount");
        System.out.println(LINE_SEPARATOR);

        int choice = InputValidator.readInt(scanner, "Select an option (1-5): ");
        double amount;

        switch (choice) {
            case 1:
                amount = 500;
                break;
            case 2:
                amount = 1000;
                break;
            case 3:
                amount = 2000;
                break;
            case 4:
                amount = 5000;
                break;
            case 5:
                amount = InputValidator.readDouble(scanner, "Enter amount to withdraw: Rs. ");
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        // Reuse the same withdrawal logic used by the Withdraw option
        processWithdrawal(amount);
    }

    // ---------------------- Mini Statement ----------------------

    private void miniStatement() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("             MINI STATEMENT");
        System.out.println(LINE_SEPARATOR);

        if (transactions.isEmpty()) {
            System.out.println("No Transactions Found");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Helper method that both deposit() and processWithdrawal() call
     * to record a new transaction, avoiding duplicate code.
     */
    private void recordTransaction(String type, double amount, double balanceAfter) {
        transactions.add(new Transaction(type, amount, balanceAfter));
    }

    // ---------------------- Account Information ----------------------

    private void accountInformation() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("           ACCOUNT INFORMATION");
        System.out.println(LINE_SEPARATOR);
        System.out.println("Account Holder : " + account.getAccountHolderName());
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Debit Card No. : " + account.getMaskedCardNumber());
        System.out.println("IFSC Code      : " + account.getIfscCode());
        System.out.println("Branch         : " + account.getBranchName());
        System.out.printf("Current Balance: Rs. %.2f%n", account.getBalance());
        System.out.println(LINE_SEPARATOR);
    }

    // ---------------------- Change PIN ----------------------

    private void changePin() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("               CHANGE PIN");
        System.out.println(LINE_SEPARATOR);

        String currentPin = InputValidator.readValidPin(scanner, "Enter current PIN: ");

        if (!currentPin.equals(account.getPin())) {
            System.out.println("Incorrect current PIN. PIN change failed.");
            return;
        }

        String newPin = InputValidator.readValidPin(scanner, "Enter new 4-digit PIN: ");
        String confirmPin = InputValidator.readValidPin(scanner, "Confirm new PIN: ");

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match. PIN change failed.");
            return;
        }

        account.setPin(newPin);
        System.out.println("PIN changed successfully! (Valid for this session only)");
    }

    // ---------------------- Exit ----------------------

    private void exit() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("Thank you for using XYZ Bank ATM. Please collect your card.");
        System.out.println(LINE_SEPARATOR);
    }
}