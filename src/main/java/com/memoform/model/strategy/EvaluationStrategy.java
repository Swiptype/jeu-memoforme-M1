package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;

/**
 * Interface pour les stratégies d'évaluation des formes.
 * Elle définit la méthode qui permet de comparer un modèle de formes à une réplique reproduite par un joueur et de calculer un score de similarité.
 * 
 * @see MFShapeContainer
 */
public interface EvaluationStrategy {

    /**
     * Le score maximal possible pour une évaluation.
     */
    public final static double MAX_SCORE = 100;

    /**
     * Compare le conteneur modèle et le conteneur reproduit par le joueur pour en calculer un score.
     * Le score doit être normalisé entre 0 et {@link #MAX_SCORE} (100).
     *
     * @param model   Le conteneur de formes original (modèle) à comparer.
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Un score d’évaluation entre 0 et {@link #MAX_SCORE} (100), où 0 représente une réplique très différente et 100 une réplique parfaitement identique.
     */
    double evaluate(MFShapeContainer model, MFShapeContainer replica);
}
