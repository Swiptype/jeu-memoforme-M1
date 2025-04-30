package com.memoform.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire contenant des outils géométriques pour effectuer des calculs géométriques de base.
 * Elle comprend des méthodes pour tester si un point est à l'intérieur d'un polygone (ray-casting)
 * et pour obtenir les sommets d'un polygone régulier basé sur son centre, le nombre de côtés et la longueur de ses côtés.
 */
public class GeoTools {

    /**
     * Détermine si un point (x, y) est à l'intérieur d'un polygone en utilisant l'algorithme de ray-casting.
     * 
     * L'algorithme de ray-casting trace un rayon à partir du point et compte le nombre d'intersections
     * avec les bords du polygone. Si le nombre d'intersections est impair, le point est à l'intérieur,
     * sinon il est à l'extérieur.
     * 
     * @param vertX Liste des coordonnées X des sommets du polygone.
     * @param vertY Liste des coordonnées Y des sommets du polygone.
     * @param x Coordonnée X du point à tester.
     * @param y Coordonnée Y du point à tester.
     * @return `true` si le point (x, y) est à l'intérieur du polygone, `false` sinon.
     */
    public static boolean rayCasting(List<Integer> vertX, List<Integer> vertY, int x, int y) {
        if (vertX.size() != vertY.size()) return false; // Vérifier la validité des listes de sommets.
    
        int nbPoints = vertX.size();
        int cpt = 0;
    
        for (int i = 0, j = nbPoints - 1; i < nbPoints; j = i++) {
            int x1 = vertX.get(i), x2 = vertX.get(j);
            int y1 = vertY.get(i), y2 = vertY.get(j);
    
            // Gérer les bords horizontaux (y1 == y2)
            if (y1 == y2) {
                if (y == y1 && x >= Math.min(x1, x2) && x <= Math.max(x1, x2)) {
                    return true; // Le point est sur un segment horizontal
                }
                continue; // Ignore ce segment pour le comptage
            }
    
            boolean firstCond = (y < y1) != (y < y2); // Vérifie si le point est dans la portée verticale du segment
            double intersectionX = x1 + ((double) (y - y1) / (y2 - y1)) * (x2 - x1); // Calcul du point d'intersection
            boolean secondCond = x < intersectionX; // Vérifie si l'intersection est à gauche du point
            
            if (firstCond && secondCond) cpt++; // Compter une intersection
        }
    
        return cpt % 2 == 1; // Nombre impair → à l'intérieur du polygone
    }

    /**
     * Génère les sommets d'un polygone régulier.
     * 
     * Cette méthode génère les sommets d'un polygone régulier basé sur un centre donné, 
     * un nombre de côtés et une longueur de côté spécifiée. Les coordonnées des sommets
     * sont retournées dans deux listes : une pour les coordonnées X et une pour les coordonnées Y.
     * 
     * @param xCenter La coordonnée X du centre du polygone.
     * @param yCenter La coordonnée Y du centre du polygone.
     * @param nbSides Le nombre de côtés du polygone.
     * @param lengthSide La longueur de chaque côté du polygone.
     * @return Une liste de deux listes : une pour les coordonnées X des sommets et l'autre pour les coordonnées Y.
     */
    public static List<List<Integer>> getVertexFromPolygon(int xCenter, int yCenter, int nbSides, int lengthSide) {
        return GeoTools.getVertexFromPolygon(xCenter, yCenter, nbSides, lengthSide, 0);
    }

    /**
     * Génère les sommets d'un polygone régulier avec un ajustement du rayon.
     * 
     * Cette méthode génère les sommets d'un polygone régulier en tenant compte d'un ajustement supplémentaire
     * pour le rayon du cercle circonscrit. Le rayon ajusté est calculé en fonction de la longueur du côté du polygone.
     * 
     * @param xCenter La coordonnée X du centre du polygone.
     * @param yCenter La coordonnée Y du centre du polygone.
     * @param nbSides Le nombre de côtés du polygone.
     * @param lengthSide La longueur de chaque côté du polygone.
     * @param deltaR Un ajustement supplémentaire à appliquer au rayon du cercle circonscrit.
     * @return Une liste de deux listes : une pour les coordonnées X des sommets et l'autre pour les coordonnées Y.
     */
    public static List<List<Integer>> getVertexFromPolygon(int xCenter, int yCenter, int nbSides, int lengthSide, int deltaR) {
        List<Integer> vertX = new ArrayList<>();
        List<Integer> vertY = new ArrayList<>();
        List<List<Integer>> vertex = List.of(vertX, vertY);
    
        // Calcul du rayon du cercle circonscrit
        double R = lengthSide / (2 * Math.sin(Math.PI / nbSides)) + deltaR;
        double theta0 = -Math.PI / 2; // Orienter un sommet vers le haut
    
        for (int i = 0; i < nbSides; i++) {
            // Calcul des coordonnées des sommets du polygone en fonction de l'angle
            int iX = (int) Math.round(xCenter + R * Math.cos(theta0 + 2 * Math.PI * i / nbSides));
            int iY = (int) Math.round(yCenter + R * Math.sin(theta0 + 2 * Math.PI * i / nbSides));
            vertX.add(iX);
            vertY.add(iY);
        }
    
        return vertex;
    }
}
