package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
	  private static final int MAX_SELECTION = 9;
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
        this.authenticate();
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
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");
        System.out.println("3. View transaction history");
        System.out.println("4. Create additional account");
        System.out.println("5. Close current account");
        if (isAdmin) {
            System.out.println("9. Collect fee");
        }


    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
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
            case 3:
                viewTransactionHistory();
                break;
            case 4:
                createAdditionalAccount();
                break;
            case 5:
                performCloseAccount();
                break;
            case 9:
                if (isAdmin) {
                    viewFeeCollection();
                } else {
                    System.out.println("Unauthorized option.");
                }
                break;
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
            userAccount.Close();
        }
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

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
