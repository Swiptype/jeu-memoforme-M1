package com.memoform.view.control_state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.memoform.model.command.MoveShape;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;
import com.memoform.view.StateController;

/**
 * État de contrôle responsable du déplacement des formes dans l'application.
 * 
 * Ce mode permet à l'utilisateur de sélectionner une forme dans la zone de dessin,
 * puis de la déplacer en fonction des mouvements de la souris.
 * 
 * @see AbstractControlState
 * 
 * @see MFShapeContainer
 * @see MFShape
 * @see StateController
 */
public class MovementState extends AbstractControlState {
    
    /** Point où l'utilisateur a appuyé pour commencer à déplacer une forme. */
    private Point startPoint;

    /** La forme actuellement sélectionnée pour être déplacée. */
    private MFShape selectedShape;

    /**
     * Construit un état de déplacement lié au contrôleur principal.
     * 
     * @param controller Le contrôleur de l'application.
     */
    public MovementState(StateController controller) {
        super(controller);
    }

    /**
     * Redéfinit le comportement en cas de passage au mode déplacement.
     * Ici, rien n'est fait puisque cet état est déjà celui de déplacement.
     */
    @Override
    public void movementMode() {}

    /**
     * Lorsqu'un clic souris est détecté, on vérifie si une forme est sélectionnée.
     * Si une forme est présente à l'endroit du clic, elle devient la forme sélectionnée.
     * 
     * @param me L'événement de souris correspondant à l'appui.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        this.startPoint = me.getPoint();
        MFShapeContainer container = this.controller.getContainer();

        this.selectedShape = null;
        // Parcours du conteneur de formes pour détecter celle sous le curseur
        for (int i = 0; i < container.getSize(); i++) {
            MFShape shape = container.getElementAt(i);
            if (shape.contains(this.startPoint.x, this.startPoint.y)) {
                this.selectedShape = shape;
                break; // Une seule forme peut être sélectionnée
            }
        }
    }

    /**
     * Lorsqu'on déplace la souris, la forme sélectionnée suit le mouvement du curseur.
     * Le panneau de dessin est redessiné pour refléter le changement de position de la forme.
     * 
     * @param me L'événement de glissement de souris.
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        if (this.selectedShape == null) return;

        // Mise à jour de la position de la forme sélectionnée en fonction du mouvement de la souris
        Point p = me.getPoint();
        this.selectedShape.setX(p.x);
        this.selectedShape.setY(p.y);

        // Redessiner le panneau après déplacement
        this.controller.getPanel().repaint();
    }

    /**
     * Lorsqu'on relâche la souris, on calcule le déplacement final de la forme.
     * La commande {@link MoveShape} est créée pour enregistrer ce déplacement.
     * 
     * @param me L'événement de relâchement de souris.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        if (this.selectedShape == null) return;

        Point endPoint = me.getPoint();
        
        // Restauration des coordonnées initiales de la forme avant de calculer le déplacement
        this.selectedShape.setX(this.startPoint.x);
        this.selectedShape.setY(this.startPoint.y);

        // Calcul du déplacement en x et y
        int xV = endPoint.x - this.startPoint.x;
        int yV = endPoint.y - this.startPoint.y;

        // Création de la commande de déplacement
        MoveShape ms = new MoveShape(selectedShape, xV, yV);

        // Enregistrement du déplacement pour gestion ultérieure (undo/redo)
        this.controller.getHandler().handle(ms);
    }
}
