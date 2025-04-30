package com.memoform.model.observer_pattern.container;

import java.util.ArrayList;
import java.util.List;

import com.memoform.model.shape.MFShape;

/**
 * Classe abstraite qui implémente les méthodes de base pour un observateur d'un conteneur de formes.
 * Cette classe gère une liste d'écouteurs et notifie ces écouteurs lorsqu'un changement se produit
 * dans le conteneur de formes.
 * 
 * @see MFShapeContainerObserver
 */
public abstract class AbstractMFShapeContainerObserver implements MFShapeContainerObserver {
    
    // Liste des écouteurs attachés à cet observateur
    private List<MFShapeContainerListener> listeners = new ArrayList<>();

    /**
     * Ajoute un écouteur à la liste des écouteurs du conteneur de formes.
     * Cet écouteur sera notifié lors de l'ajout, de la suppression ou du changement d'une forme.
     *
     * @param listener L'écouteur à ajouter.
     */
    public void addListener(MFShapeContainerListener listener) {
        listeners.add(listener);
    }

    /**
     * Retire un écouteur de la liste des écouteurs du conteneur de formes.
     * L'écouteur ne sera plus notifié des changements dans le conteneur.
     *
     * @param listener L'écouteur à retirer.
     */
    public void removeListener(MFShapeContainerListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifie tous les écouteurs qu'une forme a été ajoutée au conteneur.
     *
     * @param shape La forme qui a été ajoutée.
     */
    protected void fireShapeAdded(MFShape shape) {
        for (MFShapeContainerListener listener : listeners) {
            listener.shapeAdded(shape);
        }
    }

    /**
     * Notifie tous les écouteurs qu'une forme a été supprimée du conteneur.
     *
     * @param shape La forme qui a été supprimée.
     */
    protected void fireShapeRemoved(MFShape shape) {
        for (MFShapeContainerListener listener : listeners) {
            listener.shapeRemoved(shape);
        }
    }

    /**
     * Notifie tous les écouteurs qu'une forme dans le conteneur a changé.
     *
     * @param shape La forme qui a changé.
     */
    protected void fireShapeChanged(MFShape shape) {
        for (MFShapeContainerListener listener : listeners) {
            listener.shapeChanged(shape);
        }
    }
}
