import java.util.ArrayList;
import java.util.Collections;

public class Card {
    protected char unicodeCard;
    protected int value;

    public Card() {}

    public Card(char unicodeCard, int value) {
        this.unicodeCard = unicodeCard;
        this.value = value;
    }

    public ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card('\uF0A1', 11));
        deck.add(new Card('\uF0B1', 11));
        deck.add(new Card('\uF0C1', 11));
        deck.add(new Card('\uF0D1', 11));
        deck.add(new Card('\uF0A2', 2));
        deck.add(new Card('\uF0B2', 2));
        deck.add(new Card('\uF0C2', 2));
        deck.add(new Card('\uF0D2', 2));
        deck.add(new Card('\uF0A3', 3));
        deck.add(new Card('\uF0B3', 3));
        deck.add(new Card('\uF0C3', 3));
        deck.add(new Card('\uF0D3', 3));
        deck.add(new Card('\uF0A4', 4));
        deck.add(new Card('\uF0B4', 4));
        deck.add(new Card('\uF0C4', 4));
        deck.add(new Card('\uF0D4', 4));
        deck.add(new Card('\uF0A5', 5));
        deck.add(new Card('\uF0B5', 5));
        deck.add(new Card('\uF0C5', 5));
        deck.add(new Card('\uF0D5', 5));
        deck.add(new Card('\uF0A6', 6));
        deck.add(new Card('\uF0B6', 6));
        deck.add(new Card('\uF0C6', 6));
        deck.add(new Card('\uF0D6', 6));
        deck.add(new Card('\uF0A7', 7));
        deck.add(new Card('\uF0B7', 7));
        deck.add(new Card('\uF0C7', 7));
        deck.add(new Card('\uF0D7', 7));
        deck.add(new Card('\uF0A8', 8));
        deck.add(new Card('\uF0B8', 8));
        deck.add(new Card('\uF0C8', 8));
        deck.add(new Card('\uF0D8', 8));
        deck.add(new Card('\uF0A9', 9));
        deck.add(new Card('\uF0B9', 9));
        deck.add(new Card('\uF0C9', 9));
        deck.add(new Card('\uF0D9', 9));
        deck.add(new Card('\uF0AA', 10));
        deck.add(new Card('\uF0BA', 10));
        deck.add(new Card('\uF0CA', 10));
        deck.add(new Card('\uF0DA', 10));
        deck.add(new Card('\uF0AB', 10));
        deck.add(new Card('\uF0BB', 10));
        deck.add(new Card('\uF0CB', 10));
        deck.add(new Card('\uF0DB', 10));
        deck.add(new Card('\uF0AD', 10));
        deck.add(new Card('\uF0BD', 10));
        deck.add(new Card('\uF0CD', 10));
        deck.add(new Card('\uF0DD', 10));
        deck.add(new Card('\uF0AE', 10));
        deck.add(new Card('\uF0BE', 10));
        deck.add(new Card('\uF0CE', 10));
        deck.add(new Card('\uF0DE', 10));
        return deck;
    }

    public void shuffle(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    public char getCardChar() {
        return this.unicodeCard;
    }
}