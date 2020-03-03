import data.View;

import javax.swing.*;

public class Game {
    /**
     * Main, simply creates a thread for the game and instantiates a MainGame object which handles everything.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View(24,24,100));
    }
}
