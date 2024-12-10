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
            sb.append(c).append(" ");
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

    public void showDealerHand() {
        System.out.print("The dealer's hand is: ");
        for (Card c : hand) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * emptys hand
     */
    public void emptyHand() {
        hand = new ArrayList<>();
    }

    /**
     * @return current value of hand accounting for aces
     */
    public int getHandVal() {
        int sum = 0;
        for (Card c : hand)
            sum += c.getValue();

        if (sum > 21) {
            int aces = (int) hand.stream().filter(c -> c.getType().equals("A")).count();

            while (aces-- > 0 && sum > 21) {
                sum -= 10;
            }
        }
        return sum;
    }
}
