class TripleCut1 implements Runnable {
    @Override
    public void run() {
        // For the edge case of tripleCut() when there are no cards before the 1st card
        // firstCard is the head of the deck

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);

        deck.tripleCut(c1, c3);

        // test whether all the cards are in the right order
        boolean head = deck.head == c4;
        boolean tail = deck.head.prev == c3;
        boolean c4Ref = c4.next == c1 && c4.prev == c3;
        boolean c3Next = c3.next == c4;
        boolean c1Prev = c1.prev == c4;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c4Ref && c3Next && c1Prev)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("assignment2.Test passed.");

    }
}

class TripleCut2 implements Runnable {
    @Override
    public void run() {
        // For the edge case of tripleCut() when there are no cards after the 2nd card
        // secondCard is the tail of the deck

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);

        deck.tripleCut(c2, c4);

        boolean head = deck.head == c2;
        boolean tail = deck.head.prev == c1;
        boolean c2Prev = c2.prev == c1;
        boolean c1Ref = c1.prev == c4 && c1.next == c2;
        boolean c4Next = c4.next == c1;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c2Prev && c1Ref && c4Next)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("assignment2.Test passed.");
    }
}


class TripleCut3 implements Runnable {
    @Override
    public void run() {
        // regular case of tripleCut()

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H
        Deck.Card c5 = deck.new PlayingCard(Deck.suitsInOrder[3], 1); //AS
        Deck.Card c6 = deck.new PlayingCard(Deck.suitsInOrder[3], 2); //2S

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);
        deck.addCard(c5);
        deck.addCard(c6);       // Deck: AC 2C AH 2H AS 2S

        deck.tripleCut(c3, c5);  // Expected outcome: 2S AH 2H AS AC 2C

        boolean head = deck.head == c6;
        boolean tail = deck.head.prev == c2;
        boolean c1Ref = c1.prev == c5 && c1.next == c2;
        boolean c2Ref = c2.prev == c1 && c2.next == c6;
        boolean c3Red = c3.prev == c6 && c3.next == c4;
        boolean c4ref = c4.prev == c3 && c4.next == c5;
        boolean c5Ref = c5.prev == c4 && c5.next == c1;
        boolean c6Ref = c6.prev == c2 && c6.next == c3;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c1Ref && c2Ref && c3Red && c4ref && c5Ref && c6Ref)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class CountCut1 implements Runnable{
    @Override
    public void run() {
        // regular case

        Deck deck = new Deck();

        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c3);
        deck.addCard(c4);
        deck.addCard(c2);      // Deck : AC AH 2H 2C

        deck.countCut();       // Cut 2 numbers from the top (taking value of 2C)
        // Expected Deck Outcome : 2H 2C AC AH

        boolean head = deck.head == c4;
        boolean tail = deck.head.prev == c2;
        boolean c4Ref = c4.prev == c2 && c4.next == c1;
        boolean c1Ref = c1.prev == c4 && c1.next == c3;
        boolean c3ref = c3.prev == c1 && c3.next == c2;
        boolean c2Ref = c2.prev == c3 && c2.next == c4;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c4Ref && c1Ref && c3ref && c2Ref)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class CountCut2 implements Runnable {
    @Override
    public void run() {
        // when the value of the last card is a multiplier of the number of cards in the deck

        Deck deck = new Deck();
        Deck.PlayingCard c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.PlayingCard c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.PlayingCard c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C
        Deck.PlayingCard c4 = deck.new PlayingCard(Deck.suitsInOrder[0], 4); //4C

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);      // Deck : AC 2C 3C 4C

        deck.countCut();       // Do nothing because 4 is a multiplier of 4

        if(!(deck.head == c1) && deck.head.prev == c4) {
            throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class CountCut3 implements Runnable{
    @Override
    public void run() {
        // when the number for cut is 1

        Deck deck = new Deck();

        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C

        deck.addCard(c1);
        deck.addCard(c2);

        deck.countCut();       // Should put AC above 3C (technically not changing the deck)

        if(!(deck.head == c1) && deck.head.prev == c2) {
            throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class LocateJoker1 implements Runnable {
    @Override
    public void run() {
        // edge case where deck is empty, must return null

        Deck deck = new Deck();
        deck.locateJoker("red");

        if (deck.locateJoker("red") != null) {
            throw new AssertionError("The method locateJoker() is not returning null when the deck is empty");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class LocateJoker2 implements Runnable {
    @Override
    public void run() {
        // edge case where there is no joker in the deck, must return null
        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);

        deck.locateJoker("red");

        if (deck.locateJoker("red") != null) {
            throw new AssertionError("The method locateJoker() is not returning null when the there is no joker");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class GenerateNextKeystreamValue1 implements Runnable {
    @Override
    public void run() {
        // example case from the last page of pdf

        Deck deck = new Deck(5, 2);

        int seed = 10;
        Deck.gen.setSeed(seed);

        deck.shuffle();
        // deck.generateNextKeystreamValue();

        if (deck.generateNextKeystreamValue() != 4) {
            throw new AssertionError("The method generateNextKeystreamValue() is not returning the correct value. Expected value is 4 but got " + deck.generateNextKeystreamValue());
        }
        System.out.println("assignment2.Test passed.");
    }
}

class GenerateNextKeystreamValue2 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(1, 1);

        int seed = 31;
        Deck.gen.setSeed(seed);
        deck.shuffle();
        deck.generateNextKeystreamValue();

        if (deck.generateNextKeystreamValue() != 1) {
            throw new AssertionError("The method generateNextKeystreamValue() is not returning the correct value. Expected value is 1 but got " + deck.generateNextKeystreamValue());
        } else if (deck.head != deck.locateJoker("red")) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class GenerateNextKeystreamValue3 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(2, 2);

        int seed = 26;
        Deck.gen.setSeed(seed);

        deck.shuffle();
        int value = deck.generateNextKeystreamValue();

        if (value != 2) {
            throw new AssertionError("The method generateNextKeystreamValue() is not returning the correct value. Expected value is 2 but got " + deck.generateNextKeystreamValue());
        } else if (deck.head.getValue() != 14) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("assignment2.Test passed.");
    }
}

class GenerateNextKeystreamValue4 implements Runnable {
    public void run() {
        Deck deck = new Deck();
        Deck.PlayingCard card1 = deck.new PlayingCard("clubs", 1); // AC
        Deck.PlayingCard card2 = deck.new PlayingCard("clubs", 2); // 2C
        Deck.PlayingCard card3 = deck.new PlayingCard("clubs", 3); // 3C
        Deck.Joker rJoker = deck.new Joker("red"); // RJ
        Deck.PlayingCard card4 = deck.new PlayingCard("diamonds", 1); // AD
        Deck.PlayingCard card5 = deck.new PlayingCard("diamonds", 2); // 2D
        Deck.Joker bJoker = deck.new Joker("black"); // BJ
        Deck.PlayingCard card6 = deck.new PlayingCard("diamonds", 3); // 3D

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(rJoker);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(bJoker);
        deck.addCard(card6);


        int value = deck.generateNextKeystreamValue();

        if (value != 16) {
            throw new AssertionError("The method generateNextKeystreamValue() is not returning the correct value. Expected value is 2 but got " + deck.generateNextKeystreamValue());
        } else if (deck.head.getValue() != 16) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("Test passed!");
    }
}

public class A2_Tester2 {
    // To skip running some tests, just comment them out below.
    static String[] tests = {
            "TripleCut1",
            "TripleCut2",
            "TripleCut3",
            "CountCut1",
            "CountCut2",
            "CountCut3",
            "LocateJoker1",
            "LocateJoker2",
            "GenerateNextKeystreamValue1",
            "GenerateNextKeystreamValue2",
            "GenerateNextKeystreamValue3"
    };

    public static void main(String[] args) {
        int numPassed = 0;
        for(String className: tests)    {
            System.out.printf("%n======= %s =======%n", className);
            System.out.flush();
            try {
                Runnable testCase = (Runnable) Class.forName(className).getDeclaredConstructor().newInstance();
                testCase.run();
                numPassed++;
            } catch (AssertionError e) {
                System.out.println(e);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        System.out.printf("%n%n%d of %d tests passed.%n", numPassed, tests.length);
    }
}