package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Stratégie d'évaluation qui mesure la précision des positions des formes dans la réplique par rapport au modèle.
 * Pour chaque forme du modèle, on calcule la distance entre sa position et la position de la forme correspondante 
 * dans la réplique. Le score est déterminé par la distance entre les formes, où une petite distance génère un score élevé.
 * 
 * @see AbstractEvaluationStrategy
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class PositionEvaluationStrategy extends AbstractEvaluationStrategy {

    /**
     * Facteur de pondération pour la distance entre les formes. Plus ALPHA_POSITION est élevé, plus la distance
     * doit être faible pour obtenir un bon score.
     */
    public static double ALPHA_POSITION = 0.05;

    /**
     * Évalue la position des formes en comparant le modèle et la réplique. 
     * Pour chaque forme du modèle, on cherche la forme correspondante dans la réplique 
     * et on calcule la distance entre leurs centres. Le score pour chaque forme est donné par la formule :
     * exp(-ALPHA_POSITION * distance), où une petite distance donne un score élevé.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Le score d'évaluation de la position des formes, normalisé entre 0 et 100.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize();
        if (total == 0) return MAX_SCORE;

        double totalScore = 0;
        // Parcours de toutes les formes du modèle
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            double bestDistance = Double.MAX_VALUE;
            // Recherche de la forme correspondante dans la réplique
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    // Calcul de la distance entre les deux formes
                    double dx = shapeModel.getX() - shapeReplica.getX();
                    double dy = shapeModel.getY() - shapeReplica.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    // On garde la distance la plus courte
                    if (distance < bestDistance) {
                        bestDistance = distance;
                    }
                }
            }
            // Calcul du score pour cette forme en fonction de la distance
            double scoreForShape = Math.exp(-ALPHA_POSITION * bestDistance);
            totalScore += scoreForShape;
        }
        // Calcul du ratio et application du seuil pour la mise à l'échelle du score
        double ratio = totalScore / total;
        return super.applyThresholdScaling(ratio);
    }
}
