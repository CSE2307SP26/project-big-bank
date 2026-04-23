package test;

import main.AccountValidator;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class AccountValidatorTest {

    @Test
    public void testRequireOpenValid() {
        AccountValidator.requireOpen(true);
        assertTrue(true); 
    }

    @Test
    public void testRequireOpenClosed() {
        assertThrows(IllegalStateException.class, () -> AccountValidator.requireOpen(false));
    }

    @Test
    public void testRequirePositiveAmountValid() {
        AccountValidator.requirePositiveAmount(10.0);
        assertTrue(true);
    }

    @Test
    public void testRequirePositiveAmountZero() {
        assertThrows(IllegalArgumentException.class, () -> AccountValidator.requirePositiveAmount(0));
    }

    @Test
    public void testRequirePositiveAmountNegative() {
        assertThrows(IllegalArgumentException.class, () -> AccountValidator.requirePositiveAmount(-5.0));
    }

    @Test
    public void testRequireSufficientFundsValid() {
        AccountValidator.requireSufficientFunds(100.0, 50.0);
        assertTrue(true);
    }

    @Test
    public void testRequireSufficientFundsTooMuch() {
        assertThrows(IllegalArgumentException.class,
            () -> AccountValidator.requireSufficientFunds(50.0, 100.0));
    }

    @Test
    public void testRequireDistinctAccountsValid() {
        BankAccount a = new BankAccount();
        BankAccount b = new BankAccount();

        AccountValidator.requireDistinctAccounts(a, b);
        assertTrue(true);
    }

    @Test
    public void testRequireDistinctAccountsSame() {
        BankAccount a = new BankAccount();

        assertThrows(IllegalArgumentException.class,
            () -> AccountValidator.requireDistinctAccounts(a, a));
    }
}