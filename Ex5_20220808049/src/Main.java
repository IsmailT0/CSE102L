public class Main {
    public static void main(String[] args) throws Exception {
        Customer customer1 = new Customer("Emre Topcu");
        Customer customer2 = new Customer("Hey");
        Account account1 = new Account("123456",1000);
        Account account2= new Account("23456", 1234);
        customer1.addAccount(account1);
        //customer1.addAccount(account2);
        customer2.addAccount(account2);
        account2.deposit(1000);
        account1.deposit(500);
        account2.withdraw(1000);
        customer1.transfer(customer1.getAccount("123456"),
                customer2.getAccount("23456"),500);
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());

    }
}