import java.util.Date;


public class Ex2_20220808049 {
    public static void main(String[] args) {
        City istanbul = new City("34000", "Istanbul");
        City ankara = new City("06000", "Ankara");

        Person person1 = new Person("John", "Doe", "+1 555 1234");
        Person person2 = new Person("Jane", "Doe", "+1 555 5678");

        Tickets ticket1 = new Tickets(istanbul, ankara, new Date(), 42);
        Tickets ticket2 = new Tickets(ankara, istanbul, 23);

        System.out.println("Ticket 1: " + ticket1.getFrom().getName() + " to " + ticket1.getTo().getName() +
                " on " + ticket1.getDate() + ", seat " + ticket1.getSeat());
        System.out.println("Ticket 2: " + ticket2.getFrom().getName() + " to " + ticket2.getTo().getName() +
                " on " + ticket2.getDate() + ", seat " + ticket2.getSeat());

        System.out.println("Passenger 1: " + person1.getName() + " " + person1.getSurname() +
                ", phone number: " + person1.getPhoneNumber());
        System.out.println("Passenger 2: " + person2.getName() + " " + person2.getSurname() +
                ", phone number: " + person2.getPhoneNumber());
    }

}

class Tickets{
    private City from;
    private City to;
    private Date date;
    private int seat;

    Tickets(City from,City to,Date date,int seat){
        this.from=from;
        this.to=to;
        this.date=date;
        this.seat=seat;
    }

    Tickets(City from,City to,int seat){
        this.from=from;
        this.to=to;
        this.seat=seat;
        Date dateToday= new Date();
        long oneDay=24*60*60*1000;
        this.date=new Date(dateToday.getTime() + oneDay);
    }

    public City getFrom() {return from;}

    public City getTo() {return to;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public int getSeat() {return seat;}
    public void setSeat(int seat){this.seat=seat;}
}
class City{
    private String postalCode;
    private String name;

    City(String postalCode,String name){
        this.name=name;
        this.postalCode=postalCode;
    }

    public String getPostalCode() {return postalCode;}

    public String getName() {return name;}

}
class Person{
    private String name;
    private String surname;
    private String phoneNumber;

    Person(String name,String surname,String phoneNumber){
        this.name=name;
        this.surname=surname;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;}
}

