import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener
{
    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1, prevCard2;

    // Labels to display game info
    private JLabel matchesLabel, guessesLabel;

    // Layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int clickCount = 0, gameTime = 0, numGuesses = 0;
    private int pairsFound = 0;

    // Variable to determine when the game has started & the cards can be clicked
    // & variable to determine when happy face has been clicked
    private boolean hasStarted = false, isHappyFound = false;

    // Constructor
    public MemoryGame()
    {
        // Call the base class constructor
        super("Billie Eilish Memory Game");

        // Allocate the interface elements
        JButton restart = new JButton("Start/Restart");
        JButton quit = new JButton("Quit");
        guessesLabel = new JLabel("Guesses: 0");
        matchesLabel = new JLabel("Matches: 0");

        // Set colors of buttons & labels
        restart.setBackground(Color.BLACK);
        quit.setBackground(Color.BLACK);
        restart.setForeground(Color.GREEN);
        quit.setForeground(Color.GREEN);
        guessesLabel.setForeground(Color.GREEN);
        matchesLabel.setForeground(Color.GREEN);

        // Make borders for buttons
        restart.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        quit.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Implement action listeners for buttons
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
                hasStarted = true;
            }
        });
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Number of guesses: " + numGuesses + "\nNumber of matches: " + pairsFound);
                System.exit(0);
            }
        });

        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // Get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(25, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(5, 5, 2, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 4, 2, 2));
        labelView.add(restart);
        labelView.add(quit);
        labelView.add(guessesLabel);
        labelView.add(matchesLabel);

        // Set background colors
        boardView.setBackground(Color.BLACK);
        labelView.setBackground(Color.BLACK);
        c.setBackground(Color.BLACK);

        // Both panels should now be individually laid out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(660, 680);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Handle clicked cards
    public void actionPerformed(ActionEvent e)
    {
        // Get the currently clicked card from a click event
        FlippableCard currCard = (FlippableCard)e.getSource();

        // Only count the clicks when the game has been started
        // Only count the current click if the clicked card hasn't already been matched
        if (currCard.isMatched != true && hasStarted == true) {

            clickCount++; // increment number of clicks

            currCard.showFront(); // flip current card to front

            if (clickCount == 1) { // if it is first click

                // if it is the happy face card, start clicks over & leave card facing up
                if (currCard.customName() == "happy-face") {
                    clickCount = 0;
                    isHappyFound = true;
                }
                prevCard1 = currCard; // assign prevCard1 variable to the current clicked card

            }

            else if (clickCount == 2) { // if it is second click

                // if it is the same card that was clicked the first time, make click count 1 again
                if (currCard.customName() == prevCard1.customName()) {
                    clickCount = 1;
                }
                else {
                    clickCount = 0; // start clicks over after the second click

                    // increment & display number of guesses
                    numGuesses++;
                    guessesLabel.setText("Guesses: " + numGuesses);

                    prevCard2 = currCard; // // assign prevCard2 variable to the current clicked card

                    // if the id numbers of the cards match (if they are the same card)
                    if (prevCard1.id() == prevCard2.id()) {
                        // increment & display number of matches
                        pairsFound++;
                        matchesLabel.setText("Matches: " + pairsFound);
                        // mark the matched cards
                        prevCard1.isMatched = true;
                        prevCard2.isMatched = true;
                    }
                    else { // if the cards are not a match
                        // flip cards over after a delay of 0.5 seconds
                        new java.util.Timer().schedule(
                                new TimerTask() {
                                    public void run() {
                                        prevCard1.hideFront();
                                        prevCard2.hideFront();
                                    }
                                },
                                500
                        );
                    }
                }
            }
            // When all pairs & the happy face has been found, show the message
            if (pairsFound == 12 && isHappyFound) {
                JOptionPane.showMessageDialog(null, "Congratulations! You matched them all!");
                System.exit(0);
            }
        }
    }

    // Function to restart the game
    private void restartGame()
    {
        pairsFound = 0;
        gameTime = 0;
        clickCount = 0;
        numGuesses = 0;
        guessesLabel.setText("Guesses: 0");
        matchesLabel.setText("Matches: 0");

        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        boardView.revalidate();
        boardView.repaint();
        gameBoard.resetBoard();
        gameBoard.fillBoardView(boardView);
    }

    // Main function
    public static void main(String args[])
    {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}