/**
 * Your name here: Nicholas Belev
 * Your McGill ID here: 261076111
 **/
import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();
    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /*
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {

        /**** ADD CODE HERE ****/
        if (numOfCardsPerSuit > 13 || numOfCardsPerSuit < 1) {
            throw new IllegalArgumentException("First argument should be a number between 1 and 13.");
        }
        if (numOfSuits > 4 || numOfSuits < 1) {
            throw new IllegalArgumentException("Second argument should be a valid number of suits.");
        }

        numOfCards = numOfCardsPerSuit * numOfSuits + 2;

        PlayingCard head = new PlayingCard(suitsInOrder[0], 1);
        this.head = head;
        PlayingCard curr = head;

        for (int h = 0; h < numOfSuits; h++) {

            String suit = suitsInOrder[h];

            for (int i = 1; i <= numOfCardsPerSuit; i++) {

                if (h == 0 && i == 1) {
                    continue;
                }

                PlayingCard next = new PlayingCard(suit, i);
                curr.next = next;
                next.prev = curr;
                curr = next;
            }
        }

        Card last = this.head;
        while (last.next != null) {
            last = last.next;
        }

        last.next = new Joker("red");
        last.next.prev = last;
        last = last.next;
        last.next = new Joker("black");
        last.next.prev = last;
        last = last.next;
        last.next = this.head;
        last.next.prev = last;
    }

    /*
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {
        /**** ADD CODE HERE ****/
        Card curr = d.head;
        Card headCopy = d.head.getCopy();
        this.head = headCopy;
        Card currCopy = headCopy;

        this.numOfCards = d.numOfCards;

        for (int i = 1; i < numOfCards; i++) {
            Card nextCopy = curr.next.getCopy();
            currCopy.next = nextCopy;
            nextCopy.prev = currCopy;
            curr = curr.next;
            currCopy = currCopy.next;
        }
        currCopy.next = headCopy;
        headCopy.prev = currCopy;
    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {
    }

    /*
     * TODO: Adds the specified card at the bottom of the deck. This
     * method runs in $O(1)$.
     */
    public void addCard(Card c) {
        /**** ADD CODE HERE ****/
        if (this.head == null) {
            this.head = c;
            c.next = c;
            c.prev = c;
            this.numOfCards = 1;
        }
        else if (this.numOfCards == 1) {
            this.head.next = c;
            c.next = this.head;
            this.head.prev = c;
            c.prev = this.head;
            numOfCards++;
        }
        else {
            Card oldTail = this.head.prev;
            c.prev = oldTail;
            c.next = this.head;
            oldTail.next = c;
            this.head.prev = c;
            this.numOfCards++;
        }
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf.
     * This method runs in O(n) and uses O(n) space, where n is the total
     * number of cards in the deck.
     */
    public void shuffle() {
        /**** ADD CODE HERE ****/
        if (this.head != null) {
            Card[] deckArr = new Card[this.numOfCards];
            Card curr = this.head;
            for (int i = 0; i < deckArr.length; i++) {
                deckArr[i] = curr;
                curr = curr.next;
            }
            for (int i = deckArr.length - 1; i >= 1; i--) {
                int j = gen.nextInt(i + 1);
                Card temp = deckArr[i];
                deckArr[i] = deckArr[j];
                deckArr[j] = temp;
            }
            this.head = deckArr[0];
            Card curr1 = this.head;

            for (int i = 1; i < numOfCards; i++) {
                Card next1 = deckArr[i];
                curr1.next = next1;
                next1.prev = curr1;
                curr1 = curr1.next;
            }
            curr1.next = this.head;
            this.head.prev = curr1;
        }
    }

    /*
     * TODO: Returns a reference to the joker with the specified color in
     * the deck. This method runs in O(n), where n is the total number of
     * cards in the deck.
     */
    public Joker locateJoker(String color) {
        /**** ADD CODE HERE ****/
        String lookFor = color.toUpperCase().charAt(0) + "J";
        Card reference = this.head;
        for (int i = 0; i < numOfCards; i++) {
            if ((reference instanceof Joker) && (reference.toString().equals(lookFor))) {
                return ((Joker) reference);
            }
            reference = reference.next;
        }
        return null;
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */

    public void moveCard(Card c, int p) {
        Card oldPrev = c.prev;
        Card oldNext = c.next;
        Card curr = c;
        for (int i = 0 ; i < p; i++) {
            curr = curr.next;
        }
        Card newPrev = curr;
        Card newNext = curr.next;
        c.prev = newPrev;
        c.next = newNext;
        newPrev.next = c;
        newNext.prev = c;
        oldPrev.next = oldNext;
        oldNext.prev = oldPrev;
    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You
     * can assume that the input cards belong to the deck and the first one is
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
        /**** ADD CODE HERE ****/
        boolean ignoreCase = false;
        //Special case 1 - first is head and second is not tail, essentially a double cut because the first group is empty
        if ((firstCard == this.head) && (secondCard != this.head.prev)) {
            //Subcase, where only one card needs to be "moved", meaning a head switch
            if (numOfCards == 3) {
                this.head = secondCard.next;
            }
            else if ((firstCard.prev.prev == secondCard) && (secondCard.next.next == firstCard)) {
                this.head = secondCard.next;
            }
            else {
                Card af = firstCard;
                Card al = secondCard;
                Card bf = secondCard.next;
                Card bl = firstCard.prev;
                bl.next = af;
                al.next = bf;
                bf.prev = al;
                af.prev = bl;
                this.head = bf;
            }
        }
        //Special case 2 - second is tail but first is not head, another technical double cut needs to happen
        else if (!(firstCard == this.head) && (secondCard == this.head.prev)) {
            //One card in between special case
            if (numOfCards == 3) {
                this.head = firstCard;
            }
            else if ((secondCard.next.next == firstCard) && (firstCard.prev.prev == secondCard)) {
                this.head = firstCard;
            }
            else {
                Card af = this.head;
                Card al = firstCard.prev;
                Card bf = firstCard;
                Card bl = secondCard;
                bl.next = af;
                al.next = bf;
                bf.prev = al;
                af.prev = bl;
                this.head = bf;
            }
        }
        //Ignore cases where first card is head and second is tail as there is nothing to swap then
        else if ((firstCard == this.head) && (secondCard == this.head.prev)) {
            ignoreCase = true;
        }
        //Normal cases triple cut
        else {
            Card af = this.head;
            Card al = firstCard.prev;
            Card bf = firstCard;
            Card bl = secondCard;
            Card cf = secondCard.next;
            Card cl = this.head.prev;
            cl.next = bf;
            bl.next = af;
            al.next = cf;
            af.prev = bl;
            bf.prev = cl;
            cf.prev = al;
            this.head = cf;
        }
    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the
     * bottom card is equal to a multiple of the number of cards in the deck,
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {
        /**** ADD CODE HERE ****/
        Card curr = this.head;
        int cutValue = this.head.prev.getValue();
        //Ignores cases where countCut will change nothing
        if ((cutValue % numOfCards != 0) && (cutValue != numOfCards - 1) && (cutValue % numOfCards != numOfCards - 1)) {
            //curr is assigned to the last card in the section that will be moved
            for (int i = 0; i < cutValue - 1; i++) {
                curr = curr.next;
            }
            Card af = this.head;
            Card al = curr;
            Card bf = curr.next;
            Card bl = this.head.prev.prev;
            Card c = this.head.prev;
            c.next = bf;
            bl.next = af;
            al.next = c;
            af.prev = bl;
            bf.prev = c;
            c.prev = al;
            this.head = bf;
        }
    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the
     * card on the top of the deck, and counting down that many cards. If the
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
        /**** ADD CODE HERE ****/
        int topValue = this.head.getValue();
        Card current = this.head;
        for (int i = 0; i < topValue; i++) {
            current = current.next;
        }
        if (current instanceof Joker) {
            return null;
        }
        return current;
    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {
        /**** ADD CODE HERE ****/
        moveCard(locateJoker("red"), 1);
        moveCard(locateJoker("black"), 2);
        Card current = this.head;
        for (int i = 0; i < numOfCards; i++) {
            if (!(current instanceof Joker)) {
                current = current.next;
            }
        }
        if (current.toString().equals("RJ")) {
            tripleCut(locateJoker("red"), locateJoker("black"));
        } else if (current.toString().equals("BJ")) {
            tripleCut(locateJoker("black"), locateJoker("red"));
        }
        else {int i = 0;}
        countCut();
        Card value = lookUpCard();
        if (value == null) {
            return generateNextKeystreamValue();
        }
        else {
            return value.getValue();
        }
    }


    public abstract class Card {
        public Card next;
        public Card prev;

        public abstract Card getCopy();

        public abstract int getValue();

    }

    public class PlayingCard extends Card {
        public String suit;
        public int rank;

        public PlayingCard(String s, int r) {
            this.suit = s.toLowerCase();
            this.rank = r;
        }

        public String toString() {
            String info = "";
            if (this.rank == 1) {
                //info += "Ace";
                info += "A";
            } else if (this.rank > 10) {
                String[] cards = {"Jack", "Queen", "King"};
                //info += cards[this.rank - 11];
                info += cards[this.rank - 11].charAt(0);
            } else {
                info += this.rank;
            }
            //info += " of " + this.suit;
            info = (info + this.suit.charAt(0)).toUpperCase();
            return info;
        }

        public PlayingCard getCopy() {
            return new PlayingCard(this.suit, this.rank);
        }

        public int getValue() {
            int i;
            for (i = 0; i < suitsInOrder.length; i++) {
                if (this.suit.equals(suitsInOrder[i]))
                    break;
            }

            return this.rank + 13 * i;
        }

    }

    public class Joker extends Card {
        public String redOrBlack;

        public Joker(String c) {
            if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
                throw new IllegalArgumentException("Jokers can only be red or black");

            this.redOrBlack = c.toLowerCase();
        }

        public String toString() {
            //return this.redOrBlack + " Joker";
            return (this.redOrBlack.charAt(0) + "J").toUpperCase();
        }

        public Joker getCopy() {
            return new Joker(this.redOrBlack);
        }

        public int getValue() {
            return numOfCards - 1;
        }

        public String getColor() {
            return this.redOrBlack;
        }
    }

}