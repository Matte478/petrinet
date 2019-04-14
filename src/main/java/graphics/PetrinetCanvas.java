package graphics;

import java.awt.*;
import java.util.Vector;

public class PetrinetCanvas extends Canvas {
    private Vector<Place2D> places;
    private Vector<Transition2D> transitions;
    private Vector<Drawable> drawables;

    public PetrinetCanvas() {
        this.places = new Vector<Place2D>();
        this.transitions = new Vector<Transition2D>();
    }

    public void setDrawables(Vector<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void addPlace(Place2D place) {
        this.places.add(place);
    }

    public void addTransition(Transition2D transition) {
        this.transitions.add(transition);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Drawable drawable : drawables) {
            drawable.draw((Graphics2D) g);
        }

//        for (Place2D place : places) {
//            place.draw((Graphics2D) g);
//        }
//
//        for (Transition2D transition : transitions) {
//            transition.draw((Graphics2D) g);
//        }
    }
}
