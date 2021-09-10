import javax.swing.*;

public class FlippableCard extends JButton
{
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("res/Back.JPG"));

    // ID + Name
    private int id;
    private String customName;

    // Variable to mark matched cards
    public boolean isMatched;

    // Default constructor
    public FlippableCard() {
        super();
        isMatched = false;
    }

    // Constructor with card front initialization
    public FlippableCard(ImageIcon frontImage)
    {
        super();
        front = frontImage;
        super.setIcon(front);
        isMatched = false;
    }

    // Set the image used as the front of the card
    public void setFrontImage(ImageIcon frontImage) { front = frontImage; }

    // Card flipping functions
    public void showFront() { // Shows the front of the card
        super.setIcon(front);
    }
    public void hideFront() { // Shows the back of the card
        super.setIcon(back);
    }

    // Metadata: ID number
    public int id() { return id; }
    public void setID(int i) { id = i; }

    // Metadata: Custom name
    public String customName() { return customName; }
    public void setCustomName(String s) { customName = s; }

}