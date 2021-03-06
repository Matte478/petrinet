package petrinet.graphics;

import petrinet.logic.Edge;
import petrinet.logic.Place;
import petrinet.logic.Transition;

import java.awt.*;
import java.util.Vector;

public class PetrinetCanvas extends Canvas {
    private Vector<Place2D> places;
    private Vector<Transition2D> transitions;
    private Vector<Edge2D> edges;

    public PetrinetCanvas() {
        super();
        this.places = new Vector<>();
        this.transitions = new Vector<>();
        this.edges = new Vector<>();
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

    public void removePlace2D(long id) {
        Place2D place = getPlaceById(id);
        if(place != null) {
            Vector<Edge2D> deleteEdges = new Vector<>();
            for (Edge2D e : this.edges) {
                if(e.getSourceId() == place.getId() || e.getDestinationId() == place.getId()) {
                    deleteEdges.add(e);
                }
            }
            this.edges.removeAll(deleteEdges);
            this.places.remove(place);
        }
    }

    public void removeTransition2D(long id) {
        Transition2D transition = getTransitionById(id);
        if(transition != null) {
            Vector<Edge2D> deleteEdges = new Vector<>();
            for (Edge2D edge : this.edges) {
                if(edge.getDestinationId() == transition.getId() || edge.getSourceId() == transition.getId()) {
                    deleteEdges.add(edge);
                }
            }
            this.edges.removeAll(deleteEdges);
            this.transitions.remove(transition);
        }
    }

    public void removeEdge2D(Edge2D edge) {
        this.edges.remove(edge);
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

    public Transition2D clickedTransition(int x, int y) {
        for (Transition2D transition : transitions) {
            if (transition.contains(x, y)) {
                return transition;
            }
        }

        return null;
    }

    public Place2D clickedPlace(int x, int y) {
        for (Place2D place : places) {
            if (place.contains(x, y)) {
                return place;
            }
        }

        return null;
    }

    public Edge2D clickedEdge(int x, int y) {
        int boxX = x - 4;
        int boxY = y - 4;

        int width = 4;
        int height = 4;

        for (Edge2D edge : edges) {
            if (edge.intersects(boxX, boxY, width, height)) {
                return edge;
            }
        }
        return null;
    }
}