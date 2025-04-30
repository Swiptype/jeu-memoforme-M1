package com.memoform.model.observer_pattern;

/**
 * Interface qui définit un écouteur pour le modèle observateur.
 * Les classes qui implémentent cette interface doivent être capables de
 * recevoir des notifications de mise à jour lorsque le modèle (l'objet observé) change.
 */
public interface EcouteurModele {

    /**
     * Méthode appelée pour notifier l'écouteur qu'une mise à jour a eu lieu.
     * Lorsqu'un changement survient dans l'objet observé, cette méthode est appelée
     * pour que l'écouteur puisse réagir à cette mise à jour.
     *
     * @param source L'objet qui a subi la mise à jour et qui en informe les écouteurs.
     */
    void modeleMAJ(Object source);
}
