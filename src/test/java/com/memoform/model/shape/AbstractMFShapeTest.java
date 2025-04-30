package com.memoform.model.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractMFShapeTest {

    private AbstractMFShape shape;

    @BeforeEach
    void setUp() {
        // Utiliser une classe concrète comme Rectangle pour tester AbstractMFShape
        shape = new Rectangle(0, 0, 100, 50);
    }

    @Test
    void testGetX() {
        // Vérifier la valeur de x
        assertEquals(0, shape.getX(), "La position X doit être correcte.");
    }

    @Test
    void testSetX() {
        // Modifier X et vérifier si le changement a été pris en compte
        shape.setX(50);
        assertEquals(50, shape.getX(), "La position X doit être mise à jour.");
    }

    @Test
    void testGetY() {
        // Vérifier la valeur de y
        assertEquals(0, shape.getY(), "La position Y doit être correcte.");
    }

    @Test
    void testSetY() {
        // Modifier Y et vérifier si le changement a été pris en compte
        shape.setY(75);
        assertEquals(75, shape.getY(), "La position Y doit être mise à jour.");
    }

    @Test
    void testToString() {
        // Tester la méthode toString pour vérifier le format de la chaîne
        String expectedString = "Rectangle(0,0)[width=100, height=50]";
        assertEquals(expectedString, shape.toString(), "La représentation en chaîne du rectangle est incorrecte.");
    }
}
