package main;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    public double promptPositiveDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Please enter a positive amount.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public int promptInRange(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public String promptConfirm(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N")) return input;
            System.out.println("Please enter Y or N.");
        }
    }

    public boolean promptAuthentication(BankUser user) {
        return user.checkPassword(promptString("Input password: "));
    }

    public void promptPasswordSelection(BankUser user){
        String input1 = promptString("Input new password: ");
        String input2 = null;
        while(!input1.equals(input2)) {
            input2 = promptString("Confirm password: ");
        }

        user.setPassword(input2);
    }

    public String promptString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}