package test;

import main.BankUser;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankUserTest {

    @Test
    public void createUser() {
        BankUser newUser = new BankUser();
        assertThrows(IllegalArgumentException.class, () -> newUser.confirmUserSetup()); //confirm user created but not setup properly
    }

    @Test
    public void setUsername() {
        BankUser newUser = new BankUser();
        newUser.setUsername("My Name");
        assertEquals(newUser.getUsername(),"My Name");
    }

    @Test
    public void setInvalidUsername() {
        BankUser newUser = new BankUser();
        assertThrows(IllegalArgumentException.class, () -> newUser.setUsername(""));
    }

    @Test
    public void setAndCheckPassword() {
        BankUser newUser = new BankUser();
        newUser.setUsername("My Name");
        newUser.setPassword("12345");
        assertTrue(newUser.checkPassword("12345"));
    }

    @Test
    public void setInvalidPassword() {
        BankUser newUser = new BankUser();
        newUser.setUsername("My Name");
        assertThrows(IllegalArgumentException.class, () -> newUser.setPassword(""));
    }

    @Test
    public void getAccounts() {
        BankUser newUser = new BankUser();

        newUser.setUsername("My name");
        newUser.setPassword("12345");

        assertEquals(newUser.getAccounts().size(),0);
    }

    @Test
    public void addAccount() {
        BankUser newUser = new BankUser();
        BankAccount newAccount = new BankAccount();

        newUser.setUsername("My name");
        newUser.setPassword("12345");

        newUser.addAccount(newAccount);
        assertEquals(newUser.getAccounts().size(),1);
    }

    @Test
    public void addDuplicateAccount() {
        
        BankUser newUser = new BankUser();
        BankAccount newAccount = new BankAccount();

        newUser.setUsername("My name");
        newUser.setPassword("12345");

        newUser.addAccount(newAccount);
        assertThrows(IllegalArgumentException.class, () -> newUser.addAccount(newAccount));
    }
}
