public class EdgeNormal extends Edge {
    private final int weight;

    public EdgeNormal(Point from, Point to, int weight) throws IllegalArgumentException {
        super(from, to);

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
