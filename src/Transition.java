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
            throw new IllegalVertexException("Reset edge can be only from place to transition!");
        }
        else if (outputEdge instanceof EdgeNormal) {
            this.outputEdges.add((EdgeNormal) outputEdge);
        }
    }

    public void launch() {

        if(canLaunch()) {
            for (Edge edge : this.inputEdges) {

                // input edge can be normal or reset edge
                // so I check if edge is instance of EdgeNormal or EdgeReset
                if (edge instanceof EdgeNormal) {
                    EdgeNormal edgeNormal = (EdgeNormal) edge;
                    int token = edgeNormal.getPlace().getToken() - edgeNormal.getWeight();
                    edgeNormal.getPlace().setToken(token);
                } else if (edge instanceof EdgeReset) {
                    edge.getPlace().setToken(0);
                }
            }

            // output edge can be only instance of EdgeNormal
            // I cannot have reset edge from transition to place
            for (EdgeNormal edge : outputEdges) {
                int token = edge.getPlace().getToken() + edge.getWeight();
                edge.getPlace().setToken(token);
            }
        } else {
            System.out.println("You cannot launch this transition!");
        }
    }

    private boolean canLaunch() {

        for (Edge edge : this.inputEdges) {
            if (edge instanceof EdgeNormal) {
                EdgeNormal edgeNormal = (EdgeNormal) edge;
                if (edgeNormal.getWeight() > edgeNormal.getPlace().getToken()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Transition ID: " + this.getId() + ", name: " + this.getName();
    }
}
