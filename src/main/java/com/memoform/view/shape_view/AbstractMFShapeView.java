package com.memoform.view.shape_view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import com.memoform.model.shape.MFShape;
import com.memoform.view.shape_view.cast.CasterLink;

/**
 * Classe abstraite représentant une vue générique pour une forme.
 * 
 * Cette classe fournit des fonctionnalités de base pour la gestion de l'affichage d'une forme,
 * ainsi que la gestion de son style graphique. Les classes qui l'étendent doivent implémenter 
 * la méthode pour afficher les bords de la forme.
 * 
 * @see MFShape
 * @see CasterLink
 * @see MFShapeView
 *
 * @param <SHAPE> Le type de la forme que cette vue représente. Ce type doit être une sous-classe de {@link MFShape}.
 */
public abstract class AbstractMFShapeView<SHAPE extends MFShape> extends CasterLink implements MFShapeView<SHAPE> {

    /**
     * Couleur par défaut pour la forme (gris).
     */
    public static Color DEFAULT_COLOR = Color.GRAY;

    /**
     * La forme associée à cette vue.
     */
    protected SHAPE shape;

    /**
     * Map représentant les styles de la forme, incluant les couleurs des bords et du remplissage.
     * Les clés sont des chaînes représentant les attributs du style, telles que "edge_color" et "fill_color".
     */
    protected Map<String,Color> style;

    /**
     * Constructeur pour initialiser la vue avec une forme et des styles par défaut.
     *
     * @param shape La forme à associer à cette vue.
     */
    public AbstractMFShapeView(SHAPE shape) {
        this.shape = shape;
        this.style = new HashMap<>();
        // Définir des styles par défaut : couleur des bords (noir) et couleur de remplissage (rose).
        this.style.put("edge_color", Color.BLACK);
        this.style.put("fill_color", Color.PINK);
    }

    /**
     * Récupère la forme associée à cette vue.
     *
     * @return La forme associée à cette vue.
     */
    public SHAPE getShape() {
        return this.shape;
    }

    /**
     * Définit la forme associée à cette vue.
     *
     * @param shape La forme à associer à cette vue.
     */
    public void setShape(SHAPE shape) {
        this.shape = shape;
    }

    /**
     * Méthode de peinture par défaut pour la forme. 
     * Actuellement, cette méthode ne fait rien mais peut être étendue par des sous-classes.
     * 
     * @param g Le contexte graphique utilisé pour dessiner la forme.
     */
    public void paint(Graphics g) {
        // Affichage des contours pour l'agrandissement d'une forme (enlarge)
        // Cette ligne peut être décommentée pour activer le dessin des bords avec marges.
        //this.paintEdgeWithMargin(g);
    }

    /**
     * Méthode abstraite pour dessiner les bords de la forme avec des marges.
     * Les classes concrètes doivent fournir une implémentation spécifique de cette méthode.
     * 
     * @param g Le contexte graphique utilisé pour dessiner les bords de la forme avec marges.
     */
    public abstract void paintEdgeWithMargin(Graphics g);
}
