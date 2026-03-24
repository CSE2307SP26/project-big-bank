package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
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
        testAccount1.deposit(50);
        try {
            testAccount1.transfer(testAccount2,-25);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }
}
