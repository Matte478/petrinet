package petrinet.logic;

import java.util.Vector;

public class Petrinet {
    private Vector<Place> places = new Vector<>();
    private Vector<Transition> transitions = new Vector<>();
    private Vector<Edge> edges = new Vector<>();

    public void addPlace(String name, int token, long id) {
        try {
            Place place = new Place(id, name, token);
            this.places.add(place);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransition(String name, long id) {
        try {
            Transition transition = new Transition(id, name);
            this.transitions.add(transition);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * if edge from source to destination exists => increments weight of existing edge
     * else creates new edge
     */
    public void addEdgeNormal(long fromId, long toId, int weight) {
        Vertex from = getVertexById(fromId);
        Vertex to = getVertexById(toId);

        if (from != null && to != null) {
            EdgeNormal edge = getEdgeNormalBetweenVertex(from, to);

            // this edge doesn't exists => I create new
            if (edge == null) {
                createEdgeNormal(from, to, weight);
            }
            // this edge exists => I change only weight
            else {
                edge.incrementWeight(weight);
            }
        }
        else {
            System.out.println("\nPlace or transition with this index doesn't exist!\n");
        }
    }

    private void createEdgeNormal(Vertex from, Vertex to, int weight) {
        try {
            EdgeNormal edgeNew = new EdgeNormal(from, to, weight);
            this.edges.add(edgeNew);
        } catch (IllegalVertexException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addEdgeReset(long fromId, long toId) {
        Vertex from = getVertexById(fromId);
        Vertex to = getVertexById(toId);

        if (from != null && to != null) {
            createEdgeReset(from, to);
        }
        else {
            System.out.println("\nPlace or transition with this index doesn't exist!\n");
        }
    }

    private void createEdgeReset(Vertex from, Vertex to) {
        try {
            EdgeReset edge = new EdgeReset(from, to);
            this.edges.add(edge);
        } catch (IllegalVertexException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removePlace(long id) {
        Place place = getPlaceById(id);
        if(place != null) {
            Vector<Edge> deleteEdges = new Vector<>();
            for (Edge e : this.edges) {
                if(e.getPlace() == place) {
                    deleteEdges.add(e);
                }
            }
            this.edges.removeAll(deleteEdges);
            this.places.remove(place);
        }
    }

    public void removeTransition(long id) {
        Transition transition = getTransitionById(id);
        if(transition != null) {
            Vector<Edge> edges = transition.getInputEdges();
            edges.addAll(transition.getOutputEdges());
            Vector<Edge> deleteEdges = new Vector<>();
            for (Edge e : edges) {
                deleteEdges.add(e);
            }
            this.edges.removeAll(deleteEdges);
            this.transitions.remove(transition);
        }
    }

    public void removeEdge(Edge edge) {
        edge.getTransition().getInputEdges().remove(edge);
        edge.getTransition().getOutputEdges().remove(edge);
        this.edges.remove(edge);
    }

    public void launchTransition(long id) {
        Transition transition = getTransitionById(id);

        if(transition != null) {
            transition.launch();
        } else {
            System.out.println("\nTransition with this index doesn't exist!\n");
        }
    }

    public void print() {
        printPlaces();
        printTransitions();
        printEdges();
    }

    public void printPlaces() {
        System.out.println("Places:");
        for (Place place : places) {
            System.out.println(place);
        }
    }

    public void printTransitions() {
        System.out.println("Transitions:");
        for (Transition transition : transitions) {
            System.out.println(transition);
        }
    }

    public void printEdges() {
        System.out.println("Edges:");
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }

    public Place getPlaceById(long id) {
        for (Place place : places) {
            if (place.getId() == id) {
                return place;
            }
        }

        return null;
    }

    public Transition getTransitionById(long id) {
        for (Transition transition : transitions) {
            if (transition.getId() == id) {
                return transition;
            }
        }

        return null;
    }

    public Vertex getVertexById(long id) {
        Place place = getPlaceById(id);
        if (place != null) {
            return place;
        }

        Transition transition = getTransitionById(id);
        if (transition != null) {
            return transition;
        }

        return null;
    }

    public EdgeNormal getEdgeNormalBetweenVertex(Vertex from, Vertex to) {
        return (EdgeNormal) getEdgeBetweenVertex(from, to, 0);
    }
    public EdgeNormal getEdgeNormalBetweenVertex(long from, long to) {
        Vertex source = getVertexById(from);
        Vertex destination = getVertexById(to);
        return (EdgeNormal) getEdgeBetweenVertex(source, destination, 0);
    }

    public EdgeReset getEdgeResetBetweenVertex(Vertex from, Vertex to) {
        return (EdgeReset) getEdgeBetweenVertex(from, to, 1);
    }
    public EdgeReset getEdgeResetBetweenVertex(long from, long to) {
        Vertex source = getVertexById(from);
        Vertex destination = getVertexById(to);
        return (EdgeReset) getEdgeBetweenVertex(source, destination, 1);
    }


    private Edge getEdgeBetweenVertex(Vertex from, Vertex to, int type) {
        for (Edge edge : edges) {
            if (edge.getFrom() == from && edge.getTo() == to) {
                if (type == 0 && edge instanceof EdgeNormal) {
                    return edge;
                }
                else if (type == 1 && edge instanceof EdgeReset) {
                    return edge;
                }
            }
        }

        return null;
    }
}
