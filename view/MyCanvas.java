package view;

import model.GameElement;
import view.GameBoard.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyCanvas extends JPanel {

    private GameBoard gameBoard;
    private GameBoard.GameState state;
    private ArrayList<GameElement> gameElements = new ArrayList<>();

    public MyCanvas(GameBoard gameBoard, int width, int height) {
        this.gameBoard = gameBoard;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        state = gameBoard.getState();
        if (state == GameState.Playing) {
            for (var e : gameElements) {
                e.render(g2);
            }
        } else if (state == GameState.GameOver) {
            for (var e : gameElements) {
                e.render(g2);
            }
        } else if (state == GameState.Ready) {
            for (var e : gameElements) {
                e.render(g2);
            }
        }
    }

    public ArrayList<GameElement> getGameElements() {
        return gameElements;
    }
}
