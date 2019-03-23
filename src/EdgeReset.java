public class EdgeReset extends Edge {

    public EdgeReset(Vertex from, Vertex to) throws IllegalVertexException {
        super(from, to);
        System.out.println("dsbfj");

        if( !(from instanceof Place && to instanceof Transition) ) {
            throw new IllegalVertexException("Reset edge can be only from place to transition");
        }
    }

    @Override
    public String toString() {
        return "Edge reset, " + this.getFrom().getName() + " --->> " + this.getTo().getName();
    }
}
