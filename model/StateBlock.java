package model;

import java.awt.*;

public class StateBlock extends GameElement {

    public static final int SIZE = 10;

    public StateBlock(int x, int y) {
        super(x, y, Color.MAGENTA, true, SIZE, SIZE);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (filled) {
            g2.fillRect(x, y, width, height);
        } else {
            g2.drawRect(x, y, width, height);
        }
    }

    @Override
    public void animate() {
        // Block does not move;
    }
}
