public class Petrinet {
    private Place places[];
    private Transition transitions[];
    private Edge edges[];

    public Petrinet(Place[] places, Transition[] transitions, Edge[] edges) {
        this.places = places;
        this.transitions = transitions;
        this.edges = edges;
    }
}
