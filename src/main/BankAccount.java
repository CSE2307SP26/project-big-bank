package main;
import java.util.ArrayList;
import java.util.List;


public class BankAccount {

    private double balance;
    private boolean openState;
    private List<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.openState = true;
        this.transactionHistory = new ArrayList<>();    
    }

    public boolean IsOpen() {
        return openState;
    }

    public void deposit(double amount) {
        if (!openState) { throw new IllegalStateException("!!! This account has been closed !!!"); }

        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited $" + amount);
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
      
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void adminCollectFee(double amount){
        if (amount <= 0){
            throw new IllegalArgumentException();
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException();
        }
        this.balance -= amount;
        this.transactionHistory.add("Fee collected $" + amount);
    }
}

