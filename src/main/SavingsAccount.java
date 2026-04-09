package main;

public class SavingsAccount extends BankAccount {

    private static final double MINIMUM_BALANCE = 25.0;
    private static final int MAX_MONTHLY_WITHDRAWALS = 6;

    private int withdrawalsThisMonth = 0;

    public SavingsAccount() {
        super();
        setName("Savings Account");
    }

    @Override
    public void withdraw(double amount) {
        AccountValidator.requireOpen(isOpen());
        AccountValidator.requirePositiveAmount(amount);

        if (withdrawalsThisMonth >= MAX_MONTHLY_WITHDRAWALS) {
            throw new IllegalStateException("Monthly withdrawal limit of " + MAX_MONTHLY_WITHDRAWALS + " reached.");
        }

        if (balance - amount < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Withdrawal would breach minimum balance of $" + MINIMUM_BALANCE);
        }

        balance -= amount;
        withdrawalsThisMonth++;
        history.record(Transaction.Type.WITHDRAWAL, amount);
    }

    public void resetMonthlyWithdrawals() {
        withdrawalsThisMonth = 0;
    }

    public int getWithdrawalsThisMonth() {
        return withdrawalsThisMonth;
    }
}