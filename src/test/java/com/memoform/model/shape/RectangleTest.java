package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RectangleTest {

    private Rectangle rectangle;

    @BeforeEach
    void setUp() {
        // Création d'un rectangle avec une largeur et une hauteur spécifiques pour les tests
        rectangle = new Rectangle(100, 100, 50, 30);
    }

    @Test
    void testGetArea() {
        // Calcul de l'aire attendue du rectangle
        double expectedArea = 50 * 30;
        assertEquals(expectedArea, rectangle.getArea(), "L'aire du rectangle doit être correcte.");
    }

    @Test
    void testContainsPointInsideRectangle() {
        // Test si le point (120, 110) est bien à l'intérieur du rectangle
        boolean result = rectangle.contains(120, 110);
        assertEquals(true, result, "Le point devrait être à l'intérieur du rectangle.");
    }

    @Test
    void testOnEdge() {
        // Test si le point (150, 115) est bien sur le bord du rectangle
        boolean result = rectangle.onEdge(150, 115);
        assertEquals(true, result, "Le point devrait être sur le bord du rectangle.");
    }

    @Test
    void testSetWidth() {
        // Modifie la largeur du rectangle et vérifie si la largeur est correctement mise à jour
        rectangle.setWidth(70);
        assertEquals(70, rectangle.getWidth(), "La largeur du rectangle devrait être correctement mise à jour.");
    }

    @Test
    void testSetHeight() {
        // Modifie la hauteur du rectangle et vérifie si la hauteur est correctement mise à jour
        rectangle.setHeight(40);
        assertEquals(40, rectangle.getHeight(), "La hauteur du rectangle devrait être correctement mise à jour.");
    }

    @Test
    void testEnlarge() {
        // Agrandit le rectangle de facteur 2 et vérifie si la largeur et la hauteur ont été correctement modifiées
        rectangle.reshape(Map.of("width_add", rectangle.getWidth(), "height_add", rectangle.getHeight())); // double la largeur et la hauteur
        assertEquals(100, rectangle.getWidth(), "La largeur du rectangle devrait être doublée.");
        assertEquals(60, rectangle.getHeight(), "La hauteur du rectangle devrait être doublée.");
    }

    @Test
    void testToString() {
        // Vérifie que la méthode toString() génère la chaîne correcte
        String expected = "Rectangle(100,100)[width=50, height=30]";
        assertEquals(expected, rectangle.toString(), "La méthode toString() ne retourne pas la bonne valeur.");
    }
}
