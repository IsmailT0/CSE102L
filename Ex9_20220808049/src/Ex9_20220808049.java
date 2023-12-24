public class Ex9_20220808049 {
    public static void main(String[] args) {

    }
}

interface Sellable {
    String getName();

    double getPrice();
}

interface Package<T> {
    T extract();

    boolean pack(T item);

    boolean isEmpty();
    double getPriority();
}

interface Wrappable extends Sellable {}

interface Common<T>{
    boolean isEmpty();
    T peek();
    int size();
}

interface Stack<T> extends Common<T>{
    boolean push(T item);
    T pop();
}

interface Node<T>{
    int DEFAULT_CAPACITY=2;

    void setNext(T item);
    T getNext();
    double getPriority();
}

interface PriorityQueue<T> extends Common<T>{
    int FLEET_CAPACITY= 3;
    boolean enqueue(T item);
    T dequeue();
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

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

class Box<T extends Sellable> implements Package<T> {

    private T item;
    private boolean seal;
    private int distanceToAddress;


    public Box() {
        this.item = null;
        seal = false;
    }

    public Box(T item,int distanceToAddress) {
        this.item = item;
        seal = true;
        this.distanceToAddress=distanceToAddress;
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
    public double getPriority() {
        return (distanceToAddress / item.getPrice());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
    }

}

class Container  implements Stack<Box<?>> ,Node<Container>,Comparable<Container>{
    private Box<?>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    public Container() {
        this.boxes = new Box[DEFAULT_CAPACITY];
        this.top= -1;
        this.next = null;
        this.priority=0;
    }



    @Override
    public boolean isEmpty() {
        boolean b=true;
        for (Box<?> box : boxes) {
            if (box != null) {
                b =false;
            }
        }
        return b;
    }

    @Override
    public Box<?> peek() {
        return boxes[top];
    }

    @Override
    public int size() {
        return size;

    }

    @Override
    public boolean push(Box<?> item) {
        if (size<=DEFAULT_CAPACITY-1){
            boxes[++top]=item;
            priority += item.getPriority();
            size++;
            return true;
        }
        return false;
    }

    @Override
    public Box<?> pop() {
        if (isEmpty())
            return null;

        Box<?> box = boxes[top--];
        size--;
        return box;

    }

    @Override
    public void setNext(Container item) {
        this.next=item;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Container o) {
        return Double.compare(this.priority,o.getPriority());
    }

    @Override
    public String toString(){
        return  "Container with priority: " + priority;
    }
}

class CargoFleet implements PriorityQueue<Container>{
    private Container head;
    private int size;

    public CargoFleet() {
        this.head=null;
        this.size=0;
    }

    @Override
    public boolean isEmpty() {
        return head==null;
    }

    @Override
    public Container peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(Container item) {
        if(size ==0){
            this.head=item;
            size++;
            return true;
        } else if (size ==1) {
            if (item.compareTo(head) < 0){
                item.setNext(head);
                head = item;

            }else {
                head.setNext(item);
            }
            size++;
            return true;

        } else if (size ==2) {
            Container second = head.getNext();

            if (item.compareTo(head) < 0 ){//headden küçükse en üste koy
                item.setNext(head);
                head= item;
            }else {
                if (item.compareTo(second) < 0) {//headden büyük ikincide küçük olabilir
                    item.setNext(second);
                    head.setNext(item);
                }else{//en son
                    second.setNext(item);
                }
            }
            size++;//her türlü gerçekleşecek
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Container dequeue() {
        if (size ==0)
            return null;
        else {
            Container container = head;
            head = head.getNext();
            size--;
            return container;
        }
    }
}
class CargoCompany{
    private Container stack;
    private CargoFleet queue;

    public CargoCompany() {
        stack= new Container();
        queue= new CargoFleet();
    }

    public void add(Box<?> box){
        if (!stack.push(box)){
            if (queue.enqueue(stack)){
                stack=new Container();
                add(box);
            }else {
                ship(queue);
                add(box);
            }
        }
    }

    private void ship(CargoFleet fleet){
        while(!fleet.isEmpty()){
            Container container = fleet.dequeue();
            empty(container);
        }
    }

    private void empty(Container container){
        while (!container.isEmpty()){
            Box<?> box = container.pop();
            while(!box.isEmpty()){
                System.out.println(deliver(box));
            }
        }
    }

    private Sellable deliver(Box<?> box){
        return box.extract();
    }
}