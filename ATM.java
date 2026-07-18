import java.util.ArrayList;
import java.util.Scanner;

/**
 * ATM.java
 * Represents the ATM machine itself.
 * Holds the current Account, the list of Transactions made in this
 * session, and the shared Scanner object used for all input.
 */
public class ATM {

    private Account account;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;

    public ATM() {
        this.scanner = new Scanner(System.in);
        this.transactions = new ArrayList<>();
        this.account = createPredefinedAccount();
    }

    /**
     * Creates the single predefined account used by this ATM simulation.
     * (No registration / account creation - as per project requirements.)
     */
    private Account createPredefinedAccount() {
        return new Account(
                "Tushar Roy",
                "123456789012",
                "5678123412345678",
                "SBIN0001234",
                "Bhubaneswar Main Branch",
                25000.00,
                "1234"
        );
    }

    /**
     * Starts the ATM application.
     */
    public void start() {
        ATMService atmService = new ATMService(account, transactions, scanner);
        atmService.run();
        scanner.close();
    }
}