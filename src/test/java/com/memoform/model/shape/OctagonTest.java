package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OctagonTest {

    private MFPolygon octagon;

    @BeforeEach
    void setUp() {
        // Création d'un octogone avec un côté spécifique pour les tests
        octagon = new MFPolygon(100, 100, 8, 50);
    }

    @Test
    void testGetArea() {
        // Calcul de l'aire attendue de l'octogone
        double expectedArea = 2 * (1 + Math.sqrt(2)) * 50 * 50;
        assertEquals(Math.round(expectedArea), Math.round(octagon.getArea()), "L'aire de l'octogone doit être correcte.");
    }

    @Test
    void testContainsPointInsideOctagon() {
        // Test si le point (110, 110) est bien à l'intérieur de l'octogone
        boolean result = octagon.contains(110, 110);
        assertEquals(true, result, "Le point devrait être à l'intérieur de l'octogone.");
    }

    @Test
    void testOnEdge() {
        // Test si le point (150, 100) est bien sur le bord de l'octogone
        boolean result = octagon.onEdge(150, 100);
        assertFalse(result, "Le point ne devrait pas être sur le bord de l'octogone.");
    }

    @Test
    void testSetSide() {
        // Modifie le côté de l'octogone et vérifie si le côté est correctement mis à jour
        octagon.setSideLength(70);
        assertEquals(70, octagon.getSideLength(), "Le côté de l'octogone devrait être correctement mis à jour.");
    }

    @Test
    void testEnlarge() {
        // Agrandit l'octogone de facteur 2 et vérifie si le côté a été correctement modifié
        octagon.reshape(Map.of("side_length_add", octagon.getSideLength())); // double le côté
        assertEquals(100, octagon.getSideLength(), "Le côté de l'octogone devrait être doublé.");
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString() génère la chaîne correcte
        String expected = "MFPolygon(100,100)[nbSides=8, sideLength=50]";
        assertEquals(expected, octagon.toString(), "La méthode toString() ne retourne pas la bonne valeur.");
    }
}
