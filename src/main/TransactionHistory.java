package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionHistory {

    private final List<Transaction> history = new ArrayList<>();

    public void record(Transaction.Type type, double amount) {
        history.add(new Transaction(type, amount));
    }

    public List<Transaction> getAll() {
        return Collections.unmodifiableList(history);
    }
}