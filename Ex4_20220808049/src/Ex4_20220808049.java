import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex4_20220808049 {
    public static void main(String[] args) {

    }
}
class Computer{
    protected CPU cpu;
    protected RAM ram;


    //Constructor
    Computer(CPU cpu,RAM ram){
        this.cpu=cpu;
        this.ram=ram;
    }

    public void run(){
        int sum=0;
        for (int i =0;i< ram.getCapacity();i++)
            sum= cpu.compute(sum, ram.getValue(i,i));
        ram.setValue(0,0,sum);
    }

    @Override
    public String toString(){
        return "Computer:" + cpu + " " + ram;
    }
}

class Laptop extends Computer{
    private int milliAmp;
    private int battery;

    //Constructor
    Laptop(CPU cpu, RAM ram,int milliAmp){
        super(cpu,ram);
        this.milliAmp=milliAmp;
        this.battery= ((milliAmp/10)*3);
    }

    //shows remaining battery
    public int batteryPercentage(){return ((battery*100)/milliAmp);}

    //increase the battery by two until 90
    public void charge(){
        while (batteryPercentage()<90)
            battery+=milliAmp/50;
    }

    @Override//
    public void run(){
        if (batteryPercentage()>5)
            battery-=(milliAmp/100*3);
        else
            this.charge();
    }

    @Override
    public String toString(){
        return super.toString()+ " " +battery;
    }
}

class Desktop extends Computer{
    java.util.ArrayList<String> peripherals;

    Desktop(CPU cpu, RAM ram, String... peripherals){
        super(cpu,ram);
        this.peripherals= new ArrayList<>(Arrays.asList(peripherals));

    }

    @Override
    public void run(){
        int sum =0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            for (int j = 0; j < ram.getCapacity() ; j++) {
                sum=cpu.compute(sum,ram.getValue(i,j));
            }
        }
        ram.setValue(0,0,sum);
    }

    public void plugIn(String peripheral){peripherals.add(peripheral);}

    public String plugOut(){ return plugOut(peripherals.size()-1);}

    public String plugOut(int index){return peripherals.remove(index);}

    @Override
    public String toString(){
        String st = super.toString();

        for (int i = 0; i < peripherals.size(); i++) {
            st+=" ";
            st+=peripherals.get(i);
        }
        return st;
    }


}

class CPU{
    private String name;
    private double clock;

    //Constructor
    public CPU(String name,double clock){
        this.name=name;
        this.clock=clock;
    }

    //accessor of name
    public String getName() {return name;}

    //accessor of clock
    public double getClock() {return clock;}

    public int compute(int a , int b){return (a+b);}

    @Override
    public String toString(){
        return "CPU: " + getName()  +" "+getClock()+"Ghz";
    }
}
class RAM{
    private String type;
    private int capacity;
    private int[][] memory;

    public RAM(String type,int capacity){
        this.type=type;
        this.capacity=capacity;
        initMemory();
    }

    public String getType() {return type;}

    public int getCapacity() {return capacity;}

    //creates a memory as a matrix
    private void initMemory(){
        memory= new int[capacity][capacity];
        Random rd = new Random();
        for (int i = 0; i < memory.length ; i++) {
            for (int j = 0; j < memory.length; j++) {
                memory[i][j]= rd.nextInt(11);
            }
        }
    }

    //checks if given values are out of the array's bound
    private boolean check (int i , int j){
        int bound=getCapacity();
        return i < bound && j < bound;
    }

    public int getValue(int i , int j){
        if (check(i,j))
            return -1;
        else
            return memory[i][j];
    }

    public void setValue(int i , int j , int value){
        if (check(i,j))
            memory[i][j]=value;
    }

    @Override
    public String toString(){return "RAM: " +type+ " " + capacity+"GB";}
}