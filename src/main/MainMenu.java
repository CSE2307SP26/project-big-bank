package main;

import java.lang.foreign.ValueLayout;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
	private static final int MAX_SELECTION = 9;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");
        System.out.println("9. Close current account");
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
            case 9:
                performCloseAccount();
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
