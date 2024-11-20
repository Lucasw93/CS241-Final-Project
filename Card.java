import java.util.ArrayList;
import java.util.Collections;

public class Card {
    protected String type;
    protected char suit;
    protected int value;

    public Card() {}

    public Card(String type, char suit, int value) {
        this.type = type;
        this.suit = suit;
        this.value = value;
    }

    //spade = u2660; heart = u2665; diamond = u2666; club = u2663;
    public ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card("A", '\u2660', 11));
        deck.add(new Card("A", '\u2665', 11));
        deck.add(new Card("A", '\u2666', 11));
        deck.add(new Card("A", '\u2663', 11));
        deck.add(new Card("2", '\u2660', 2));
        deck.add(new Card("2", '\u2665', 2));
        deck.add(new Card("2", '\u2666', 2));
        deck.add(new Card("2", '\u2663', 2));
        deck.add(new Card("3", '\u2660', 3));
        deck.add(new Card("3", '\u2665', 3));
        deck.add(new Card("3", '\u2666', 3));
        deck.add(new Card("3", '\u2663', 3));
        deck.add(new Card("4", '\u2660', 4));
        deck.add(new Card("4", '\u2665', 4));
        deck.add(new Card("4", '\u2666', 4));
        deck.add(new Card("4", '\u2663', 4));
        deck.add(new Card("5", '\u2660', 5));
        deck.add(new Card("5", '\u2665', 5));
        deck.add(new Card("5", '\u2666', 5));
        deck.add(new Card("5", '\u2663', 5));
        deck.add(new Card("6", '\u2660', 6));
        deck.add(new Card("6", '\u2665', 6));
        deck.add(new Card("6", '\u2666', 6));
        deck.add(new Card("6", '\u2663', 6));
        deck.add(new Card("7", '\u2660', 7));
        deck.add(new Card("7", '\u2665', 7));
        deck.add(new Card("7", '\u2666', 7));
        deck.add(new Card("7", '\u2663', 7));
        deck.add(new Card("8", '\u2660', 8));
        deck.add(new Card("8", '\u2665', 8));
        deck.add(new Card("8", '\u2666', 8));
        deck.add(new Card("8", '\u2663', 8));
        deck.add(new Card("9", '\u2660', 9));
        deck.add(new Card("9", '\u2665', 9));
        deck.add(new Card("9", '\u2666', 9));
        deck.add(new Card("9", '\u2663', 9));
        deck.add(new Card("10", '\u2660', 10));
        deck.add(new Card("10", '\u2665', 10));
        deck.add(new Card("10", '\u2666', 10));
        deck.add(new Card("10", '\u2663', 10));
        deck.add(new Card("J", '\u2660', 10));
        deck.add(new Card("J", '\u2665', 10));
        deck.add(new Card("J", '\u2666', 10));
        deck.add(new Card("J", '\u2663', 10));
        deck.add(new Card("Q", '\u2660', 10));
        deck.add(new Card("Q", '\u2665', 10));
        deck.add(new Card("Q", '\u2666', 10));
        deck.add(new Card("Q", '\u2663', 10));
        deck.add(new Card("K", '\u2660', 10));
        deck.add(new Card("K", '\u2665', 10));
        deck.add(new Card("K", '\u2666', 10));
        deck.add(new Card("K", '\u2663', 10));
        return deck;
    }

    public void shuffle(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public String toString() {
        return Character.toString(this.suit) + this.type;
    }
}