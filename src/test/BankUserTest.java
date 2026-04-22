package test;

import main.BankUser;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

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
    public void setNewPassword() {
        BankUser newUser = new BankUser();
        newUser.setUsername("My Name");
        newUser.setPassword("123");
        newUser.setPassword("456");
        assertTrue(newUser.checkPassword("456"));
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

    @Test
    public void testOneIncorrectPasswordDoesNotLockUser() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong");

        assertFalse(user.isLocked());
    }

    @Test
    public void testThreeIncorrectPasswordsLockUser() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");

        assertTrue(user.isLocked());
    }

    @Test
    public void testFailedAttemptsIncreaseAfterWrongPassword() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong");

        assertEquals(1, user.getFailedAttempts());
    }

    @Test
    public void testCorrectPasswordResetsFailedAttempts() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong");
        user.verifyPassword("pass123");

        assertEquals(0, user.getFailedAttempts());
    }

    @Test
    public void testRemainingAttemptsAfterOneWrongPassword() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong");

        assertEquals(2, user.getRemainingAttempts());
    }

    @Test
    public void testRemainingAttemptsAfterThreeWrongPasswords() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");

        assertEquals(0, user.getRemainingAttempts());
    }

    @Test
    public void testLockedUserCannotVerifyCorrectPassword() {
        BankUser user = new BankUser();
        user.setUsername("My name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");

        assertFalse(user.verifyPassword("pass123"));
    }

    @Test
    public void testUnlockRemovesLock() {
        BankUser user = new BankUser();
        user.setUsername("my name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");
        user.unlock();

        assertFalse(user.isLocked());
    }
    
    @Test
    public void testUnlockResetsFailedAttempts() {
        BankUser user = new BankUser();
        user.setUsername("my name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");
        user.unlock();

        assertEquals(0, user.getFailedAttempts());
    }

    @Test
    public void testVerifyPasswordWorksAfterUnlock() {
        BankUser user = new BankUser();
        user.setUsername("my name");
        user.setPassword("pass123");

        user.verifyPassword("wrong1");
        user.verifyPassword("wrong2");
        user.verifyPassword("wrong3");
        user.unlock();

        assertTrue(user.verifyPassword("pass123"));
}
}
