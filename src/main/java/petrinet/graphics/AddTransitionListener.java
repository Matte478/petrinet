package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddTransitionListener implements MouseListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;

    public AddTransitionListener(PetrinetCanvas canvas, Petrinet petrinet) {
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

        this.addTransition(x-20, y-20);
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

    public void addTransition(int x, int y) {
        this.petrinet.incrementID();
        petrinet.addTransition("", this.petrinet.getID());
        canvas.addTransition(x, y, petrinet.getTransitionById(this.petrinet.getID()));
    }
}
