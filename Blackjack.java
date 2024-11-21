import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;

public class Blackjack extends Card {
    protected Deque<Card> cardStack = new ArrayDeque<>();
    protected Deque<Player> playerQueue = new ArrayDeque<>();
    protected HashMap<Integer, String> cheatSheet = new HashMap<>();

    protected Player player = new Player("player1");
    protected Player dealer = new Player();

    public Blackjack(String type, char suit, int value) {
        super(type, suit, value);
        ArrayList<Card> deck = buildDeck();
        shuffle(deck);
        for (Card card : deck) {
            cardStack.push(card);
        }
        playerQueue.add(player);
        playerQueue.add(dealer);
        play();
    }

    public void play() {
        System.out.println("Welcome to the CS 241 Casino! The dealer is populating your hand and their hand now!");
        populateHands();
        System.out.printf("The dealer's hand is %s.\n", dealer.getCard(0));
        determineAction();
    }

    public void populateHands() {
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
    }

    public void determineAction() {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            player.showHand();
            System.out.println("Please enter a number for which action you want to perform:\n1) Stand\n2) Hit\n3) View Cheat Sheet");
            String uInp = in.nextLine();
            try {
                Integer.parseInt(uInp);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }
            int uInpInt = Integer.parseInt(uInp);
            if (uInpInt == 1) {

            } else if (uInpInt == 2) {
                flag = false;
            } else if (uInpInt == 3) {

            } else {
                System.out.println("Please enter a number from 1 to 3.");
                continue;
            }
        }
    }
    
    public void hit() {
        
    }
}

 
   
