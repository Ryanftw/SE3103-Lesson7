 package model.observerPattern;

 import model.strategyPattern.EnemyCompositeFastStrategy;
 import view.GameBoard;

 public class ShooterObserver implements Observer {

     private GameBoard gameBoard;

     public ShooterObserver(GameBoard gameBoard) {
         this.gameBoard = gameBoard;
     }

     @Override
     public void shooterGotHit() {
         if(gameBoard.getShooter().health > 0) gameBoard.getShooter().health -= 1;

     }

     @Override
     public void shooterGetsPoints() {
         gameBoard.setGamePoints(10);
     }

     @Override
     public void shooterGotHealth() {
         if(gameBoard.getShooter().health < 4) gameBoard.getShooter().health += 1;
     }

     @Override
     public void enemyGetsHarder() {
         gameBoard.getEnemyComposite().setMoveStrategy(new EnemyCompositeFastStrategy(gameBoard.getEnemyComposite()));
     }

 }