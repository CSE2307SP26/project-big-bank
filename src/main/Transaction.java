package main;

public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_OUT, TRANSFER_IN, FEE, INTEREST }

    private final Type type;
    private final double amount;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public Type getType() { 
        return type; 
    }
    
    public double getAmount() { 
        return amount; 
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f", type, amount);
    }
}
