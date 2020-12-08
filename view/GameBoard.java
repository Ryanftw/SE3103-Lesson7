package view;

import controller.KeyController;
import controller.TimerListener;
import model.*;
import model.observerPattern.ShooterObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoard {

    public enum GameState {
        Ready, Playing, GameOver
    }

    public enum ChangePlayingState {
        DEFAULT, CHANGESTATE
    }

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;

    public static int FPS = 20;
    public static final int DELAY = 1000 / FPS;

    private int gameBoardHealth = 0;
    private ChangePlayingState stateBlock = ChangePlayingState.DEFAULT;
    private GameState state = GameState.Ready;
    private Shooter shooter;
    private Timer timer;
    private EnemyComposite enemyComposite;
    private JFrame window;
    private MyCanvas canvas;
    private TimerListener timerListener;
    private JLabel points = new JLabel("Score : ");
    private int gamePoints = 0;

    public GameBoard(JFrame window) {
        this.window = window;
    }

    public void init() {
        Container cp = window.getContentPane();

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);

        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        startButton.setFocusable(false);
        quitButton.setFocusable(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH, southPanel);

        JPanel northPanel = new JPanel();
        northPanel.add(points);
        cp.add(BorderLayout.NORTH, northPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 150, 150, Color.yellow, 30));

        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);
        ShooterObserver o = new ShooterObserver(this);
        startButton.addActionListener(event -> {
            gamePoints = 0;
            canvas.getGameElements().clear();
            canvas.getGameElements().add(new StateBlock(GameBoard.WIDTH-(GameBoard.WIDTH-14), GameBoard.HEIGHT/2));
            shooter = new Shooter(GameBoard.WIDTH / 2, GameBoard.HEIGHT - ShooterElement.SIZE);
            shooter.addShooterListener(o);
            enemyComposite = new EnemyComposite();
            enemyComposite.addEnemyListener(o);
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            points.setText("Score : ");
            state = GameState.Playing;
            timer.start();
        });

        quitButton.addActionListener(event -> System.exit(0));

    }

    public void createHealth() {
        Random random = new Random();
        int xloc, yloc;
        if(random.nextInt((2500-0) + 0) > 2450) {
            do {
                xloc = random.ints(1, GameBoard.WIDTH).findFirst().getAsInt();
                yloc = random.ints(GameBoard.HEIGHT-40, GameBoard.HEIGHT).findFirst().getAsInt();
            } while (xloc == shooter.x && yloc == shooter.y);

            Health health = new Health(xloc, yloc);
            canvas.getGameElements().add(health);
            gameBoardHealth++;
        }

    }

    public MyCanvas getCanvas() {
        return canvas;
    }

    public Timer getTimer() {
        return timer;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public EnemyComposite getEnemyComposite() {
        return enemyComposite;
    }

    public void updatePoints(String message) {
        points.setText("Score : " + message);
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void addGameBoardHealth() {
        if (gameBoardHealth < 4) {
            gameBoardHealth += 1;
        }
    }

    public void subtractGameBoardHealth() {
        if (gameBoardHealth > 0) {
            gameBoardHealth -= 1;
        }
    }

    public int getGameBoardHealth() {
        return gameBoardHealth;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(int gamePoints) {
        this.gamePoints += gamePoints;
        points.setText("Score : " + this.gamePoints);
    }

    public ChangePlayingState getStateBlock() {
        return stateBlock;
    }

    public void setStateBlock(ChangePlayingState stateBlock) {
        this.stateBlock = stateBlock;
    }

}
