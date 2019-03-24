public class EdgeNormal extends Edge {
    private int weight;

    public EdgeNormal(Vertex from, Vertex to, int weight) throws IllegalVertexException, IllegalArgumentException {
        super(from, to);

        if ( weight >= 1 ) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("\nWeight must be > 0!\n");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void incrementWeight(int weight) throws IllegalArgumentException {
        if(weight < 1) {
            throw new IllegalArgumentException("\nWeight must be > 0!\n");
        }

        this.weight+=weight;
    }

    @Override
    public void transfer() {
        if (this.getFrom() instanceof Place && this.getTo() instanceof Transition) {
            int token = this.getPlace().getToken() - this.getWeight();
            this.getPlace().setToken(token);
        } else {
            int token = this.getPlace().getToken() + this.getWeight();
            this.getPlace().setToken(token);
        }
    }

    @Override
    public boolean canTransfer() {
        return ( this.getPlace().getToken() >= this.getWeight() );
    }

    @Override
    public String toString() {
        return "Edge normal, weight: " + this.getWeight() + "  " + this.getFrom().getName() + " ---> " + this.getTo().getName();
    }
}
