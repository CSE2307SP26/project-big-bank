package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
}
