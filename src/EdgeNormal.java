public class EdgeNormal extends Edge {
    private int weight;

    public EdgeNormal(Vertex from, Vertex to, int weight) throws IllegalVertexException, IllegalArgumentException {
        super(from, to);

        if ( weight >= 1 ) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight must be > 0!");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void incrementWeight(int weight) throws IllegalArgumentException {
        if(weight < 1) {
            throw new IllegalArgumentException("Weight must be > 0!");
        }

        this.weight+=weight;
    }

    @Override
    public String toString() {
        return "Edge normal, weight: " + this.getWeight() + "  " + this.getFrom().getName() + " ---> " + this.getTo().getName();
    }
}
