public class Ex3_20220808049 {
    public static void main(String[] args) {
        Author kernighanRitchie = new Author("Brian W.", "Kernighan", "bkern@example.com");
        Author robertMartin = new Author("Robert C.", "Martin", "rmartin@example.com");
        Author javaConcurrency = new Author("Brian", "Goetz", "bgoetz@example.com");
        Author headFirst = new Author("Eric", "Freeman", "efreeman@example.com");

        // Create books and ebooks
        Book cProgramming = new Book("978-3-16-148410-0", "The C Programming Language", kernighanRitchie,
                49.99);
        Book cleanCode = new Book("978-0-13-110362-7", "Clean Code: A Handbook " +
                "of Agile Software Craftsmanship", robertMartin, 29.99);
        EBook cleanCodeEbook = new EBook("978-0-13-110362-7", "Clean Code: A Handbook of" +
                " Agile Software Craftsmanship", robertMartin, "https://example.com/ebook1.pdf", 5.2);
        EBook javaConcurrencyEbook = new EBook("978-1-491-90578-4",
                "Java Concurrency in Practice", javaConcurrency, 39.99,
                "https://example.com/ebook2.pdf", 8.1);

        // Create paper books
        PaperBook headFirstDesign = new PaperBook("978-1-119-05661-9", "Head First" +
                " Design Patterns: A Brain-Friendly Guide", headFirst, 59.99, 10, true);
        PaperBook effectiveJava = new PaperBook("978-0-321-35668-0",
                "Effective Java", robertMartin, true);

        // Print out book details
        System.out.println(cProgramming.getTitle() + " by " + cProgramming.getAuthor().getName() + " " +
                cProgramming.getAuthor().getSurname());
        System.out.println("ISBN: " + cProgramming.getIsbn());
        System.out.println("Price: $" + cProgramming.getPrice());

        System.out.println(cleanCode.getTitle() + " by " + cleanCode.getAuthor().getName() + " " +
                cleanCode.getAuthor().getSurname());
        System.out.println("ISBN: " + cleanCode.getIsbn());
        System.out.println("Price: $" + cleanCode.getPrice());

        System.out.println(cleanCodeEbook.getTitle() + " by " + cleanCodeEbook.getAuthor().getName() + " " +
                cleanCodeEbook.getAuthor().getSurname());
        System.out.println("ISBN: " + cleanCodeEbook.getIsbn());
        System.out.println("Price: $" + cleanCodeEbook.getPrice());
        System.out.println("Download URL: " + cleanCodeEbook.getDownloadUrl());
        System.out.println("Size: " + cleanCodeEbook.getSizeMb() + " MB");

        System.out.println(javaConcurrencyEbook.getTitle() + " by " + javaConcurrencyEbook.getAuthor().getName() + " "
                + javaConcurrencyEbook.getAuthor().getSurname());
        System.out.println("ISBN: " + javaConcurrencyEbook.getIsbn());
        System.out.println("Price: $" + javaConcurrencyEbook.getPrice());
        System.out.println("Download URL: " + javaConcurrencyEbook.getDownloadUrl());
        System.out.println("Size: " + javaConcurrencyEbook.getSizeMb() + " MB");

        System.out.println(headFirstDesign.getTitle() + " by " + headFirstDesign.getAuthor().getName() + " " +
                headFirstDesign.getAuthor().getSurname());
        System.out.println("ISBN: " + headFirstDesign.getIsbn());
        System.out.println("Price: $" + headFirstDesign.getPrice());
        System.out.println("ShippingWeight: " + headFirstDesign.getShippingWeight()+" lbs");
        System.out.println("InStock: " + headFirstDesign.getInStock()  );

        System.out.println(effectiveJava.getTitle() + " by " + effectiveJava.getAuthor().getName() + " " +
                effectiveJava.getAuthor().getSurname());
        System.out.println("ISBN: " + effectiveJava.getIsbn());
        System.out.println("Price: $" + effectiveJava.getPrice());
        System.out.println("ShippingWeight: " + effectiveJava.getShippingWeight()+" lbs");
        System.out.println("InStock: " + effectiveJava.getInStock() + " ");

    }


}
class Author{
    private String name;
    private String surname;
    private String mail;

    public Author(String name,String surname,String mail){
        this.name=name;
        this.surname=surname;
        this.mail=mail;
    }

    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getMail() {return mail;}
    public void setMail(String mail){this.mail=mail;}
}
class Book{
    private String isbn;
    private String title;
    private Author author;
    private double price;



    public Book(String isbn, String title, Author author, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        setPrice(15.25);
    }

    public String getIsbn() {return isbn;}

    public String getTitle() {return title;}

    public Author getAuthor() {return author;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
}
class EBook extends Book{
    private String downloadUrl;
    private double sizeMb;

    public EBook(String isbn, String title, Author author, double price,
                 String downloadUrl, double sizeMb) {
        super(isbn, title, author, price);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    public EBook(String isbn, String title, Author author,
                 String downloadUrl, double sizeMb) {
        super(isbn, title, author);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    public String getDownloadUrl() {return downloadUrl;}

    public double getSizeMb() {return sizeMb;}
}
class PaperBook extends Book{
    private int shippingWeight;
    private boolean inStock;

    public PaperBook(String isbn, String title, Author author, double price,
                     int shippingWeight, boolean inStock) {
        super(isbn, title, author, price);
        this.shippingWeight = shippingWeight;
        this.inStock = inStock;
    }

    public PaperBook(String isbn, String title, Author author,
                     boolean inStock) {
        super(isbn, title, author);
        this.inStock = inStock;
        this.shippingWeight= (int)(Math.random()*10+5);
    }

    public int getShippingWeight() {return shippingWeight;}

    public boolean getInStock() {return inStock;}

    public void setInStock(boolean inStock) {this.inStock = inStock;}
}