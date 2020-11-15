package model;

import java.awt.*;
import java.util.ArrayList;

public class Shooter extends GameElement {

    private ArrayList<GameElement> components = new ArrayList<>();

    public Shooter(int x, int y) {
        super(x, y, 0, 0);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x - size, y - size, Color.white, false);
        var s2 = new ShooterElement(x, y - size, Color.white, false);
        var s3 = new ShooterElement(x - size, y, Color.white, false);
        var s4 = new ShooterElement(x, y, Color.white, false);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);
    }

    @Override
    public void render(Graphics2D g2) {
        for( var c: components) {
            c.render(g2);
        }
    }

    @Override
    public void animate() {

    }


}
