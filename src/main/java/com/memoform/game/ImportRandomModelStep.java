package com.memoform.game;

import java.util.Random;

import com.memoform.game.MemoForm.MFContainer;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;

/**
 * Étape du jeu {@link MemoForm} permettant de générer et d'importer aléatoirement
 * un ensemble de formes dans un conteneur donné (modèle ou réplique).
 * Cette classe fait partie de la chaîne de responsabilité (hérite de {@link StepLink}).
 * 
 * @see StepLink
 * 
 * @see MemoForm
 * @see MFShapeContainer
 * @see MFShape
 */
public class ImportRandomModelStep extends StepLink {

    /** Générateur de nombres aléatoires pour créer les formes. */
    private final static Random ALEA = new Random();

    /** Constantes de dimensions maximales des formes et de la zone de placement. */
    private final static int 
        MAX_X = 720, MAX_Y = 1080,
        MAX_RADIUS = 100, MAX_SIDE_LENGTH = 100,
        MAX_WIDTH = 100, MAX_HEIGHT= 100;

    /** Types de formes possibles à générer. */
    private static enum Form {
        CIRCLE, RECTANGLE, POLYGON
    } 

    /** Conteneur cible dans lequel importer les formes générées (modèle ou réplique). */
    private MFContainer witchContainer;

    /** Nombre de formes à générer. */
    private int nbShapes;

    /**
     * Constructeur spécifiant le conteneur (modèle ou réplique) et le nombre de formes à générer.
     * 
     * @param witchContainer Le conteneur à remplir (MODEL ou REPLICA).
     * @param nbShapes Le nombre de formes à générer.
     */
    public ImportRandomModelStep(MFContainer witchContainer, int nbShapes) {
        this.witchContainer = witchContainer;
        this.nbShapes = nbShapes;
    }

    /**
     * Constructeur par défaut utilisant le conteneur modèle.
     * 
     * @param nbShapes Le nombre de formes à générer.
     */
    public ImportRandomModelStep(int nbShapes) {
        this(MFContainer.MODEL, nbShapes);
    }

    /**
     * Exécution de l'action locale : génération aléatoire de formes
     * et importation dans le conteneur désigné.
     * 
     * @param game Le jeu {@link MemoForm} en cours.
     */
    @Override
    protected void localAction(MemoForm game) {
        MFShapeContainer container = new MFShapeContainer();
        Form[] values = Form.values();
        for (int i = 0; i < nbShapes; i++) {
            container.add(createShape(values[ALEA.nextInt(values.length)]));
        }

        switch (witchContainer) {
            case MODEL:
                game.setModel(container);
                break;
            case REPLICA:
                game.setReplica(container);
                break;
            default:
                break;
        }
    }

    /**
     * Crée une forme aléatoire du type spécifié.
     * 
     * @param form Le type de forme à créer.
     * @return Une forme aléatoire de type {@link MFShape}.
     */
    private MFShape createShape(Form form) {
        int[] nbSides = new int[]{3, 5 ,8}; // Côtés possibles pour les polygones
        switch (form) {
            case CIRCLE:
                return new Circle(
                    ALEA.nextInt(MAX_X), 
                    ALEA.nextInt(MAX_Y),
                    ALEA.nextInt(MAX_RADIUS)+1
                );

            case RECTANGLE:
                return new Rectangle(
                    ALEA.nextInt(MAX_X), 
                    ALEA.nextInt(MAX_Y), 
                    ALEA.nextInt(MAX_WIDTH)+1, 
                    ALEA.nextInt(MAX_HEIGHT)+1
                );

            case POLYGON:
                return new MFPolygon(
                    ALEA.nextInt(MAX_X), 
                    ALEA.nextInt(MAX_Y), 
                    nbSides[ALEA.nextInt(nbSides.length)], 
                    ALEA.nextInt(MAX_SIDE_LENGTH)+1
                );

            default:
                return null;
        }
    }
}
