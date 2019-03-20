public abstract class Edge {
    private final Place place;
    private final Transition transition;

    public Edge(Point from, Point to) throws IllegalArgumentException {

        if (from instanceof Place && to instanceof Transition) {
            this.place = (Place) from;
            this.transition = (Transition) to;

            this.transition.addInputEdge(this);
        }

        else if (from instanceof Transition && to instanceof Place) {
            this.place = (Place) to;
            this.transition = (Transition) from;

            this.transition.addOutputEdge( (EdgeNormal) this );
        }

        else {
            throw new IllegalArgumentException("dfjiso");
        }

//        if ( source.toLowerCase().equals("place") ) {
//            // I check if this edge already exists
//            for (Edge edge : transition.getInputEdges()) {
//                if (edge.getPlace().equals(place)) {
//                    throw new IllegalArgumentException("You cannot create this edge! Edge to this place from this transition already exists.");
//                }
//            }
//
//            this.startFrom = "place";
//            transition.addInputEdge(this);
//
//        } else if ( source.toLowerCase().equals("transition") ) {
//
//            // I check if this edge already exists
//            for (Edge edge : transition.getOutputEdges()) {
//                if (edge.getPlace().equals(place)) {
//                    throw new IllegalArgumentException("You cannot create this edge! Edge to this place from this transition already exists.");
//                }
//            }
//
//            this.startFrom = "transition";
//            EdgeNormal edge = (EdgeNormal) this;
//            transition.addOutputEdge(edge);
//        } else {
//            throw new IllegalArgumentException("Illegal argument startFrom");
//        }
//
//        this.place = place;
//        this.transition = transition;
    }

    public Place getPlace() {
        return place;
    }

    public Transition getTransition() {
        return transition;
    }
}
