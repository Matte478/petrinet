package petrinet.graphics;

import petrinet.logic.Transition;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Transition2D extends Rectangle2D.Float implements Drawable {

    private Transition transition;

    public Transition2D(float x, float y, Transition transition) {
        super(x, y, 40, 40);
        this.transition = transition;
    }

    public void launch() {
        this.transition.launch();
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

    public void draw(Graphics2D graphics) {
        if(transition.canLaunch()) {
            graphics.setColor(new Color(153, 255, 153));
            graphics.fill(this);
            graphics.setColor(new Color(102, 155, 102));
            graphics.draw(this);
        } else {
            graphics.setColor(new Color(255, 153, 153));
            graphics.fill(this);
            graphics.setColor(new Color(255, 102, 102));
            graphics.draw(this);
        }
        graphics.setColor(Color.BLACK);

        petrinetDrawName(graphics);
    }

    private void petrinetDrawName(Graphics2D graphics) {
        String name = this.transition.getName();

        FontMetrics fm = graphics.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(name, graphics);

        int offset = 20 - Math.round(fm.stringWidth(name)/2);
        int x = (int) this.x + offset;
        int y = (int) this.y + 55;

        graphics.setColor(new Color(255, 255, 255, 180));
        graphics.fillRect(x, y - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());

        graphics.setColor(Color.BLACK);
        graphics.drawString( name, this.x + offset, this.y+55);
    }

    @Override
    public boolean contains(double x, double y) {
        return super.contains(x, y, 1, 1);
    }
}
