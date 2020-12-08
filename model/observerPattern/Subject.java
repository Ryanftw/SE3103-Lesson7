package model.observerPattern;

import model.EnemyComposite;
import model.Shooter;

public interface Subject {

    void addShooterListener(Observer o);
    void addEnemyListener(Observer o);
    void notifyObserver(Shooter.myEvent event);
    void notifyEnemyObserver(EnemyComposite.enemyEvent event);

}
