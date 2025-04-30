package com.memoform.main;

import java.util.Map;

import com.memoform.model.command.AddShape;
import com.memoform.model.command.CommandHandler;
import com.memoform.model.command.MoveShape;
import com.memoform.model.command.Reschape;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.Rectangle;
import com.memoform.view.DrawingGUI;
import com.memoform.view.look_feek.DefaultLookAndFeel;

public class ViewMain {

    public static void main(String[] args) {

        DrawingGUI.LOOK = new DefaultLookAndFeel();

        // Gestion des commandes
        CommandHandler handler = new CommandHandler();

        // Conteneur de formes
        MFShapeContainer container = new MFShapeContainer();

        // Ajout de formes d'exemple
        addExemple(container, handler);

        new DrawingGUI(container, handler).setup(DrawingGUI.MODE.EDITOR_MODE);
    }

    public static void addExemple(MFShapeContainer container, CommandHandler handler) {
        // Création et ajout de formes
        Rectangle r1 = new Rectangle(0, 0, 10, 10);
        handler.handle(new AddShape(container, r1));

        Circle c1 = new Circle(0, 0, 15);
        handler.handle(new AddShape(container, c1));

        Rectangle r2 = new Rectangle(200, 200, 30, 15);
        handler.handle(new AddShape(container, r2));

        MFPolygon t1 = new MFPolygon(300, 300,3);
        handler.handle(new AddShape(container, t1));

        MFPolygon p1 = new MFPolygon(400, 300,5);
        handler.handle(new AddShape(container, p1));

        MFPolygon o1 = new MFPolygon(600, 300,8);
        handler.handle(new AddShape(container, o1));

        MFPolygon poly1 = new MFPolygon(800, 300,15);
        handler.handle(new AddShape(container, poly1));

        handler.handle(new MoveShape(c1, 30, 50));
        handler.handle(new MoveShape(r1, 70, 50));

        handler.handle(new Reschape(c1, Map.of("radius_add", 2)));
        handler.handle(new Reschape(c1, Map.of("radius_add", 1)));

        handler.handle(new Reschape(r1, Map.of("radius_add", 1)));
        handler.handle(new Reschape(r1, Map.of("radius_add", 1)));
    }
}
