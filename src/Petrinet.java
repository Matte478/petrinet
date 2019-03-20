import java.util.Vector;

public class Petrinet {
    private Vector<Place> places = new Vector<>();
    private Vector<Transition> transitions = new Vector<>();
    private Vector<Edge> edges = new Vector<>();
    private long counterId = 0;

    public void addPlace(String name, int token) {
        try {
            Place place = new Place(this.counterId, name, token);
            this.places.add(place);
            this.counterId++;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransition(String name) {
        try {
            Transition transition = new Transition(this.counterId, name);
            this.transitions.add(transition);
            this.counterId++;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEdgeNormal(long fromId, long toId, int weight) {
        Point from = getPointById(fromId);
        Point to = getPointById(toId);

        if (from != null && to != null) {
            try {
                EdgeNormal edge = new EdgeNormal(from, to, weight);
                this.edges.add(edge);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Place or transition with this index doesn't exist");
        }
    }

    public void launchTransition(long id) {
        Transition transition = getTransitionById(id);
        if(transition != null) {
            transition.start();
        } else {
            System.out.println("Transition with this index doesn't exist");
        }
    }

    public Place getPlaceById(long id) {
//        places.forEach((n) -> id == n.getId() ?? return n : ''; );
        for (Place place : places) {
            if (place.getId() == id) {
                return place;
            }
        }

        return null;
    }

    public Transition getTransitionById(long id) {
//        places.forEach((n) -> id == n.getId() ?? return n : ''; );
        for (Transition transition : transitions) {
            if (transition.getId() == id) {
                return transition;
            }
        }

        return null;
    }

    private Point getPointById(long id) {
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

    public void print() {
        System.out.println("Places:");
        for (Place place : places) {
            System.out.println("ID: " + place.getId() + " name: " + place.getName() + " token: " + place.getToken());
        }

        System.out.println("Transitions:");
        for (Transition transition : transitions) {
            System.out.println("ID: " + transition.getId() + " name: " + transition.getName());
        }

        System.out.println("Edges:");
        for (Edge edge : edges) {
            System.out.println("Place ID: " + edge.getPlace().getId() + " transition ID: " + edge.getTransition().getId());
        }
    }
}
