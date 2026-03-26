package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 0;
	private static final int MAX_SELECTION = 12;
    private static final String ADMIN_PASS = "admin";


    private List<BankAccount> accounts;
	private BankAccount userAccount;
    private Scanner keyboardInput;
    private boolean isAdmin;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new ArrayList<>();
        this.accounts.add(this.userAccount);
    }

    private void authenticate(){
        System.out.print("Enter admin password (or type anything for regular user): ");
        String input = keyboardInput.next();
        if (input.equals(ADMIN_PASS)) {
            isAdmin = true;
            System.out.println("Admin access granted");
        } else {
            isAdmin = false;
            System.out.println("Continuing as regular user");
        }
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void displayOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        
        System.out.println("1. Make a Deposit");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Transfer Money");
        System.out.println("6. Create additional Account");
        System.out.println("7. Close current Account");
        System.out.println("8. Switch to other Account");
        System.out.println("0. Exit the App");
        if (isAdmin) {
            System.out.println("9. Collect fee");
            System.out.println("10. Make interest payment");
        }


    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 0 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2: 
                performWithdraw();
                break;
            case 3: 
                displayBalance();
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                performTransfer();
                break;
            case 6:
                createAdditionalAccount();
                break;
            case 7:
                performCloseAccount();
                break;
            case 8: 
                performSwitchAccount();
                break;
            case 9:
                if (isAdmin) {
                    viewFeeCollection();
                } else {
                    System.out.println("Unauthorized option.");
                }
                break;
            case 10:
                if (isAdmin) {
                    addInterestPayment();
                } else {
                    System.out.println("Unauthorized option.");
                }
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        try { userAccount.deposit(depositAmount); } catch (IllegalStateException e) { System.out.println(e.getMessage());}
    }

    public void performCloseAccount() {
        String confirm = "";
        while (!confirm.equals("Y") && !confirm.equals("N")) {
            System.out.print("Account closure is permanent, are you sure? Y/N ");
            confirm = keyboardInput.next();
        }
        if (confirm.equals("Y")) {
            userAccount.close();
        }
    }

    public void performWithdraw() {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextDouble();
        }

        try {
            userAccount.withdraw(withdrawAmount);
            System.out.println("Withdrawal successful.");
            System.out.println("New balance: $" + userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
        }
    }

    public void displayBalance() {
        System.out.println("Current balance: $" + userAccount.getBalance());
    }

    private void viewTransactionHistory() {
        List<String> history = userAccount.getTransactionHistory();

        if (history.isEmpty()) {
            System.out.println("\nNo transactions found.\n");
            return;
        }

        System.out.println("\nTransaction History:");
        for (String transaction : history) {
            System.out.println(transaction);
        }
        System.out.println();
    }

    public void createAdditionalAccount() {
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
        userAccount = newAccount;
        
        System.out.println("Additional account has been created.");
        System.out.println("You are now using account #" + accounts.size());
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
  
    private void viewFeeCollection() {
        double feeAmount = -1;
    
        while (feeAmount <= 0) {
            System.out.print("Enter fee amount: ");
            feeAmount = keyboardInput.nextDouble();
        }
    
        try {
            userAccount.adminCollectFee(feeAmount);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid fee amount or insufficient balance.");
        }
    }

    private void addInterestPayment() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
    
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + (i + 1));
        }
    
        int selection = -1;
        while (selection < 1 || selection > accounts.size()) {
            System.out.print("Select account number: ");
            selection = keyboardInput.nextInt();
        }
    
        BankAccount selectedAccount = accounts.get(selection - 1);
    
        double interestAmount = -1;
        while (interestAmount <= 0) {
            System.out.print("Enter interest amount: ");
            interestAmount = keyboardInput.nextDouble();
        }
    
        try {
            selectedAccount.adminAddInterest(interestAmount);
            System.out.println("Interest added successfully to Account #" + selection);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid interest payment amount.");
        }
    }

    public BankAccount getCurrentAccount() {
        return userAccount;
    }
    
    public BankAccount getAccount(int index) {
        return accounts.get(index);
    }
    
    public void switchAccount(int accountNumber) {
        if (accountNumber < 1 || accountNumber > accounts.size()) {
            throw new IllegalArgumentException("Invalid account selection.");
        }
    
        userAccount = accounts.get(accountNumber - 1);
    }

    public void performTransfer() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + (i + 1));
        }

        int selection = -1;
        while (selection < 1 || selection > accounts.size()) {
            System.out.print("Select account number: ");
            selection = keyboardInput.nextInt();
        }
        
        double amount = -1;
        while (amount <= 0) {
            System.out.print("How much would you like to transfer: ");
            amount = keyboardInput.nextDouble();
        }

        try {
            userAccount.transfer(accounts.get(selection-1), amount);
            System.out.println("Transfer successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void performSwitchAccount() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
    
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + (i + 1));
        }
    
        System.out.print("Select account number: ");
        int selection = keyboardInput.nextInt();
    
        try {
            switchAccount(selection);
            System.out.println("Switched to account #" + selection);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        authenticate();
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
        System.out.println("Thank you for using 237 Bank App!\n");
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
