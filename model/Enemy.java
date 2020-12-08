package model;

import java.awt.*;

public class Enemy extends GameElement {

    public Enemy(int x, int y, int size, Color color, boolean filled) {
        super(x, y, color, filled, size, size, 0, 0);
    }

    public Enemy(int x, int y, int size, Color color, boolean filled, int health, int upgrade) {
        super(x, y, color, filled, size, size, health, upgrade);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (filled)
            g2.fillRect(x, y, width, height);
        else
            g2.drawRect(x, y, width, height);
    }

    @Override
    public void animate() {

    }
}
