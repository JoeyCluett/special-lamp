/**
 *
 * @author joe
 */
public class Card {
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    private int suit, rank;

    int getSuit() { return suit; }
    int getRank() { return rank; }
    
    void setSuit(int suit) { this.suit = suit; }
    void setRank(int rank) { this.rank = rank; }
}