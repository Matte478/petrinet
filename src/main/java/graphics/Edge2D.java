package graphics;

import petrinet.Edge;
import petrinet.EdgeNormal;
import petrinet.Place;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Edge2D extends Line2D.Float implements Drawable {
    private long id;
    private boolean reset;
    private Edge edge;
    private Place2D place2D;
    private Transition2D transition2D;

    public Edge2D(long id, Edge edge, Place2D source, Transition2D destination, boolean reset) {
        super(source.getXf()+20, source.getYf()+20, destination.getXf()+20, destination.getYf()+20);
        this.id = id;
        this.edge = edge;
        this.place2D = source;
        this.transition2D = destination;
        this.reset = reset;
    }

    public Edge2D(long id, Edge edge, Transition2D source, Place2D destination, boolean reset) {
        super(source.getXf()+20, source.getYf()+20, destination.getXf()+20, destination.getYf()+20);
        this.id = id;
        this.edge = edge;
        this.place2D = destination;
        this.transition2D = source;
        this.reset = reset;
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(this);
        float distanceX = this.x1 < this.x2 ? this.x2 - this.x1 : this.x1 - this.x2;
        float distanceY = this.y1 < this.y2 ? this.y2 - this.y1 : this.y1 - this.y2;

        float x = this.x1 < this.x2 ? this.x1 + distanceX / 2 : this.x2 + distanceX / 2;
        float y = this.y1 < this.y2 ? this.y1 + distanceY / 2 : this.y2 + distanceY / 2;


//        Polygon poly = new Polygon(new int[] { Math.round(this.x2-20),Math.round(this.x2-30),Math.round(this.x2-30) }, new int[] { Math.round(this.y2),Math.round(this.y2-10),Math.round(this.y2+10) }, 3);
//        graphics.fill(poly);

        arrowHead(graphics);

        if(edge instanceof EdgeNormal) {
            if(((EdgeNormal) edge).getWeight() > 1) {
                graphics.drawString(String.valueOf(((EdgeNormal) edge).getWeight()), x + 3, y - 3);
            }
        }
    }

    private void arrowHead(Graphics2D graphics){
        AffineTransform tx = new AffineTransform();
        AffineTransform old_tx = graphics.getTransform();
        Polygon arrowHead = new Polygon(new int[] { 0, -6, 6 }, new int[] { 6, -6, -6 }, 3);

        double dx = (this.x2-this.x1), dy = (this.y2-this.y1);
        double len = Math.sqrt(dx*dx + dy*dy);
        double udx = dx/len, udy = dy/len;
        double cordx = this.x2  - 31 * udx, cordy = this.y2  - 31 * udy;
        double r_cordx = this.x2  - 39 * udx, r_cordy = this.y2  - 39 * udy;
        // vynulovanie transform
        tx.setToIdentity();
        // načitanie uhlu hrany
        double angle = Math.atan2(this.y2 - this.y1, this.x2 - this.x1);
        // nastavenie súradnic na bod kde sa ma vykresľovať
        tx.translate( cordx,  cordy );
        // nastavenie uhla natočenia šípky
        tx.rotate((angle - Math.PI / 2d));
        // nastavenie transormácie pre danú g
        graphics.setTransform(tx);
        if (reset){
            graphics.fill(arrowHead);
            tx.setToIdentity();
            tx.translate(r_cordx,r_cordy);
            tx.rotate((angle - Math.PI / 2d));
            graphics.setTransform(tx);
            graphics.fill(arrowHead);
        } else {
            // vykreslenie šípky
            graphics.fill(arrowHead);
        }
        graphics.setTransform(old_tx);
    }

    public long getId() {
        return id;
    }
}
