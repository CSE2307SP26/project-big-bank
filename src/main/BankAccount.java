package main;

import java.util.List;

public class BankAccount {

    private double balance;
    private boolean open;
    private final TransactionHistory history = new TransactionHistory();
    private String name;

    public BankAccount() {
        this.balance = 0;
        this.open = true;
    }

    public boolean isOpen() { 
        return open; 
    }

    public double getBalance() { 
        return balance; 
    }

    public List<Transaction> getTransactionHistory() {
        return history.getAll();
    }

    public void close() {
        open = false;
    }

    public String getName() {
        return name != null ? name: "Unnamed Account";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void deposit(double amount) {
        AccountValidator.requireOpen(open);
        AccountValidator.requirePositiveAmount(amount);
        balance += amount;
        history.record(Transaction.Type.DEPOSIT, amount);
    }

    public void withdraw(double amount) {
        AccountValidator.requireOpen(open);
        AccountValidator.requirePositiveAmount(amount);
        AccountValidator.requireSufficientFunds(balance, amount);
        balance -= amount;
        history.record(Transaction.Type.WITHDRAWAL, amount);
    }

    public void transfer(BankAccount target, double amount) {
        AccountValidator.requireOpen(open);
        AccountValidator.requireOpen(target.isOpen());
        AccountValidator.requireDistinctAccounts(this, target);
        AccountValidator.requirePositiveAmount(amount);
        AccountValidator.requireSufficientFunds(balance, amount);

        balance -= amount;
        history.record(Transaction.Type.TRANSFER_OUT, amount);

        target.balance += amount;
        target.history.record(Transaction.Type.TRANSFER_IN, amount);
    }

    void applyAdjustment(double delta, Transaction.Type type) {
        balance += delta;
        history.record(type, Math.abs(delta));
    }
}