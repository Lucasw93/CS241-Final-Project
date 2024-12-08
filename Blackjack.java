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
        System.out.println("Purchase CS 241 Casino Premium to unlock unavailable options...");

        System.out.println(
                "Cheat Sheet suggestion values: \nH = Hit\nS = Stand\nP = Split\nD/H = Double down if possible otherwise Hit\nD/S = Double down if possible otherwise Stand\nP/H = Split if double down after split is possible otherwise Hit\nR/H = Surrender if possible otherwise Hit\n\n");
        while (continuePlaying) {
            resetHands();
            populateHands();
            if (playerQueue.peek().equals(dealer)) {
                Player tmp = playerQueue.poll();
                playerQueue.add(tmp);
            }
            System.out.printf("The dealer's hand is %s.\n", dealer.getCard(0));
            if (cardStack.size() <= 0) {
                ArrayList<Card> deck = buildDeck();
                shuffle(deck);
                for (Card card : deck) {
                    cardStack.push(card);
                }
            }
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
                System.out.println(
                        "Please enter a number for which action you want to perform:\n1) Stand\n2) Hit\n3) View Cheat Sheet");
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
            } else if (handVal > 17 && handVal < 21) {
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
        System.out.printf("Let's see who won. Your hand is: %s. The dealer's hand is: %s.\n", player.toString(),
                dealer.toString());
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
        String advice = "No advice.";

        String key = playerTotal + "-" + dealerCard;
        System.out.println(key);
        if (cheatSheet.containsKey(key)) {
            advice = cheatSheet.get(key);
        }

        System.out.printf("Based on the cheat sheet, your hand value of %d against the dealer's %s suggests: %s\n",
                playerTotal, dealerCard, advice);
    }

    public void BlackjackCheatSheet() {
        cheatSheet.put("8-2", "H");
        cheatSheet.put("8-3", "H");
        cheatSheet.put("8-4", "H");
        cheatSheet.put("8-5", "H");
        cheatSheet.put("8-6", "H");
        cheatSheet.put("8-7", "H");
        cheatSheet.put("8-8", "H");
        cheatSheet.put("8-9", "H");
        cheatSheet.put("8-10", "H");
        cheatSheet.put("8-A", "H");
        cheatSheet.put("9-2", "H");
        cheatSheet.put("9-3", "D/H");
        cheatSheet.put("9-4", "D/H");
        cheatSheet.put("9-5", "D/H");
        cheatSheet.put("9-6", "D/H");
        cheatSheet.put("9-7", "H");
        cheatSheet.put("9-8", "H");
        cheatSheet.put("9-9", "H");
        cheatSheet.put("9-10", "H");
        cheatSheet.put("9-A", "H");
        cheatSheet.put("10-2", "D/H");
        cheatSheet.put("10-3", "D/H");
        cheatSheet.put("10-4", "D/H");
        cheatSheet.put("10-5", "D/H");
        cheatSheet.put("10-6", "D/H");
        cheatSheet.put("10-7", "D/H");
        cheatSheet.put("10-8", "D/H");
        cheatSheet.put("10-9", "D/H");
        cheatSheet.put("10-10", "H");
        cheatSheet.put("10-A", "H");
        cheatSheet.put("11-2", "D/H");
        cheatSheet.put("11-3", "D/H");
        cheatSheet.put("11-4", "D/H");
        cheatSheet.put("11-5", "D/H");
        cheatSheet.put("11-6", "D/H");
        cheatSheet.put("11-7", "D/H");
        cheatSheet.put("11-8", "D/H");
        cheatSheet.put("11-9", "D/H");
        cheatSheet.put("11-10", "D/H");
        cheatSheet.put("12-2", "H");
        cheatSheet.put("12-3", "H");
        cheatSheet.put("12-4", "S");
        cheatSheet.put("12-5", "S");
        cheatSheet.put("12-6", "S");
        cheatSheet.put("12-7", "H");
        cheatSheet.put("12-8", "H");
        cheatSheet.put("12-9", "H");
        cheatSheet.put("12-10", "H");
        cheatSheet.put("12-A", "H");
        cheatSheet.put("13-2", "S");
        cheatSheet.put("13-3", "S");
        cheatSheet.put("13-4", "S");
        cheatSheet.put("13-5", "S");
        cheatSheet.put("13-6", "S");
        cheatSheet.put("13-7", "H");
        cheatSheet.put("13-8", "H");
        cheatSheet.put("13-9", "H");
        cheatSheet.put("13-10", "H");
        cheatSheet.put("13-A", "H");
        cheatSheet.put("14-2", "S");
        cheatSheet.put("14-3", "S");
        cheatSheet.put("14-4", "S");
        cheatSheet.put("14-5", "S");
        cheatSheet.put("14-6", "S");
        cheatSheet.put("14-7", "H");
        cheatSheet.put("14-8", "H");
        cheatSheet.put("14-9", "H");
        cheatSheet.put("14-10", "H");
        cheatSheet.put("14-A", "H");
        cheatSheet.put("15-2", "S");
        cheatSheet.put("15-3", "S");
        cheatSheet.put("15-4", "S");
        cheatSheet.put("15-5", "S");
        cheatSheet.put("15-6", "S");
        cheatSheet.put("15-7", "H");
        cheatSheet.put("15-8", "H");
        cheatSheet.put("15-9", "H");
        cheatSheet.put("15-10", "R/H");
        cheatSheet.put("15-A", "H");
        cheatSheet.put("16-2", "S");
        cheatSheet.put("16-3", "S");
        cheatSheet.put("16-4", "S");
        cheatSheet.put("16-5", "S");
        cheatSheet.put("16-6", "S");
        cheatSheet.put("16-7", "H");
        cheatSheet.put("16-8", "H");
        cheatSheet.put("16-9", "R/H");
        cheatSheet.put("16-10", "R/H");
        cheatSheet.put("16-A", "R/H");
        cheatSheet.put("17-2", "S");
        cheatSheet.put("17-3", "S");
        cheatSheet.put("17-4", "S");
        cheatSheet.put("17-5", "S");
        cheatSheet.put("17-6", "S");
        cheatSheet.put("17-7", "S");
        cheatSheet.put("17-9", "S");
        cheatSheet.put("17-10", "S");
        cheatSheet.put("17-A", "S");
        cheatSheet.put("A,2-2", "H");
        cheatSheet.put("A,2-3", "H");
        cheatSheet.put("A,2-4", "H");
        cheatSheet.put("A,2-5", "D/H");
        cheatSheet.put("A,2-6", "D/H");
        cheatSheet.put("A,2-7", "H");
        cheatSheet.put("A,2-A", "H");
        cheatSheet.put("A,2-9", "H");
        cheatSheet.put("A,2-10", "H");
        cheatSheet.put("A,2-A", "H");
        cheatSheet.put("A,3-2", "H");
        cheatSheet.put("A,3-3", "H");
        cheatSheet.put("A,3-4", "H");
        cheatSheet.put("A,3-5", "D/H");
        cheatSheet.put("A,3-6", "D/H");
        cheatSheet.put("A,3-7", "H");
        cheatSheet.put("A,3-A", "H");
        cheatSheet.put("A,3-9", "H");
        cheatSheet.put("A,3-10", "H");
        cheatSheet.put("A,3-A", "H");
        cheatSheet.put("A,4-2", "H");
        cheatSheet.put("A,4-3", "H");
        cheatSheet.put("A,4-4", "D/H");
        cheatSheet.put("A,4-5", "D/H");
        cheatSheet.put("A,4-6", "D/H");
        cheatSheet.put("A,4-7", "H");
        cheatSheet.put("A,4-A", "H");
        cheatSheet.put("A,4-9", "H");
        cheatSheet.put("A,4-10", "H");
        cheatSheet.put("A,4-A", "H");
        cheatSheet.put("A,5-2", "H");
        cheatSheet.put("A,5-3", "H");
        cheatSheet.put("A,5-4", "D/H");
        cheatSheet.put("A,5-5", "D/H");
        cheatSheet.put("A,5-6", "D/H");
        cheatSheet.put("A,5-7", "H");
        cheatSheet.put("A,5-A", "H");
        cheatSheet.put("A,5-9", "H");
        cheatSheet.put("A,5-10", "H");
        cheatSheet.put("A,5-A", "H");
        cheatSheet.put("A,6-2", "H");
        cheatSheet.put("A,6-3", "D/H");
        cheatSheet.put("A,6-4", "D/H");
        cheatSheet.put("A,6-5", "D/H");
        cheatSheet.put("A,6-6", "D/H");
        cheatSheet.put("A,6-7", "H");
        cheatSheet.put("A,6-A", "H");
        cheatSheet.put("A,6-9", "H");
        cheatSheet.put("A,6-10", "H");
        cheatSheet.put("A,6-A", "H");
        cheatSheet.put("A,7-2", "S");
        cheatSheet.put("A,7-3", "D/S");
        cheatSheet.put("A,7-4", "D/S");
        cheatSheet.put("A,7-5", "D/S");
        cheatSheet.put("A,7-6", "D/S");
        cheatSheet.put("A,7-7", "S");
        cheatSheet.put("A,7-8", "S");
        cheatSheet.put("A,7-9", "H");
        cheatSheet.put("A,7-10", "H");
        cheatSheet.put("A,7-A", "H");
        cheatSheet.put("A,8-2", "S");
        cheatSheet.put("A,8-3", "S");
        cheatSheet.put("A,8-4", "S");
        cheatSheet.put("A,8-5", "S");
        cheatSheet.put("A,8-6", "S");
        cheatSheet.put("A,8-7", "S");
        cheatSheet.put("A,8-9", "S");
        cheatSheet.put("A,8-10", "S");
        cheatSheet.put("A,8-A", "S");
        cheatSheet.put("2,2-2", "P/H");
        cheatSheet.put("2,2-3", "P/H");
        cheatSheet.put("2,2-4", "P");
        cheatSheet.put("2,2-5", "P");
        cheatSheet.put("2,2-6", "P");
        cheatSheet.put("2,2-7", "P");
        cheatSheet.put("2,2-8", "H");
        cheatSheet.put("2,2-9", "H");
        cheatSheet.put("2,2-10", "H");
        cheatSheet.put("2,2-A", "H");
        cheatSheet.put("3,3-2", "P/H");
        cheatSheet.put("3,3-3", "P/H");
        cheatSheet.put("3,3-4", "P");
        cheatSheet.put("3,3-5", "P");
        cheatSheet.put("3,3-6", "P");
        cheatSheet.put("3,3-7", "P");
        cheatSheet.put("3,3-8", "H");
        cheatSheet.put("3,3-9", "H");
        cheatSheet.put("3,3-10", "H");
        cheatSheet.put("3,3-A", "H");
        cheatSheet.put("4,4-2", "H");
        cheatSheet.put("4,4-3", "H");
        cheatSheet.put("4,4-4", "H");
        cheatSheet.put("4,4-5", "P/H");
        cheatSheet.put("4,4-6", "P/H");
        cheatSheet.put("4,4-7", "P");
        cheatSheet.put("4,4-8", "H");
        cheatSheet.put("4,4-9", "H");
        cheatSheet.put("4,4-10", "H");
        cheatSheet.put("5,5-A", "D/H");
        cheatSheet.put("5,5-2", "D/H");
        cheatSheet.put("5,5-3", "D/H");
        cheatSheet.put("5,5-4", "D/H");
        cheatSheet.put("5,5-5", "D/H");
        cheatSheet.put("5,5-6", "D/H");
        cheatSheet.put("5,5-7", "D/H");
        cheatSheet.put("5,5-8", "D/H");
        cheatSheet.put("5,5-9", "D/H");
        cheatSheet.put("5,5-10", "H");
        cheatSheet.put("5,5-A", "H");
        cheatSheet.put("6,6-2", "P/H");
        cheatSheet.put("6,6-3", "P");
        cheatSheet.put("6,6-4", "P");
        cheatSheet.put("6,6-5", "P");
        cheatSheet.put("6,6-6", "P");
        cheatSheet.put("6,6-7", "H");
        cheatSheet.put("6,6-8", "H");
        cheatSheet.put("6,6-9", "H");
        cheatSheet.put("6,6-10", "H");
        cheatSheet.put("7,7-2", "P");
        cheatSheet.put("7,7-3", "P");
        cheatSheet.put("7,7-4", "P");
        cheatSheet.put("7,7-5", "P");
        cheatSheet.put("7,7-6", "P");
        cheatSheet.put("7,7-7", "P");
        cheatSheet.put("7,7-8", "H");
        cheatSheet.put("7,7-9", "H");
        cheatSheet.put("7,7-10", "H");
        cheatSheet.put("7,7-A", "H");
        cheatSheet.put("8,8-2", "P");
        cheatSheet.put("8,8-3", "P");
        cheatSheet.put("8,8-4", "P");
        cheatSheet.put("8,8-5", "P");
        cheatSheet.put("8,8-6", "P");
        cheatSheet.put("8,8-7", "P");
        cheatSheet.put("8,8-8", "P");
        cheatSheet.put("8,8-9", "P");
        cheatSheet.put("8,8-10", "P");
        cheatSheet.put("8,8-A", "P");
        cheatSheet.put("9,9-2", "P");
        cheatSheet.put("9,9-3", "P");
        cheatSheet.put("9,9-4", "P");
        cheatSheet.put("9,9-5", "P");
        cheatSheet.put("9,9-6", "P");
        cheatSheet.put("9,9-7", "S");
        cheatSheet.put("9,9-8", "P");
        cheatSheet.put("9,9-9", "P");
        cheatSheet.put("9,9-10", "S");
        cheatSheet.put("9,9-A", "S");
        cheatSheet.put("10,10-2", "S");
        cheatSheet.put("10,10-3", "S");
        cheatSheet.put("10,10-4", "S");
        cheatSheet.put("10,10-5", "S");
        cheatSheet.put("10,10-6", "S");
        cheatSheet.put("10,10-7", "S");
        cheatSheet.put("10,10-8", "S");
        cheatSheet.put("10,10-9", "S");
        cheatSheet.put("10,10-10", "S");
        cheatSheet.put("10,10-A", "S");
        cheatSheet.put("A,A-2", "P");
        cheatSheet.put("A,A-3", "P");
        cheatSheet.put("A,A-4", "P");
        cheatSheet.put("A,A-5", "P");
        cheatSheet.put("A,A-6", "P");
        cheatSheet.put("A,A-7", "P");
        cheatSheet.put("A,A-8", "P");
        cheatSheet.put("A,A-9", "P");
        cheatSheet.put("A,A-10", "P");
        cheatSheet.put("A,A-A", "P");
    }
}
