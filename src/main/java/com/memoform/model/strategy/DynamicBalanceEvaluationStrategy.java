package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;

/**
 * Stratégie d'évaluation basée sur un équilibre dynamique, qui ajuste les poids des critères de manière 
 * dynamique en fonction des scores des parties précédentes. Elle évalue un conteneur de formes en utilisant 
 * plusieurs critères (présence, types, positions, tailles) et ajuste l'importance de chaque critère selon 
 * les performances passées.
 * 
 * @see EvaluationStrategy
 * 
 * @see MFShapeContainer
 */
public class DynamicBalanceEvaluationStrategy implements EvaluationStrategy {

    /**
     * Tableau pour stocker les scores des critères de la partie précédente.
     * Les scores sont enregistrés dans l'ordre suivant : [présence, types, positions, tailles].
     */
    private double[] previousScores;

    /**
     * Tableau des poids attribués aux critères d'évaluation. Chaque critère a un poids initial de 25%.
     */
    private double[] weights;

    /**
     * Constructeur de {@link DynamicBalanceEvaluationStrategy}, initialisant les scores précédents 
     * et les poids des critères.
     */
    public DynamicBalanceEvaluationStrategy() {
        this.previousScores = new double[4];  // Initialisation des scores
        this.weights = new double[]{25, 25, 25, 25};  // Poids de départ
    }

    /**
     * Évalue la qualité d'un conteneur de formes reproduit par un joueur par rapport au modèle.
     * Le score est calculé en utilisant plusieurs critères d'évaluation (présence, types, positions, tailles) 
     * et en ajustant les poids en fonction des scores des parties précédentes.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Un score d'évaluation normalisé entre 0 et 100, basé sur les critères dynamiquement pondérés.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        // Calcul des scores de la partie actuelle
        double scorePresence = new PresenceEvaluationStrategy().evaluate(model, replica);
        double scoreTypes = new TypeEvaluationStrategy().evaluate(model, replica);
        double scorePositions = new PositionEvaluationStrategy().evaluate(model, replica);
        double scoreSizes = new SizeEvaluationStrategy().evaluate(model, replica);

        // Mémorisation des scores de la partie actuelle pour le prochain calcul
        previousScores[0] = scorePresence;
        previousScores[1] = scoreTypes;
        previousScores[2] = scorePositions;
        previousScores[3] = scoreSizes;

        // Ajustement des poids en fonction des scores de la partie précédente
        adjustWeights(previousScores);

        // Calcul du score final en utilisant les poids ajustés
        double totalScore = 0;
        totalScore += weights[0] * scorePresence / 4;
        totalScore += weights[1] * scoreTypes / 4;
        totalScore += weights[2] * scorePositions / 4;
        totalScore += weights[3] * scoreSizes / 4;

        return totalScore;
    }

    /**
     * Ajuste les poids des critères en fonction des scores des parties précédentes.
     * Si un critère obtient un score élevé (proche de 25), son poids est réduit.
     * Si un critère obtient un score faible (inférieur à 15), son poids est augmenté.
     * Les poids sont ensuite réajustés pour que la somme des poids soit égale à 100 (MAX_SCORE).
     * 
     * @param previousScores Les scores des critères de la partie précédente.
     */
    private void adjustWeights(double[] previousScores) {
        double totalWeight = MAX_SCORE;
        double adjustment = 1.0;  // Valeur d'ajustement pour les poids

        for (int i = 0; i < previousScores.length; i++) {
            double score = previousScores[i];

            if (23 <= score && score <= 25) {
                // Si le score est proche de 25, réduire le poids de ce critère
                weights[i] = Math.max(1, weights[i] - adjustment);
            } else if (score < 15) {
                // Si le score est éloigné de 25, augmenter le poids de ce critère
                weights[i] = Math.min(40, weights[i] + adjustment);
            }
        }

        // Réajuster les poids pour que la somme soit toujours égale à 100 (MAX_SCORE)
        double total = 0;
        for (double weight : weights) {
            total += weight;
        }

        // Ajuster les poids proportionnellement pour que la somme soit 100 (MAX_SCORE)
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] / total * totalWeight;
        }
    }
}
