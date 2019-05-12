package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public abstract class ModeButton extends Button {
    protected final PetrinetCanvas canvas;
    protected final Petrinet petrinet;

    public ModeButton(String label, PetrinetCanvas canvas, Petrinet petrinet) {
        this.setLabel(label);
        this.canvas = canvas;
        this.petrinet = petrinet;
        this.addActionListener(createActionListener());
    }

    protected ActionListener createActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeListeners();
                setModeListener();
            }
        };
    }

    protected void removeListeners() {
        MouseListener[] listeners = this.canvas.getMouseListeners();
        for(MouseListener listener : listeners) {
            this.canvas.removeMouseListener(listener);
        }
    }

    protected abstract void setModeListener();

}
