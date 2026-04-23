package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_OUT, TRANSFER_IN, FEE, INTEREST }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Type type;
    private final double amount;
    private final LocalDateTime timestamp;
    private String note;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.note = "";
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        String base = String.format("[%s] %s: $%.2f", 
        timestamp.format(FORMATTER), type, amount);
        return note.isEmpty() ? base : base + " | Note: " + note;
    }
}
