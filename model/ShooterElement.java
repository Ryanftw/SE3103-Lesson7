package model;

import java.awt.*;

public class ShooterElement extends GameElement {

    public static final int SIZE = 20;

    public ShooterElement(int x, int y, Color color, boolean filled, int health) {
        super(x, y, color, filled, SIZE, SIZE, health);
    }

    @Override
    public void render(Graphics2D g2) {
        if (super.filled) {
            g2.fillRect(x, y, width, height);
        } else {
            g2.drawRect(x, y, width, height);
        }
    }

    @Override
    public void animate() {

    }
}
