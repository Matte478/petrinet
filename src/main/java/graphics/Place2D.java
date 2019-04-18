package graphics;

import petrinet.Place;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Place2D extends Ellipse2D.Float implements Drawable {

    private Place place;

    public Place2D(float x, float y, Place place) {
        super(x, y, 40, 40);
        this.place = place;
    }

    public float getXf() {
        return this.x;
    }
    public float getYf() {
        return this.y;
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fill(this);
        graphics.setColor(Color.BLACK);
        graphics.draw(this);
        graphics.drawString( String.valueOf(place.getToken()), this.x+15, this.y+25);

        petrinetDrawName(graphics);
    }

    private void petrinetDrawName(Graphics2D graphics) {
        String name = this.place.getName();
        int offset = 20 - (name.length())*3;
        int x = (int) this.x + offset;
        int y = (int) this.y + 55;

        FontMetrics fm = graphics.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(this.place.getName(), graphics);

        graphics.setColor(new Color(255, 255, 255, 180));

        graphics.fillRect(x,y - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());

        graphics.setColor(Color.BLACK);
        graphics.drawString( this.place.getName(), this.x + offset, this.y+55);
    }

    public long getId() {
        return place.getId();
    }
}
