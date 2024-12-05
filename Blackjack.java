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
        boolean continuePlaying = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the CS 241 Casino! The dealer is populating your hand and their hand now!");
        while (continuePlaying) {
            resetHands();
            if (cardStack.size() <= 3) {
                ArrayList<Card> deck = buildDeck();
                shuffle(deck);
                for (Card card : deck) {
                    cardStack.push(card);
                }
            }
            populateHands();
            if (playerQueue.peek().equals(dealer)) {
                Player tmp = playerQueue.poll();
                playerQueue.add(tmp);
            }
            System.out.printf("The dealer's hand is %s.\n", dealer.getCard(0));
            boolean inHand = true;
            while (inHand) {
                Player currentPlayer = playerQueue.poll();
                if (currentPlayer.equals(player)) {
                    inHand = determineAction(scan);
                    if (inHand == false) {
                        playerQueue.add(currentPlayer);
                        break;
                    }
                } else {
                    inHand = dealerPlay();
                    if (inHand == true) {
                        whoWon();
                        inHand = false;
                    }
                }
                playerQueue.add(currentPlayer);
            }
            continuePlaying = continuePlaying(scan);
        }
    }

    public void populateHands() {
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
    }

    public void resetHands() {
        for (Player p : playerQueue)
            p.emptyHand();
    }

    public boolean determineAction(Scanner in) {
        boolean flag = true;
        boolean didPlayerBust = false;
        while (flag) {
            player.showHand();
            int handVal = player.getHandVal();
            if (handVal < 21) {
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
                    flag = false;
                } else if (uInpInt == 2) {
                    hit();
                    continue;
                } else if (uInpInt == 3) {

                } else {
                    System.out.println("Please enter a number from 1 to 3.");
                    continue;
                }
            } else if (handVal == 21) {
                flag = false;
                System.out.println("Blackjack!");
            } else {
                flag = false;
                didPlayerBust = true;
                System.out.println("You busted! Better luck next time!");
            }
        }
        return !didPlayerBust;
    }

    public boolean dealerPlay() {
        boolean flag = true;
        boolean didDealerBust = false;
        while (flag) {
            dealer.showDealerHand();
            int handVal = dealer.getHandVal();
            if (handVal < 17) {
                dealerHit();
                continue;
            } else if (handVal >= 17 && handVal < 21) {
                flag = false;
            } else if (handVal == 21) {
                flag = false;
                System.out.println("The dealer has Blackjack!");
            } else {
                flag = false;
                didDealerBust = true;
                System.out.println("The dealer busted. You win!");
            }
        }
        return !didDealerBust;
    }

    public void whoWon() {
        int dVal = dealer.getHandVal();
        int pVal = player.getHandVal();
        System.out.printf("Let's see who won. Your hand is: %s. The dealer's hand is: %s.\n", player.toString(), dealer.toString());
        if (pVal > dVal) {
            System.out.println("You won! Congrats!");
        } else if (dVal == pVal) {
            System.out.println("You and the dealer both had the same hand. It's a tie!");
        } else {
            System.out.println("The dealer won. Better luck next time!");
        }
    }

    public void hit() {
        player.addCard(cardStack.pop());
    }

    public void dealerHit() {
        dealer.addCard(cardStack.pop());
    }

    public boolean continuePlaying(Scanner in) {
        while (true) {
            System.out.println("Do you want to play another hand? Please enter y for yes or n for no:");
            String input = in.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Please enter either y or n.");
            }
        }
    }
}

 
   
