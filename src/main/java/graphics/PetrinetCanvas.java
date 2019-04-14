package graphics;

import generated.Transition;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class PetrinetCanvas extends Canvas implements MouseListener {
    private Vector<Place2D> places;
    private Vector<Transition2D> transitions;
    private Vector<Edge2D> edges;
//    private Vector<Drawable> drawables;

    public PetrinetCanvas() {
        this.places = new Vector<Place2D>();
        this.transitions = new Vector<Transition2D>();
        this.edges = new Vector<Edge2D>();
        this.addMouseListener(this);
    }

//    public void setDrawables(Vector<Drawable> drawables) {
//        this.drawables = drawables;
//    }

    public void addPlace(Place2D place) {
        this.places.add(place);
    }

    public void addTransition(Transition2D transition) {
        this.transitions.add(transition);
    }

    public void addEdge(Edge2D edge) {
        this.edges.add(edge);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Edge2D edge : edges) {
            edge.draw((Graphics2D) g);
        }
        for (Place2D place : places) {
            place.draw((Graphics2D) g);
        }
        for (Transition2D transition : transitions) {
            transition.draw((Graphics2D) g);
        }

//        for (Place2D place : places) {
//            place.draw((Graphics2D) g);
//        }
//
//        for (Transition2D transition : transitions) {
//            transition.draw((Graphics2D) g);
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Transition2D clickedTransition = clickedTransition(x,y);

        if (clickedTransition != null) {
            clickedTransition.launch();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Transition2D clickedTransition(int x, int y) {
        for (Transition2D transition : transitions) {
            if (transition.contains(x, y)) {
                return transition;
            }
        }

        return null;
    }
}
