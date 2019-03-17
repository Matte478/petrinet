public class EdgeNormal extends Edge {
    private final int weight;

    public EdgeNormal(Place place, Transition transition, String source, int weight) throws IllegalArgumentException {
        super(place, transition, source);

        if ( weight >= 1 ) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Illegal argument weight");
        }
    }

    public int getWeight() {
        return weight;
    }
}
