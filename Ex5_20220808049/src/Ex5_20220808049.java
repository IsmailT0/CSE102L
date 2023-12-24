import java.util.ArrayList;

public class Ex5_20220808049 {
    public static void main(String[] args) {

    }
}


class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) throws InsufficientFundsException{
        if (balance < 0)
            throw new InsufficientFundsException(balance);
        else {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidTransactionException {
        if (amount < 0)
            throw new InvalidTransactionException(amount);
        this.balance += amount;
    }

    public void withdraw(double amount) throws InvalidTransactionException,InsufficientFundsException {
        if (amount < 0)
            throw new InvalidTransactionException(amount);
        if (balance < amount)
            throw new InsufficientFundsException(balance, amount);
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + ", Balance: " + balance;
    }

}


class Customer {
    private String name;
    private ArrayList<Account> accounts;

    Customer(String name) {
        accounts = new ArrayList<>();
        this.name = name;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account;
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void addAccount(Account account) throws AccountAlreadyExistsException {
        String accountNumber = account.getAccountNumber();
        try {
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistsException(accountNumber);
        } catch (AccountNotFoundException e) {
            accounts.add(account);
        } finally {
            System.out.println(this);
        }
        System.out.println("Added account: " + accountNumber + " with " + account.getBalance());
    }

    public void removeAccount(String accountNumber) {
        Account account = getAccount(accountNumber);
        accounts.remove(account);
    }

    public void transfer(Account fromAccount, Account toAccount, double amount) throws InvalidTransactionException {
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e,
                    "cannot transfer funds from account " + fromAccount.getAccountNumber() +
                            " to account " + toAccount.getAccountNumber());
        }
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Customer ").append(name).append(":\n");
        for (Account account : accounts) {
            stringBuilder.append("\t").append(account).append("\n");
        }
        return stringBuilder.toString();
    }
}

//exceptions
class InsufficientFundsException extends RuntimeException {
    InsufficientFundsException(double balance) {
        super("Wrong balance: " + balance);
    }

    InsufficientFundsException(double balance, double amount) {
        super("Required amount is " + amount + " but only " + balance + " remaining");
    }
}

class AccountAlreadyExistsException extends RuntimeException {
    AccountAlreadyExistsException(String accountNumber) {
        super("Account number " + accountNumber + "already exists");
    }
}

class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(String accountNumber) {
        super("Account number " + accountNumber + " already exists");
    }
}


class InvalidTransactionException extends Exception {
    InvalidTransactionException(double amount) {
        super("Invalid amount: " + amount);
    }

    InvalidTransactionException(InvalidTransactionException e, String message) {
        super(message + ":\n\t" + e.getMessage());
    }
}
