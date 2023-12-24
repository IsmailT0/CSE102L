import java.util.Scanner;

public class Ex1_20220808049 {
    public static void main(String[] args) {
        Stock stock = new Stock("ORCL","Oracle Corporation");
        stock.currentPrice=34.35;
        stock.previousClosingPrice=34.5;
        System.out.println(stock.getChangePercent());
        Fan fan= new Fan();
        fan.turnOn();
        System.out.println(fan);

        //Challenge part
        Scanner sc = new Scanner(System.in);
        System.out.print("How many fan object do you want? ");
        int count= sc.nextInt();
        Fan[] fans = new Fan[count];
        double radius=1;
        for (int i =0;i< fans.length;i++){
            if (i%2==0){
                fans[i]= new Fan();
            }
            else{
                fans[i]= new Fan(radius,"yellow");
            }
        }

    }
    public static void changeSpeed(Fan[] fans){
        for (int i =0;i<fans.length;i++){
            if (i%3==2){
                if (fans[i].isOn()){
                    switch (fans[i].getSpeed()){
                        case 1: fans[i].setSpeed(2);
                        case 2: fans[i].setSpeed(3);
                        case 3: fans[i].setSpeed(1);
                    }
                }
            }
        }
    }

}
class Stock{
    String symbol;
    String name;
    double previousClosingPrice;
    double currentPrice;
    Stock(String name,String symbol){
        this.name=name;
        this.symbol=symbol;
    }

    public double getChangePercent(){
        return (currentPrice-previousClosingPrice)/previousClosingPrice*100;
    }
}
class Fan{
    final static int SLOW = 1;
    final static int MEDIUM=2;
    final static int FAST=3;
    private int speed;
    private boolean on;
    private double radius;
    String color;

    Fan(){
        speed=SLOW;
        on=false;
        radius=5;
        color="blue";
    }
    Fan(double radius,String color){
        this();
        this.radius=radius;
        this.color=color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isOn() {
        return on;
    }

    public void turnOn() {
        on = true;
    }
    public void turnOff() {
        on = false;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String toString(){
        String s = switch (speed) {
            case SLOW -> "SLOW";
            case MEDIUM -> "MEDIUM";
            case FAST -> "FAST";
            default -> "";
        };
        if (on){
            return "Fan speed is "+ s + " the color is " +
                    color+" radius is " +radius;
        }
        else {
            return "Fan is off. \nFan color is "+color+ " radius is "+ radius;
        }

    }
}
