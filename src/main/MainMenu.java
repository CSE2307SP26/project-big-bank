package main;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private static final int EXIT_SELECTION = 0;

    private BankUser bankUser;

    private final ConsoleUI ui;
    private int userSelection;
    private boolean isAdmin;

    private BankAccount selectedAccount;

    public MainMenu() {
        this.ui = new ConsoleUI();
        this.userSelection = -1;
    }

    private int getStartMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 2);
    }

    private int getUserMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 10);
    }

    private int getAdminMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 2);
    }

    private void run() {

        while (userSelection != EXIT_SELECTION) { //Start Menu
            displayStartMenu();
            userSelection = getStartMenuSelection();
            processStartInput(userSelection);
        }

        System.out.println("Thank you for using 237 Bank App!");
    }

    private void runAsUser() {
        userLogOn();

        while (userSelection != EXIT_SELECTION) {
            displayUserOptions();
            userSelection = getUserMenuSelection();;
            processUserInput(userSelection);
        }

        userSelection = -1; //reset to prevent exit chain
    }

    private void runAsAdmin() {
        selectedAccount = new BankAccount();

        while (userSelection != EXIT_SELECTION) {
            displayAdminOptions();
            userSelection = getAdminMenuSelection();
            processAdminInput(userSelection);
        }
        
        userSelection = -1; //reset to prevent exit chain
    }

    private void userLogOn() {
        bankUser = new BankUser();
        bankUser.setUsername(ui.promptString("Input username: "));
        bankUser.setPassword(ui.promptString("Input password: "));

        bankUser.addAccount(new BankAccount());
        selectedAccount = getAccount(0);
    }

    private void setCurrentAccount(BankAccount account) {
        selectedAccount = account;
    }

    private int getNumberOfAccounts()       { return bankUser.getAccounts().size(); }
    private BankAccount getAccount(int i)   { return bankUser.getAccounts().get(i); }

    private void displayStartMenu() {
        System.out.println("\nWelcome to the 237 Bank App!");
        System.out.println("1. User Log In");
        System.out.println("2. Admin Portal");
        System.out.println("0. Exit the App");
    }

    private void displayUserOptions() {
        System.out.println("\n1. Make a Deposit");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Transfer Money");
        System.out.println("6. Create Additional Account");
        System.out.println("7. Close Current Account");
        System.out.println("8. Switch Account");
        System.out.println("9. Rename Current Account");
        System.out.println("10. View All Accounts and Balances");
        System.out.println("0. Log Out");
    }

    private void displayAdminOptions() {
        System.out.println("\n1. Collect Fee");
        System.out.println("2. Add Interest Payment");
        System.out.println("0. Log Out");
    }

    private void displayAccounts() {
        ArrayList<BankAccount> accounts = bankUser.getAccounts();

        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("  %d. %s | Balance: $%.2f%n",
                i + 1, accounts.get(i).getName(), accounts.get(i).getBalance());
        }
    }


    private void processStartInput(int selection) {
        switch (selection) {
            case 1: runAsUser();          break;
            case 2: runAsAdmin();         break;
        }
    }

    private void processUserInput(int selection) {
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
            case 10: viewAllAccountsAndBalances(); break;
        }
    }

    private void processAdminInput(int selection) {
        switch (selection) {
            case 1: viewFeeCollection();          break;
            case 2: addInterestPayment();         break;
        }
    }

    private void performDeposit() {
        double amount = ui.promptPositiveDouble("How much would you like to deposit: ");
        try {
            selectedAccount.deposit(amount);
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, selectedAccount.getBalance());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void performWithdraw() {
        double amount = ui.promptPositiveDouble("How much would you like to withdraw: ");
        try {
            selectedAccount.withdraw(amount);
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, selectedAccount.getBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private void displayBalance() {
        System.out.printf("Current balance: $%.2f%n", selectedAccount.getBalance());
    }

    private void viewTransactionHistory() {
        List<Transaction> history = selectedAccount.getTransactionHistory();
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

    private void performTransfer() {
        if (getNumberOfAccounts() < 2) {
            System.out.println("No other accounts available to transfer to.");
            return;
        }
        displayAccounts();
        int selection = ui.promptInRange("Select target account: ", 1, getNumberOfAccounts());
        double amount = ui.promptPositiveDouble("How much would you like to transfer: ");
        try {
            selectedAccount.transfer(getAccount(selection - 1), amount);
            System.out.println("Transfer successful!");
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private void performCloseAccount() {
        String confirm = ui.promptConfirm("Account closure is permanent. Are you sure? Y/N: ");
        if (confirm.equals("Y")) {
            selectedAccount.close();
            System.out.println("Account closed.");
        }
    }

    private void createAdditionalAccount() {
        BankAccount newAccount = new BankAccount();
        bankUser.addAccount(newAccount);
        setCurrentAccount(newAccount);
        System.out.println("New account created. You are now using account #" + getNumberOfAccounts());
    }

    private void performSwitchAccount() {
        if (getNumberOfAccounts() < 2) {
            System.out.println("No other accounts available.");
            return;
        }
        displayAccounts();
        int selection = ui.promptInRange("Select account number: ", 1, getNumberOfAccounts());
        setCurrentAccount(getAccount(selection - 1));
        System.out.println("Switched to account #" + selection);
    }

    private void switchAccount(int accountNumber) {
        if (accountNumber < 1 || accountNumber > getNumberOfAccounts()) {
            throw new IllegalArgumentException("Invalid account selection.");
        }
        setCurrentAccount(getAccount(accountNumber - 1));
    }

    private void viewFeeCollection() {
        double amount = ui.promptPositiveDouble("Enter fee amount: ");
        try {
            selectedAccount.collectFee(amount);
            System.out.printf("Fee of $%.2f collected.%n", amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Fee failed: " + e.getMessage());
        }
    }

    private void addInterestPayment() {
        displayAccounts();
        int selection = ui.promptInRange("Select account number: ", 1, getNumberOfAccounts());
        double amount = ui.promptPositiveDouble("Enter interest amount: ");
        try {
            getAccount(selection - 1).addInterest(amount);
            System.out.printf("Interest of $%.2f added to account #%d.%n", amount, selection);
        } catch (IllegalArgumentException e) {
            System.out.println("Interest failed: " + e.getMessage());
        }
    }

    private void performRenameAccount() {
        String name = ui.promptString("Enter a name for this account: ");
        selectedAccount.setName(name);
        System.out.println("Account renamed to \"" + name + "\".");
    }

    private void viewAllAccountsAndBalances() {
        if (getNumberOfAccounts() == 0) {
            System.out.println("No accounts available.");
            return;
        }
        displayAccounts();
    }

    public static void main(String[] args) {
        new MainMenu().run();
    }
}