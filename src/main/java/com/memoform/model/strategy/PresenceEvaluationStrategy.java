package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;

/**
 * Stratégie d'évaluation qui mesure la présence des formes dans la réplique par rapport au modèle.
 * Elle compare le nombre de formes dans le modèle et dans la réplique, et attribue un score basé sur la similarité de ces nombres.
 * Si le nombre de formes est identique, un score maximal est attribué. Sinon, une pénalité est appliquée.
 * @see AbstractEvaluationStrategy
 * 
 * @see MFShapeContainer
 */
public class PresenceEvaluationStrategy extends AbstractEvaluationStrategy {

    /**
     * Évalue la présence des formes en comparant le nombre de formes entre le modèle et la réplique.
     * Si le nombre de formes est identique dans le modèle et la réplique, le score maximal (100) est attribué.
     * Si les nombres sont différents, une pénalité proportionnelle à la différence est appliquée.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Le score d'évaluation de la présence des formes, normalisé entre 0 et 100.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        int modelCount = model.getSize();    // Nombre de formes dans le modèle
        int replicaCount = replica.getSize(); // Nombre de formes dans la réplique

        if (modelCount == replicaCount) return MAX_SCORE; // Si les nombres sont égaux, score maximal

        // Calcul du ratio de présence, une pénalité est appliquée si les nombres diffèrent
        double ratio = Math.min(modelCount, replicaCount) / (double) Math.max(modelCount, replicaCount);
        
        // Retour du score mis à l'échelle en fonction du seuil
        return super.applyThresholdScaling(ratio);
    }
}
