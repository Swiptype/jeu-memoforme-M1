package com.memoform.model.shape;

import com.memoform.model.observer_pattern.AbstractModeleEcoutable;

/**
 * Classe abstraite représentant une forme géométrique de base dans l'application.
 * Cette classe implémente l'interface {@link MFShape} et fournit des méthodes de base
 * pour manipuler les coordonnées (x, y) de la forme. Elle permet également d'envoyer des
 * notifications aux écouteurs à chaque fois que la forme change.
 * 
 * @see MFShape
 * @see AbstractModeleEcoutable
 */
public abstract class AbstractMFShape extends AbstractModeleEcoutable implements MFShape {

    /**
     * Coordonnée X de la forme.
     */
    protected int x, y;

    /**
     * Constructeur pour initialiser une forme avec des coordonnées spécifiées.
     * 
     * @param x la coordonnée X initiale de la forme
     * @param y la coordonnée Y initiale de la forme
     */
    public AbstractMFShape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Récupère la coordonnée X de la forme.
     * 
     * @return la coordonnée X de la forme
     */
    public int getX() {
        return this.x;
    }

    /**
     * Définit la coordonnée X de la forme.
     * Après avoir mis à jour la coordonnée, un événement de changement est déclenché.
     * 
     * @param x la nouvelle coordonnée X de la forme
     */
    public void setX(int x) {
        this.x = x;
        this.fireChangement();
    }

    /**
     * Récupère la coordonnée Y de la forme.
     * 
     * @return la coordonnée Y de la forme
     */
    public int getY() {
        return this.y;
    }

    /**
     * Définit la coordonnée Y de la forme.
     * Après avoir mis à jour la coordonnée, un événement de changement est déclenché.
     * 
     * @param y la nouvelle coordonnée Y de la forme
     */
    public void setY(int y) {
        this.y = y;
        this.fireChangement();
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la forme.
     * La chaîne contient le nom de la classe et les coordonnées de la forme.
     * 
     * @return la représentation sous forme de chaîne de caractères de la forme
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.x + "," + this.y + ")";
    }

    /**
     * Vérifie si les coordonnées spécifiées (x, y) se trouvent à l'intérieur de la forme.
     * Cette méthode est abstraite et doit être implémentée par les sous-classes pour chaque type spécifique de forme.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est à l'intérieur de la forme, sinon false
     */
    public abstract boolean contains(int x, int y);

    /**
     * Vérifie si les coordonnées spécifiées (x, y) se trouvent sur le bord de la forme.
     * Cette méthode est abstraite et doit être implémentée par les sous-classes pour chaque type spécifique de forme.
     * 
     * @param x la coordonnée X du point à tester
     * @param y la coordonnée Y du point à tester
     * @return true si le point est sur le bord de la forme, sinon false
     */
    public abstract boolean onEdge(int x, int y);

    /**
     * Crée une copie exacte de la forme.
     * Cette méthode est abstraite et doit être implémentée par les sous-classes pour chaque type spécifique de forme.
     * 
     * @return une nouvelle instance de la forme qui est une copie exacte de l'originale
     */
    public abstract MFShape clone();
}
