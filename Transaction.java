import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction.java
 * Represents a single transaction record (deposit, withdrawal, etc.)
 */
public class Transaction {

    private String transactionType;
    private double amount;
    private double balanceAfterTransaction;
    private LocalDateTime timestamp;

    // Formatter used to display the date/time in a clean readable format
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

    public Transaction(String transactionType, double amount, double balanceAfterTransaction) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = LocalDateTime.now(); // captures the current date & time
    }

    // ---------- Getters ----------

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a nicely formatted, single-line summary of the transaction.
     * Used directly when printing the Mini Statement.
     */
    @Override
    public String toString() {
        return String.format("%-12s | Rs. %-10.2f | Balance: Rs. %-10.2f | %s",
                transactionType, amount, balanceAfterTransaction, timestamp.format(FORMATTER));
    }
}