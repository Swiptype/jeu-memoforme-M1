package com.memoform.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.memoform.model.ShapeFileHandler;
import com.memoform.model.command.CommandHandler;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;
import com.memoform.view.DrawingGUI;

public class FileMain {

    public static void main(String[] args) {
        MFShapeContainer container = new MFShapeContainer();
        
        DrawingGUI gui = new DrawingGUI(container, new CommandHandler());
        gui.setup(DrawingGUI.MODE.EDITOR_MODE);

        Runnable whenDone = new Runnable() {

            @Override
            public void run() {
                System.out.println(container);

                String datadir = "dist/shape_config/";

                long time = System.currentTimeMillis();
                String filePath = datadir +"config_"+ currentMillisToActualDate(time);

                ShapeFileHandler.saveShapesToFile(container, filePath);
                try {
                    List<MFShape> shapesLoaded = ShapeFileHandler.loadShapesFromFile(filePath);
                    System.out.println(shapesLoaded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        };

        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                // Ton script ici
                whenDone.run();
            }
        });
    }

    private static String currentMillisToActualDate(long millis) {
        // Convertir en LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(millis),
            ZoneId.systemDefault()
        );

        // Formatter la date au format AA_MM_JJ_HH_MM_SS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd_HH.mm.ss");
        String formatted = dateTime.format(formatter);

        return formatted;
    }
    
}
