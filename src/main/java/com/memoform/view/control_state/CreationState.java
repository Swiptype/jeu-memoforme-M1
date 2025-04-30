package com.memoform.view.control_state;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import com.memoform.model.command.AddShape;
import com.memoform.model.shape.MFShape;
import com.memoform.view.StateController;

/**
 * État de contrôle responsable de la création de formes dans l'application.
 * 
 * Ce mode permet à l'utilisateur de cliquer dans la zone de dessin pour
 * insérer une nouvelle forme à l'endroit cliqué. La forme à créer est
 * fournie dynamiquement par un {@link Supplier}.
 * 
 * @see AbstractControlState
 * 
 * @see MFShape
 * @see StateController
 */
public class CreationState extends AbstractControlState {
    
    /** Fournisseur de formes permettant de créer dynamiquement des instances. */
    private Supplier<? extends MFShape> classSupplier = null;

    /** La forme actuellement en cours de création. */
    private MFShape currShape = null;

    /** Le point de départ du clic souris pour créer la forme. */
    private Point startPoint = null;

    /**
     * Construit un état de création lié au contrôleur principal.
     * 
     * @param controller Le contrôleur de l'application.
     */
    public CreationState(StateController controller) {
        super(controller);
    }

    /**
     * Définit le fournisseur qui sera utilisé pour créer les nouvelles formes.
     * 
     * @param classSupplier Un {@link Supplier} qui retourne une nouvelle instance de {@link MFShape}.
     */
    public void setShapeSupplier(Supplier<? extends MFShape> classSupplier) {
        this.classSupplier = classSupplier;
    }

    /**
     * Redéfinit le comportement en cas de passage au mode création.
     * Ici, rien n'est fait puisque cet état est déjà celui de création.
     */
    @Override
    public void creationMode() {}

    /**
     * Lorsqu'un clic souris est détecté, une nouvelle forme est créée,
     * positionnée et ajoutée au conteneur via une commande {@link AddShape}.
     * 
     * @param e L'événement de souris correspondant à l'appui.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (this.classSupplier == null) return;

        // Création de la forme
        this.currShape = this.classSupplier.get();

        // Positionnement initial de la forme
        this.startPoint = e.getPoint();
        this.currShape.setX(this.startPoint.x);
        this.currShape.setY(this.startPoint.y);

        // Ajout via la commande AddShape (prise en charge d'Undo/Redo)
        AddShape as = new AddShape(this.controller.getContainer(), this.currShape);        
        this.controller.getHandler().handle(as);
    }

    /**
     * Lorsqu'on glisse la souris après avoir cliqué, aucun comportement n'est défini ici.
     * 
     * @param e L'événement de glissement de souris.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.currShape == null) return;
        // Future amélioration possible : redimensionnement pendant la création
    }

    /**
     * Réinitialise la forme courante lors du relâchement de la souris.
     * 
     * @param e L'événement de relâchement.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.currShape = null;
    }
}
