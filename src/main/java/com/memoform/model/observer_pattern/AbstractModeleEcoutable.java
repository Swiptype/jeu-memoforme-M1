package com.memoform.model.observer_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite qui implémente le modèle de conception "Observer".
 * Elle permet à un objet de notifier ses écouteurs des changements qui se produisent.
 * Cette classe contient des méthodes pour gérer les écouteurs et déclencher les notifications de changements.
 * 
 * @see ModeleEcoutable
 * 
 * @see EcouteurModele
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

    // Liste des écouteurs enregistrés pour recevoir les notifications
    private List<EcouteurModele> ecouteurs;

    /**
     * Constructeur qui initialise la liste des écouteurs.
     */
    public AbstractModeleEcoutable() {
        this.ecouteurs = new ArrayList<>();
    }

    /**
     * Ajoute un écouteur à la liste des écouteurs.
     * L'écouteur sera notifié lorsqu'un changement se produira.
     *
     * @param e L'écouteur à ajouter.
     */
    public void ajoutEcouteur(EcouteurModele e) {
        this.ecouteurs.add(e);
    }

    /**
     * Retire un écouteur de la liste des écouteurs.
     * L'écouteur ne sera plus notifié des changements après avoir été retiré.
     *
     * @param e L'écouteur à retirer.
     */
    public void retraitEcouteur(EcouteurModele e) {
        this.ecouteurs.remove(e);
    }

    /**
     * Notifie tous les écouteurs enregistrés qu'un changement s'est produit.
     * Cette méthode appelle la méthode `modeleMAJ` de chaque écouteur pour lui transmettre l'information.
     */
    protected void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.modeleMAJ(this);  // Appelle la méthode de mise à jour de chaque écouteur
        }
    }
}
