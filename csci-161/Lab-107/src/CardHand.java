/**
 * @author joey
 */
public class CardHand {
    
    private class Card {
        public Card(int rank, int suit) {
            this.rank = rank;
            this.suit = suit;
        }
        
        public int suit, rank;
    }
    
    private class CardList {
        private Node head = null;
        private int num_cards = 0;
        
        // only constructor does nothing
        public CardList() { }
        
        private class Node {
            public Node(Card card, Node next) {
                this.next = next;
                this.card.suit = card.suit;
                this.card.rank = card.rank;
            }
            
            Node next;
            Card card; // rank or suit of 0 is invalid
        }
    
        public void addCard(Card card) {
            head = new Node(card, head); // insertion in constant time
        }
    }
    
    CardList cl_heart   = new CardList();
    CardList cl_diamond = new CardList();
    CardList cl_spade   = new CardList();
    CardList cl_club    = new CardList();
    
    /**
     * @param rank rank of card to add to hand
     * @param suit suit of card to add to hand
     */
    public void addCard(int rank, int suit) {
        switch(suit) {
            case Suit.Club:
                cl_club.addCard(new Card(rank, suit));
                break;
            case Suit.Diamond:
                cl_diamond.addCard(new Card(rank, suit));
                break;
            case Suit.Heart:
                cl_heart.addCard(new Card(rank, suit));
                break;
            case Suit.Spade:
                cl_spade.addCard(new Card(rank, suit));
                break;
            default:
                break;
        }
    }
}
