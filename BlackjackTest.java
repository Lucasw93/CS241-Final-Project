import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

public class BlackjackTest {

    @Test 
    public void NullTest() {
        assertNotNull(new Player());
        assertNotNull(new Card());
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
    public void TestDeck() {
        Card C = new Card();
        ArrayList <Card> A = C.buildDeck();
        assertEquals(A.size(), 52);
       
    }
    
    @Test
    public void TestIsPlayer() {
        Player P = new Player("Is player");
        assertEquals(P.dealer, false);

    }

    @Test
    public void TestIsDealer() {
        Player P = new Player();
        assertEquals(P.dealer, true);

    }
}