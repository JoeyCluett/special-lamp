import java.util.*; // Iterator

/**
 * @author joey
 */
public class CardHand implements Iterable {
    
    private LinkedPositionalList<Card> plist = new LinkedPositionalList<>();

    private Position<Card> pos_Diamond = null;
    private Position<Card> pos_Heart   = null;
    private Position<Card> pos_Spade   = null;
    private Position<Card> pos_Club    = null;
    
    private int num_cards = 0;

    @Override
    public String toString() {
        String ret = "CardHand[";
        Position<Card> tmp = plist.first();
        
        while(tmp != null) {
            ret += tmp.getElement().toString();
            ret += ",";
            tmp = plist.after(tmp);
        }
        ret += "]";
        
        return ret;
    }
    
    @Override
    public Iterator iterator() {
        return new card_iterator();
    }
    
    public class card_iterator implements Iterator {
        
        Position<Card> pcard;
        int suit = 0;
        boolean suit_specific;
        
        public void setPosition(Position<Card> pcard) {
            this.pcard = pcard;
            this.suit = pcard.getElement().getSuit();
        }
        
        public card_iterator() {
            setPosition(plist.first());
            suit_specific = false; // default iterates over entire hand
        }
        
        @Override
        public boolean hasNext() {
            Position<Card> card = plist.after(pcard);
            
            if(suit_specific) {
                if(card == null || card.getElement().getSuit() != suit)
                    return false;
            } else {
                if(card == null)
                    return false; // only check presence
            }
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
        
    }
    
    public void addCard(Card c) {
        int s = c.getSuit();
                
        switch(s) {
            case Suit.Diamond:
                if(pos_Diamond != null)
                    pos_Diamond = plist.addAfter(pos_Diamond, c);
                else
                    pos_Diamond = plist.addLast(c);
                break;
            case Suit.Club:
                if(pos_Club != null)
                    pos_Club = plist.addAfter(pos_Club, c);
                else
                    pos_Club = plist.addLast(c);
                break;
            case Suit.Heart:
                if(pos_Heart != null)
                    pos_Heart = plist.addAfter(pos_Heart, c);
                else
                    pos_Heart = plist.addLast(c);
                break;
            case Suit.Spade:
                if(pos_Spade != null)
                    pos_Spade = plist.addAfter(pos_Spade, c);
                else
                    pos_Spade = plist.addLast(c);
                break;
            default:
                break;
        }
    }
}
