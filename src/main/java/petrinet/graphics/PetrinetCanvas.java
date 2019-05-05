package petrinet.graphics;

import petrinet.logic.Edge;
import petrinet.logic.Place;
import petrinet.logic.Transition;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class PetrinetCanvas extends Canvas implements MouseListener {
    private Vector<Place2D> places;
    private Vector<Transition2D> transitions;
    private Vector<Edge2D> edges;
    private CustomAdder customAdder;

//    private int Edge

    public final int ADD_PLACE = 0;
    public final int ADD_TRANSITION = 1;
    public final int ADD_EDGE_NORMAL = 2;
    public final int ADD_EDGE_RESET = 3;
    public final int DELETE = 4;
    public final int RUN = 5;
    private int mode;

    public PetrinetCanvas() {
        super();
        this.places = new Vector<>();
        this.transitions = new Vector<>();
        this.edges = new Vector<>();
        this.customAdder = null;
        this.mode = -1;
        this.addMouseListener(this);
    }

    public Vector<Place2D> getPlaces() {
        return places;
    }

    public Vector<Transition2D> getTransitions() {
        return transitions;
    }

    public Vector<Edge2D> getEdges() {
        return edges;
    }

    public void setCustomAdder(CustomAdder ca) {
        this.customAdder = ca;
    }

    public void setMode(int mode) {
        switch (mode) {
            case ADD_PLACE:
            case ADD_TRANSITION:
            case ADD_EDGE_NORMAL:
            case ADD_EDGE_RESET:
            case DELETE:
            case RUN:
                this.mode = mode;
                break;
            default:
                this.mode = -1;
                break;
        }
    }

    public int getMode() {
        return mode;
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        switch (mode) {
            case ADD_PLACE:
               modeAddPlace(x, y, e);
                break;
            case ADD_TRANSITION:
                modeAddTransition(x, y);
                break;
            case ADD_EDGE_NORMAL:
                modeAddEdge(x, y, false);
                break;
            case ADD_EDGE_RESET:
                modeAddEdge(x, y, true);
                break;
            case RUN:
                modeRun(x, y);
                break;
        }
        repaint();
    }

    private void modeAddPlace(int x, int y, MouseEvent e) {
        Place2D p = clickedPlace(x, y);
        if(p != null) {
            if(e.getButton() == MouseEvent.BUTTON1) p.incrementToken();
            else if (e.getButton() == MouseEvent.BUTTON3) p.decrementToken();
        } else {
            customAdder.addPlace(x - 20, y - 20);
        }
    }

    private void modeAddTransition(int x, int y) {
        customAdder.addTransition(x-20, y-20);
    }

    private void modeAddEdge(int x, int y, boolean reset) {
        Place2D place = clickedPlace(x, y);
        if (place != null) {
            customAdder.addEdge(place.getId(), reset);
            return;
        }

        Transition2D transition = clickedTransition(x, y);
        if(transition != null) {
            customAdder.addEdge(transition.getId(), reset);
        }
    }

    private void modeRun(int x, int y) {
        launchTransition(x, y);
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

    private void launchTransition(int x, int y) {
        Transition2D clickedTransition = clickedTransition(x,y);

        if (clickedTransition != null) {
            clickedTransition.launch();
            repaint();
        }
    }

    private Transition2D clickedTransition(int x, int y) {
        for (Transition2D transition : transitions) {
            if (transition.contains(x, y)) {
                return transition;
            }
        }

        return null;
    }

    private Place2D clickedPlace(int x, int y) {
        for (Place2D place : places) {
            if (place.contains(x, y)) {
                return place;
            }
        }

        return null;
    }
}