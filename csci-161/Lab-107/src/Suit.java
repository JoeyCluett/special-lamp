/**
 *
 * @author joey
 */
import java.util.*;

public class Suit {
    public static final int Diamond = 1;
    public static final int Heart   = 2;
    public static final int Spade   = 3;
    public static final int Club    = 4;
    
    public static int RandomSuit() {
        Random rand = new Random();
        return (rand.nextInt() % 4) + 1; // modulo operation gives 0 - 3, plus 1 gives 1 - 4
    }
    
}
