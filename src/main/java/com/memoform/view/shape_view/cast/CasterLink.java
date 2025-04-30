package com.memoform.view.shape_view.cast;

import com.memoform.model.shape.MFShape;
import com.memoform.view.shape_view.MFShapeView;

/**
 * Classe abstraite représentant un lien dans une chaîne de responsabilités pour le casting de formes.
 * 
 * Cette classe permet de déterminer si une forme donnée appartient à un type particulier, et de
 * renvoyer la vue correspondante pour cette forme. Si le lien actuel ne peut pas traiter la forme,
 * il passe le traitement au lien suivant dans la chaîne.
 * 
 * @see MFShape
 * @see MFShapeView
 */
public abstract class CasterLink {
    
    private CasterLink next = null;

    /**
     * Définit le lien suivant dans la chaîne de responsabilité.
     *
     * @param next Le lien suivant dans la chaîne.
     */
    public final void setNextLink(CasterLink next) {
        this.next = next;
    }

    /**
     * Définit le dernier lien dans la chaîne de responsabilité.
     * Si la chaîne est vide, le lien actuel devient le dernier.
     *
     * @param last Le dernier lien à ajouter.
     */
    public final void setLastLink(CasterLink last) {
        if (this.next == null) {
            this.next = last;
        } else {
            this.next.setLastLink(last);
        }
    }

    /**
     * Méthode abstraite pour vérifier si une forme appartient au type géré par ce lien.
     *
     * @param shape La forme à vérifier.
     * @return true si la forme appartient à ce type, sinon false.
     */
    protected abstract boolean membership(MFShape shape);

    /**
     * Méthode abstraite pour obtenir la vue correspondant à une forme donnée.
     *
     * @param shape La forme à transformer en vue.
     * @return La vue correspondante pour la forme donnée.
     */
    protected abstract MFShapeView<?> getCorrespondingView(MFShape shape);

    /**
     * Obtient la vue correspondant à une forme en parcourant la chaîne de responsabilité.
     * Si la forme est gérée par ce lien, la vue correspondante est renvoyée.
     * Sinon, le lien suivant est interrogé.
     *
     * @param shape La forme pour laquelle obtenir la vue.
     * @return La vue correspondante à la forme, ou null si aucun lien ne gère la forme.
     */
    public final MFShapeView<?> getShapeView(MFShape shape) {
        if (this.membership(shape)) {
            return this.getCorrespondingView(shape);
        }
        if (this.next != null) return this.next.getShapeView(shape);
        else return null;
    }
}
