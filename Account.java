/**
 * Account.java
 * Represents a single bank account.
 * Holds all account details and provides getters/setters (encapsulation).
 */
public class Account {

    private String accountHolderName;
    private String accountNumber;
    private String debitCardNumber;
    private String ifscCode;
    private String branchName;
    private double balance;
    private String pin;

    // Constructor to initialize a new account
    public Account(String accountHolderName, String accountNumber, String debitCardNumber,
                    String ifscCode, String branchName, double balance, String pin) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.debitCardNumber = debitCardNumber;
        this.ifscCode = ifscCode;
        this.branchName = branchName;
        this.balance = balance;
        this.pin = pin;
    }

    // ---------- Getters and Setters ----------

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * Masks the debit card number, showing only the last 4 digits.
     * Example: 5678123412345678 -> ************5678
     */
    public String getMaskedCardNumber() {
        int visibleDigits = 4;
        String lastDigits = debitCardNumber.substring(debitCardNumber.length() - visibleDigits);
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < debitCardNumber.length() - visibleDigits; i++) {
            masked.append("*");
        }
        masked.append(lastDigits);

        return masked.toString();
    }
}