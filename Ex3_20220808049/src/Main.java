import java.util.Date;

public class Main {
    public static void main(String[] args) {

        java.util.Date date1= new Date();
        long a = 24*60*60*1000;
        java.util.Date date= new Date(date1.getDate()+a);
    }
}