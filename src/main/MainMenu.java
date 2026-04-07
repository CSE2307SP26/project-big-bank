package main;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private static final int EXIT_SELECTION = 0;
    private static final String ADMIN_PASS = "admin";

    private final List<BankAccount> accounts;
    private BankAccount userAccount;
    private AdminAccount adminAccount;
    private final ConsoleUI ui;
    private boolean isAdmin;

    public MainMenu() {
        this.ui = new ConsoleUI();
        this.accounts = new ArrayList<>();
        this.userAccount = new BankAccount();
        this.adminAccount = new AdminAccount(userAccount);
        this.accounts.add(userAccount);
    }

    private void setCurrentAccount(BankAccount account) {
        userAccount = account;
        adminAccount = new AdminAccount(account);
    }

    public int getNumberOfAccounts()       { return accounts.size(); }
    public BankAccount getCurrentAccount() { return userAccount; }
    public BankAccount getAccount(int i)   { return accounts.get(i); }
    public boolean isAdmin()               { return isAdmin; }

    private void authenticate() {
        String input = ui.promptString("Enter admin password (or press Enter for regular user): ");
        if (input.equals(ADMIN_PASS)) {
            isAdmin = true;
            System.out.println("Admin access granted.");
        } else {
            isAdmin = false;
            System.out.println("Continuing as regular user.");
        }
    }

    public void displayOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        System.out.println("1. Make a Deposit");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Transfer Money");
        System.out.println("6. Create Additional Account");
        System.out.println("7. Close Current Account");
        System.out.println("8. Switch Account");
        System.out.println("9. Rename Current Account");
        System.out.println("0. Exit");
        if (isAdmin) {
            System.out.println("10.  Collect Fee");
            System.out.println("11. Add Interest Payment");
        }
    }

    private void displayAccounts() {
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("  %d. %s | Balance: $%.2f%n",
                i + 1, accounts.get(i).getName(), accounts.get(i).getBalance());
        }
    }

    public int getUserSelection() {
        int max = isAdmin ? 11 : 9;
        return ui.promptInRange("Please make a selection: ", EXIT_SELECTION, max);
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1: performDeposit();          break;
            case 2: performWithdraw();         break;
            case 3: displayBalance();          break;
            case 4: viewTransactionHistory();  break;
            case 5: performTransfer();         break;
            case 6: createAdditionalAccount(); break;
            case 7: performCloseAccount();     break;
            case 8: performSwitchAccount();    break;
            case 9: performRenameAccount();    break;
            case 10:
                if (isAdmin) viewFeeCollection();
                else System.out.println("Unauthorized option.");
                break;
            case 11:
                if (isAdmin) addInterestPayment();
                else System.out.println("Unauthorized option.");
                break;
        }
    }

    public void performDeposit() {
        double amount = ui.promptPositiveDouble("How much would you like to deposit: ");
        try {
            userAccount.deposit(amount);
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, userAccount.getBalance());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performWithdraw() {
        double amount = ui.promptPositiveDouble("How much would you like to withdraw: ");
        try {
            userAccount.withdraw(amount);
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, userAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    public void displayBalance() {
        System.out.printf("Current balance: $%.2f%n", userAccount.getBalance());
    }

    private void viewTransactionHistory() {
        List<Transaction> history = userAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("\nNo transactions found.\n");
            return;
        }
        System.out.println("\nTransaction History:");
        for (Transaction t : history) {
            System.out.println("  " + t);
        }
        System.out.println();
    }

    public void performTransfer() {
        if (accounts.size() < 2) {
            System.out.println("No other accounts available to transfer to.");
            return;
        }
        displayAccounts();
        int selection = ui.promptInRange("Select target account: ", 1, accounts.size());
        double amount = ui.promptPositiveDouble("How much would you like to transfer: ");
        try {
            userAccount.transfer(accounts.get(selection - 1), amount);
            System.out.println("Transfer successful!");
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    public void performCloseAccount() {
        String confirm = ui.promptConfirm("Account closure is permanent. Are you sure? Y/N: ");
        if (confirm.equals("Y")) {
            userAccount.close();
            System.out.println("Account closed.");
        }
    }

    public void createAdditionalAccount() {
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
        setCurrentAccount(newAccount);
        System.out.println("New account created. You are now using account #" + accounts.size());
    }

    public void performSwitchAccount() {
        if (accounts.size() < 2) {
            System.out.println("No other accounts available.");
            return;
        }
        displayAccounts();
        int selection = ui.promptInRange("Select account number: ", 1, accounts.size());
        setCurrentAccount(accounts.get(selection - 1));
        System.out.println("Switched to account #" + selection);
    }

    public void switchAccount(int accountNumber) {
        if (accountNumber < 1 || accountNumber > accounts.size()) {
            throw new IllegalArgumentException("Invalid account selection.");
        }
        setCurrentAccount(accounts.get(accountNumber - 1));
    }

    private void viewFeeCollection() {
        double amount = ui.promptPositiveDouble("Enter fee amount: ");
        try {
            adminAccount.collectFee(amount);
            System.out.printf("Fee of $%.2f collected.%n", amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Fee failed: " + e.getMessage());
        }
    }

    private void addInterestPayment() {
        displayAccounts();
        int selection = ui.promptInRange("Select account number: ", 1, accounts.size());
        double amount = ui.promptPositiveDouble("Enter interest amount: ");
        try {
            new AdminAccount(accounts.get(selection - 1)).addInterest(amount);
            System.out.printf("Interest of $%.2f added to account #%d.%n", amount, selection);
        } catch (IllegalArgumentException e) {
            System.out.println("Interest failed: " + e.getMessage());
        }
    }

    public void performRenameAccount() {
        String name = ui.promptString("Enter a name for this account: ");
        userAccount.setName(name);
        System.out.println("Account renamed to \"" + name + "\".");
    }

    public void run() {
        authenticate();
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection();
            processInput(selection);
        }
        System.out.println("Thank you for using 237 Bank App!");
    }

    public static void main(String[] args) {
        new MainMenu().run();
    }
}