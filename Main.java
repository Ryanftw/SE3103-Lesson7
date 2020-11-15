import view.GameBoard;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Space Invader");
        window.setLocation(500, 200);

        var game = new GameBoard(window);
        game.init();
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
