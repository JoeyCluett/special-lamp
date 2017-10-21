import java.util.*;

/**
 *
 * @author joe
 */
public class Client {
    // test
    public static void main(String[] args) {
        ArrayDoubleStack<Integer> ads = new ArrayDoubleStack<>(500, 1500);
        
        Random rand = new Random();

        boolean result;
        
        do {
            result = ads.bluePush(rand.nextInt()); // push random integer onto Blue stack
        } while(result);
        
        do {
            result = ads.redPush(rand.nextInt());
        } while(result);
        
        System.out.println("Blue stack size: " + ads.blueSize());
        System.out.println("Red stack size:  " + ads.redSize());
        
        Integer i = 0;
        
        do {
            i = ads.redPop();
            System.out.println("Red pop: " + i);
        } while(i != null);
        
    }
}
