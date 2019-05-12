package petrinet.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RunListener implements MouseListener {
    private PetrinetCanvas canvas;

    public RunListener(PetrinetCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        launchTransition(x, y);
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

    private void launchTransition(int x, int y) {
        Transition2D clickedTransition = this.canvas.clickedTransition(x,y);

        if (clickedTransition != null) {
            clickedTransition.launch();
            this.canvas.repaint();
        }
    }
}
