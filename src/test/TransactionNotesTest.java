package test;

import main.BankAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TransactionNotesTest {
    @Test
    public void testTransactionDefaultNoteIsEmpty() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        assertEquals("", t.getNote());
    }

    @Test
    public void testSetNOte() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        t.setNote("birthday gift money");
        assertEquals("birthday gift money", t.getNote());
    }

    @Test
    public void testNoteAppearsInString() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        t.setNote("rent");
        assertTrue(t.toString().contains("Note: rent"));
    }

    @Test 
    public void testNoNoteInStringWhenEmpty() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        assertFalse(t.toString().contains("Note:"));
    }

    @Test 
    public void testOverwriteNOte() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        Transaction t = account.getTransactionHistory().get(0);
        t.setNote("first");
        t.setNote("updated");
        assertEquals("updated", t.getNote());
        assertTrue(t.toString().contains("updated"));
    }

    @Test
    public void testNoteOnWithdraw() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.withdraw(50);
        Transaction t = account.getTransactionHistory().get(1);
        t.setNote("groceries");
        assertTrue(t.toString().contains("Note: groceries"));
    }
}
