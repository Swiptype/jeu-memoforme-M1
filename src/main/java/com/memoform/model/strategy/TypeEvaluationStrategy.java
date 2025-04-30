package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Stratégie d'évaluation pour la correspondance des types de formes entre un modèle et une réplique.
 * 
 * Cette stratégie évalue la correspondance des types des formes entre le modèle et la réplique.
 * Pour chaque forme du modèle, elle recherche une forme correspondante (même type) dans la réplique.
 * Le score final est calculé en fonction du ratio des formes correctement appariées, et est ajusté
 * par un seuil de pondération.
 * 
 * @see AbstractEvaluationStrategy
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class TypeEvaluationStrategy extends AbstractEvaluationStrategy {

    /**
     * Évalue la correspondance des types de formes.
     * Pour chaque forme du modèle, on cherche une forme correspondante (même type) dans la réplique.
     * 
     * @param model Le modèle des formes à évaluer.
     * @param replica La réplique des formes à comparer.
     * @return Le score d'évaluation pour la correspondance des types de formes, ajusté selon un seuil.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize(); // Nombre total de formes dans le modèle
        if (total == 0) return MAX_SCORE; // Si le modèle est vide, retourner le score maximal

        int correct = 0; // Nombre de formes correctement appariées par type
        // Parcours des formes du modèle
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            // Recherche de la forme correspondante dans la réplique
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    correct++; // Si une correspondance est trouvée, on l'enregistre
                    break; // On passe à la forme suivante du modèle
                }
            }
        }

        // Calcul du ratio de correspondances et application de l'échelle de seuil
        double ratio = (double) correct / total;
        return super.applyThresholdScaling(ratio); // Retourner le score ajusté
    }
}
