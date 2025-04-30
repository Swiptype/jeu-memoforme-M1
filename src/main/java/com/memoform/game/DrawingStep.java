package com.memoform.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.memoform.game.MemoForm.MFContainer;
import com.memoform.model.command.CommandHandler;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.view.DrawingGUI;

/**
 * Étape de la chaîne de responsabilité permettant à l'utilisateur de dessiner un modèle.
 * Cette étape affiche une interface graphique pour dessiner des formes dans un conteneur.
 * 
 * @see StepLink
 * 
 * @see MemoForm
 * @see MFShapeContainer
 * @see CommandHandler
 * @see DrawingGUI
 */
public class DrawingStep extends StepLink {

    // Conteneur à utiliser pour dessiner (modèle ou réplique)
    private MFContainer witchContainer;

    /**
     * Constructeur permettant de définir le conteneur (modèle ou réplique) à utiliser pour dessiner.
     *
     * @param witchContainer Le conteneur à utiliser (soit modèle, soit réplique)
     */
    public DrawingStep(MFContainer witchContainer) {
        this.witchContainer = witchContainer;
    }

    /**
     * Constructeur par défaut qui initialise avec le conteneur MODEL.
     */
    public DrawingStep() {
        this(MFContainer.MODEL);
    }

    /**
     * Action spécifique à cette étape. Affiche une interface graphique permettant à l'utilisateur de dessiner des formes.
     * Lorsque la fenêtre de dessin est fermée, l'étape suivante de la chaîne est appelée.
     *
     * @param game L'instance du jeu qui sera mise à jour avec les formes dessinées
     */
    @Override
    protected void localAction(MemoForm game) {
        // Création du conteneur pour les formes et du gestionnaire de commandes
        MFShapeContainer container = new MFShapeContainer();
        CommandHandler handler = new CommandHandler();

        DrawingGUI gui = new DrawingGUI(container, handler);
        gui.setup(DrawingGUI.MODE.EDITOR_MODE);

        // Synchronisation sur "game" pour bloquer le thread jusqu'à fermeture
        synchronized (game) {
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Met à jour le conteneur du jeu en fonction du type
                    switch (witchContainer) {
                        case MODEL:
                            game.setModel(container); break;
                        case REPLICA:
                            game.setReplica(container); break;
                        default: break;
                    }

                    // Réveille le thread bloqué
                    synchronized (game) {
                        game.notify();
                    }
                }
            });

            synchronized(game) {
                try {
                    // Attend la fermeture de la fenêtre avant de continuer
                    game.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("DrawingStep interrupted: " + e.getMessage());
                }
            }
        }
    }
}
