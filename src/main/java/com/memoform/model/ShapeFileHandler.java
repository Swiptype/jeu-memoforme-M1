package com.memoform.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;

/**
 * Classe utilitaire responsable de la sauvegarde et du chargement
 * des formes géométriques (MFShape) vers et depuis des fichiers texte.
 * <br>
 * Elle permet de persister des formes sous forme de texte,
 * et de les reconstituer à partir de ce texte.
 * 
 * @see MFShapeContainer
 * @see MFShape
 */
public class ShapeFileHandler {

    /**
     * Sauvegarde toutes les formes contenues dans un conteneur {@link MFShapeContainer}
     * vers un fichier.
     *
     * @param container Le conteneur contenant les formes à sauvegarder.
     * @param filePath  Le chemin du fichier dans lequel écrire les données.
     */
    public static void saveShapesToFile(MFShapeContainer container, String filePath) {
        List<MFShape> shapes = new ArrayList<>();
        for (int index = 0; index < container.getSize(); index++)
            shapes.add(container.getElementAt(index));
        ShapeFileHandler.saveShapesToFile(shapes, filePath);
    }

    /**
     * Sauvegarde une liste de formes dans un fichier texte.
     *
     * @param shapes   La liste des formes à enregistrer.
     * @param filePath Le chemin du fichier dans lequel écrire les données.
     */
    public static void saveShapesToFile(List<MFShape> shapes, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (MFShape shape : shapes) {
                writer.write(shape.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge une liste de formes à partir d’un fichier texte.
     *
     * @param filePath Le chemin du fichier contenant les données de formes.
     * @return Une liste de formes reconstituées à partir du fichier.
     * @throws IOException En cas d’erreur de lecture du fichier.
     */
    public static List<MFShape> loadShapesFromFile(String filePath) throws IOException {
        List<MFShape> shapes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            MFShape shape = parseShape(line);
            if (shape != null) {
                shapes.add(shape);
            }
        }
        reader.close();
        return shapes;
    }

    /**
     * Interprète une ligne de texte pour en créer une instance de {@link MFShape}.
     *
     * @param line La ligne de texte représentant une forme.
     * @return Une instance de forme, ou {@code null} si la ligne est invalide.
     */
    private static MFShape parseShape(String line) {
        line = line.trim();

        if (line.startsWith("Rectangle")) {
            return parseRectangle(line);
        }

        if (line.startsWith("MFPolygon")) {
            return parseMFPolygon(line);
        }

        if (line.startsWith("Circle")) {
            return parseCircle(line);
        }

        return null;
    }

    /**
     * Interprète une ligne de texte pour créer un {@link Rectangle}.
     * Format attendu : {@code Rectangle(x,y)[width=..., height=...]}
     *
     * @param line La ligne représentant un rectangle.
     * @return L’instance correspondante de {@link Rectangle}.
     */
    private static MFShape parseRectangle(String line) {
        String[] parts = line.split("\\[");
        String[] coords = parts[0].replace("Rectangle", "").replace("(", "").replace(")", "").split(",");
        String[] dimensions = parts[1].replace("]", "").split(", ");

        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int width = Integer.parseInt(dimensions[0].split("=")[1]);
        int height = Integer.parseInt(dimensions[1].split("=")[1]);

        return new Rectangle(x, y, width, height);
    }

    /**
     * Interprète une ligne de texte pour créer un {@link MFPolygon}.
     * Format attendu : {@code MFPolygon(x,y)[nbSides=..., sideLength=...]}
     *
     * @param line La ligne représentant un polygone.
     * @return L’instance correspondante de {@link MFPolygon}.
     */
    private static MFShape parseMFPolygon(String line) {
        String[] parts = line.split("\\[");
        String[] coords = parts[0].replace("MFPolygon", "").replace("(", "").replace(")", "").split(",");
        String[] dimensions = parts[1].replace("]", "").split(", ");

        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int nbSides = Integer.parseInt(dimensions[0].split("=")[1]);
        int sideLength = Integer.parseInt(dimensions[1].split("=")[1]);

        return new MFPolygon(x, y, nbSides, sideLength);
    }

    /**
     * Interprète une ligne de texte pour créer un {@link Circle}.
     * Format attendu : {@code Circle(x,y)[radius=...]}
     *
     * @param line La ligne représentant un cercle.
     * @return L’instance correspondante de {@link Circle}.
     */
    private static MFShape parseCircle(String line) {
        String[] parts = line.split("\\[");
        String[] coords = parts[0].replace("Circle", "").replace("(", "").replace(")", "").split(",");
        String[] radius = parts[1].replace("]", "").split("=");

        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int radiusValue = Integer.parseInt(radius[1]);

        return new Circle(x, y, radiusValue);
    }
}
