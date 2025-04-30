package com.memoform.model.shape;

import java.util.Map;

/**
 * Représente un rectangle dans un espace 2D.
 * Hérite de {@link AbstractMFShape}.
 * Cette classe permet de définir un rectangle avec des dimensions (largeur et hauteur),
 * de vérifier si un point est à l'intérieur ou sur le bord du rectangle,
 * et de calculer son aire.
 * 
 * @see AbstractMFShape
 * @see MFShape
 */
public class Rectangle extends AbstractMFShape {

    /**
     * Largeur par défaut du rectangle.
     */
    public static int DEFAULT_WIDTH = 50;

    /**
     * Hauteur par défaut du rectangle.
     */
    public static int DEFAULT_HEIGHT = 30;

    /**
     * La largeur du rectangle.
     */
    private int width;

    /**
     * La hauteur du rectangle.
     */
    private int height;

    /**
     * Crée un rectangle avec une position (x, y) donnée et des dimensions spécifiques.
     *
     * @param x      La position x du rectangle.
     * @param y      La position y du rectangle.
     * @param width  La largeur du rectangle.
     * @param height La hauteur du rectangle.
     */
    public Rectangle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Crée un rectangle avec une position (x, y) donnée et des dimensions par défaut.
     *
     * @param x La position x du rectangle.
     * @param y La position y du rectangle.
     */
    public Rectangle(int x, int y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Crée un rectangle à la position (0, 0) avec des dimensions par défaut.
     */
    public Rectangle() {
        this(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Crée une copie de ce rectangle.
     *
     * @return Un nouveau rectangle avec les mêmes propriétés.
     */
    public MFShape clone() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    /**
     * Obtient la largeur du rectangle.
     *
     * @return La largeur du rectangle.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Définit la largeur du rectangle.
     *
     * @param width La nouvelle largeur du rectangle.
     */
    public void setWidth(int width) {
        this.width = width;
        this.fireChangement();
    }

    /**
     * Obtient la hauteur du rectangle.
     *
     * @return La hauteur du rectangle.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Définit la hauteur du rectangle.
     *
     * @param height La nouvelle hauteur du rectangle.
     */
    public void setHeight(int height) {
        this.height = height;
        this.fireChangement();
    }

    /**
     * Vérifie si un point (x, y) est à l'intérieur du rectangle.
     *
     * @param pointNE Un tableau contenant les coordonnées du coin nord-est du rectangle.
     * @param width   La largeur du rectangle.
     * @param height  La hauteur du rectangle.
     * @param x       La coordonnée x du point à vérifier.
     * @param y       La coordonnée y du point à vérifier.
     * @return true si le point est à l'intérieur du rectangle, sinon false.
     */
    private boolean inRectangle(int[] pointNE, int width, int height, int x, int y) {
        return pointNE[0] <= x && x <= pointNE[0] + width && pointNE[1] <= y && y <= pointNE[1] + height;
    }

    /**
     * Vérifie si un point (x, y) est à l'intérieur du rectangle.
     *
     * @param x La coordonnée x du point à vérifier.
     * @param y La coordonnée y du point à vérifier.
     * @return true si le point est à l'intérieur du rectangle, sinon false.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.inRectangle(new int[]{this.x, this.y}, this.width, this.height, x, y);
    }

    /**
     * Vérifie si un point (x, y) est sur le bord du rectangle.
     * Utilise deux rectangles : un plus petit et un plus grand que le rectangle original.
     *
     * @param x La coordonnée x du point à vérifier.
     * @param y La coordonnée y du point à vérifier.
     * @return true si le point est sur le bord du rectangle, sinon false.
     */
    @Override
    public boolean onEdge(int x, int y) {
        // Rectangle plus petit
        int xMin = this.x + ON_EDGE_MARGIN, yMin = this.y + ON_EDGE_MARGIN;
        int widthMin = this.width - 2 * ON_EDGE_MARGIN, heightMin = this.height - 2 * ON_EDGE_MARGIN;

        // Rectangle plus grand
        int xMax = this.x - ON_EDGE_MARGIN, yMax = this.y - ON_EDGE_MARGIN;
        int widthMax = this.width + 2 * ON_EDGE_MARGIN, heightMax = this.height + 2 * ON_EDGE_MARGIN;

        return !this.inRectangle(new int[]{xMin, yMin}, widthMin, heightMin, x, y) &&
               this.inRectangle(new int[]{xMax, yMax}, widthMax, heightMax, x, y);
    }

    /**
     * Modifie les dimensions et la position du rectangle en fonction des changements spécifiés.
     *
     * @param changes Un map contenant les changements à appliquer :
     *                - "width_add" pour ajouter à la largeur,
     *                - "height_add" pour ajouter à la hauteur,
     *                - "x_move" pour déplacer sur l'axe x,
     *                - "y_move" pour déplacer sur l'axe y.
     */
    @Override
    public void reshape(Map<String, Integer> changes) {
        if (changes.isEmpty()) return;
        if (changes.containsKey("width_add")) this.width += changes.get("width_add");
        if (changes.containsKey("height_add")) this.height += changes.get("height_add");
        if (changes.containsKey("x_move")) this.x += changes.get("x_move");
        if (changes.containsKey("y_move")) this.y += changes.get("y_move");
        this.fireChangement();
    }

    /**
     * Calcule l'aire du rectangle.
     *
     * @return L'aire du rectangle (largeur * hauteur).
     */
    @Override
    public double getArea() {
        return width * height;
    }

    /**
     * Retourne une représentation sous forme de chaîne du rectangle.
     *
     * @return La chaîne représentant le rectangle, incluant sa position, largeur et hauteur.
     */
    @Override
    public String toString() {
        return super.toString() + "[width=" + this.width + ", height=" + this.height + "]";
    }
}
