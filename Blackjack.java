import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;

public class Blackjack extends Card {
    protected Deque<Card> cardStack = new ArrayDeque<>();
    protected Deque<Player> playerQueue = new ArrayDeque<>();
    protected HashMap<String, String> cheatSheet = new HashMap<>();

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
                    viewCheatSheet();
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

    public void viewCheatSheet() {
        BlackjackCheatSheet();
        if (player.getHand().isEmpty()) {
            System.out.println("Your hand is empty!");
            return;
        }

        int playerTotal = player.getHandVal();
        String dealerCard = dealer.getCard(0).getType();
        String advice = "standing";

        String key = playerTotal + "-" + dealerCard;
        System.out.println(key);
        if (cheatSheet.containsKey(key)) {
            advice = cheatSheet.get(key);
        }
        System.out.printf("Based on the cheat sheet, your hand value of %d against the dealer's %s suggests %s.\n", playerTotal, dealerCard, advice);
    }

    public void BlackjackCheatSheet() {
        cheatSheet.put("8-2", "hitting");
        cheatSheet.put("8-3", "hitting");
        cheatSheet.put("8-4", "hitting");
        cheatSheet.put("8-5", "hitting");
        cheatSheet.put("8-6", "hitting");
        cheatSheet.put("8-7", "hitting");
        cheatSheet.put("8-8", "hitting");
        cheatSheet.put("8-9", "hitting");
        cheatSheet.put("8-10", "hitting");
        cheatSheet.put("8-A", "hitting");
        cheatSheet.put("9-2", "hitting");
        cheatSheet.put("9-3", "hitting");
        cheatSheet.put("9-4", "hitting");
        cheatSheet.put("9-5", "hitting");
        cheatSheet.put("9-6", "hitting");
        cheatSheet.put("9-7", "hitting");
        cheatSheet.put("9-8", "hitting");
        cheatSheet.put("9-9", "hitting");
        cheatSheet.put("9-10", "hitting");
        cheatSheet.put("9-A", "hitting");
        cheatSheet.put("10-2", "hitting");
        cheatSheet.put("10-3", "hitting");
        cheatSheet.put("10-4", "hitting");
        cheatSheet.put("10-5", "hitting");
        cheatSheet.put("10-6", "hitting");
        cheatSheet.put("10-7", "hitting");
        cheatSheet.put("10-8", "hitting");
        cheatSheet.put("10-9", "hitting");
        cheatSheet.put("10-10", "hitting");
        cheatSheet.put("10-A", "hitting");
        cheatSheet.put("11-2", "hitting");
        cheatSheet.put("11-3", "hitting");
        cheatSheet.put("11-4", "hitting");
        cheatSheet.put("11-5", "hitting");
        cheatSheet.put("11-6", "hitting");
        cheatSheet.put("11-7", "hitting");
        cheatSheet.put("11-8", "hitting");
        cheatSheet.put("11-9", "hitting");
        cheatSheet.put("11-10", "hitting");
        cheatSheet.put("12-2", "hitting");
        cheatSheet.put("12-3", "hitting");
        cheatSheet.put("12-4", "standing");
        cheatSheet.put("12-5", "standing");
        cheatSheet.put("12-6", "standing");
        cheatSheet.put("12-7", "hitting");
        cheatSheet.put("12-8", "hitting");
        cheatSheet.put("12-9", "hitting");
        cheatSheet.put("12-10", "hitting");
        cheatSheet.put("12-A", "hitting");
        cheatSheet.put("13-2", "standing");
        cheatSheet.put("13-3", "standing");
        cheatSheet.put("13-4", "standing");
        cheatSheet.put("13-5", "standing");
        cheatSheet.put("13-6", "standing");
        cheatSheet.put("13-7", "hitting");
        cheatSheet.put("13-8", "hitting");
        cheatSheet.put("13-9", "hitting");
        cheatSheet.put("13-10", "hitting");
        cheatSheet.put("13-A", "hitting");
        cheatSheet.put("14-2", "standing");
        cheatSheet.put("14-3", "standing");
        cheatSheet.put("14-4", "standing");
        cheatSheet.put("14-5", "standing");
        cheatSheet.put("14-6", "standing");
        cheatSheet.put("14-7", "hitting");
        cheatSheet.put("14-8", "hitting");
        cheatSheet.put("14-9", "hitting");
        cheatSheet.put("14-10", "hitting");
        cheatSheet.put("14-A", "hitting");
        cheatSheet.put("15-2", "standing");
        cheatSheet.put("15-3", "standing");
        cheatSheet.put("15-4", "standing");
        cheatSheet.put("15-5", "standing");
        cheatSheet.put("15-6", "standing");
        cheatSheet.put("15-7", "hitting");
        cheatSheet.put("15-8", "hitting");
        cheatSheet.put("15-9", "hitting");
        cheatSheet.put("15-10", "hitting");
        cheatSheet.put("15-A", "hitting");
        cheatSheet.put("16-2", "standing");
        cheatSheet.put("16-3", "standing");
        cheatSheet.put("16-4", "standing");
        cheatSheet.put("16-5", "standing");
        cheatSheet.put("16-6", "standing");
        cheatSheet.put("16-7", "hitting");
        cheatSheet.put("16-8", "hitting");
        cheatSheet.put("16-9", "hitting");
        cheatSheet.put("16-10", "hitting");
        cheatSheet.put("16-A", "hitting");
        cheatSheet.put("17-2", "standing");
        cheatSheet.put("17-3", "standing");
        cheatSheet.put("17-4", "standing");
        cheatSheet.put("17-5", "standing");
        cheatSheet.put("17-6", "standing");
        cheatSheet.put("17-7", "standing");
        cheatSheet.put("17-9", "standing");
        cheatSheet.put("17-10", "standing");
        cheatSheet.put("17-A", "standing");
        cheatSheet.put("A,2-2", "hitting");
        cheatSheet.put("A,2-3", "hitting");
        cheatSheet.put("A,2-4", "hitting");
        cheatSheet.put("A,2-5", "hitting");
        cheatSheet.put("A,2-6", "hitting");
        cheatSheet.put("A,2-7", "hitting");
        cheatSheet.put("A,2-A", "hitting");
        cheatSheet.put("A,2-9", "hitting");
        cheatSheet.put("A,2-10", "hitting");
        cheatSheet.put("A,2-A", "hitting");
        cheatSheet.put("A,3-2", "hitting");
        cheatSheet.put("A,3-3", "hitting");
        cheatSheet.put("A,3-4", "hitting");
        cheatSheet.put("A,3-5", "hitting");
        cheatSheet.put("A,3-6", "hitting");
        cheatSheet.put("A,3-7", "hitting");
        cheatSheet.put("A,3-A", "hitting");
        cheatSheet.put("A,3-9", "hitting");
        cheatSheet.put("A,3-10", "hitting");
        cheatSheet.put("A,3-A", "hitting");
        cheatSheet.put("A,4-2", "hitting");
        cheatSheet.put("A,4-3", "hitting");
        cheatSheet.put("A,4-4", "hitting");
        cheatSheet.put("A,4-5", "hitting");
        cheatSheet.put("A,4-6", "hitting");
        cheatSheet.put("A,4-7", "hitting");
        cheatSheet.put("A,4-A", "hitting");
        cheatSheet.put("A,4-9", "hitting");
        cheatSheet.put("A,4-10", "hitting");
        cheatSheet.put("A,4-A", "hitting");
        cheatSheet.put("A,5-2", "hitting");
        cheatSheet.put("A,5-3", "hitting");
        cheatSheet.put("A,5-4", "hitting");
        cheatSheet.put("A,5-5", "hitting");
        cheatSheet.put("A,5-6", "hitting");
        cheatSheet.put("A,5-7", "hitting");
        cheatSheet.put("A,5-A", "hitting");
        cheatSheet.put("A,5-9", "hitting");
        cheatSheet.put("A,5-10", "hitting");
        cheatSheet.put("A,5-A", "hitting");
        cheatSheet.put("A,6-2", "hitting");
        cheatSheet.put("A,6-3", "hitting");
        cheatSheet.put("A,6-4", "hitting");
        cheatSheet.put("A,6-5", "hitting");
        cheatSheet.put("A,6-6", "hitting");
        cheatSheet.put("A,6-7", "hitting");
        cheatSheet.put("A,6-A", "hitting");
        cheatSheet.put("A,6-9", "hitting");
        cheatSheet.put("A,6-10", "hitting");
        cheatSheet.put("A,6-A", "hitting");
        cheatSheet.put("A,7-2", "standing");
        cheatSheet.put("A,7-3", "standing");
        cheatSheet.put("A,7-4", "standing");
        cheatSheet.put("A,7-5", "standing");
        cheatSheet.put("A,7-6", "standing");
        cheatSheet.put("A,7-7", "standing");
        cheatSheet.put("A,7-8", "standing");
        cheatSheet.put("A,7-9", "hitting");
        cheatSheet.put("A,7-10", "hitting");
        cheatSheet.put("A,7-A", "hitting");
        cheatSheet.put("A,8-2", "standing");
        cheatSheet.put("A,8-3", "standing");
        cheatSheet.put("A,8-4", "standing");
        cheatSheet.put("A,8-5", "standing");
        cheatSheet.put("A,8-6", "standing");
        cheatSheet.put("A,8-7", "standing");
        cheatSheet.put("A,8-9", "standing");
        cheatSheet.put("A,8-10", "standing");
        cheatSheet.put("A,8-A", "standing");
        cheatSheet.put("2,2-2", "hitting");
        cheatSheet.put("2,2-3", "hitting");
        cheatSheet.put("2,2-4", "hitting or standing");
        cheatSheet.put("2,2-5", "hitting or standing");
        cheatSheet.put("2,2-6", "hitting or standing");
        cheatSheet.put("2,2-7", "hitting or standing");
        cheatSheet.put("2,2-8", "hitting");
        cheatSheet.put("2,2-9", "hitting");
        cheatSheet.put("2,2-10", "hitting");
        cheatSheet.put("2,2-A", "hitting");
        cheatSheet.put("3,3-2", "hitting");
        cheatSheet.put("3,3-3", "hitting");
        cheatSheet.put("3,3-4", "hitting or standing");
        cheatSheet.put("3,3-5", "hitting or standing");
        cheatSheet.put("3,3-6", "hitting or standing");
        cheatSheet.put("3,3-7", "hitting or standing");
        cheatSheet.put("3,3-8", "hitting");
        cheatSheet.put("3,3-9", "hitting");
        cheatSheet.put("3,3-10", "hitting");
        cheatSheet.put("3,3-A", "hitting");
        cheatSheet.put("4,4-2", "hitting");
        cheatSheet.put("4,4-3", "hitting");
        cheatSheet.put("4,4-4", "hitting");
        cheatSheet.put("4,4-5", "hitting");
        cheatSheet.put("4,4-6", "hitting");
        cheatSheet.put("4,4-7", "hitting or standing");
        cheatSheet.put("4,4-8", "hitting");
        cheatSheet.put("4,4-9", "hitting");
        cheatSheet.put("4,4-10", "hitting");
        cheatSheet.put("5,5-A", "hitting");
        cheatSheet.put("5,5-2", "hitting");
        cheatSheet.put("5,5-3", "hitting");
        cheatSheet.put("5,5-4", "hitting");
        cheatSheet.put("5,5-5", "hitting");
        cheatSheet.put("5,5-6", "hitting");
        cheatSheet.put("5,5-7", "hitting");
        cheatSheet.put("5,5-8", "hitting");
        cheatSheet.put("5,5-9", "hitting");
        cheatSheet.put("5,5-10", "hitting");
        cheatSheet.put("5,5-A", "hitting");
        cheatSheet.put("6,6-2", "hitting");
        cheatSheet.put("6,6-3", "hitting or standing");
        cheatSheet.put("6,6-4", "hitting or standing");
        cheatSheet.put("6,6-5", "hitting or standing");
        cheatSheet.put("6,6-6", "hitting or standing");
        cheatSheet.put("6,6-7", "hitting");
        cheatSheet.put("6,6-8", "hitting");
        cheatSheet.put("6,6-9", "hitting");
        cheatSheet.put("6,6-10", "hitting");
        cheatSheet.put("7,7-2", "hitting or standing");
        cheatSheet.put("7,7-3", "hitting or standing");
        cheatSheet.put("7,7-4", "hitting or standing");
        cheatSheet.put("7,7-5", "hitting or standing");
        cheatSheet.put("7,7-6", "hitting or standing");
        cheatSheet.put("7,7-7", "hitting or standing");
        cheatSheet.put("7,7-8", "hitting");
        cheatSheet.put("7,7-9", "hitting");
        cheatSheet.put("7,7-10", "hitting");
        cheatSheet.put("7,7-A", "hitting");
        cheatSheet.put("8,8-2", "hitting or standing");
        cheatSheet.put("8,8-3", "hitting or standing");
        cheatSheet.put("8,8-4", "hitting or standing");
        cheatSheet.put("8,8-5", "hitting or standing");
        cheatSheet.put("8,8-6", "hitting or standing");
        cheatSheet.put("8,8-7", "hitting or standing");
        cheatSheet.put("8,8-8", "hitting or standing");
        cheatSheet.put("8,8-9", "hitting or standing");
        cheatSheet.put("8,8-10", "hitting or standing");
        cheatSheet.put("8,8-A", "hitting or standing");
        cheatSheet.put("9,9-2", "hitting or standing");
        cheatSheet.put("9,9-3", "hitting or standing");
        cheatSheet.put("9,9-4", "hitting or standing");
        cheatSheet.put("9,9-5", "hitting or standing");
        cheatSheet.put("9,9-6", "hitting or standing");
        cheatSheet.put("9,9-7", "standing");
        cheatSheet.put("9,9-8", "hitting or standing");
        cheatSheet.put("9,9-9", "hitting or standing");
        cheatSheet.put("9,9-10", "standing");
        cheatSheet.put("9,9-A", "standing");
        cheatSheet.put("10,10-2", "standing");
        cheatSheet.put("10,10-3", "standing");
        cheatSheet.put("10,10-4", "standing");
        cheatSheet.put("10,10-5", "standing");
        cheatSheet.put("10,10-6", "standing");
        cheatSheet.put("10,10-7", "standing");
        cheatSheet.put("10,10-8", "standing");
        cheatSheet.put("10,10-9", "standing");
        cheatSheet.put("10,10-10", "standing");
        cheatSheet.put("10,10-A", "standing");
        cheatSheet.put("A,A-2", "hitting or standing");
        cheatSheet.put("A,A-3", "hitting or standing");
        cheatSheet.put("A,A-4", "hitting or standing");
        cheatSheet.put("A,A-5", "hitting or standing");
        cheatSheet.put("A,A-6", "hitting or standing");
        cheatSheet.put("A,A-7", "hitting or standing");
        cheatSheet.put("A,A-8", "hitting or standing");
        cheatSheet.put("A,A-9", "hitting or standing");
        cheatSheet.put("A,A-10", "hitting or standing");
        cheatSheet.put("A,A-A", "hitting or standing");
    }
}
