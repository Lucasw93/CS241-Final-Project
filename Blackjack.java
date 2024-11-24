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
        System.out.println("Welcome to the CS 241 Casino! The dealer is populating your hand and their hand now!");
        populateHands();
        System.out.printf("The dealer's hand is %s.\n", dealer.getCard(0));
        while (continuePlaying) {
            if (cardStack.size() <= 0) {
                ArrayList<Card> deck = buildDeck();
                shuffle(deck);
                for (Card card : deck) {
                    cardStack.push(card);
                }
            }
            Player currentPlayer = playerQueue.poll();
            if (currentPlayer.equals(player)) {
                continuePlaying = determineAction();
            } else {
                continuePlaying = dealerPlay();
                if (continuePlaying == true) {
                    whoWon();
                }
                continuePlaying = continuePlaying();
            }
            playerQueue.add(currentPlayer);
        }

    }

    public void populateHands() {
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
        player.addCard(cardStack.pop());
        dealer.addCard(cardStack.pop());
    }

    public boolean determineAction() {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        boolean didPlayerBust = false;
        while (flag) {
            player.showHand();
            if (calculatePlayerHandVal() < 21) {
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
            } else if (calculatePlayerHandVal() == 21) {
                flag = false;
                System.out.print("Blackjack!");
            } else {
                ArrayList<Card> playerHand = player.getHand();
                boolean flag2 = false;
                for (int i = 0; i < playerHand.size(); i++) {
                    if (playerHand.get(i).getType().equals("A")) {
                        flag2 = true;
                    }
                }
                if (flag2 == true) {
                    for (int i = 0; i < playerHand.size(); i++) {
                        if (playerHand.get(i).getType().equals("A")) {
                            playerHand.get(i).setValue(1);
                        }
                    }
                    flag2 = false;
                } else {
                    flag = false;
                    didPlayerBust = true;
                    System.out.println("You busted! Better luck next time!");
                }
            }
        }
        in.close();
        return !didPlayerBust;
    }

    public boolean dealerPlay() {
        boolean flag = true;
        boolean didDealerBust = false;
        while (flag) {
            dealer.showDealerHand();
            if (calculateDealerHandVal() < 17) {
                dealerHit();
                continue;
            } else if (calculateDealerHandVal() > 17 && calculateDealerHandVal() < 21) {
                flag = false;
            } else {
                flag = false;
                didDealerBust = true;
                System.out.println("The dealer busted. You win!");
            }
        }
        return !didDealerBust;
    }

    public void whoWon() {
        if (calculatePlayerHandVal() > calculateDealerHandVal()) {
            System.out.println("You won! Congrats!");
        } else {
            System.out.println("The dealer won. Better luck next time!");
        }
    }

    public int calculateDealerHandVal() {
        ArrayList<Card> dealerHand = dealer.getHand();
        int sum = 0;
        for (int i = 0; i < dealerHand.size(); i++) {
            sum += dealerHand.get(i).getValue();
        }
        return sum;
    }

    public int calculatePlayerHandVal() {
        ArrayList<Card> playerHand = player.getHand();
        int sum = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            sum += playerHand.get(i).getValue();
        }
        return sum;
    }
    
    public void hit() {
        player.addCard(cardStack.pop());
    }

    public void dealerHit() {
        dealer.addCard(cardStack.pop());
    }

    public boolean continuePlaying() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to play another hand? Please enter y for yes or n for no:");
            String input = in.nextLine();
            if (input.equals("y")) {
                in.close();
                return true;
            } else if (input.equals("n")) {
                in.close();
                return false;
            } else {
                System.out.println("Please enter either y or n.");
            }
        }
    }
}

 
   
