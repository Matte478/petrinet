public class EdgeReset extends Edge {

    public EdgeReset(Vertex from, Vertex to) throws IllegalVertexException {
        super(from, to);

        if( !(from instanceof Place && to instanceof Transition) ) {
            throw new IllegalVertexException("\nReset edge can be only from place to transition!\n");
        }
    }

    @Override
    public void launch() {
        this.getPlace().setToken(0);
    }

    @Override
    public boolean canLaunch() {
        return true;
    }

    @Override
    public String toString() {
        return "Edge reset, \t\t\t" + this.getFrom().getName() + " --->> " + this.getTo().getName();
    }
}
