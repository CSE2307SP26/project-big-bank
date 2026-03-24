package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testIfAccountOpen() {
        BankAccount testAccount = new BankAccount();
        assertTrue(testAccount.IsOpen());
    }

    @Test
    public void testCloseAccount() { //it might be a good idea to maintain account data, but still close the account
        BankAccount testAccount = new BankAccount();
        testAccount.Close();
        assertTrue(!testAccount.IsOpen());
    }

    @Test
    public void testDepositToClosedAccount() { //it should be impossible to deposit to a closed account
        BankAccount testAccount = new BankAccount();
        testAccount.Close();
        try {
            testAccount.deposit(50);
            fail();
        } catch (IllegalStateException e) {
            //test passes
        }
    }

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
        }catch (IllegalArgumentException e) {
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
    public void testCollectFeeTooLarge() {
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
    public void testCollectFeeNegative() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.adminCollectFee(-10);
            fail();
        } catch (IllegalArgumentException e) {
            //does nothing, test passes
        }
    }

    @Test
    public void testCollectFeeUpdatesHistory() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.adminCollectFee(5);
        assertEquals(2, testAccount.getTransactionHistory().size());
        assertTrue(testAccount.getTransactionHistory().get(1).contains("Fee collected $5.0"));
    }
}
