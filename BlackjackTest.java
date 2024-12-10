import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlackjackTest {

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
}