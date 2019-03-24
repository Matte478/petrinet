public abstract class Edge {
    private final Vertex from;
    private final Vertex to;


    public Edge(Vertex from, Vertex to) throws IllegalVertexException {

        if(from instanceof Place && to instanceof Transition) {
            this.from = from;
            this.to = to;

            ((Transition) to).addInputEdge(this);
        }

        else if (from instanceof Transition && to instanceof Place) {
            this.from = from;
            this.to = to;

            ((Transition) from).addOutputEdge(this);
        }

        else {
            throw new IllegalVertexException("\nEdge can be only from place to transition or vice versa!\n");
        }
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public Place getPlace() {
        if(this.from instanceof Place) {
            return (Place) this.from;
        }

        return (Place) this.to;
    }

    public Transition getTransition() {
        if(this.from instanceof Transition) {
            return (Transition) this.from;
        }

        return (Transition) this.to;
    }

    @Override
    public String toString() {
        return this.getFrom().getName() + "--->" + this.getTo().getName();
    }

    public abstract void transfer();
    public abstract boolean canTransfer();
}
