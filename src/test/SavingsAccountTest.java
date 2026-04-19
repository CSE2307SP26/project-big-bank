package test;
import main.SavingsAccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
    @Test
    public void testSavingsAccountDefaultName() {
        SavingsAccount account = new SavingsAccount();
        assertEquals("Savings Account", account.getName());
    }

    

    @Test
    public void testSavingsMinimum() {
        SavingsAccount account = new SavingsAccount();
        account.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(30));
    }

    @Test
    public void testSavingsAllowedWithdraw() {
        SavingsAccount account = new SavingsAccount();
        account.deposit(100);
        account.withdraw(50);
        assertEquals(50.0, account.getBalance(), 0.01);
    }

    @Test
    public void testSavingsMonthlyWithdrawLimit() {
        SavingsAccount account = new SavingsAccount();
        account.deposit(1000);
        for (int i = 0; i < 6; i++) {
            account.withdraw(10);
        }
        assertThrows(IllegalStateException.class, () -> account.withdraw(10));
    }

    @Test
    public void testSavingsMonthlyReset() {
        SavingsAccount account = new SavingsAccount();
        account.deposit(1000);
        for (int i = 0; i < 6; i++) {
            account.withdraw(10);
        }
        account.resetMonthlyWithdrawals();
        account.withdraw(10);
        assertEquals(930.0, account.getBalance(), 0.01);
    }

    @Test
    public void testSavingsCountWithdrawals() {
        SavingsAccount account = new SavingsAccount();
        account.deposit(500);
        account.withdraw(10);
        account.withdraw(10);
        assertEquals(2, account.getWithdrawalsThisMonth());
    }
}
