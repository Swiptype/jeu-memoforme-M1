package com.memoform.view.shape_view;

import java.awt.Color;
import java.awt.Graphics;

import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFShape;
import com.memoform.view.shape_view.cast.CasterLink;
import com.memoform.view.shape_view.cast.NonSpecificCaster;

/**
 * Vue représentant un cercle. Cette classe hérite de {@link AbstractMFShapeView} 
 * et permet de dessiner un cercle en utilisant les informations contenues dans l'objet {@link Circle}.
 * Elle gère l'affichage du cercle avec des options de style (couleur de remplissage, couleur des bords).
 * 
 * Elle implémente également l'affichage des cercles avec des marges (minimale et maximale).
 * 
 * @see AbstractMFShapeView
 * 
 * @see MFShape
 * @see Circle
 * @see NonSpecificCaster
 */
public class CircleView extends AbstractMFShapeView<Circle> {

    /**
     * Constructeur pour initialiser la vue du cercle avec la forme {@link Circle}.
     *
     * @param circle Le cercle à associer à cette vue.
     */
    public CircleView(Circle circle) {
        super(circle);
    }

    /**
     * Dessine un cercle avec son remplissage et ses bords.
     * 
     * La méthode utilise les couleurs définies dans le style pour dessiner un cercle rempli et ses bords.
     * Si les couleurs ne sont pas définies, des couleurs par défaut seront utilisées.
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner le cercle.
     */
    @Override
    public void paint(Graphics g) {
        Color previousColor = g.getColor();

        int x = this.shape.getX(), y = this.shape.getY(), radius = this.shape.getRadius();

        // Remplissage du cercle
        g.setColor(this.style.getOrDefault("fill_color", DEFAULT_COLOR));
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Dessin des bords du cercle
        g.setColor(this.style.getOrDefault("edge_color", DEFAULT_COLOR));
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);

        g.setColor(previousColor);
        super.paint(g);
    }

    /**
     * Dessine les bords du cercle avec des marges minimales et maximales.
     * 
     * Cette méthode permet d'afficher deux cercles supplémentaires autour du cercle original :
     * - Un cercle avec un rayon réduit (marge minimale).
     * - Un cercle avec un rayon agrandi (marge maximale).
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner les bords du cercle avec marges.
     */
    @Override
    public void paintEdgeWithMargin(Graphics g) {
        Color previousColor = g.getColor();

        int x = this.shape.getX(), y = this.shape.getY(), radius = this.shape.getRadius();

        g.setColor(Color.RED);

        // Cercle minimal (réduit avec marge)
        int minRadius = radius - MFShape.ON_EDGE_MARGIN;
        g.drawOval(x - minRadius, y - minRadius, minRadius * 2, minRadius * 2);

        // Cercle maximal (agrandi avec marge)
        int maxRadius = radius + MFShape.ON_EDGE_MARGIN;
        g.drawOval(x - maxRadius, y - maxRadius, maxRadius * 2, maxRadius * 2);

        g.setColor(previousColor);
    }

    /**
     * Vérifie si la forme donnée est une instance de {@link Circle}.
     *
     * @param shape La forme à vérifier.
     * @return {@code true} si la forme est un cercle, sinon {@code false}.
     */
    @Override
    protected boolean membership(MFShape shape) {
        return shape instanceof Circle;
    }

    /**
     * Retourne la vue correspondante pour une forme donnée.
     *
     * @param shape La forme à afficher.
     * @return La vue du cercle associée à la forme.
     */
    @Override
    protected MFShapeView<?> getCorrespondingView(MFShape shape) {
        return new CircleView((Circle) shape);
    }
    
    /**
     * Crée un {@link CasterLink} pour lier la forme {@link Circle} à sa vue {@link CircleView}.
     *
     * @return Un lien de type {@link CasterLink} pour créer des vues de {@link Circle}.
     */
    public static CasterLink createViewCaster() {
        return new NonSpecificCaster(Circle.class, CircleView.class);
    }
}
