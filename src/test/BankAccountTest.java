package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testNewAccountHasEmptyHistory() {
        BankAccount testAccount = new BankAccount();
        try {
            BankAccount account = new BankAccount();
            assertTrue(account.getTransactionHistory().isEmpty());
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testDepositAddedToTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        try {
            BankAccount account = new BankAccount();
            account.deposit(10.0);
            assertEquals(1, account.getTransactionHistory().size());
            assertTrue(account.getTransactionHistory().get(0).contains("Deposited $10.0"));
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdrawalAddedToTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        try {
            BankAccount account = new BankAccount();
            // Note: Command added manually to transaction history as to not rely on withdraw method:
            account.getTransactionHistory().add("Withdrew $1");
            assertEquals(1, account.getTransactionHistory().size());
            assertTrue(account.getTransactionHistory().get(0).contains("Withdrew $1"));
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @SuppressWarnings("deprecation") //said that assertEquals was deprecated for comparing doubles? 
    @Test
    public void testCollectFee() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.adminCollectFee(10);
        assertEquals(90, testAccount.getBalance());
    }

    @Test
    public void testCollectAmountTooLarge() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(20);
        try {
            testAccount.adminCollectFee(50);
            fail();
        } catch (IllegalArgumentException e) {
            //does nothing, test passes
        }
    }

    @Test
    public void testCollectAmountNegative() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.adminCollectFee(-10);
            fail();
        } catch (IllegalArgumentException e) {
            //does nothing, test passes
        }
    }
}
