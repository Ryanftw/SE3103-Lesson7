package model;

import model.observerPattern.Observer;
import model.observerPattern.Subject;
import model.strategyPattern.EnemyCompositeDefaultStrategy;
import model.strategyPattern.EnemyCompositeMoveStrategy;
import view.GameBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EnemyComposite extends GameElement implements Subject{

    public enum enemyEvent {
        HALFPOINTS, HITSMORESTUFF,
    }

    public static final int NROWS = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE_X = 5;
    public static final int UNIT_MOVE_Y = 20;

    private int pointCheck;
    private ArrayList<ArrayList<GameElement>> rows;
    private boolean movingToRight = true;
    private ArrayList<GameElement> bombs;
    private boolean flag = false;
    private EnemyCompositeMoveStrategy moveStrategy;
    private ArrayList<Observer> observers = new ArrayList<>();
    //private EnemyCompositeMoveStrategy fastMoveStrategy;


    public EnemyComposite() {
        moveStrategy = new EnemyCompositeDefaultStrategy(this);
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        for(int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for(int c = 0; c < NCOLS; c++) {
                oneRow.add(new Enemy(
                        c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.yellow, true
                ));
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {
        // render enemy array
        for (var r : rows) {
            for (var e : r) {
                e.render(g2);
            }
        }

        // render bombs
        for (var b : bombs) {
            b.render(g2);
        }
    }

    @Override
    public void animate() {
        this.moveStrategy.moveAlgorithm();

        // animate bombs
        for (var b : bombs)
            b.animate();
    }

    public int bottomEnd() {
        int yEnd = 0;
        for (int i = rows.size() - 1; i >= 0; i--) {
            if (rows.get(i).size() == 0) {
                continue;
            }
            yEnd = rows.get(i).get(0).y + ENEMY_SIZE;
            break;
        }
        return yEnd;
    }

    public void dropBombs() {
        Random random = new Random();
        for (var row : rows) {
            for (var e : row) {
                if (random.nextFloat() < 0.1F) {
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBounds() {
        var remove = new ArrayList<GameElement>();
        for (var b : bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter) {
        flag = false;
        var removeBullets = new ArrayList<GameElement>();

        // bullets vs enemies
        for (var row : rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy : row) {
                pointCheck = 0;
                var e = enemy;
                for (var bullet : shooter.getWeapons()) {
                    if (enemy.collideWith(bullet)) {
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                        if(e == enemy && pointCheck == 0) {
                            pointCheck++;
                            shooter.notifyObserver(Shooter.myEvent.BULLETHITSENEMY);
                        }
                    }
                }
            }
            row.removeAll(removeEnemies);
        }
        shooter.getWeapons().removeAll(removeBullets);

        // bullets vs bombs
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for (var b : bombs) {
            for (var bullet : shooter.getWeapons()) {
                if (b.collideWith(bullet)) {
                    removeBombs.add(b);
                    removeBullets.add(bullet);

                }
            }
        }

        // shooter vs bombs
        boolean unbuildFlag = false;
        for (var b : bombs) {
            for (var c : shooter.getComponents()) {
                if (b.collideWith(c)) {
                    flag = true;
                    removeBombs.add(b);
                    shooter.notifyObserver(Shooter.myEvent.HITSBOMB);
                    unbuildFlag = true;
                }
            }
        }
        if(unbuildFlag) {
            shooter.minusRebuild();
        }

        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);
    }

    public ArrayList<GameElement> getBombs() {
        return bombs;
    }

    public boolean getFlag() {
        return flag;
    }

    public ArrayList<ArrayList<GameElement>> getRows() {
        return rows;
    }

    public void setMoveStrategy(EnemyCompositeMoveStrategy strategy) {
        moveStrategy = strategy;
    }

    public void setMovingToRight(boolean tf) {
        movingToRight = tf;
    }

    public boolean getMovingToRight() {
        return movingToRight;
    }

    @Override
    public void notifyEnemyObserver(enemyEvent event) {
        switch(event) {
            case HALFPOINTS:
                for (var o : observers) {
                    o.enemyGetsHarder();
                }
                break;
            case HITSMORESTUFF:
                for (var o : observers) {
                    //do more stuff
                }
                break;
        }
    }
    @Override
    public void addEnemyListener(Observer o) {
        observers.add(o);
    }
    @Override
    public void addShooterListener(Observer o) {
    }

    @Override
    public void notifyObserver(Shooter.myEvent event) {
    }

}
