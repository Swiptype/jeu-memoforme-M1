package com.memoform.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.memoform.game.MemoForm.MFContainer;
import com.memoform.model.ShapeFileHandler;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.MFShape;

/**
 * Étape de la chaîne de responsabilité qui permet d'importer un modèle à partir d'un fichier.
 * Cette étape affiche une interface graphique pour choisir un fichier de modèle et l'importer dans le jeu.
 * 
 * @see StepLink
 * 
 * @see ShapeFileHandler
 * @see MFShapeContainer
 * @see MFShape
 */
public class ImportFileModelStep extends StepLink {

    // Chemin vers le répertoire contenant les configurations de formes
    public static Path REPO_PATH = Paths.get("shape_config");

    // Conteneur à utiliser pour importer le modèle (modèle ou réplique)
    private MFContainer witchContainer;

    /**
     * Constructeur pour définir le conteneur (modèle ou réplique) à utiliser.
     *
     * @param witchContainer Le conteneur à utiliser (soit modèle, soit réplique)
     */
    public ImportFileModelStep(MFContainer witchContainer) {
        this.witchContainer = witchContainer;
    }

    /**
     * Constructeur par défaut qui initialise avec le conteneur MODEL.
     */
    public ImportFileModelStep() {
        this(MFContainer.MODEL);
    }

    /**
     * Charge un modèle depuis un fichier en lisant les formes et en les ajoutant à un conteneur.
     *
     * @param filePath Le chemin du fichier à charger
     * @return Le conteneur de formes chargé
     */
    private MFShapeContainer getModelFromFilePath(String filePath) {
        List<MFShape> shapes = new ArrayList<>();
        // On essaie de récupérer les différentes formes depuis chemin en entré.
        try {
            shapes = ShapeFileHandler.loadShapesFromFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //On en crée un conteneur.
        MFShapeContainer container = new MFShapeContainer();
        for (MFShape mfShape : shapes) {
            container.add(mfShape);
        }
        return container;
    }

    /**
     * Action spécifique à cette étape. Affiche une fenêtre permettant de choisir un modèle à importer.
     * Une fois un modèle choisi, il est ajouté au jeu (soit comme modèle, soit comme réplique),
     * puis l'étape suivante de la chaîne est appelée.
     *
     * @param game L'instance du jeu qui sera mise à jour avec le modèle ou la réplique importée
     */
    @Override
    protected void localAction(MemoForm game) {
        // Liste des fichiers de configuration présents dans le répertoire de modèles
        Vector<String> config = new Vector<>();
        try {
            //On récupère les fichiers de configuration présents dans le répertoire
            Files.walk(REPO_PATH)
                .filter(Files::isRegularFile)
                .forEach(fichier -> {
                    // On récupère seulement le nom du fichier
                    Path relatif = REPO_PATH.relativize(fichier);
                    config.add(relatif.toString());
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création de l'interface graphique pour choisir un fichier
        JFrame frame = new JFrame("Modèles Importés"); 
        frame.setLayout(new BorderLayout());

        JComboBox<String> comboBox = new JComboBox<>(config);
        frame.getContentPane().add(comboBox, BorderLayout.CENTER);

        JButton doneButton = new JButton("Terminer");
        frame.getContentPane().add(doneButton, BorderLayout.SOUTH);

        // Action à effectuer lorsque l'utilisateur clique sur "Terminer"
        doneButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupère le modèle sélectionné et le charge dans le jeu
                MFShapeContainer container = getModelFromFilePath(REPO_PATH.toString() + "/" + comboBox.getSelectedItem().toString());
                switch (witchContainer) {
                    case MODEL:
                        game.setModel(container); 
                        break;
                    case REPLICA:
                        game.setReplica(container); 
                        break;
                    default:
                        break;
                }
                frame.dispose();
                
                // Réveille le thread bloqué
                synchronized (game) {
                    game.notify();
                }
            }

        });

        // Paramètres de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(320, 240));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (game) {
            try {
                game.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("ImportFileModelStep interrupted: " + e.getMessage());
            }
        }
    }
}
