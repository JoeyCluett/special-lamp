import java.util.*; // Random

/**
 * @author joe
 */
public class Client {
    // test
    public static void main(String[] args) {
        //testDoubleStack();
        testLeakyStack();
    }
    
    public static void testLeakyStack() {
        ArrayLeakyStack<Integer> als = new ArrayLeakyStack<>(6);
        
        // add more data than the stack has room for
        System.out.println("Adding data...");
        for(int i = 0; i < 10; i++) { // 10 > 6
            als.push(i);
            System.out.println("    ALS: " + als);
        }
        
        // remove some but not all data
        System.out.println("Removing data...");
        for(int i = 0; i < 3; i++) {
            als.pop();
            System.out.println("    ALS: " + als);
        }
        
        // add some more data
        System.out.println("Adding more data...");
        for(int i = 0; i < 10; i++) {
            als.push(i+13);
            System.out.println("    ALS: " + als);
        }
    }
    
    public static void testDoubleStack() {
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
