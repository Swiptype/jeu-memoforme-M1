package com.memoform.view.shape_view;

import java.awt.Graphics;

import com.memoform.model.shape.MFShape;

/**
 * Interface représentant une vue pour une forme spécifique.
 * 
 * Cette interface définit les méthodes nécessaires pour afficher une forme à l'écran.
 * Les classes qui implémentent cette interface doivent être capables de peindre la forme ainsi
 * que ses bords avec des marges sur un composant graphique.
 * 
 * @see MFShape
 *
 * @param <SHAPE> Le type de la forme que cette vue représente. Ce type doit être une sous-classe de {@link MFShape}.
 */
public interface MFShapeView<SHAPE extends MFShape> {

    /**
     * Récupère la forme associée à cette vue.
     *
     * @return La forme associée à cette vue.
     */
    SHAPE getShape();

    /**
     * Définit la forme associée à cette vue.
     *
     * @param shape La forme à associer à cette vue.
     */
    void setShape(SHAPE shape);

    /**
     * Peint la forme sur un composant graphique.
     * 
     * Cette méthode est responsable de l'affichage graphique de la forme.
     *
     * @param g Le contexte graphique utilisé pour dessiner la forme.
     */
    void paint(Graphics g);

    /**
     * Peint les bords de la forme avec des marges supplémentaires.
     * 
     * Cette méthode est responsable de l'affichage graphique des bords de la forme,
     * en incluant des marges autour de celle-ci.
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner les bords de la forme avec marges.
     */
    void paintEdgeWithMargin(Graphics g);
}
