/**
 *
 * @author joey
 */
public class Client {
    public static void main(String[] args) {
        CardHand ch = new CardHand();
        
        for(int i = 0; i < 0; i++) {
            ch.addCard(new Card(Rank.RandomRank(), Suit.RandomSuit()));
        }
        
        for(Object o : ch) {
            Card c = (Card)o;
            System.out.println(c);
        }
        
        System.out.println("" + ch);
    }
}
