package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Stratégie d'évaluation basée sur la taille des formes.
 * Cette stratégie évalue la différence de taille entre les formes du modèle et celles de la réplique.
 * Pour chaque forme du modèle, la différence d'aire est calculée entre la forme modèle et la forme correspondante (forme du même type et la plus proche),
 * puis un score est attribué en fonction de cette différence.
 * 
 * La formule utilisée pour évaluer la taille est la suivante :
 * max(0, 1 - min(1, sizeDiff / MAX_DIFF))
 * où sizeDiff est la différence absolue entre l'aire de la forme modèle et celle de la forme répliquée,
 * et MAX_DIFF est la différence maximale de taille permise pour obtenir un score de 1.
 * 
 * @see AbstractEvaluationStrategy
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class SizeEvaluationStrategy extends AbstractEvaluationStrategy {

    /**
     * La différence maximale de taille autorisée pour obtenir un score de 1.
     * Si la différence entre les tailles des formes est inférieure ou égale à MAX_DIFF,
     * un score proche de 1 est attribué.
     */
    public static double MAX_DIFF = 4000;

    /**
     * Évalue la taille des formes en comparant leurs aires.
     * Pour chaque forme du modèle, la forme la plus proche dans la réplique est trouvée (forme du même type).
     * La différence d'aire entre ces deux formes est utilisée pour calculer le score.
     * La formule utilisée est :
     * max(0, 1 - min(1, sizeDiff / MAX_DIFF))
     * où sizeDiff est la différence absolue entre l'aire des deux formes, et MAX_DIFF est la valeur maximale autorisée pour un score de 1 sinon 0.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduites (réplique).
     * @return Le score d'évaluation basé sur la différence de taille des formes.
     */
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize();
        if (total == 0) return MAX_SCORE; // Si le modèle est vide, renvoie le score maximal.

        double totalScore = 0;

        // Parcours de chaque forme du modèle pour évaluer la taille.
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            double bestDistance = Double.MAX_VALUE;
            double sizeDiff = Double.MAX_VALUE;

            // Recherche de la forme correspondante dans la réplique.
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    double dx = shapeModel.getX() - shapeReplica.getX();
                    double dy = shapeModel.getY() - shapeReplica.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    // Si cette forme est la plus proche, on met à jour la différence de taille.
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        sizeDiff = Math.abs(shapeModel.getArea() - shapeReplica.getArea());
                    }
                }
            }

            // Calcul du score pour la forme en fonction de la différence de taille.
            double scoreForShape = Math.max(0, 1 - Math.min(1, sizeDiff / MAX_DIFF));
            totalScore += scoreForShape;
        }

        // Calcul du score moyen basé sur toutes les formes du modèle.
        double ratio = totalScore / total;
        return super.applyThresholdScaling(ratio); // Applique un redimensionnement du score.
    }
}
