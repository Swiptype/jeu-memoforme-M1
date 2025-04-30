package com.memoform.model.command;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Commande pour ajouter une forme (MFShape) dans un conteneur (MFShapeContainer).
 * Cette commande implémente l'interface {@link Command} et permet d'ajouter une forme
 * dans le conteneur tout en permettant l'annulation (compensation) de cette opération.
 * 
 * @see Command
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class AddShape implements Command {
    
    private MFShapeContainer container;  // Le conteneur dans lequel la forme sera ajoutée
    private MFShape shape;  // La forme à ajouter

    /**
     * Constructeur de la commande AddShape.
     * 
     * @param container Le conteneur dans lequel la forme sera ajoutée
     * @param shape La forme à ajouter dans le conteneur
     */
    public AddShape(MFShapeContainer container, MFShape shape) {
        this.container = container;
        this.shape = shape;
    }

    /**
     * Effectue l'opération d'ajout de la forme dans le conteneur.
     * Cette méthode est appelée pour ajouter la forme dans le conteneur.
     */
    @Override
    public void operate() {
        this.container.add(shape);
    }

    /**
     * Compense l'opération d'ajout en supprimant la forme du conteneur.
     * Cette méthode est appelée pour annuler l'ajout de la forme.
     */
    @Override
    public void compensate() {
        this.container.remove(shape);
    }

}
