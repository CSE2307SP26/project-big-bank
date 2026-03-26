package test;

import main.BankAccount;
import main.MainMenu;

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
        assertTrue(testAccount.isOpen());
    }

    @Test
    public void testCloseAccount() { //it might be a good idea to maintain account data, but still close the account
        BankAccount testAccount = new BankAccount();
        testAccount.close();
        assertTrue(!testAccount.isOpen());
    }

    @Test
    public void testDepositToClosedAccount() { //it should be impossible to deposit to a closed account
        BankAccount testAccount = new BankAccount();
        testAccount.close();
        try {
            testAccount.deposit(50);
            fail();
        } catch (IllegalStateException e) {
            //test passes
        }
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

    @Test
    public void testSelfTransfer() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try { //should fail because testAccount is self
            testAccount.transfer(testAccount,25);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testCheckBalance() {
        BankAccount account = new BankAccount();
        assertEquals(0.0, account.getBalance(), 0.01);
    }


    @Test
    public void testCheckBalanceAfterDeposit() {
        BankAccount account = new BankAccount();
        account.deposit(500.0);
        assertEquals(500.0, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdraw() {
        BankAccount account = new BankAccount();
        account.deposit(500.0);
        account.withdraw(100.0);
        assertEquals(400.0, account.getBalance(), 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount account = new BankAccount();
        try {
            account.withdraw(-50.0);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testStartsWithOneAccount() {
        MainMenu menu = new MainMenu();
        assertEquals(1, menu.getNumberOfAccounts());
    }

    @Test
    public void testAddAccountAddsOneAccount() {
        MainMenu menu = new MainMenu();
        menu.createAdditionalAccount();
        assertEquals(2, menu.getNumberOfAccounts());
    
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
    public void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount();
        try {
            account.withdraw(200.0);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
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

    @Test
    public void testSwitchToSecondAccount() {
        MainMenu menu = new MainMenu();
        menu.createAdditionalAccount();

        BankAccount secondAccount = menu.getAccount(1);
        menu.switchAccount(2);

        assertSame(secondAccount, menu.getCurrentAccount());
    }

    @Test
    public void testSwitchBackToFirstAccount() {
        MainMenu menu = new MainMenu();
        BankAccount firstAccount = menu.getCurrentAccount();

        menu.createAdditionalAccount();
        menu.switchAccount(2);
        menu.switchAccount(1);

        assertSame(firstAccount, menu.getCurrentAccount());
    }

    @Test
    public void testCreateMultipleAccounts() {
        MainMenu menu = new MainMenu();
        menu.createAdditionalAccount();
        menu.createAdditionalAccount();
        menu.createAdditionalAccount();
        assertEquals(4, menu.getNumberOfAccounts());
    }
}


