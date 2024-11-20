import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class testprint {
    public static void main(String[] args) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        Card card = new Card();
        ArrayList<Card> deck = card.buildDeck();
        for (int i = 0; i < deck.size(); i++) {
            out.println(deck.get(i).getCardChar());
        }
    }
}
