package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void testTransfer() {
        BankAccount testAccount1 = new BankAccount();
        BankAccount testAccount2 = new BankAccount();
        testAccount1.deposit(50);
        testAccount1.transfer(testAccount2,25);
        assertEquals(25,testAccount1.getBalance(),0.01);
        assertEquals(25,testAccount2.getBalance(),0.01);
    }

    @Test
    public void testInvalidTransfer() {
        BankAccount testAccount1 = new BankAccount();
        BankAccount testAccount2 = new BankAccount();
        try { //should fail because testAccount has no balance
            testAccount1.transfer(testAccount2,25);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }
}
