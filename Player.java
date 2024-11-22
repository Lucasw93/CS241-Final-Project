import java.util.ArrayList;

public class Player {
    String name;
    boolean dealer;

    ArrayList<Card> hand = new ArrayList<>();

    Player(String name) {
        this.name = name;
        dealer = false;
    }

    Player() {
        dealer = true;
    }

    public void addCard(Card c) {
        hand.add(c);
    }
    
    public Card getCard(int i) {
        return hand.get(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : hand) {
            sb.append(c);
        }
        return sb.toString();
    }

    public void showHand() {
        System.out.print("Your hand is: ");
         for (Card c : hand) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
