package graphics;

import petrinet.Transition;

import java.awt.*;

public class Transition2D extends Rectangle.Float implements Drawable {

    private Transition transition;

    public Transition2D(float x, float y, Transition transition) {
        super(x, y, 40, 40);
        this.transition = transition;
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fill(this);
        graphics.setColor(Color.BLACK);
        graphics.draw(this);
    }

    public float getXf() {
        return this.x;
    }
    public float getYf() {
        return this.y;
    }

    public long getId() {
        return transition.getId();
    }
}
