package com.memoform.view.shape_view;

import java.awt.Color;
import java.awt.Graphics;

import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;
import com.memoform.view.shape_view.cast.CasterLink;
import com.memoform.view.shape_view.cast.NonSpecificCaster;

/**
 * Vue représentant un rectangle. Cette classe hérite de {@link AbstractMFShapeView} 
 * et permet de dessiner un rectangle à l'aide des informations contenues dans l'objet {@link Rectangle}.
 * Elle gère l'affichage du rectangle avec des options de style (couleur de remplissage, couleur des bords).
 * 
 * Elle implémente également l'affichage des bords du rectangle avec des marges minimales et maximales.
 * 
 * @see AbstractMFShapeView
 * 
 * @see Rectangle
 * @see NonSpecificCaster
 */
public class RectangleView extends AbstractMFShapeView<Rectangle> {

    /**
     * Constructeur pour initialiser la vue du rectangle avec la forme {@link Rectangle}.
     *
     * @param rectangle Le rectangle à associer à cette vue.
     */
    public RectangleView(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Dessine un rectangle avec son remplissage et ses bords.
     * 
     * La méthode utilise les couleurs définies dans le style pour dessiner un rectangle rempli et ses bords.
     * Si les couleurs ne sont pas définies, des couleurs par défaut seront utilisées.
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner le rectangle.
     */
    @Override
    public void paint(Graphics g) {
        Color previousColor = g.getColor();

        // Dessin du rectangle avec remplissage
        g.setColor(this.style.getOrDefault("fill_color", DEFAULT_COLOR));
        g.fillRect(this.shape.getX(), this.shape.getY(), this.shape.getWidth(), this.shape.getHeight());

        // Dessin des bords du rectangle
        g.setColor(this.style.getOrDefault("edge_color", DEFAULT_COLOR));
        g.drawRect(this.shape.getX(), this.shape.getY(), this.shape.getWidth(), this.shape.getHeight());

        // Restauration de la couleur précédente
        g.setColor(previousColor);
        super.paint(g);
    }

    /**
     * Dessine les bords du rectangle avec des marges minimales et maximales.
     * 
     * Cette méthode permet d'afficher deux rectangles supplémentaires autour du rectangle original :
     * - Un rectangle avec une réduction de taille (marge minimale).
     * - Un rectangle avec une augmentation de taille (marge maximale).
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner les bords du rectangle avec marges.
     */
    @Override
    public void paintEdgeWithMargin(Graphics g) {
        Color previousColor = g.getColor();

        int x = this.shape.getX(), y = this.shape.getY();
        int width = this.shape.getWidth(), height = this.shape.getHeight();

        int margin = MFShape.ON_EDGE_MARGIN;
        g.setColor(Color.RED);

        // Dessin du rectangle avec marge minimale
        g.drawRect(x + margin, y + margin, width - 2 * margin, height - 2 * margin);

        // Dessin du rectangle avec marge maximale
        g.drawRect(x - margin, y - margin, width + 2 * margin, height + 2 * margin);

        g.setColor(previousColor);
    }

    /**
     * Vérifie si la forme donnée est une instance de {@link Rectangle}.
     *
     * @param shape La forme à vérifier.
     * @return {@code true} si la forme est un rectangle, sinon {@code false}.
     */
    @Override
    protected boolean membership(MFShape shape) {
        return shape instanceof Rectangle;
    }

    /**
     * Retourne la vue correspondante pour une forme donnée.
     *
     * @param shape La forme à afficher.
     * @return La vue du rectangle associée à la forme.
     */
    @Override
    protected MFShapeView<?> getCorrespondingView(MFShape shape) {
        return new RectangleView((Rectangle) shape);
    }

    /**
     * Crée un {@link CasterLink} pour lier la forme {@link Rectangle} à sa vue {@link RectangleView}.
     *
     * @return Un lien de type {@link CasterLink} pour créer des vues de {@link Rectangle}.
     */
    public static CasterLink createViewCaster() {
        return new NonSpecificCaster(Rectangle.class, RectangleView.class);
    }
}
