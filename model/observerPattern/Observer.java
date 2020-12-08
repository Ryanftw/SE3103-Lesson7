package model.observerPattern;

public interface Observer {

    void shooterGotHit();

    void shooterGotHealth();

    void shooterGetsPoints();

    void enemyGetsHarder();

    //void enemyHalfHealth();

}