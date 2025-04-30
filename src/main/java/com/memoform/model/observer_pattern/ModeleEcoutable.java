package com.memoform.model.observer_pattern;

/**
 * Interface qui définit un modèle écoutable, c'est-à-dire un objet qui peut avoir des écouteurs
 * attachés à lui pour recevoir des notifications de changement.
 * 
 * Les classes qui implémentent cette interface permettent aux écouteurs de s'abonner et de se désabonner,
 * et d'être notifiées lorsqu'un changement survient dans l'objet écoutable.
 */
public interface ModeleEcoutable {
    
    /**
     * Ajoute un écouteur au modèle écoutable.
     * L'écouteur sera notifié des changements qui se produisent dans cet objet.
     *
     * @param e L'écouteur à ajouter.
     */
    void ajoutEcouteur(EcouteurModele e);
    
    /**
     * Retire un écouteur du modèle écoutable.
     * Une fois retiré, l'écouteur ne sera plus notifié des changements de cet objet.
     *
     * @param e L'écouteur à retirer.
     */
    void retraitEcouteur(EcouteurModele e);

}
