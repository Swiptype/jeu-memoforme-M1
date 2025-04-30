package com.memoform.main;

import java.util.Map;

import com.memoform.model.command.AddShape;
import com.memoform.model.command.CommandHandler;
import com.memoform.model.command.Reschape;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.command.MoveShape;
import com.memoform.model.command.RemoveShape;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.Rectangle;

public class TestMain {
    
    public static void main(String[] args) {
        testHandler();   
    }

    private static void testHandler() {
        Rectangle r = new Rectangle(0, 0, 10, 10);
        Circle c = new Circle(0, 0, 10);

        MFShapeContainer container = new MFShapeContainer();

        MFPolygon t = new MFPolygon(0, 0, 3, 50);
        MFPolygon p = new MFPolygon(0, 0, 5, 50);
        MFPolygon o = new MFPolygon(0, 0, 8, 50);

        CommandHandler handler = new CommandHandler();
        AddShape ad1 = new AddShape(container, c); 
        handler.handle(ad1);
        AddShape ad2 = new AddShape(container, r); 
        handler.handle(ad2);

        AddShape ad3 = new AddShape(container, t); 
        handler.handle(ad3);
        AddShape ad4 = new AddShape(container, p); 
        handler.handle(ad4);
        AddShape ad5 = new AddShape(container, o); 
        handler.handle(ad5);
        

        System.out.println(container); 
        System.out.println();

        MoveShape m1 = new MoveShape(r, 10, 5); 
        handler.handle(m1);
        MoveShape m2 = new MoveShape(c, -4, 5); 
        handler.handle(m2);

        System.out.println(container); 
        System.out.println();
        
        Reschape ec1 = new Reschape(c, Map.of("radius_add", 10)); 
        handler.handle(ec1);
        Reschape er1 = new Reschape(r, Map.of("width_add", 10)); 
        handler.handle(er1);

        System.out.println(container); 
        System.out.println();

        RemoveShape rm1 = new RemoveShape(container, c);
        handler.handle(rm1);
        RemoveShape rm2 = new RemoveShape(container, c);
        handler.handle(rm2);

        System.out.println(container); 
        System.out.println();

        int undo = 6;
        for (int i = 0; i < undo; i++) {
            handler.undo();
            System.out.println(container); 
            System.out.println();
        }

        int redo = 6;
        for (int i = 0; i < redo; i++) {
            handler.redo();
            System.out.println(container); 
            System.out.println();
        }
    }
}