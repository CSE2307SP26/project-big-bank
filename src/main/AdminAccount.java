package main;

public class AdminAccount {

    private final BankAccount account;

    public AdminAccount(BankAccount account) {
        this.account = account;
    }

    public void collectFee(double amount) {
        AccountValidator.requirePositiveAmount(amount);
        AccountValidator.requireSufficientFunds(account.getBalance(), amount);
        account.applyAdjustment(-amount, Transaction.Type.FEE);
    }

    public void addInterest(double amount) {
        AccountValidator.requirePositiveAmount(amount);
        account.applyAdjustment(amount, Transaction.Type.INTEREST);
    }
}