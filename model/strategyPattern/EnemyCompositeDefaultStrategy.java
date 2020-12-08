package model.strategyPattern;

import model.EnemyComposite;
import view.GameBoard;

public class EnemyCompositeDefaultStrategy implements EnemyCompositeMoveStrategy {
    public static final int UNIT_MOVE_X = 5;
    public static final int UNIT_MOVE_Y = 20;
    public static final int ENEMY_SIZE = 20;
    private EnemyComposite enemyComposite;
    private boolean movingToRight = true;


    public EnemyCompositeDefaultStrategy(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }
    @Override
    public void moveAlgorithm() {

        int dx = UNIT_MOVE_X;
        int dy = UNIT_MOVE_Y;
        boolean flag = false;
        if (enemyComposite.getMovingToRight()) {
            if (rightEnd() >= GameBoard.WIDTH) {
                dx = -dx;
                enemyComposite.setMovingToRight(false);// = false;
                flag = true;
            }
        } else {
            dx = -dx;
            if (leftEnd() <= 0) {
                dx = -dx;
                enemyComposite.setMovingToRight(true);// = false;
                flag = true;
            }
        }

        // update x loc
        for (var row : enemyComposite.getRows()) {
            for (var e : row) {
                if (flag)
                    e.y += dy;
                e.x += dx;
            }
        }
    }

    private int rightEnd() {
        int xEnd = -100;
        for (var row : enemyComposite.getRows()) {
            if (row.size() == 0)
                continue;
            int x = row.get(row.size() - 1).x + ENEMY_SIZE;
            if (x > xEnd)
                xEnd = x;
        }
        return xEnd;
    }

    private int leftEnd() {
        int xEnd = 9000;
        for (var row : enemyComposite.getRows()) {
            if (row.size() == 0)
                continue;
            int x = row.get(0).x;
            if (x < xEnd)
                xEnd = x;
        }
        return xEnd;
    }

}
