package com.memoform.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;

public class MFShapeContainerTest {

    private List<MFShape> shapes;
    private MFShapeContainer container;

    @BeforeEach
    void setUp() {
        Random alea = new Random();

        this.shapes = new ArrayList<>();
        this.container = new MFShapeContainer();

        Rectangle rect = new Rectangle(alea.nextInt(300), alea.nextInt(300), alea.nextInt(100), alea.nextInt(100));
        this.shapes.add(rect);
        this.container.add(rect);

        Circle circle = new Circle(alea.nextInt(300), alea.nextInt(300), alea.nextInt(100));
        this.shapes.add(circle);
        this.container.add(circle);
    }

    @Test
    void hasTheSameShapes() {
        assertEquals(this.shapes.size(), this.container.getSize());
        for (int i = 0; i < this.shapes.size(); i++) {
            assertEquals(this.shapes.get(i), this.container.getElementAt(i));
        }
    }
    
}
