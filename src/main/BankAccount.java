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

    public boolean isOpen() {
        return openState;
    }

    public void close() {
        openState = false;
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
      
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void withdraw(double amount) {
        if (!openState) { throw new IllegalStateException("!!! This account has been closed !!!"); }

        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew $" + amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    
    
    public void transfer(BankAccount target, double amount) {
        if (!openState) { throw new IllegalStateException("!!! This account has been closed !!!"); } 
        if (!target.isOpen()) { throw new IllegalStateException("!!! Target account has been closed !!!"); }

        if(target.equals(this)) { throw new IllegalArgumentException("!!! Cannot transfer to own account !!!");}

        if(amount > 0) {
            //first make sure we can withdraw the intended value
            try {
                this.withdraw(amount);
            } catch (Exception e) {
                throw new IllegalArgumentException("!!! Account lacks funds to transfer !!!");
            }

            target.deposit(amount);

        } else {
            throw new IllegalArgumentException();
        }
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

    public void adminAddInterest(double amount){
        if (amount <= 0){
            throw new IllegalArgumentException();
        }
        this.balance += amount;
        this.transactionHistory.add("Interest deposited $" + amount);
    }
}

