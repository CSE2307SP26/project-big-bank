package test;
import main.BankAccount;
import main.BankUser;
import main.MainMenu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MainMenuTest {

    @Test
    public void testViewAllAccountsAndBalancesWithNoAccounts() {
        MainMenu menu = new MainMenu();
        BankUser user = new BankUser();
        user.setUsername("testuser");
        user.setPassword("password");
        menu.setBankUser(user);

        String summary = menu.getAccountsSummary();
        assertEquals("No accounts available.", summary);
    }

    @Test
    public void testViewAllAccountsAndBalancesWithOneAccount() {
        MainMenu menu = new MainMenu();
        BankUser user = new BankUser();

        user.setUsername("testuser");
        user.setPassword("password");

        BankAccount account = new BankAccount();
        account.setName("Account1");
        account.deposit(100.0);

        user.addAccount(account);
        menu.setBankUser(user);

        String summary = menu.getAccountsSummary();
        assertTrue(summary.contains("Available accounts:"));
        assertTrue(summary.contains("Account1"));
        assertTrue(summary.contains("100.00"));
    }

    @Test
    public void testViewAllAccountsAndBalancesWithMultipleAccounts() {
        MainMenu menu = new MainMenu();
        BankUser user = new BankUser();

        user.setUsername("testuser");
        user.setPassword("password");

        BankAccount checking = new BankAccount();
        checking.setName("Account1");
        checking.deposit(50.0);

        BankAccount savings = new BankAccount();
        savings.setName("Account2");
        savings.deposit(200.0);

        user.addAccount(checking);
        user.addAccount(savings);
        menu.setBankUser(user);

        String summary = menu.getAccountsSummary();

        assertTrue(summary.contains("Account1"));
        assertTrue(summary.contains("Account2"));
        assertTrue(summary.contains("50.00"));
        assertTrue(summary.contains("200.00"));
        assertTrue(summary.contains("1."));
        assertTrue(summary.contains("2."));
    }
}