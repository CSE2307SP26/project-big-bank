package main;

public class BankAccount {

    private double balance;
    private boolean openState;

    public BankAccount() {
        this.balance = 0;
        this.openState = true;
    }

    public boolean IsOpen() {
        return openState;
    }

    public void deposit(double amount) {
        if (!openState) { throw new IllegalStateException("!!! This account has been closed !!!"); }

        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void Close() {
        openState = false;
    }
}
