package com.memoform.view.shape_view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.List;

import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.MFShape;
import com.memoform.model.utils.GeoTools;
import com.memoform.view.shape_view.cast.CasterLink;
import com.memoform.view.shape_view.cast.NonSpecificCaster;

/**
 * Vue représentant un polygone générique. Cette classe hérite de {@link AbstractMFShapeView} 
 * et permet de dessiner un polygone en utilisant les informations contenues dans l'objet {@link MFPolygon}.
 * Elle gère l'affichage du polygone avec des options de style (couleur de remplissage, couleur des bords).
 * 
 * Elle implémente également l'affichage des bords du polygone avec des marges minimales et maximales.
 * 
 * @see AbstractMFShapeView
 * 
 * @see MFPolygon
 * @see GeoTools
 * @see NonSpecificCaster
 */
public class MFPolygonView extends AbstractMFShapeView<MFPolygon> {

    /**
     * Constructeur pour initialiser la vue du polygone avec la forme {@link MFPolygon}.
     *
     * @param pentagon Le polygone à associer à cette vue.
     */
    public MFPolygonView(MFPolygon pentagon) {
        super(pentagon);
    }

    /**
     * Dessine un polygone avec son remplissage et ses bords.
     * 
     * La méthode utilise les couleurs définies dans le style pour dessiner un polygone rempli et ses bords.
     * Si les couleurs ne sont pas définies, des couleurs par défaut seront utilisées.
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner le polygone.
     */
    @Override
    public void paint(Graphics g) {
        Color previousColor = g.getColor();

        int x = this.shape.getX(), y = this.shape.getY();
        int nbSides = this.shape.getNbSides();
        int sideLength = this.shape.getSideLength();
        
        // Récupère les sommets du polygone
        List<List<Integer>> vertex = GeoTools.getVertexFromPolygon(x, y, nbSides, sideLength);
        int[] xPoints = vertex.get(0).stream().mapToInt(Integer::intValue).toArray();
        int[] yPoints = vertex.get(1).stream().mapToInt(Integer::intValue).toArray();

        Polygon polygon = new Polygon(xPoints, yPoints, nbSides);

        // Dessin du remplissage
        g.setColor(this.style.getOrDefault("fill_color", DEFAULT_COLOR));
        g.fillPolygon(polygon);

        // Dessin des bords
        g.setColor(this.style.getOrDefault("edge_color", DEFAULT_COLOR));
        g.drawPolygon(polygon);

        g.setColor(previousColor);
        super.paint(g);
    }

    /**
     * Dessine les bords du polygone avec des marges minimales et maximales.
     * 
     * Cette méthode permet d'afficher deux polygones supplémentaires autour du polygone original :
     * - Un polygone avec une réduction de taille (marge minimale).
     * - Un polygone avec une augmentation de taille (marge maximale).
     * 
     *
     * @param g Le contexte graphique utilisé pour dessiner les bords du polygone avec marges.
     */
    @Override
    public void paintEdgeWithMargin(Graphics g) {
        Color previousColor = g.getColor();

        int x = this.shape.getX(), y = this.shape.getY();
        int nbSides = this.shape.getNbSides();
        int sideLength = this.shape.getSideLength();

        // Détection des contours avec marges
        List<List<Integer>> vertexMin = GeoTools.getVertexFromPolygon(x, y, nbSides, sideLength, -2 * MFShape.ON_EDGE_MARGIN);
        List<List<Integer>> vertexMax = GeoTools.getVertexFromPolygon(x, y, nbSides, sideLength, MFShape.ON_EDGE_MARGIN);

        g.setColor(Color.RED);
        
        // Dessin des bords avec marge minimale et maximale
        for (int i = 0; i < nbSides; i++) {
            int x1 = vertexMin.get(0).get(i), x2 = vertexMin.get(0).get((i + 1) % vertexMin.get(0).size());
            int y1 = vertexMin.get(1).get(i), y2 = vertexMin.get(1).get((i + 1) % vertexMin.get(1).size());
            g.drawLine(x1, y1, x2, y2);

            x1 = vertexMax.get(0).get(i);
            x2 = vertexMax.get(0).get((i + 1) % vertexMax.get(0).size());
            y1 = vertexMax.get(1).get(i);
            y2 = vertexMax.get(1).get((i + 1) % vertexMax.get(1).size());
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(previousColor);
    }

    /**
     * Vérifie si la forme donnée est une instance de {@link MFPolygon}.
     *
     * @param shape La forme à vérifier.
     * @return {@code true} si la forme est un polygone, sinon {@code false}.
     */
    @Override
    protected boolean membership(MFShape shape) {
        return shape instanceof MFPolygon;
    }

    /**
     * Retourne la vue correspondante pour une forme donnée.
     *
     * @param shape La forme à afficher.
     * @return La vue du polygone associée à la forme.
     */
    @Override
    protected MFShapeView<?> getCorrespondingView(MFShape shape) {
        return new MFPolygonView((MFPolygon) shape);
    }

    /**
     * Crée un {@link CasterLink} pour lier la forme {@link MFPolygon} à sa vue {@link MFPolygonView}.
     *
     * @return Un lien de type {@link CasterLink} pour créer des vues de {@link MFPolygon}.
     */
    public static CasterLink createViewCaster() {
        return new NonSpecificCaster(MFPolygon.class, MFPolygonView.class);
    }
}
