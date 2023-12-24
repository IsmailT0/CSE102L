interface Sellable {
    String getName();

    double getPrice();
}

interface Package<T> {
    T extract();

    boolean pack(T item);

    boolean isEmpty();
}

interface Wrappable extends Sellable {

}

public class Ex8_20220808049 {
    public static void main(String[] args) {

    }
}

abstract class Product implements Sellable {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " ( " + name + " , " + price + " )";
    }
}

class Mirror<T> extends Product {
    private int width;
    private int height;

    public Mirror(int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    public T reflect(T item) {
        System.out.println(item);
        return item;
    }

}


class Paper extends Product implements Wrappable {
    private String note;

    public Paper(String note) {
        super("A4", 3);
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T> {

    private T item;

    public Matroschka(T item) {
        super("Doll", item.getPrice() + 5);
        this.item = item;
    }


    @Override
    public String toString() {
        return this.getClass() + " { " + item + "  ]";
    }

    public T extract() {
        if (isEmpty())
            return null;

        T item1 = this.item;
        this.item = null;
        return item1;
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }
}

class Box<T extends Sellable> implements Package<T> {

    private T item;
    private boolean seal;

    public Box() {
        this.item = null;
        seal = false;
    }

    public Box(T item) {
        this.item = item;
        seal = true;
    }


    @Override
    public T extract() {

        if (isEmpty()) {
            seal = false;
            return null;

        } else {

            T item = this.item;
            this.item = null;
            seal = false;
            return item;
        }
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
    }

}
