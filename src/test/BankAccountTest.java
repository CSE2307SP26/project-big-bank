package test;

import main.BankAccount;
import main.CheckingAccount;
import main.MainMenu;
import main.SavingsAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        assertEquals(50, account.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
    }

    @Test
    public void testIfAccountOpen() {
        BankAccount account = new BankAccount();
        assertTrue(account.isOpen());
    }

    @Test
    public void testCloseAccount() {
        BankAccount account = new BankAccount();
        account.close();
        assertFalse(account.isOpen());
    }

    @Test
    public void testDepositToClosedAccount() {
        BankAccount account = new BankAccount();
        account.close();
        assertThrows(IllegalStateException.class, () -> account.deposit(50));
    }

    @Test
    public void testNewAccountHasEmptyHistory() {
        BankAccount account = new BankAccount();
        assertTrue(account.getTransactionHistory().isEmpty());
    }

    @Test
    public void testDepositAddedToTransactionHistory() {
        BankAccount account = new BankAccount();
        account.deposit(10.0);
        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(0).toString().contains("DEPOSIT"));
    }

    @Test
    public void testWithdrawalAddedToTransactionHistory() {
        BankAccount account = new BankAccount();
        account.deposit(50.0);
        account.withdraw(10.0);
        assertEquals(2, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(1).toString().contains("WITHDRAWAL"));
    }

    @Test
    public void testTransfer() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        account1.deposit(50);
        account1.transfer(account2, 25);
        assertEquals(25, account1.getBalance(), 0.01);
        assertEquals(25, account2.getBalance(), 0.01);
    }

    @Test
    public void testInvalidTransfer() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account1.transfer(account2, 25));
    }

    @Test
    public void testSelfTransfer() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account.transfer(account, 25));
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
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(200.0));
    }

    @Test
    public void testCollectFee() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.collectFee(10);
        assertEquals(90, account.getBalance(), 0.01);
    }

    @Test
    public void testCollectFeeTooLarge() {
        BankAccount account = new BankAccount();
        account.deposit(20);
        assertThrows(IllegalArgumentException.class, () -> account.collectFee(50));
    }

    @Test
    public void testCollectFeeNegative() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.collectFee(-10));
    }

    @Test
    public void testCollectFeeUpdatesHistory() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        account.collectFee(5);
        assertEquals(2, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(1).toString().contains("FEE"));
    }

    @Test
    public void testInterestPayment() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        account.addInterest(20);
        assertEquals(70, account.getBalance(), 0.01);
    }

    @Test
    public void testInterestPaymentNegative() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.addInterest(-10));
    }

    @Test
    public void testInterestPaymentUpdatesHistory() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        account.addInterest(5);
        assertEquals(2, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().get(1).toString().contains("INTEREST"));
    }

    @Test
    public void testAccountDefaultName() {
        BankAccount account = new BankAccount();
        assertEquals("Unnamed Account", account.getName());
    }

    @Test
    public void testRenameAccount() {
        BankAccount account = new BankAccount();
        account.setName("My Account");
        assertEquals("My Account", account.getName());
    }

    @Test
    public void testCheckingAccountDefaultName() {
        CheckingAccount account = new CheckingAccount();
        assertEquals("Checking Account", account.getName());
    }

    @Test
    public void testSavingsAccountDefaultName() {
        SavingsAccount account = new SavingsAccount();
        assertEquals("Savings Account", account.getName());
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