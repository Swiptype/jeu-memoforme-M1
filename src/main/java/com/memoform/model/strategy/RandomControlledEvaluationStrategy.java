package com.memoform.model.strategy;

import java.util.Random;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Stratégie d'évaluation où les poids des critères sont générés de manière aléatoires.
 * Les poids sont générés aléatoirement, mais la somme des 4 poids est toujours égale à 100.
 * Cette stratégie évalue un modèle et sa réplique en attribuant des poids aléatoires à chaque critère d'évaluation (présence, type, position, taille).
 * Les scores de chaque critère sont ensuite pondérés par ces poids générés.
 * 
 * @see EvaluationStrategy
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class RandomControlledEvaluationStrategy implements EvaluationStrategy {

    private static final Random RANDOM = new Random();

    /**
     * Évalue la réplique par rapport au modèle en utilisant des poids aléatoires.
     * Les scores pour chaque critère sont obtenus via des stratégies d'évaluation spécifiques
     * (présence, type, position, taille), puis pondérés par les poids générés aléatoirement.
     * 
     * @param model   Le conteneur de formes original (modèle).
     * @param replica Le conteneur de formes reproduit par le joueur.
     * @return Le score global d'évaluation pondéré entre 0 et 100.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        // Générer des poids de manière contrôlée
        double[] weights = generateControlledWeights();

        double score = 0.0;

        // Appliquer les poids générés à chaque critère
        score += weights[0] * new PresenceEvaluationStrategy().evaluate(model, replica) / 4;    // Poids pour la présence
        score += weights[1] * new TypeEvaluationStrategy().evaluate(model, replica) / 4;       // Poids pour le type
        score += weights[2] * new PositionEvaluationStrategy().evaluate(model, replica) / 4;   // Poids pour la position
        score += weights[3] * new SizeEvaluationStrategy().evaluate(model, replica) / 4;       // Poids pour la taille

        return score;
    }

    /**
     * Génère 4 poids aléatoires contrôlés, où la somme des poids est toujours égale à 100 (MAX_SCORE).
     * Les poids sont générés de manière à ce que chaque critère ait un poids entre 0 et 100,
     * et la somme totale des poids soit exactement égale à 100.
     * 
     * @return Un tableau de 4 poids aléatoires, dont la somme est égale à 100.
     */
    private double[] generateControlledWeights() {
        double[] weights = new double[4];
        double remaining = MAX_SCORE;

        // Générer un poids aléatoire pour le premier critère
        weights[0] = RANDOM.nextDouble() * remaining;
        remaining -= weights[0];

        // Générer un poids aléatoire pour le deuxième critère
        weights[1] = RANDOM.nextDouble() * remaining;
        remaining -= weights[1];

        // Générer un poids aléatoire pour le troisième critère
        weights[2] = RANDOM.nextDouble() * remaining;
        remaining -= weights[2];

        // Le dernier poids est ce qu'il reste pour que la somme soit exactement 100 (MAX_SCORE)
        weights[3] = remaining;

        return weights;
    }

}
