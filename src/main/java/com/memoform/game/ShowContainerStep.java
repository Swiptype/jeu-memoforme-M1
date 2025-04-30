package com.memoform.game;

import java.util.Timer;
import java.util.TimerTask;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.view.DrawingGUI;

/**
 * Étape dans la chaîne de responsabilité qui affiche un conteneur de formes (MFShapeContainer)
 * pendant un temps défini, puis passe à l'étape suivante de la chaîne.
 * 
 * @see StepLink
 * 
 * @see MemoForm
 * @see MFShapeContainer
 * @see DrawingGUI
 */
public class ShowContainerStep extends StepLink {

    // Timer pour gérer le délai d'affichage
    private Timer timer = new Timer();
    
    // Durée en secondes pendant laquelle le conteneur sera affiché
    private int seconds;

    /**
     * Constructeur pour définir le temps d'affichage.
     *
     * @param seconds Durée d'affichage en secondes
     */
    public ShowContainerStep(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Constructeur par défaut avec un temps d'affichage de 10 secondes.
     */
    public ShowContainerStep() {
        this(10);
    }

    /**
     * Action spécifique à cette étape. Affiche le conteneur de formes pendant un certain temps.
     * Après le délai, le conteneur est fermé et l'étape suivante est exécutée.
     *
     * @param game L'instance du jeu qui est utilisée pour récupérer le modèle
     */
    @Override
    protected void localAction(MemoForm game) {
        MFShapeContainer model = game.getModel();
        if (model == null) return;

        DrawingGUI modelGUI = new DrawingGUI(model);
        modelGUI.setup(DrawingGUI.MODE.READER_MODE);

        synchronized (game) {
            //Lance un timer de <this.seconds>
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    modelGUI.dispose(); // Ferme la fenêtre

                    synchronized (game) {
                        game.notify(); // Réveille le thread
                    }
                }
            }, this.seconds * 1000);

            synchronized (game) {
                try {
                    game.wait(); // Attend la fermeture
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("ShowContainerStep interrupted: " + e.getMessage());
                }
            }
        }
    }
}
