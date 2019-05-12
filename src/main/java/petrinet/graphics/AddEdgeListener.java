package petrinet.graphics;

import petrinet.logic.Edge;
import petrinet.logic.EdgeNormal;
import petrinet.logic.Petrinet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddEdgeListener implements MouseListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;
    private boolean reset;

    private long edgeSourceid = -1;
    private long edgeDestinationid = -1;

    public AddEdgeListener(PetrinetCanvas canvas, Petrinet petrinet, boolean reset) {
        this.canvas = canvas;
        this.petrinet = petrinet;
        this.reset = reset;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(!reset && (this.canvas.clickedEdge(x, y) != null)) {
            if(e.getButton() == MouseEvent.BUTTON1) ((EdgeNormal) canvas.clickedEdge(x, y).getEdge()).incrementWeight(1);
            else if (e.getButton() == MouseEvent.BUTTON3) ((EdgeNormal) canvas.clickedEdge(x, y).getEdge()).decrementWeight(1);
        }

        Place2D place = this.canvas.clickedPlace(x, y);
        if (place != null) {
            this.addEdge(place.getId(), reset);
            return;
        }

        Transition2D transition = this.canvas.clickedTransition(x, y);
        if(transition != null) {
            this.addEdge(transition.getId(), reset);
        }

        this.canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void addEdge(long id, boolean reset) {
        if(edgeSourceid < 0) {
            edgeSourceid = id;
        } else if (edgeDestinationid < 0) {
            edgeDestinationid = id;
        }

        if(edgeSourceid >= 0 && edgeDestinationid >= 0) {
            boolean exists = (petrinet.getEdgeNormalBetweenVertex(edgeSourceid, edgeDestinationid) != null) || (petrinet.getEdgeResetBetweenVertex(edgeSourceid, edgeDestinationid) != null);
            if (reset) petrinet.addEdgeReset(edgeSourceid, edgeDestinationid);
            else petrinet.addEdgeNormal(edgeSourceid, edgeDestinationid, 1);

            Edge edge = reset ? petrinet.getEdgeResetBetweenVertex(edgeSourceid, edgeDestinationid) : petrinet.getEdgeNormalBetweenVertex(edgeSourceid, edgeDestinationid);
            if(edge != null & !exists) {
                canvas.addEdge(edge, edgeSourceid, edgeDestinationid, reset);
            }
            resetEdgeIds();
        }
        this.canvas.repaint();
    }

    private void resetEdgeIds() {
        this.edgeSourceid = -1;
        this.edgeDestinationid = -1;
    }
}
