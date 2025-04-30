package com.memoform.model.shape;

import java.util.Map;

import com.memoform.model.observer_pattern.ModeleEcoutable;

/**
 * Interface représentant une forme géométrique dans l'application.
 * Une forme possède des propriétés géométriques, telles que sa position et sa taille,
 * ainsi que des méthodes permettant de la manipuler et de la cloner.
 * Elle implémente l'interface {@link ModeleEcoutable} pour pouvoir être observée
 * par des écouteurs qui réagiront aux changements dans la forme.
 * 
 * @see ModeleEcoutable
 */
public interface MFShape extends ModeleEcoutable {

    /**
     * Marge utilisée pour déterminer si un point est sur le bord de la forme.
     */
    public static int ON_EDGE_MARGIN = 3;

    /**
     * Récupère la position X de la forme.
     * 
     * @return la coordonnée X de la forme
     */
    int getX();

    /**
     * Récupère la position Y de la forme.
     * 
     * @return la coordonnée Y de la forme
     */
    int getY();

    /**
     * Définit la position X de la forme.
     * 
     * @param x la nouvelle coordonnée X
     */
    void setX(int x);

    /**
     * Définit la position Y de la forme.
     * 
     * @param y la nouvelle coordonnée Y
     */
    void setY(int y);

    /**
     * Vérifie si les coordonnées (x, y) se trouvent à l'intérieur de la forme.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est à l'intérieur de la forme, false sinon
     */
    boolean contains(int x, int y);

    /**
     * Vérifie si les coordonnées (x, y) se trouvent sur le bord de la forme.
     * Un point est considéré comme étant sur le bord s'il est dans un rayon défini par {@link #ON_EDGE_MARGIN}.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est sur le bord de la forme, false sinon
     */
    boolean onEdge(int x, int y);

    /**
     * Modifie la forme en fonction des changements spécifiés dans la map.
     * Les changements peuvent inclure des ajustements de taille, de position, etc.
     * 
     * @param changes la map contenant les changements à appliquer
     */
    void reshape(Map<String,Integer> changes);

    /**
     * Crée une copie exacte de la forme.
     * 
     * @return une nouvelle instance de la forme qui est une copie de l'originale
     */
    MFShape clone();

    /**
     * Calcule et retourne l'aire de la forme.
     * 
     * @return l'aire de la forme
     */
    double getArea();
}
