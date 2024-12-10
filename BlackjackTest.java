import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BlackjackTest {
    @Test
    public void TestAddingCard() {
        Player p = new Player();

        p.addCard(new Card("", ' ', 0));
        p.addCard(new Card("", ' ', 0));
        assertEquals(p.getHand().size(), 2);

        p.addCard(new Card("", ' ',  0));
        assertEquals(p.getHand().size(), 3);

        p.emptyHand();
        assertTrue(p.getHand().isEmpty());
    }


    @Test
    public void TestGettingCard() {
        Player p = new Player();

        p.addCard(new Card("A", '\u2660', 11));
        p.addCard(new Card("K", '\u2660', 10));
        p.addCard(new Card("Q", '\u2660', 10));
        p.addCard(new Card("2", '\u2660', 2));

        assertEquals(p.getCard(0).getType(), "A");
        assertEquals(p.getCard(0).getValue(), 11);
        assertEquals(p.getCard(1).getType(), "K");
        assertEquals(p.getCard(1).getValue(), 10);
        assertEquals(p.getCard(2).getType(), "Q");
        assertEquals(p.getCard(2).getValue(), 10);
        assertEquals(p.getCard(3).getType(), "2");
        assertEquals(p.getCard(3).getValue(), 2);
    }

    @Test
    public void TestShowHand() {
        Player p = new Player();
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        p.addCard(new Card("A", '\u2660', 11));

        p.showHand();
        assertEquals(testOut.toString().trim(), "Your hand is: \u2660A");

        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        p.addCard(new Card("K", '\u2660', 11));

        p.showHand();
        assertEquals(testOut.toString().trim(), "Your hand is: \u2660A \u2660K");
    }


    @Test
    public void TestHandVal() {
        Player p = new Player();
        p.addCard(new Card("4", ' ', 4));
        p.addCard(new Card("8", ' ', 8));
        assertEquals(p.getHandVal(), 12);

        p = new Player();
        p.addCard(new Card("A", ' ', 11));
        p.addCard(new Card("9", ' ', 9));
        p.addCard(new Card("9", ' ', 9));
        assertEquals(p.getHandVal(), 19);

        p = new Player();
        p.addCard(new Card("A", ' ', 11));
        p.addCard(new Card("A", ' ', 11));
        p.addCard(new Card("9", ' ', 9));
        p.addCard(new Card("9", ' ', 9));
        assertEquals(p.getHandVal(), 20);

        p = new Player();
        p.addCard(new Card("A", ' ', 11));
        p.addCard(new Card("K", ' ', 10));
        assertEquals(p.getHandVal(), 21);
    }

    @Test
    public void TestDeck (){
        Card c = new Card();
        ArrayList<Card> deck = c.buildDeck();
        assertEquals(deck.size(), 52);

        c.shuffle(deck);
        assertEquals(deck.size(), 52);
    }

    @Test
    public void TestIsPLayer() {
        Player P = new Player("Is player");
        assertFalse(P.dealer);
    }

    @Test
    public void TestIsDealer() {
        Player P = new Player();
        assertTrue(P.dealer);
    }
}