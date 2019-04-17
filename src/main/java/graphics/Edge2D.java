package graphics;

import petrinet.Edge;
import petrinet.EdgeNormal;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Edge2D extends Line2D.Float implements Drawable {
    private Edge edge;
    private boolean reset;

    public Edge2D(Edge edge, Place2D source, Transition2D destination, boolean reset) {
        super(source.getXf()+20, source.getYf()+20, destination.getXf()+20, destination.getYf()+20);
        this.edge = edge;
        this.reset = reset;
    }

    public Edge2D(Edge edge, Transition2D source, Place2D destination, boolean reset) {
        super(source.getXf()+20, source.getYf()+20, destination.getXf()+20, destination.getYf()+20);
        this.edge = edge;
        this.reset = reset;
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(this);
        float distanceX = this.x1 < this.x2 ? this.x2 - this.x1 : this.x1 - this.x2;
        float distanceY = this.y1 < this.y2 ? this.y2 - this.y1 : this.y1 - this.y2;

        float x = this.x1 < this.x2 ? this.x1 + distanceX / 2 : this.x2 + distanceX / 2;
        float y = this.y1 < this.y2 ? this.y1 + distanceY / 2 : this.y2 + distanceY / 2;


        arrowHead(graphics);

        if(!reset) {
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

//    public long getId() {
//        return id;
//    }
}
