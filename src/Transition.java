import java.util.Vector;

public class Transition {
    private long id;
    private final String name;
    private Vector<Edge> inputEdges = new Vector<>();
    private Vector<Edge> outputEdges = new Vector<>();

    public Transition(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Vector<Edge> getInputEdges() {
        return inputEdges;
    }

    public Vector<Edge> getOutputEdges() {
        return outputEdges;
    }

    public void addInputEdge(Edge inputEdge) {
        this.inputEdges.add(inputEdge);
    }

    public void addOutputEdge(Edge outputEdge) {
        this.outputEdges.add(outputEdge);
    }

    public void start() {
        if(canStart()) {
            for (Edge edge : inputEdges) {
                int token = edge.getPlace().getToken() - edge.getWeight();
                edge.getPlace().setToken(token);
            }
            for (Edge edge : outputEdges) {
                int token = edge.getPlace().getToken() + edge.getWeight();
                edge.getPlace().setToken(token);
            }
        } else {
            System.out.println("You cannot start this transition!");
        }
    }

    private boolean canStart() {
        for (Edge edge : inputEdges) {
            if (edge.getWeight() > edge.getPlace().getToken()) {
                return false;
            }
        }
        return true;
    }
}
