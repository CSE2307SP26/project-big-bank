package main;

public class AccountValidator {

    public static void requireOpen(boolean openState) {
        if (!openState)
            throw new IllegalStateException("Account is closed.");
    }

    public static void requirePositiveAmount(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive.");
    }

    public static void requireSufficientFunds(double balance, double amount) {
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient funds.");
    }

    public static void requireDistinctAccounts(BankAccount a, BankAccount b) {
        if (a == b)
            throw new IllegalArgumentException("Cannot transfer to the same account.");
    }
}