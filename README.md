# project26

## Team Members:

* Alice (alicerho)
* Megan (megan-ouyang)
* oleg (i-0leg)
* Saanya (saanya-m)

## User stories

1. A bank customer should be able to deposit into an existing account. (Shook)
2. A bank customer should be able to withdraw from an account. (megan)
3. A bank customer should be able to check their account balance. (megan)
4. A bank customer should be able to view their transaction history for an account. (alice)
5. A bank customer should be able to create an additional account with the bank. (alice)
6. A bank customer should be able to close an existing account. (oleg)
7. A bank customer should be able to transfer money from one account to another. (oleg)
8. A bank adminstrator should be able to collect fees from existing accounts when necessary. (saanya)
9. A bank adminstrator should be able to add an interest payment to an existing account when necessary. (saanya)

10. A bank customer should be able to switch between accounts. (alice)

11. A bank customer should be able to view all their existing accounts and their balances at once. (alice)
12. A bank customer should be able to open a checkings or savings account (megan)
13. A bank user can log in and log out of the bank app (oleg)
14. A bank customer should create a new account when they start the app if they do not already have an account (saanya)
15. A bank customer should be able to name/rename their accounts (megan)
16. A user should have/pick a password for log in (oleg)
17. A bank administrator should be able to view all accounts and their associated names (saanya)
18. A bank administrator should be able to reopen a closed account (alice)

19. A bank user should be able to see transaction timestamps in the transaction history (megan)
20. A bank user should be able to add a note to any transaction (megan)
21. For sensitive actions like closing an account, the system should require authentication by requiring the user to input their password (oleg)
22. A bank user should be able to change the password to their account (oleg)
23. A bank user should be locked out of their account after entering multiple incorrect passwords (alice)
24. A bank administrator should be able to unlock a bank users account if they've been locked out (alice)
25. A bank admin should be able to view the transaction history of any account on the app (saanya)
26. A bank admin should be able to view a log / history of their own actions / commands (saanya)

## Is there anything that you implemented but doesn't currently work?
1. Some edge-case UI interactions may not be handled properly

## What commands are needed to compile and run your code from the command line?
cd the directory then

./runApp.sh

if it fails run to correct the sh file
chmod +x runApp.sh
sed -i 's/\r$//' runApp.sh