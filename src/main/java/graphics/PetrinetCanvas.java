package graphics;

import petrinet.Edge;
import petrinet.Place;
import petrinet.Transition;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class PetrinetCanvas extends Canvas implements MouseListener {
    private Vector<Place2D> places;
    private Vector<Transition2D> transitions;
    private Vector<Edge2D> edges;

    public PetrinetCanvas() {
        this.places = new Vector<>();
        this.transitions = new Vector<>();
        this.edges = new Vector<>();
        this.addMouseListener(this);
    }


    public void addPlace(int x, int y, Place place) {
        this.places.add(new Place2D(x, y, place));
    }

    public void addTransition(int x, int y, Transition transition) {
        this.transitions.add(new Transition2D(x, y, transition));
    }

    public void addEdge(Edge edge, long sourceId, long destinationId, boolean reset) {
        Place2D place = getPlaceById(sourceId);
        Transition2D transition = getTransitionById(destinationId);

        if(place != null && transition != null) {
            addEdge2D(edge, place, transition, reset);
            return;
        }

        transition = getTransitionById(sourceId);
        place = getPlaceById(destinationId);

        addEdge2D(edge, transition, place, reset);
    }

    private void addEdge2D(Edge edge, Place2D source, Transition2D destination, boolean reset) {
        this.edges.add(new Edge2D(edge, source, destination, reset));
    }
    private void addEdge2D(Edge edge, Transition2D source, Place2D destination, boolean reset) {
        this.edges.add(new Edge2D(edge, source, destination, reset));
    }

    private Place2D getPlaceById(long id) {
        for(Place2D place : this.places) {
            if (place.getId() == id) {
                return place;
            }
        }

        return null;
    }

    private Transition2D getTransitionById(long id) {
        for(Transition2D transition : this.transitions) {
            if (transition.getId() == id) {
                return transition;
            }
        }

        return null;
    }

    public int getPetrinetWidth() {
        int width = 0;

        for (Place2D place : places) {
            int placeX = (int) place.getX();
            if(placeX > width) width = placeX;
        }

        for (Transition2D transition : transitions) {
            int transitionX = (int) transition.getX();
            if(transitionX > width) width = transitionX;
        }

        // 40 is size of element
        return (width + 40);
    }

    public int getPetrinetHeight() {
        int height = 0;

        for (Place2D place : places) {
            int placeY = (int) place.getY();
            if(placeY > height) height = placeY;
        }

        for (Transition2D transition : transitions) {
            int transitionY = (int) transition.getY();
            if(transitionY > height) height = transitionY;
        }

        // 60 is size of element + label
        return (height + 60);
    }

    public void petrinetSize(int x, int y) {
//        int minX = 0;
        int maxX = 0;
//        int minY = 0;
        int maxY = 0;

        for (Place2D place : places) {
            int placeX = (int) place.getX();
            int placeY = (int) place.getY();

//            setMin(minX, minY, placeX, placeY);
            setMax(maxX, maxY, placeX, placeY);
        }

        for (Transition2D transition : transitions) {
            int transitionX = (int) transition.getX();
            int transitiony = (int) transition.getY();

//            setMin(minX, minY, transitionX, transitiony);
            setMax(maxX, maxY, transitionX, transitiony);
        }

        x = maxX;
        y = maxY;
    }

    private void setMin(int minX, int minY, int x, int y) {
        if(x < minX) minX = x;
        if(y < minY) minY = y;
    }
    private void setMax(int maxX, int maxY, int x, int y) {
        if(x > maxX) maxX = x;
        if(y > maxY) maxY = y;
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Transition2D clickedTransition = clickedTransition(x,y);

        if (clickedTransition != null) {
            clickedTransition.launch();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
