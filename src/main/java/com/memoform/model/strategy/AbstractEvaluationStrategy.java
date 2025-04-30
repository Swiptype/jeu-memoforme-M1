package com.memoform.model.strategy;

/**
 * Classe abstraite pour les stratégies d'évaluation des formes.
 * Cette classe implémente l'interface {@link EvaluationStrategy} et fournit une logique de base pour l'évaluation en fonction d'un seuil.
 * 
 * @see EvaluationStrategy
 */
public abstract class AbstractEvaluationStrategy implements EvaluationStrategy {

    /**
     * Le seuil par défaut utilisé dans l'évaluation.
     */
    public static double DEFAULT_THRESHOLD = 0;

    /**
     * Le seuil utilisé pour déterminer si une évaluation doit être ajustée.
     * Si le ratio est inférieur à ce seuil, le score sera ajusté à 0.
     */
    protected double threshold;

    /**
     * Constructeur de {@link AbstractEvaluationStrategy} avec un seuil spécifié.
     *
     * @param threshold Le seuil à appliquer pour ajuster l'évaluation.
     */
    public AbstractEvaluationStrategy(double threshold) {
        this.threshold = threshold;
    }

    /**
     * Constructeur de {@link AbstractEvaluationStrategy} utilisant le seuil par défaut.
     */
    public AbstractEvaluationStrategy() {
        this(DEFAULT_THRESHOLD);
    }

    /**
     * Applique un ajustement basé sur un seuil au ratio d'évaluation. Si le ratio est inférieur au seuil, le score devient 0.
     * Si le ratio est supérieur ou égal au seuil, un score normalisé entre 0 et {@link EvaluationStrategy#MAX_SCORE} est calculé.
     *
     * @param ratio Le ratio d'évaluation (devrait être entre 0 et 1).
     * @return Un score normalisé entre 0 et {@link EvaluationStrategy#MAX_SCORE} ou 0 si le ratio est inférieur au seuil.
     * @throws IllegalArgumentException Si le ratio est inférieur à 0 ou supérieur à 1.
     */
    protected final double applyThresholdScaling(double ratio) {
        if (0 > ratio || ratio > 1) throw new IllegalArgumentException("Le ratio doit être entre 0 et 1.");
        if (ratio == 1) return MAX_SCORE;
        return ratio < this.threshold 
                ? 0
                : MAX_SCORE * ((ratio - this.threshold) / (1 - this.threshold));
    }
}
