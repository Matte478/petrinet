import java.util.Vector;

public class Transition extends Vertex {
    private Vector<Edge> inputEdges = new Vector<>();
    private Vector<EdgeNormal> outputEdges = new Vector<>();

    public Transition(long id, String name) {
        super(id, name);
    }

    public Vector<Edge> getInputEdges() {
        return inputEdges;
    }

    public Vector<EdgeNormal> getOutputEdges() {
        return outputEdges;
    }

    public void addInputEdge(Edge inputEdge) {
        this.inputEdges.add(inputEdge);
    }

    public void addOutputEdge(Edge outputEdge) throws IllegalVertexException {
        if (outputEdge instanceof EdgeReset) {
            throw new IllegalVertexException("\nReset edge can be only from place to transition!\n");
        }
        else if (outputEdge instanceof EdgeNormal) {
            this.outputEdges.add((EdgeNormal) outputEdge);
        }
    }

    public void launch() {
        if(canLaunch()) {
            for (Edge edge : this.inputEdges) {
                edge.launch();
            }
            for (EdgeNormal edge : outputEdges) {
                edge.launch();
            }
        } else {
            System.out.println("\nYou cannot launch this transition!\n");
        }
    }

    private boolean canLaunch() {
        for (Edge edge : this.inputEdges) {
            if (!edge.canLaunch()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Transition ID: " + this.getId() + ", name: " + this.getName();
    }
}
