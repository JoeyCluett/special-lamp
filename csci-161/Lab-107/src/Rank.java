/**
 *
 * @author joey
 */
import java.util.*;

public class Rank {
    public static final int Ace   = 1;
    public static final int Two   = 2;
    public static final int Three = 3;
    public static final int Four  = 4;
    public static final int Five  = 5;
    public static final int Six   = 6;
    public static final int Seven = 7;
    public static final int Eight = 8;
    public static final int Nine  = 9;
    public static final int Ten   = 10;
    public static final int Jack  = 11;
    public static final int Queen = 12;
    public static final int King  = 13;
    
    public static int RandomRank() {
        Random rand = new Random();
        return (rand.nextInt() % 13) + 1;
    }
    
}
