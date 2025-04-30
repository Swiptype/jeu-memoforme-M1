package com.memoform.model.command;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Commande pour supprimer une forme (MFShape) d'un conteneur.
 * Cette commande implémente l'interface {@link Command} et permet de supprimer une forme
 * tout en permettant l'annulation (compensation) de cette suppression.
 * 
 * @see Command
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class RemoveShape implements Command {
    
    private MFShapeContainer container;  // Le conteneur contenant la forme
    private MFShape shape;               // La forme à supprimer

    /**
     * Constructeur de la commande RemoveShape.
     * 
     * @param container Le conteneur contenant la forme à supprimer
     * @param shape La forme à supprimer
     */
    public RemoveShape(MFShapeContainer container, MFShape shape) {
        this.container = container;
        this.shape = shape;
    }

    /**
     * Effectue l'opération de suppression de la forme du conteneur.
     * Cette méthode est appelée pour supprimer la forme du conteneur.
     */
    @Override
    public void operate() {
        this.container.remove(shape);
    }

    /**
     * Compense l'opération de suppression en rajoutant la forme au conteneur.
     * Cette méthode est appelée pour annuler la suppression de la forme.
     */
    @Override
    public void compensate() {
        this.container.add(shape);
    }
}
