package com.memoform.model.container;

import java.util.ArrayList;
import java.util.List;

import com.memoform.model.observer_pattern.EcouteurModele;
import com.memoform.model.observer_pattern.container.AbstractMFShapeContainerObserver;
import com.memoform.model.shape.MFShape;

/**
 * Cette classe représente un conteneur pour des objets de type MFShape.
 * Elle permet d'ajouter, de supprimer, de remplacer des formes et de notifier
 * les changements via le patron de conception Observer.
 * 
 * @see AbstractMFShapeContainerObserver
 * @see EcouteurModele
 * 
 * @see MFShape
 */
public class MFShapeContainer extends AbstractMFShapeContainerObserver implements EcouteurModele {

    private List<MFShape> shapes = new ArrayList<>();  // Liste pour stocker les formes

    /**
     * Retourne la taille du conteneur, c'est-à-dire le nombre de formes qu'il contient.
     *
     * @return Le nombre de formes dans le conteneur.
     */
    public int getSize() {
        return this.shapes.size();
    }

    /**
     * Retourne la forme à l'index spécifié.
     *
     * @param index L'index de la forme à récupérer.
     * @return La forme à l'index spécifié.
     */
    public MFShape getElementAt(int index) {
        return this.shapes.get(index);
    }

    /**
     * Ajoute une forme au conteneur et notifie l'ajout via l'observateur.
     *
     * @param shape La forme à ajouter.
     */
    public void add(MFShape shape) {
        this.shapes.add(shape);  // Ajoute la forme à la liste des formes
        shape.ajoutEcouteur(this);  // Ajoute l'observateur (lui-même) à la forme
        this.fireShapeAdded(shape);  // Notifie l'ajout de la forme
    }

    /**
     * Supprime une forme du conteneur et notifie la suppression via l'observateur.
     *
     * @param shape La forme à supprimer.
     */
    public void remove(MFShape shape) {
        this.shapes.remove(shape);  // Supprime la forme de la liste des formes
        shape.retraitEcouteur(this);  // Retire l'observateur (lui-même) de la forme
        this.fireShapeRemoved(shape);  // Notifie la suppression de la forme
    }

    /**
     * Remplace une forme existante par une nouvelle dans le conteneur.
     * L'ancienne forme est supprimée et la nouvelle est ajoutée.
     *
     * @param oldShape La forme à remplacer.
     * @param newShape La nouvelle forme qui remplace l'ancienne.
     */
    public void replace(MFShape oldShape, MFShape newShape) {
        this.add(newShape);  // Ajoute la nouvelle forme
        this.remove(oldShape);  // Supprime l'ancienne forme
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du conteneur,
     * incluant toutes les formes qu'il contient.
     *
     * @return La représentation sous forme de chaîne du conteneur et de ses formes.
     */
    @Override
    public String toString() {
        String indent = "   ";
        String toString = this.getClass().getSimpleName() + "{";
        if (this.getSize() > 0) toString += "\n";  // Si le conteneur n'est pas vide, ajout d'une nouvelle ligne
        for (MFShape shape : shapes) {
            toString += indent + shape + "\n";  // Ajoute chaque forme à la chaîne
        }
        toString += "}";
        return toString;
    }

    /**
     * Méthode qui est appelée pour mettre à jour le modèle lorsque la forme change.
     * Elle est appelée par les objets observés (les formes).
     *
     * @param source L'objet source de la mise à jour, ici une instance de MFShape.
     */
    @Override
    public void modeleMAJ(Object source) {
        if (!(source instanceof MFShape)) return;  // Vérifie que l'objet source est une instance de MFShape
        this.fireShapeChanged((MFShape) source);  // Notifie que la forme a changé
    }
}
