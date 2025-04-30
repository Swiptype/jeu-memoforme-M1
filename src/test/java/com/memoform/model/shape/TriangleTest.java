package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TriangleTest {

    private MFPolygon triangle;

    @BeforeEach
    void setUp() {
        // Création d'un triangle avec une longueur de côté spécifique pour les tests
        triangle = new MFPolygon(100, 100, 3, 50);
    }

    @Test
    void testGetArea() {
        // Calcul de l'aire attendue du triangle (aire d'un triangle équilatéral)
        double expectedArea = (Math.sqrt(3) / 4) * 50 * 50;
        assertEquals(Math.round(expectedArea), Math.round(triangle.getArea()), "L'aire du triangle doit être correcte.");
    }

    @Test
    void testContainsPointInsideTriangle() {
        // Test si le point (120, 120) est bien à l'intérieur du triangle
        boolean result = triangle.contains(110, 110);
        assertTrue(result, "Le point devrait être à l'intérieur du triangle.");
    }

    @Test
    void testOnEdge() {
        // Test si le point (150, 100) est bien sur le bord du triangle
        int[] p1 = new int[] {150,100};
        boolean result = triangle.onEdge(p1[0], p1[1]);
        assertFalse(result, "Le point("+Arrays.toString(p1)+") ne devrait pas être sur le bord du triangle.");
    }

    @Test
    void testSetSideLength() {
        // Modifie la longueur du côté du triangle et vérifie si la valeur est correctement mise à jour
        triangle.setSideLength(70);
        assertEquals(70, triangle.getSideLength(), "Le côté du triangle devrait être correctement mis à jour.");
    }

    @Test
    void testEnlarge() {
        // Agrandit le triangle de facteur 2 et vérifie si le côté a été correctement modifié
        triangle.reshape(Map.of("side_length_add", triangle.getSideLength())); // double la longueur du côté
        assertEquals(100, triangle.getSideLength(), "Le côté du triangle devrait être doublé.");
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString() génère la chaîne correcte
        String expected = "MFPolygon(100,100)[nbSides=3, sideLength=50]";
        assertEquals(expected, triangle.toString(), "La méthode toString() ne retourne pas la bonne valeur.");
    }
}
