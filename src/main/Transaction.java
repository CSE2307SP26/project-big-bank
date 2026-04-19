package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_OUT, TRANSFER_IN, FEE, INTEREST }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Type type;
    private final double amount;
    private final LocalDateTime timestamp;
    //private String note;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        
    }

    public Type getType() { 
        return type; 
    }
    
    public double getAmount() { 
        return amount; 
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    

    @Override
    public String toString() {
        return String.format("%s: $%.2f", timestamp.format(FORMATTER), type, amount);
    }
}
