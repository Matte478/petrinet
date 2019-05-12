package petrinet.graphics;

import petrinet.logic.Petrinet;

public class DeleteButton extends ModeButton {

    public DeleteButton(PetrinetCanvas canvas, Petrinet petrinet) {
        super("Delete", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new DeleteListener(canvas, petrinet));
    }
}
