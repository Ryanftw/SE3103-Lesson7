package model;
import model.observerPattern.Observer;
import model.observerPattern.Subject;
import java.awt.*;
import java.util.ArrayList;

public class Shooter extends GameElement implements Subject {

    public enum myEvent {
        HITSHEALTH, HITSBOMB, BULLETHITSENEMY
    }

    public static final int UNIT_MOVE = 10;

    private int maxBullets = 3;
    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean upgraded = false;

    //private ShooterShootStrategy shootStrategy;

    public Shooter(int x, int y) {
        super(x, y, 0, 0, 4);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x - size, y - size, Color.white, false, 1);
        var s2 = new ShooterElement(x, y - size, Color.white, false, 1);
        var s3 = new ShooterElement(x - size, y, Color.white, false, 1);
        var s4 = new ShooterElement(x, y, Color.white, false, 1);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);
    }


    public void rebuild() {
        components.clear();
        var size = ShooterElement.SIZE;
        if (this.health == 2) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            var s2 = new ShooterElement(x, y, Color.white, false, 1);
            components.add(s1);
            components.add(s2);
        } else if (this.health == 3) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            var s2 = new ShooterElement(x, y, Color.white, false, 1);
            var s3 = new ShooterElement(x, y - size, Color.white, false, 1);
            components.add(s1);
            components.add(s2);
            components.add(s3);
        } else if (this.health == 4) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            var s2 = new ShooterElement(x, y, Color.white, false, 1);
            var s3 = new ShooterElement(x, y - size, Color.white, false, 1);
            var s4 = new ShooterElement(x - size, y - size, Color.white, false, 1);
            components.add(s1);
            components.add(s2);
            components.add(s3);
            components.add(s4);
        }
    }

    public void minusRebuild() {
        components.clear();
        var size = ShooterElement.SIZE;
        if (this.health == 1) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            components.add(s1);
        } else if (this.health == 2) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            var s2 = new ShooterElement(x, y, Color.white, false, 1);
            components.add(s1);
            components.add(s2);
        } else if (this.health == 3) {
            var s1 = new ShooterElement(x - size, y, Color.white, false, 1);
            var s2 = new ShooterElement(x, y, Color.white, false, 1);
            var s3 = new ShooterElement(x, y - size, Color.white, false, 1);
            components.add(s1);
            components.add(s2);
            components.add(s3);
        }
    }

    public void upgradeWeapon() {
        maxBullets = 9;
        upgraded = true;
    }

    public void downgradeWeapon() {
        maxBullets = 3;
        upgraded = false;
    }

    public boolean getUpgraded() {
        return upgraded;
    }

    public void moveRight() {
        super.x += UNIT_MOVE;
        for (var c : components) {
            c.x += UNIT_MOVE;
        }
    }

    public void moveLeft() {
        super.x -= UNIT_MOVE;
        for (var c : components) {
            c.x -= UNIT_MOVE;
        }
    }

    public boolean canFireMoreBullet() {
        return weapons.size() < maxBullets;
    }

    public void removeBulletsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var w : weapons) {
            if (w.y < 0)
                remove.add(w);
        }
        weapons.removeAll(remove);
    }

    @Override
    public void render(Graphics2D g2) {
        for (var c : components) {
            c.render(g2);
        }

        for (var w : weapons) {
            w.render(g2);
        }
    }

    @Override
    public void animate() {
        for (var w : weapons) {
            w.animate();
        }
    }

    //FINISH THE OBSERVERS AND CREATE THE STRATEGY PATTERN!!!! FUCKING HURRY UP.

    public ArrayList<GameElement> getWeapons() {
        return weapons;
    }

    public ArrayList<GameElement> getComponents() {
        return components;
    }



    //public void setShootStrategy(ShooterShootStrategy shootStrategy) {
    //    this.shootStrategy = shootStrategy;
    //}

    @Override
    public void addShooterListener(Observer o) {
        observers.add(o);
    }

    @Override
    public void addEnemyListener(Observer o) {

    }

    @Override
    public void notifyObserver(myEvent theEvent) {
        switch (theEvent) {
            case HITSHEALTH:
                for (var o : observers) {
                    o.shooterGotHealth();
                }
                break;
            case HITSBOMB:
                for (var o : observers) {
                    o.shooterGotHit();
                }
            case BULLETHITSENEMY:
                for (var o : observers) {
                    o.shooterGetsPoints();
                }
                break;
        }
    }

    @Override
    public void notifyEnemyObserver(EnemyComposite.enemyEvent event) {

    }

}
