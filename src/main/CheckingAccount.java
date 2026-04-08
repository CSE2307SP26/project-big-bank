package main;

public class CheckingAccount extends BankAccount {

    private static final double OVERDRAFT_LIMIT = -100.0;
    private static final double OVERDRAFT_FEE = 5.0;

    public CheckingAccount() {
        super();
        setName("Checking Account");
    }

    @Override
    public void withdraw(double amount) {
        AccountValidator.requireOpen(isOpen());
        AccountValidator.requirePositiveAmount(amount);

        if (balance - amount < OVERDRAFT_LIMIT) {
            throw new IllegalArgumentException("Withdrawal would exceed overdraft limit of $" + Math.abs(OVERDRAFT_LIMIT));
        }

        balance -= amount;
        history.record(Transaction.Type.WITHDRAWAL, amount);

        if (balance < 0) {
            balance -= OVERDRAFT_FEE;
            history.record(Transaction.Type.FEE, OVERDRAFT_FEE);
            System.out.printf("Overdraft! A $%.2f fee has been charged. New balance: $%.2f%n", OVERDRAFT_FEE, balance);
        }
    }
}