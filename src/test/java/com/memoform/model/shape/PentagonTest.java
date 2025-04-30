package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PentagonTest {

    private MFPolygon pentagon;

    @BeforeEach
    void setUp() {
        // Création d'un pentagone avec un côté spécifique pour les tests
        pentagon = new MFPolygon(100, 100, 5, 50);
    }

    @Test
    void testGetArea() {
        // Calcul de l'aire attendue du pentagone
        double expectedArea = (1.0 / 4.0) * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * 50 * 50;
        assertEquals(Math.round(expectedArea), Math.round(pentagon.getArea()), "L'aire du pentagone doit être correcte.");
    }

    @Test
    void testContainsPointInsidePentagon() {
        // Test si le point (110, 110) est bien à l'intérieur du pentagone
        boolean result = pentagon.contains(110, 110);
        assertEquals(true, result, "Le point devrait être à l'intérieur du pentagone.");
    }

    @Test
    void testOnEdge() {
        // Test si le point (150, 100) est bien sur le bord du pentagone
        boolean result = pentagon.onEdge(150, 100);
        assertFalse(result, "Le point ne devrait pas être sur le bord du pentagone.");
    }

    @Test
    void testSetSide() {
        // Modifie le côté du pentagone et vérifie si le côté est correctement mis à jour
        pentagon.setSideLength(70);
        assertEquals(70, pentagon.getSideLength(), "Le côté du pentagone devrait être correctement mis à jour.");
    }

    @Test
    void testEnlarge() {
        // Agrandit le pentagone de facteur 2 et vérifie si le côté a été correctement modifié
        pentagon.reshape(Map.of("side_length_add", pentagon.getSideLength())); // double le côté
        assertEquals(100, pentagon.getSideLength(), "Le côté du pentagone devrait être doublé.");
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString() génère la chaîne correcte
        String expected = "MFPolygon(100,100)[nbSides=5, sideLength=50]";
        assertEquals(expected, pentagon.toString(), "La méthode toString() ne retourne pas la bonne valeur.");
    }
}
