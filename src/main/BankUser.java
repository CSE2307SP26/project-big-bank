package main;

import java.util.ArrayList;

public class BankUser {

    private String username;
    private String password;
    private ArrayList<BankAccount> accounts;

    public BankUser() {
        this.username = "";
        this.password = "";
        this.accounts = new ArrayList<>();
    }

    private boolean validateString(String test) {
        return !test.isBlank();
    }

    public void setUsername(String input) {
        if(!validateString(input)) { throw new IllegalArgumentException("Username must be text");}

        username = input;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String input) {
        return input.equals(password);
    }

    public void setPassword(String input) {
        if(!validateString(input)) { throw new IllegalArgumentException("Password must be text");}

        password = input;

        confirmUserSetup();
    }

    public void confirmUserSetup() { //throws error if strings dont validate
        if(!validateString(username)) { throw new IllegalArgumentException("Username must be text");}
        if(!validateString(password)) { throw new IllegalArgumentException("Password must be text");}
    }

    public void addAccount(BankAccount account) {
        confirmUserSetup();

        if(accounts.contains(account)) { throw new IllegalArgumentException("Account already added");}

        accounts.add(account);
    }

    public ArrayList<BankAccount> getAccounts() {
        confirmUserSetup();
        return accounts;
    }
}