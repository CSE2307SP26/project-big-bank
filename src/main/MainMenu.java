package main;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
	private static final int MAX_SELECTION = 4;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");
        System.out.println("3. View transaction history");
        System.out.println("4. Collect fee");


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
                viewFeeCollection();
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
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
