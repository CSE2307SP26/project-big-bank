package main;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private static final int EXIT_SELECTION = 0;

    private BankUser bankUser;

    private final ConsoleUI ui;
    private int userSelection;
    private boolean isAdmin;
    private static List<BankUser> allUsers = new ArrayList<>();

    private BankAccount selectedAccount;

    public MainMenu() {
        this.ui = new ConsoleUI();
        this.userSelection = -1;
    }

    private int getStartMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 2);
    }

    private int getUserMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 12);
    }

    private int getAdminMenuSelection() {
        return ui.promptInRange("Please make a selection: ", 0, 6);
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
        boolean loginSuccessful = userLogOn();
        if (!loginSuccessful) {
            userSelection = -1;
            return;
        }
    
        while (userSelection != EXIT_SELECTION) {
            displayUserOptions();
            userSelection = getUserMenuSelection();
            processUserInput(userSelection);
        }
        userSelection = -1;
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

    private BankUser findUser(String username) {
        for (BankUser user:allUsers){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    private boolean userLogOn() {
        String username = ui.promptString("Input username: ");
        BankUser existingUser = findUser(username);
    
        if (existingUser != null) {
            handleExistingUserLogin(existingUser);
        } else {
            createNewUser(username);
        }
    
        if (bankUser != null) {
            selectedAccount = getAccount(0);
            return true;
        }
        return false;
    }

    private void handleExistingUserLogin(BankUser existingUser) {
        if (existingUser.isLocked()) {
            System.out.println("This account is locked and cannot be accessed.");
            bankUser = null;
            return;
        }
    
        System.out.println("Welcome back " + existingUser.getUsername());
    
        if (authenticateUser(existingUser)) {
            bankUser = existingUser;
        }
    }

    private boolean authenticateUser(BankUser user) {
        while (true) {
            String passwordAttempt = ui.promptString("Input password: ");
            if (user.verifyPassword(passwordAttempt)) {
                return true;
            }
            if (user.isLocked()) {
                handleLockedOutUser();
                return false;
            }
            System.out.println("Incorrect password. Attempts remaining: " + user.getRemainingAttempts());
        }
    }

    private void handleLockedOutUser() {
        System.out.println("Too many incorrect attempts. This user is now locked out.");
        bankUser = null;
        selectedAccount = null;
    }

    private void createNewUser(String username) {
        System.out.println("New user detected. Create your account:");
    
        bankUser = new BankUser();
        bankUser.setUsername(username);
        bankUser.setPassword(ui.promptString("Input password: "));
        bankUser.addAccount(new BankAccount());
        allUsers.add(bankUser);
    
        System.out.println("account created successfully!");
    }

    private void setCurrentAccount(BankAccount account) {
        selectedAccount = account;
    }
    public void setBankUser(BankUser user) {
        this.bankUser = user;
    }

    private int getNumberOfAccounts() { 
        if (bankUser == null) {
            return 0;
        }
        return bankUser.getAccounts().size(); 
    }
    private BankAccount getAccount(int i) {
        if (bankUser == null) {
            throw new IllegalStateException("No user accounts available.");
        }
        return bankUser.getAccounts().get(i);
    }

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
        System.out.println("11. Add Note to Transaction");
        System.out.println("12. Change Password");
        System.out.println("0. Log Out");
    }

    private void displayAdminOptions() {
        System.out.println("\n1. Collect Fee");
        System.out.println("2. Add Interest Payment");
        System.out.println("3. Reopen Closed Account");
        System.out.println("4. View All Accounts");
        System.out.println("5. Unlock Locked User");
        System.out.println("6. View Account Transaction History");
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
            case 11: performAddNote(); break;
            case 12: performChangePassword(); break;
        }
    }

    private void processAdminInput(int selection) {
        switch (selection) {
            case 1: viewFeeCollection();          break;
            case 2: addInterestPayment();         break;
            case 3: reopenClosedAccount();        break;
            case 4: viewAllUsersAndAccounts();    break;
            case 5: unlockLockedUser();           break;
            case 6: adminViewTransactHistory();   break;
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
        if (!authenticateSensitiveAction("Enter password to close account: ")) {
            return;
        }
    
        String confirm = ui.promptConfirm("Account closure is permanent. Are you sure? Y/N: ");
        if (confirm.equals("Y")) {
            selectedAccount.close();
            System.out.println("Account closed.");
        }
    }

    private boolean authenticateSensitiveAction(String prompt) {
        while (true) {
            String passwordAttempt = ui.promptString(prompt);
    
            if (bankUser.verifyPassword(passwordAttempt)) {
                return true;
            }
    
            if (bankUser.isLocked()) {
                handleLockedSession();
                return false;
            }
    
            System.out.println("Incorrect password. Attempts remaining: " + bankUser.getRemainingAttempts());
        }
    }

    private void handleLockedSession() {
        System.out.println("Too many incorrect attempts. You have been logged out and account has been locked.");
        bankUser = null;
        selectedAccount = null;
        userSelection = 0;
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
        System.out.print(getAccountsSummary());
    }

    public String getAccountsSummary() {
        if (bankUser == null || getNumberOfAccounts() == 0) {
            return "No accounts available.";
        }
        String result = "Available accounts:\n";
        List<BankAccount> accounts = bankUser.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            result += String.format("  %d. %s | Balance: $%.2f%n",
                    i + 1,
                    accounts.get(i).getName(),
                    accounts.get(i).getBalance());
        }
        return result;
    }

    private void reopenClosedAccount() {
        List<BankAccount> closedAccounts = getClosedAccounts();
    
        if (closedAccounts.isEmpty()) {
            System.out.println("No closed accounts to reopen.");
            return;
        }
    
        displayClosedAccounts(closedAccounts);
        BankAccount selected = selectClosedAccount(closedAccounts);
        selected.reopen();
    
        System.out.println("Account has been reopened.");
    }

    private List<BankAccount> getClosedAccounts() {
        List<BankAccount> closed = new ArrayList<>();
    
        for (int i = 0; i < getNumberOfAccounts(); i++) {
            BankAccount acc = getAccount(i);
            if (!acc.isOpen()) {
                closed.add(acc);
            }
        }
    
        return closed;
    }

    private void displayClosedAccounts(List<BankAccount> accounts) {
        System.out.println("Closed accounts:");
    
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount acc = accounts.get(i);
            System.out.println((i + 1) + ". " + acc.getName() + " | Balance: $" + acc.getBalance());
        }
    }

    private BankAccount selectClosedAccount(List<BankAccount> accounts) {
        int selection = ui.promptInRange(
            "Select account to reopen: ",
            1,
            accounts.size()
        );
    
        return accounts.get(selection - 1);
    }

    private void viewAllUsersAndAccounts() {
        if (allUsers.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
    
        for (BankUser user : allUsers) {
            System.out.println("User: " + user.getUsername());
    
            for (BankAccount acc : user.getAccounts()) {
                System.out.println("  - " + acc.getName() + " | Balance: $" + acc.getBalance());
            }
        }
    }


    private void performAddNote() {
        List<Transaction> history = selectedAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions to add a note to.");
            return;
        }
        System.out.println("Recent transactions");
        for (int i = 0; i < history.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, history.get(i));
        }
        int selection = ui.promptInRange("Select a transaction: ", 1, history.size());
        String note = ui.promptString("Enter note: ");
        history.get(selection - 1).setNote(note);
        System.out.println("Added note to transaction");
    }

    private void performChangePassword() {
        if (!authenticateSensitiveAction("Enter password to change password: ")) {
            return;
        }
    
        ui.promptPasswordSelection(bankUser);
        System.out.println("Password changed successfully.");
    }

    private void adminViewTransactHistory() {
        if (allUsers.isEmpty()) {
            System.out.println("No users available");
            return;
        }

        for (int i=0; i<allUsers.size(); i++) {
            System.out.println((i+1) + "." + allUsers.get(i).getUsername());
        }
        int chooseUser = ui.promptInRange("Select user: ", 1, allUsers.size());
        BankUser selectedUser = allUsers.get(chooseUser-1);
        if (selectedUser.getAccounts().isEmpty()){
            System.out.println("This user has no accounts");
        }
        ArrayList<BankAccount> accounts = selectedUser.getAccounts();
        for (int i=0; i<accounts.size(); i++){
            System.out.printf("%d. %s | Balance: $%.2f%n", i + 1, accounts.get(i).getName(), accounts.get(i).getBalance());
        }
        int accSelection = ui.promptInRange("Select account: ", 1, accounts.size());
        BankAccount account = accounts.get(accSelection - 1);
        List<Transaction> history = account.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
    
        System.out.println("\nTransaction History:");
        for (Transaction t : history) {
            System.out.println("  " + t);
        }
    
    }

    private void unlockLockedUser() {
        List<BankUser> lockedUsers = getLockedUsers();
    
        if (lockedUsers.isEmpty()) {
            System.out.println("No locked users found.");
            return;
        }
    
        displayLockedUsers(lockedUsers);
        BankUser selectedUser = selectLockedUser(lockedUsers);
        selectedUser.unlock();
    
        System.out.println("User account has been unlocked.");
    }

    private List<BankUser> getLockedUsers() {
        List<BankUser> lockedUsers = new ArrayList<>();
    
        for (BankUser user : allUsers) {
            if (user.isLocked()) {
                lockedUsers.add(user);
            }
        }
    
        return lockedUsers;
    }

    private void displayLockedUsers(List<BankUser> lockedUsers) {
        System.out.println("Locked users:");
    
        for (int i = 0; i < lockedUsers.size(); i++) {
            System.out.println((i + 1) + ". " + lockedUsers.get(i).getUsername());
        }
    }

    private BankUser selectLockedUser(List<BankUser> lockedUsers) {
        int selection = ui.promptInRange(
            "Select user to unlock: ",
            1,
            lockedUsers.size()
        );
    
        return lockedUsers.get(selection - 1);
    }

    public static void main(String[] args) {
        new MainMenu().run();
    }
}