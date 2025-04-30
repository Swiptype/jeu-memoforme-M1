package com.memoform.model.shape;

import com.memoform.model.utils.GeoTools;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant un polygone régulier. Un polygone est défini par un centre (x, y),
 * un nombre de côtés et la longueur de chaque côté.
 * Cette classe étend {@link AbstractMFShape} et implémente les méthodes spécifiques pour manipuler
 * un polygone, y compris les méthodes pour vérifier si un point est à l'intérieur ou sur le bord du polygone.
 * 
 * @see AbstractMFShape
 * @see MFShape
 */
public class MFPolygon extends AbstractMFShape {

    /**
     * Longueur de côté par défaut du polygone.
     */
    public static int DEFAULT_SIDE_LENGTH = 50;

    /**
     * Nombre de côtés par défaut du polygone.
     */
    public static int DEFAULT_NB_SIDE = 5;

    /**
     * Nombre de côtés du polygone.
     */
    protected final int nbSides;

    /**
     * Longueur de chaque côté du polygone.
     */
    protected int sideLength;
    
    /**
     * Constructeur pour créer un polygone avec des coordonnées spécifiques, un nombre de côtés et une longueur de côté.
     * 
     * @param x la coordonnée X du centre du polygone
     * @param y la coordonnée Y du centre du polygone
     * @param nbSides le nombre de côtés du polygone
     * @param sideLength la longueur de chaque côté du polygone
     * @throws IllegalArgumentException si le nombre de côtés est inférieur à 3
     */
    public MFPolygon(int x, int y, int nbSides, int sideLength) {
        super(x, y);
        if (nbSides < 3) throw new IllegalArgumentException("Une forme a un minimum de 3 côtés !!!");
        this.nbSides = nbSides;
        this.sideLength = sideLength;
    }

    /**
     * Constructeur pour créer un polygone avec des coordonnées spécifiques et un nombre de côtés,
     * utilisant la longueur de côté par défaut.
     * 
     * @param x la coordonnée X du centre du polygone
     * @param y la coordonnée Y du centre du polygone
     * @param nbSides le nombre de côtés du polygone
     */
    public MFPolygon(int x, int y, int nbSides) {
        this(x, y, nbSides, DEFAULT_SIDE_LENGTH);
    }

    /**
     * Constructeur pour créer un polygone avec un nombre de côtés donné et des coordonnées (0, 0),
     * utilisant la longueur de côté par défaut.
     * 
     * @param nbSides le nombre de côtés du polygone
     */
    public MFPolygon(int nbSides) {
        this(0, 0, nbSides, DEFAULT_SIDE_LENGTH);
    }

    /**
     * Constructeur pour créer un polygone avec des coordonnées (0, 0), un nombre de côtés par défaut
     * et une longueur de côté par défaut.
     */
    public MFPolygon() {
        this(0, 0, DEFAULT_NB_SIDE, DEFAULT_SIDE_LENGTH);
    }

    /**
     * Crée une copie exacte de ce polygone.
     * 
     * @return une nouvelle instance de {@link MFPolygon} ayant les mêmes coordonnées,
     *         nombre de côtés et longueur de côté
     */
    public MFShape clone() {
        return new MFPolygon(this.x, this.y, this.nbSides, this.sideLength);
    }

    /**
     * Récupère le nombre de côtés du polygone.
     * 
     * @return le nombre de côtés du polygone
     */
    public int getNbSides() {
        return this.nbSides;
    }

    /**
     * Récupère la longueur de chaque côté du polygone.
     * 
     * @return la longueur de chaque côté du polygone
     */
    public int getSideLength() {
        return this.sideLength;
    }

    /**
     * Définit la longueur de chaque côté du polygone.
     * Après avoir modifié la longueur du côté, un événement de changement est déclenché.
     * 
     * @param sideLength la nouvelle longueur du côté du polygone
     */
    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
        this.fireChangement();
    }

    /**
     * Vérifie si le point donné par ses coordonnées (x, y) se trouve à l'intérieur du polygone.
     * Utilise l'algorithme de "ray-casting" pour déterminer si un point est à l'intérieur.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est à l'intérieur du polygone, sinon false
     */
    @Override
    public boolean contains(int x, int y) {
        List<List<Integer>> vertex = GeoTools.getVertexFromPolygon(this.x, this.y, this.nbSides, this.sideLength);
        return GeoTools.rayCasting(vertex.get(0), vertex.get(1), x, y);
    }

    /**
     * Vérifie si le point donné par ses coordonnées (x, y) se trouve sur le bord du polygone.
     * Utilise une approche similaire à celle de l'intérieur, mais avec des marges supplémentaires
     * pour simuler un contour autour du polygone.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est sur le bord du polygone, sinon false
     */
    @Override
    public boolean onEdge(int x, int y) {
        List<List<Integer>> vertexMin = GeoTools.getVertexFromPolygon(this.x, this.y, this.nbSides, this.sideLength, -2 * ON_EDGE_MARGIN);
        List<List<Integer>> vertexMax = GeoTools.getVertexFromPolygon(this.x, this.y, this.nbSides, this.sideLength, ON_EDGE_MARGIN);
        
        boolean inMin = GeoTools.rayCasting(vertexMin.get(0), vertexMin.get(1), x, y);
        boolean inMax = GeoTools.rayCasting(vertexMax.get(0), vertexMax.get(1), x, y);
        
        return !inMin && inMax;
    }

    /**
     * Redimensionne le polygone en modifiant la longueur du côté selon les changements fournis.
     * Les changements sont fournis sous la forme d'une map où la clé est "side_length_add" pour indiquer
     * l'ajout à la longueur du côté actuel.
     * 
     * @param changes un map contenant les changements à appliquer au polygone
     */
    @Override
    public void reshape(Map<String, Integer> changes) {
        if (changes.isEmpty()) return;
        if (changes.containsKey("side_length_add")) this.sideLength += changes.get("side_length_add");
        this.fireChangement();
    }

    /**
     * Calcule et retourne l'aire du polygone en utilisant la formule géométrique appropriée pour un polygone régulier.
     * 
     * @return l'aire du polygone
     */
    public double getArea() {
        return (nbSides * sideLength * sideLength) / (4 * Math.tan(Math.PI / nbSides));
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du polygone.
     * La chaîne contient les coordonnées du centre du polygone, le nombre de côtés et la longueur des côtés.
     * 
     * @return la représentation sous forme de chaîne de caractères du polygone
     */
    @Override
    public String toString() {
        return super.toString() + "[nbSides=" + this.nbSides + ", sideLength=" + this.sideLength + "]";
    }
}
