package petrinet.graphics;

import petrinet.logic.Edge;
import petrinet.logic.Petrinet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DeleteListener implements MouseListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;

    public DeleteListener(PetrinetCanvas canvas, Petrinet petrinet) {
        this.canvas = canvas;
        this.petrinet = petrinet;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Edge2D edge = this.canvas.clickedEdge(x, y);
        Place2D place = this.canvas.clickedPlace(x, y);
        Transition2D transition = this.canvas.clickedTransition(x, y);

        if(edge != null) {
            this.removeEdge(edge);
        } else if(place != null) {
           this.removePlace(place.getId());
        } else if (transition != null) {
            this.removeTransition(transition.getId());
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

    private void removePlace(long id) {
        this.canvas.removePlace2D(id);
        this.petrinet.removePlace(id);
    }

    private void removeTransition(long id) {
        this.canvas.removeTransition2D(id);
        this.petrinet.removeTransition(id);
    }

    private void removeEdge(Edge2D edge) {
        Edge e = edge.getEdge();
        this.canvas.removeEdge2D(edge);
        this.petrinet.removeEdge(e);
    }
}
