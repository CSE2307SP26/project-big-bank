package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import main.CheckingAccount;

public class CheckingAccountTest {
    @Test
    public void testCheckingAccountDefaultName() {
        CheckingAccount account = new CheckingAccount();
        assertEquals("Checking Account", account.getName());
    }

    @Test 
    public void testCheckingOverdraft() {
        CheckingAccount account = new CheckingAccount();
        account.deposit(50);
        account.withdraw(80);
        assertTrue(account.getBalance() < 0);
    }

    @Test 
    public void testCheckingOverdraftFeeCharge() {
        CheckingAccount account = new CheckingAccount();
        account.deposit(50);
        account.withdraw(80);
        assertEquals(-35.0, account.getBalance(), 0.01);
    }

    @Test
    public void testCheckingOverdraftLimits() {
        CheckingAccount account = new CheckingAccount();
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(150));
    }

    @Test
    public void testCheckingNoOverdraftIfPositive() {
        CheckingAccount account = new CheckingAccount();
        account.deposit(100);
        account.withdraw(50);
        assertEquals(50.0, account.getBalance(), 0.01);
    }

    @Test
    public void testCheckingOverdraftRecordedHistory() {
        CheckingAccount account = new CheckingAccount();
        account.deposit(50);
        account.withdraw(80);
        assertEquals(3, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(2).toString().contains("FEE"));
    }
}
