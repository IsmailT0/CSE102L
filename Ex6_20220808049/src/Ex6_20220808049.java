import java.util.ArrayList;
import java.util.Date;

public class Ex6_20220808049 {
    public static void main(String[] args) {
        Product a = new ReadingBook("a",5,"aa",154,"aaa");

        Product b = new ReadingBook("b",5,"bb",154,"bbb");
        System.out.println(a.compareTo(b));
    }

}
//interfaces
interface Colorable{
    void paint(String color);
}
interface Rideable{
    void ride();
}
interface PaymentMethod{
    boolean pay(double amount);
}
abstract class Product implements Comparable<Product>{
    private String name;
    private double price;

    Product(String name, double price){
        this.name=name;
        this.price=price;
    }

    public String getName() {return name;}
    public double getPrice() {return price;}


    public int compareTo(Product product) {
        return Double.compare(this.price, product.getPrice());
    }

    public String toString(){
        return getClass().getName() + "[name= " + name + ", " + "price= " + price + "]";
    }
}

abstract class Book extends Product{
    private String author;
    private int pageCount;

    Book(String name,double price,String author, int pageCount){
        super(name,price);
        this.author=author;
        this.pageCount=pageCount;
    }

    public String getAuthor() {return author;}

    public int getPageCount() {return pageCount;}
}
class ReadingBook extends Book{
    private String genre;

    public ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }

    public String getGenre() {return genre;}
}
class ColoringBook extends Book implements Colorable{
    private String color;
    public ColoringBook(String name, double price, String author, int pageCount) {
        super(name, price, author, pageCount);
    }

    public String getColor() {return color;}


    @Override
    public void paint(String color) {
        this.color=color;
    }
}

class ToyHorse extends Product implements Rideable{


    ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {

    }
}
class Bicycle extends Product implements Colorable,Rideable{
    private String color;

    public Bicycle(String name, double price) {
        super(name, price);
    }

    public String getColor() {return color;}

    @Override
    public void paint(String color) {
        this.color=color;
    }

    @Override
    public void ride() {

    }
}
class CreditCard implements PaymentMethod{
    private long cardNumber;
    private  String cadHolderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String cadHolderName, Date expirationDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cadHolderName = cadHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        return false;
    }
}
class PayPal implements PaymentMethod{
    private String username;
    private String password;

    public PayPal(String username, String password) {
        this.username = username;
        String encrypted = "";
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            ch = (char) (((ch + 3 - 32) % 95) + 32);
            encrypted += ch;
        }
        this.password = encrypted;
    }


    @Override
    public boolean pay(double amount) {
        return false;
    }
}
class User{
    private String username;
    private String email;
    private PaymentMethod payment;
    private ArrayList<Product> cart;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        cart= new ArrayList<>();
    }

    public String getUsername() {return username;}

    public String getEmail() {return email;}

    public void setPayment(PaymentMethod payment) {this.payment = payment;}

    public Product getProduct(int index){return cart.get(index);}

    public void removeProduct(int index){cart.remove(index);}

    public void addProduct(Product product){cart.add(product);}

    public void purchase(){
        double total=0;
        for (Product product : cart) {
            total+=product.getPrice();
        }
        if (payment.pay(total))
            cart.clear();
    }
}