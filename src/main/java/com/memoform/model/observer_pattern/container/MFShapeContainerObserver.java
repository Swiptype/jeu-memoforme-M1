package com.memoform.model.observer_pattern.container;

/**
 * Interface permettant à un conteneur de formes d'ajouter et de retirer des écouteurs.
 * Cette interface est utilisée pour notifier les écouteurs d'événements dans le conteneur,
 * comme l'ajout, la suppression ou la modification des formes.
 * 
 * Un conteneur de formes implémente cette interface pour gérer l'inscription des écouteurs,
 * qui sont ensuite notifiés lorsque des changements surviennent dans le conteneur.
 * 
 * @see MFShapeContainerListener
 */
public interface MFShapeContainerObserver {

    /**
     * Ajoute un écouteur au conteneur de formes.
     * L'écouteur sera notifié lors de changements dans le conteneur, comme l'ajout, 
     * la suppression ou la modification d'une forme.
     * 
     * @param listener l'écouteur à ajouter
     */
    void addListener(MFShapeContainerListener listener);

    /**
     * Retire un écouteur du conteneur de formes.
     * L'écouteur ne sera plus notifié des changements dans le conteneur après son retrait.
     * 
     * @param listener l'écouteur à retirer
     */
    void removeListener(MFShapeContainerListener listener);
}

