package test;
import main.Transaction;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import main.BankAccount;

public class TransactionHistoryTest {
     @Test
    public void testNewAccountHasEmptyHistory() {
        BankAccount account = new BankAccount();
        assertTrue(account.getTransactionHistory().isEmpty());
    }

    @Test
    public void testDepositAddedToTransactionHistory() {
        BankAccount account = new BankAccount();
        account.deposit(10.0);
        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(0).toString().contains("DEPOSIT"));
    }

    @Test
    public void testWithdrawalAddedToTransactionHistory() {
        BankAccount account = new BankAccount();
        account.deposit(50.0);
        account.withdraw(10.0);
        assertEquals(2, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(1).toString().contains("WITHDRAWAL"));
    }

    @Test
    public void testTransactionHistoryContainsCorrectTypes() {
        BankAccount acc = new BankAccount();
        acc.deposit(100);
        acc.withdraw(40);

        assertTrue(acc.getTransactionHistory().get(0).toString().contains("DEPOSIT"));
        assertTrue(acc.getTransactionHistory().get(1).toString().contains("WITHDRAWAL"));
    }
}
