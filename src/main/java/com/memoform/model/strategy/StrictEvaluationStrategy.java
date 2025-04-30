package com.memoform.model.strategy;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Implémentation stricte de la stratégie d'évaluation.
 * Cette stratégie évalue la correspondance entre les formes du modèle et celles de la réplique
 * en utilisant quatre critères distincts. Chaque critère est attribué un score de 25 points si la
 * correspondance est parfaite. Si une correspondance parfaite n'est pas trouvée pour un critère, 
 * la pénalité est totale pour ce critère, et il ne rapporte aucun point.
 * 
 * Les critères d'évaluation sont les suivants :
 * - La présence des formes.
 * - La correspondance des types de formes.
 * - La correspondance des positions des formes.
 * - La correspondance des tailles des formes.
 * 
 * 
 * @see EvaluationStrategy
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class StrictEvaluationStrategy implements EvaluationStrategy {

    /**
     * Évalue la correspondance entre le modèle et la réplique en utilisant quatre critères stricts.
     * Chaque critère peut rapporter jusqu'à 25 points, pour un total de 100 points.
     * Si un critère ne correspond pas parfaitement, il ne rapporte aucun point.
     * 
     * @param model   Le conteneur de formes représentant le modèle.
     * @param replica Le conteneur de formes représentant la réplique.
     * @return Le score total basé sur les quatre critères d'évaluation.
     */
    @Override
    public double evaluate(MFShapeContainer model, MFShapeContainer replica) {
        double score = 0.0;

        // Évaluation de chaque critère de manière stricte
        score += evaluatePresence(model, replica);    // 25 points maximum
        score += evaluateTypes(model, replica);       // 25 points maximum
        score += evaluatePositions(model, replica);   // 25 points maximum
        score += evaluateSizes(model, replica);       // 25 points maximum

        return score;
    }

    /**
     * Évalue la présence des formes en comparant le nombre de formes.
     * Si le nombre est exactement identique, on attribue 25 points. Sinon, 0.
     * 
     * @param model   Le conteneur de formes représentant le modèle.
     * @param replica Le conteneur de formes représentant la réplique.
     * @return 25 si le nombre de formes est identique, sinon 0.
     */
    private double evaluatePresence(MFShapeContainer model, MFShapeContainer replica) {
        int modelCount = model.getSize();
        int replicaCount = replica.getSize();
        if (modelCount == replicaCount) {
            return 25;
        }
        return 0;
    }

    /**
     * Évalue la correspondance des types de formes.
     * Si chaque forme du modèle a une correspondance exacte en type dans la réplique, on attribue 25 points.
     * Sinon, 0.
     * 
     * @param model   Le conteneur de formes représentant le modèle.
     * @param replica Le conteneur de formes représentant la réplique.
     * @return 25 si toutes les formes ont une correspondance exacte en type, sinon 0.
     */
    private double evaluateTypes(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize();
        if (total == 0) return 25;
        int correct = 0;
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    correct++;
                    break; // Correspondance trouvée pour cette forme
                }
            }
        }
        return correct == total ? 25 : 0; // 25 points si toutes les formes ont une correspondance exacte
    }

    /**
     * Évalue la position des formes.
     * Pour chaque forme du modèle, on cherche la forme correspondante dans la réplique et on calcule la distance
     * entre leurs centres. Si la distance est exactement nulle, on attribue 25 points. Sinon, 0.
     * 
     * @param model   Le conteneur de formes représentant le modèle.
     * @param replica Le conteneur de formes représentant la réplique.
     * @return 25 si toutes les formes ont une position exacte, sinon 0.
     */
    private double evaluatePositions(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize();
        if (total == 0) return 25;
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            boolean foundMatch = false;
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    double dx = shapeModel.getX() - shapeReplica.getX();
                    double dy = shapeModel.getY() - shapeReplica.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance == 0) { // Position parfaite
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                return 0; // Si une position ne correspond pas exactement, on donne 0 points
            }
        }
        return 25;
    }

    /**
     * Évalue la taille des formes en comparant leurs aires.
     * Si l'aire des formes modèle et réplique est exactement identique, on attribue 25 points.
     * Sinon, 0.
     * 
     * @param model   Le conteneur de formes représentant le modèle.
     * @param replica Le conteneur de formes représentant la réplique.
     * @return 25 si toutes les tailles des formes sont identiques, sinon 0.
     */
    private double evaluateSizes(MFShapeContainer model, MFShapeContainer replica) {
        int total = model.getSize();
        if (total == 0) return 25;
        for (int i = 0; i < model.getSize(); i++) {
            MFShape shapeModel = model.getElementAt(i);
            boolean foundMatch = false;
            for (int j = 0; j < replica.getSize(); j++) {
                MFShape shapeReplica = replica.getElementAt(j);
                if (shapeModel.getClass().equals(shapeReplica.getClass())) {
                    double areaModel = shapeModel.getArea();
                    double areaReplica = shapeReplica.getArea();
                    if (areaModel == areaReplica) { // Taille parfaitement identique
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                return 0; // Si une taille ne correspond pas exactement, on donne 0 points
            }
        }
        return 25;
    }
}
