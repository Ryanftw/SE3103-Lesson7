package controller;
import model.*;
import model.Shooter.myEvent;
import view.GameBoard;
import view.GameBoard.GameState;
import view.TextDraw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class TimerListener implements ActionListener {

    public enum EventType {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
    }

    private GameBoard gameBoard;
    private LinkedList<EventType> eventQueue;
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;
    private boolean upgradeFlag = false;
    private int rebuildTimes = 0;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++frameCounter;
        update();
        processEventQueue();
        processCollision();
        gameBoard.getCanvas().repaint();
    }

    private void processEventQueue() {
        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null)
                return;

            switch (e) {
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if (shooter.canFireMoreBullet()) {
                        if (gameBoard.getStateBlock() == GameBoard.ChangePlayingState.CHANGESTATE) {
                            shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                            shooter.getWeapons().add(new Bullet(shooter.x - 20, shooter.y));
                            shooter.getWeapons().add(new Bullet(shooter.x + 20, shooter.y));
                        } else {
                            shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                        }
                    }
                    break;
            }
        }

        if (frameCounter == BOMB_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBombs();
            frameCounter = 0;
        }
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        var figures = gameBoard.getCanvas().getGameElements();
        shooter.removeBulletsOutOfBound();
        enemyComposite.removeBombsOutOfBounds();
        enemyComposite.processCollision(shooter);
        if(enemyComposite.getFlag()) {
            gameBoard.subtractGameBoardHealth();
        }
        var removeBullets = new ArrayList<GameElement>();
        for (var b : shooter.getWeapons()) {
            for (var e : figures) {
                if(b.collideWith(e)) {
                    if (e instanceof StateBlock) {
                        if (gameBoard.getStateBlock() == GameBoard.ChangePlayingState.DEFAULT) {
                            gameBoard.setStateBlock(GameBoard.ChangePlayingState.CHANGESTATE);
                            gameBoard.getTimer().setDelay(40);
                            shooter.upgradeWeapon();
                            removeBullets.add(b);
                            System.out.println("HIT BLOCK");
                        } else {
                            gameBoard.setStateBlock(GameBoard.ChangePlayingState.DEFAULT);
                            gameBoard.getTimer().setDelay(50);
                            shooter.downgradeWeapon();
                            removeBullets.add(b);
                        }
                    }
                }
            }
        }



        rebuildTimes = 0;
        var removeHealth = new ArrayList<GameElement>();
        for(var f : figures) {
            for (var c : shooter.getComponents()) {
                if (f.collideWith(c)) {
                    if (f instanceof Health) {
                        removeHealth.add(f);
                        if (shooter.health < 4) {
                            rebuildTimes++;
                        }
                        shooter.notifyObserver(myEvent.HITSHEALTH);
                    }
                }
            }
        }
        if(rebuildTimes > 0) {
            shooter.rebuild();
        }

        if (gameBoard.getGamePoints() >= 200) {
            gameBoard.setState(GameState.GameOver);
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements()
                    .add(new TextDraw("YOU WIN!  SCORE : " + gameBoard.getGamePoints(), 150, 150, Color.yellow, 30));
        }
        if (enemyComposite.bottomEnd() >= GameBoard.HEIGHT) {
            gameBoard.setState(GameState.GameOver);
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements()
                    .add(new TextDraw("GAME OVER  SCORE : " + gameBoard.getGamePoints(), 150, 150, Color.yellow, 30));
        }
        if (shooter.health == 0) {
            gameBoard.setState(GameState.GameOver);
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements()
                    .add(new TextDraw("GAME OVER  SCORE : " + gameBoard.getGamePoints(), 150, 150, Color.yellow, 30));
        }

        //figures.removeAll(removeBullets);

        if(gameBoard.getGamePoints() >= 100) {
            enemyComposite.notifyEnemyObserver(EnemyComposite.enemyEvent.HALFPOINTS);
        }
        if (gameBoard.getGameBoardHealth() < 3 || removeHealth.size() > 0) {// || removeUpgrade.size() >0) {
            for (var r : removeHealth) {
                gameBoard.addGameBoardHealth();
            }
            figures.removeAll(removeHealth);
            gameBoard.createHealth();
        }
    }

    private void update() {
        for (var e : gameBoard.getCanvas().getGameElements()) {
            e.animate();
        }
    }


    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }



}
