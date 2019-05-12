package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddPlaceListener implements MouseListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;

    public AddPlaceListener(PetrinetCanvas canvas, Petrinet petrinet) {
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
        Place2D p = this.canvas.clickedPlace(x, y);
        if(p != null) {
            if(e.getButton() == MouseEvent.BUTTON1) p.incrementToken();
            else if (e.getButton() == MouseEvent.BUTTON3) p.decrementToken();
        } else {
            this.addPlace(x - 20, y - 20);
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

    private void addPlace(int x, int y) {
        this.petrinet.incrementID();
        this.petrinet.addPlace("", 1, this.petrinet.getID());
        canvas.addPlace(x, y, petrinet.getPlaceById(this.petrinet.getID()));
    }
}
