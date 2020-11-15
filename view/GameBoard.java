package view;

import controller.TimerListener;
import model.Shooter;
import model.ShooterElement;

import javax.swing.*;
import java.awt.*;

public class GameBoard {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
    private Shooter shooter;
    private Timer timer;

    private JFrame window;
    private MyCanvas canvas;

    public GameBoard(JFrame window) {
        this.window = window;
    }

    public void init() {
        Container cp = window.getContentPane();

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH, southPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 100, 100, Color.yellow, 30));

        timer = new Timer(50, new TimerListener(this));
        //shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT - ShooterElement.SIZE);
        //canvas.getGameElements().add(shooter);
        //canvas.repaint();

        startButton.addActionListener(event -> {
            canvas.getGameElements().clear();
            shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT - ShooterElement.SIZE);
            canvas.getGameElements().add(shooter);
            canvas.repaint();
            //timer.start();
        });


    }

    public MyCanvas getCanvas() {
        return canvas;
    }
}
