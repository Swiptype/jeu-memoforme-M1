package com.memoform.model.shape;

import java.util.Map;

/**
 * Classe représentant un cercle. Un cercle est défini par un centre (x, y) et un rayon.
 * Cette classe étend {@link AbstractMFShape} et implémente les méthodes spécifiques pour manipuler
 * un cercle, y compris les méthodes pour vérifier si un point est à l'intérieur ou sur le bord du cercle.
 * 
 * @see AbstractMFShape
 * @see MFShape
 */
public class Circle extends AbstractMFShape {

    /**
     * Rayon par défaut du cercle.
     */
    public static int DEFAULT_RADIUS = 40;
    
    /**
     * Rayon du cercle.
     */
    private int radius;

    /**
     * Constructeur pour créer un cercle avec des coordonnées spécifiques et un rayon donné.
     * 
     * @param x la coordonnée X du centre du cercle
     * @param y la coordonnée Y du centre du cercle
     * @param radius le rayon du cercle
     */
    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    /**
     * Constructeur pour créer un cercle avec des coordonnées spécifiques et un rayon par défaut.
     * 
     * @param x la coordonnée X du centre du cercle
     * @param y la coordonnée Y du centre du cercle
     */
    public Circle(int x, int y) {
        this(x, y, DEFAULT_RADIUS);
    }

    /**
     * Constructeur pour créer un cercle avec des coordonnées (0, 0) et un rayon par défaut.
     */
    public Circle() {
        this(0, 0, DEFAULT_RADIUS);
    }

    /**
     * Crée une copie exacte de ce cercle.
     * 
     * @return une nouvelle instance de {@link Circle} ayant les mêmes coordonnées et le même rayon
     */
    public MFShape clone() {
        return new Circle(this.x, this.y, this.radius);
    }

    /**
     * Récupère le rayon du cercle.
     * 
     * @return le rayon du cercle
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Définit le rayon du cercle.
     * Après avoir mis à jour le rayon, un événement de changement est déclenché.
     * 
     * @param radius le nouveau rayon du cercle
     */
    public void setRadius(int radius) {
        this.radius = radius;
        this.fireChangement();
    }

    /**
     * Vérifie si le point donné par ses coordonnées (x, y) se trouve à l'intérieur du cercle.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est à l'intérieur du cercle, sinon false
     */
    @Override
    public boolean contains(int x, int y) {
        int distX = x - this.x;
        int distY = y - this.y;
        return distX * distX + distY * distY <= this.radius * this.radius;
    }

    /**
     * Vérifie si le point donné par ses coordonnées (x, y) se trouve sur le bord du cercle.
     * Utilise une approche de bordure en comparant la distance au centre du cercle avec un rayon minimum et maximum,
     * simulant un contour autour du cercle.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est sur le bord du cercle, sinon false
     */
    @Override
    public boolean onEdge(int x, int y) {
        int distX = x - this.x;
        int distY = y - this.y;

        // Simulation de deux cercles : un plus petit, un plus grand
        int minRadius = this.radius - ON_EDGE_MARGIN;
        int maxRadius = this.radius + ON_EDGE_MARGIN;
        return minRadius * minRadius <= distX * distX + distY * distY
                && distX * distX + distY * distY <= maxRadius * maxRadius;
    }

    /**
     * Redimensionne le cercle en modifiant son rayon selon les changements fournis.
     * Les changements sont fournis sous la forme d'une map où la clé est "radius_add" pour indiquer
     * l'ajout au rayon actuel.
     * 
     * @param changes un map contenant les changements à appliquer au cercle
     */
    @Override
    public void reshape(Map<String, Integer> changes) {
        if (!changes.containsKey("radius_add")) return;
        this.radius += changes.get("radius_add");
        this.fireChangement();
    }

    /**
     * Calcule et retourne l'aire du cercle.
     * L'aire est calculée comme étant π * rayon².
     * 
     * @return l'aire du cercle
     */
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du cercle.
     * La chaîne contient les coordonnées du centre du cercle ainsi que son rayon.
     * 
     * @return la représentation sous forme de chaîne de caractères du cercle
     */
    @Override
    public String toString() {
        return super.toString() + "[radius=" + this.radius + "]";
    }
}
