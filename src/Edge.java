public abstract class Edge {
    private final Place place;
    private final Transition transition;
    private final int weight;

    public Edge(Place place, Transition transition, int weight) {
        this.place = place;
        this.transition = transition;
        this.weight = weight;

        transition.addInputEdge(this);
    }

    public Place getPlace() {
        return place;
    }

    public Transition getTransition() {
        return transition;
    }

    public int getWeight() {
        return weight;
    }
}
