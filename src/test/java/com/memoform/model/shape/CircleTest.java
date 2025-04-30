package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CircleTest {

    private Circle circle;

    @BeforeEach
    void setUp() {
        // Création d'un cercle avec un rayon spécifique pour les tests
        circle = new Circle(100, 100, 50);
    }

    @Test
    void testGetArea() {
        // Calcul de l'aire attendue du cercle
        double expectedArea = Math.PI * 50 * 50;
        assertEquals(expectedArea, circle.getArea(), "L'aire du cercle doit être correcte.");
    }

    @Test
    void testContainsPointInsideCircle() {
        // Test si le point (110, 110) est bien à l'intérieur du cercle
        boolean result = circle.contains(110, 110);
        assertEquals(true, result, "Le point devrait être à l'intérieur du cercle.");
    }

    @Test
    void testOnEdge() {
        // Test si le point (150, 100) est bien sur le bord du cercle
        boolean result = circle.onEdge(150, 100);
        assertEquals(true, result, "Le point devrait être sur le bord du cercle.");
    }

    @Test
    void testSetRadius() {
        // Modifie le rayon du cercle et vérifie si le rayon est correctement mis à jour
        circle.setRadius(100);
        assertEquals(100, circle.getRadius(), "Le rayon devrait être correctement mis à jour.");
    }

    @Test
    void testEnlarge() {
        // Double le rayon et vérifie si le rayon a été correctement modifié
        circle.reshape(Map.of("radius_add", circle.getRadius())); // double le rayon
        assertEquals(100, circle.getRadius(), "Le rayon devrait être doublé.");
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString() génère la chaîne correcte
        String expected = "Circle(100,100)[radius=50]";
        assertEquals(expected, circle.toString(), "La méthode toString() ne retourne pas la bonne valeur.");
    }
}
