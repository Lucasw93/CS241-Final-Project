import java.util.ArrayList;

// DONT FORGET TO RUN chcp 65001 IN TERMINAL BEFORE PRINTING ANYTHING
public class test {
    public static void main(String[] args) {
        Card card = new Card();
        ArrayList<Card> deck = card.buildDeck();
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }
        System.out.println("=======================");
        card.shuffle(deck);
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }
    }
}
