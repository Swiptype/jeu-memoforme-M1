package com.memoform.view.control_state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.memoform.model.command.RemoveShape;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;
import com.memoform.view.StateController;

/**
 * État de contrôle responsable de la suppression des formes dans l'application.
 * 
 * Ce mode permet à l'utilisateur de sélectionner une forme en cliquant dessus pour
 * la supprimer de la zone de dessin.
 * 
 * @see AbstractControlState
 * 
 * @see MFShapeContainer
 * @see MFShape
 * @see StateController
 */
public class RemoveState extends AbstractControlState {

    /**
     * Construit un état de suppression lié au contrôleur principal.
     * 
     * @param controller Le contrôleur de l'application.
     */
    public RemoveState(StateController controller) {
        super(controller);
    }

    /**
     * Redéfinit le comportement en cas de passage au mode suppression.
     * Ici, rien n'est fait puisque cet état est déjà celui de suppression.
     */
    @Override
    public void removeMode() {}

    /**
     * Lorsqu'un clic souris est détecté, on vérifie si une forme est sélectionnée.
     * Si une forme est présente à l'endroit du clic, elle est supprimée.
     * 
     * @param me L'événement de souris correspondant au clic.
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        Point p = me.getPoint();
        MFShapeContainer container = this.controller.getContainer();
        
        // Parcours du conteneur de formes pour détecter celle sous le curseur
        for (int i = 0; i < container.getSize(); i++) {
            MFShape shape = container.getElementAt(i);
            if (shape.contains(p.x, p.y)) {
                // Création de la commande de suppression
                RemoveShape rs = new RemoveShape(this.controller.getContainer(), shape);
                // Enregistrement de la suppression pour gestion ultérieure (undo/redo)
                this.controller.getHandler().handle(rs);
                break; // Une seule forme peut être supprimée à la fois
            }
        }
    }
}
