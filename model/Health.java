package model;

import java.awt.*;

public class Health extends GameElement {

    public static final int SIZE = 5;
    public static final int UNIT_MOVE = 5;

    public Health(int x, int y) {
        super(x, y, Color.blue, true, SIZE, SIZE * 2);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (filled) {
            g2.fillOval(x, y, width, height);
        } else {
            g2.drawOval(x, y, width, height);
        }
    }

    @Override
    public void animate() {
        // health does not move
    }
}
