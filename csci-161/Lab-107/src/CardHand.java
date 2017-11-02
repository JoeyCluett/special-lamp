import java.util.*; // Iterator

/**
 * @author joey
 */
public class CardHand implements Iterable {
    
    private LinkedPositionalList<Card> plist = new LinkedPositionalList<>();
    private Position<Card>[] suit_positions = (Position<Card>[])(new Object[4]);

    private int num_cards = 0;

    @Override
    public Iterator iterator() {
        return new card_iterator();
    }
    
    public class card_iterator implements Iterator {
        
        Position<Card> pcard;
        int suit = 0;
        
        public void setPosition(Position<Card> pcard) {
            this.pcard = pcard;
            this.suit = pcard.getElement().getSuit();
        }
        
        public card_iterator() {
            setPosition(plist.first());
        }
        
        @Override
        public boolean hasNext() {
            Position<Card> card = plist.after(pcard);
            if(card == null || card.getElement().getSuit() != suit)
                return false;
            return true;
        }

        @Override
        public Object next() {
            Object o = pcard.getElement();
            pcard = plist.after(pcard);
            return o;
        }
    }
    
    public CardHand() {
        for(int i = 0; i < 4; i++)
            suit_positions[i] = null; // an error check as well as a position
    }
    
    public void addCard(Card c) {
        int s = c.getSuit();
        
        if(suit_positions[s-1] != null) {
            suit_positions[s-1] = plist.addAfter(suit_positions[s-1], c);
        } else {
            suit_positions[s-1] = plist.addLast(c);
        }
    }
    
    
    
}
