package com.memoform.model.observer_pattern.container;

import com.memoform.model.shape.MFShape;

/**
 * Interface permettant d'écouter les événements sur un conteneur de formes.
 * Elle définit les méthodes que tout objet écoutant un conteneur de formes doit implémenter.
 * 
 * L'implémentation de cette interface permet à un objet d'être notifié des changements
 * dans le conteneur de formes, tels que l'ajout, la suppression ou la modification d'une forme.
 * 
 * @see MFShape
 */
public interface MFShapeContainerListener {

    /**
     * Méthode appelée lorsqu'une forme est ajoutée au conteneur.
     *
     * @param shape la forme qui a été ajoutée
     */
    void shapeAdded(MFShape shape);

    /**
     * Méthode appelée lorsqu'une forme est supprimée du conteneur.
     *
     * @param shape la forme qui a été supprimée
     */
    void shapeRemoved(MFShape shape);

    /**
     * Méthode appelée lorsqu'une forme dans le conteneur a changé.
     *
     * @param shape la forme qui a été modifiée
     */
    void shapeChanged(MFShape shape);
}
