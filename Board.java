import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Constructor
    public Board(int size, ActionListener AL)
    {
        // Allocate and configure the game board: an array of cards
        // Leave one extra space for the "happy face", added at the end
        cards = new FlippableCard[size];

        // Fill the cards array
        int imageIdx = 1;
        for (int i = 0; i < (size-1); i += 2) {

            // Load the front image from the resources folder
            String imgPath = "res/billie" + imageIdx + ".jpeg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));
            imageIdx++;  // get ready for the next pair of cards

            // Setup two cards at a time
            FlippableCard c1 = new FlippableCard(img);
            FlippableCard c2 = new FlippableCard(img);

            // add action listeners
            c1.addActionListener(AL);
            c2.addActionListener(AL);

            // add names (makes it so same picture cards have different names)
            c1.setCustomName("billie" + imageIdx);
            c2.setCustomName("billie" + (imageIdx + 1));

            // add id's (makes it so same picture cards have same id number)
            c1.setID(i);
            c2.setID(i);

            // Add them to the array
            cards[i] = c1;
            cards[i + 1] = c2;
        }
        // Add the "happy face" image
        String imgPath = "res/happy-face.jpg";
        ImageIcon img = new ImageIcon(loader.getResource(imgPath));
        FlippableCard c1 = new FlippableCard(img);
        c1.addActionListener(AL);
        c1.setCustomName("happy-face");
        cards[size-1] = c1;

        randomizeCards(cards);
    }

    // Function to fill a JPanel with cards
    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {
            c.setBackground(Color.BLACK); // set color of each card
            c.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // set border of each card
            view.add(c);
        }
    }

    // Function to reset the board
    public void resetBoard()
    {
        // Set all cards to not be matched
        for (FlippableCard c : cards) {
            c.isMatched = false;
        }
        // Randomize card positions
        randomizeCards(cards);

        // Flip all cards over
        hideAll();
    }

    // Function to show the front of every card
    public void showAll() {
        for (FlippableCard c : cards) {
            c.showFront();
        }
    }

    // Function to show the back of every card
    public void hideAll() {
        for (FlippableCard c : cards) {
            c.hideFront();
        }
    }

    // Function to randomize the cards in the array of cards
    public static void randomizeCards(FlippableCard[] cardsArray) {
        Random random = new Random();

        for (int i = 0; i < cardsArray.length; i++) {
            int randomPosition = random.nextInt(cardsArray.length);
            FlippableCard temp = cardsArray[i];
            cardsArray[i] = cardsArray[randomPosition];
            cardsArray[randomPosition] = temp;
        }
    }
}