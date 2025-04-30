package com.memoform.model.strategy;

import java.util.Arrays;

import com.memoform.model.container.MFShapeContainer;

/**
 * Implémentation simple de la stratégie d’évaluation.
 * Cette stratégie évalue la réplique par rapport au modèle en calculant la moyenne des scores obtenus
 * pour 4 critères différents :
 * 1. Présence des formes
 * 2. Type des formes
 * 3. Position des formes (pénalité exponentielle)
 * 4. Taille des formes (pénalité exponentielle)
 * 
 * La somme des scores des critères est ensuite moyennée pour obtenir un score global compris entre 0 et 100.
 * 
 * @see EvaluationStrategy
 * 
 * @see MFShapeContainer
 */
public class SimpleEvaluationStrategy implements EvaluationStrategy {

    /**
     * Évalue la réplique par rapport au modèle en utilisant 4 critères d’évaluation.
     * Chaque critère obtient un score (présence, type, position, taille) et la moyenne de ces scores
     * est retournée comme score global de l’évaluation.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Le score global d'évaluation en prenant la moyenne des scores des 4 critères.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {   
        // Tableau pour stocker les scores des 4 critères
        double[] values = new double[4];
        
        // Calcul du score pour chaque critère
        values[0] = new SizeEvaluationStrategy().evaluate(model, replica);   // Taille des formes
        values[1] = new PositionEvaluationStrategy().evaluate(model, replica); // Position des formes
        values[2] = new TypeEvaluationStrategy().evaluate(model, replica);    // Type des formes
        values[3] = new PresenceEvaluationStrategy().evaluate(model, replica); // Présence des formes

        // Calcul de la moyenne des scores des 4 critères
        double mean = Arrays.stream(values)
                        .average()
                        .orElse(0.0);
                        
        return mean; // Retourne la moyenne comme score global
    }
}
