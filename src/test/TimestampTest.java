package test;

import main.BankAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TimestampTest {
    @Test
    public void testTrancationHasTimestamp() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        assertNotNull(t.getTimestamp());
    }

    @Test
    public void testTimestampIsRecentOnDeposit() {
        LocalDateTime before = LocalDateTime.now();
        BankAccount account = new BankAccount();
        account.deposit(50);
        LocalDateTime after = LocalDateTime.now();

        Transaction t = account.getTransactionHistory().get(0);
        assertTrue(t.getTimestamp().isAfter(before) || t.getTimestamp().isEqual(before));
        assertTrue(t.getTimestamp().isBefore(after) || t.getTimestamp().isEqual(after));
    }

    @Test
    public void testTimestampIsRecentOnWithdraw() {
        LocalDateTime before = LocalDateTime.now();
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.withdraw(50);
        LocalDateTime after = LocalDateTime.now();

        Transaction t = account.getTransactionHistory().get(1);
        assertTrue(t.getTimestamp().isAfter(before) || t.getTimestamp().isEqual(before));
        assertTrue(t.getTimestamp().isBefore(after) || t.getTimestamp().isEqual(after));
    }

    @Test
    public void testTimestampAsString() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        //format: [yyyy-MM-DD HH:mm:ss] --> look for brackets
        assertTrue(t.toString().contains("["));
        assertTrue(t.toString().contains("]"));
    }

    @Test
    public void testEachtransactionHasUniqueTimestamp() throws InterruptedException {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Thread.sleep(10);
        account.deposit(50);

        Transaction t1 = account.getTransactionHistory().get(0);
        Transaction t2 = account.getTransactionHistory().get(1);
        assertTrue(t2.getTimestamp().isAfter(t1.getTimestamp()));
    }
}
