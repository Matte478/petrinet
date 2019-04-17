package petrinet;

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
            Vector<Edge> normal = new Vector<>();
            Vector<Edge> reset = new Vector<>();

            filterEdges(normal, reset, this.inputEdges);

            for (Edge edge : normal) {
                edge.transfer();
            }

            for (Edge edge : reset) {
                edge.transfer();
            }

            for (EdgeNormal edge : outputEdges) {
                edge.transfer();
            }
        } else {
            System.out.println("\nYou cannot launch this transition!\n");
        }
    }

    private void filterEdges(Vector<Edge> normal, Vector<Edge> reset,  Vector<Edge> edges) {
        for (Edge edge : edges) {
            if (edge instanceof EdgeNormal) {
                normal.add(edge);
            } else if (edge instanceof EdgeReset) {
                reset.add(edge);
            }
        }
    }

    public boolean canLaunch() {
        for (Edge edge : this.inputEdges) {

            if (!edge.canTransfer()) {
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
