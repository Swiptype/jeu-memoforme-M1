package com.memoform.game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.strategy.EvaluationStrategy;

/**
 * Étape dans la chaîne de responsabilité qui évalue le score du jeu en utilisant une stratégie d'évaluation.
 * Après l'évaluation, elle passe à l'étape suivante de la chaîne.
 * Cette étape peut également afficher le score dans une fenêtre graphique.
 * 
 * @see StepLink
 * @see MemoForm
 * @see MFShapeContainer
 * @see EvaluationStrategy
 */
public class ScoringStep extends StepLink {

    // Stratégie d'évaluation utilisée pour calculer le score
    private EvaluationStrategy strategy;

    // Indicateur pour savoir si le score doit être affiché
    private boolean show;

    /**
     * Constructeur permettant de définir la stratégie d'évaluation et si le score doit être affiché.
     *
     * @param strategy La stratégie d'évaluation à utiliser pour calculer le score
     * @param show Un booléen indiquant si le score doit être affiché dans une fenêtre graphique
     */
    public ScoringStep(EvaluationStrategy strategy, boolean show) {
        this.strategy = strategy;
        this.show = show;
    }

    /**
     * Constructeur par défaut qui affiche toujours le score.
     *
     * @param strategy La stratégie d'évaluation à utiliser pour calculer le score
     */
    public ScoringStep(EvaluationStrategy strategy) {
        this(strategy, true);
    }

    /**
     * Action spécifique à cette étape. Évalue le score en comparant le modèle et la réplique
     * à l'aide de la stratégie d'évaluation. Si le modèle est manquant, une exception est levée.
     * Si la réplique est manquante, un score de 0 est affiché. Ensuite, l'étape suivante de la chaîne est exécutée.
     *
     * @param game L'instance du jeu qui est utilisée pour récupérer les modèles et la réplique
     * @throws IllegalArgumentException Si le modèle est manquant
     */
    @Override
    protected void localAction(MemoForm game) {
        // Récupère le modèle et la réplique du jeu
        MFShapeContainer model = game.getModel();
        MFShapeContainer replica = game.getReplica();
        
        // Si le modèle est manquant, une exception est levée
        if (model == null) {
            throw new IllegalArgumentException("Il manque un 'model' à comparer !");
        }
        
        // Si la réplique est manquante, un score de 0 est affiché
        if (replica == null) {
            System.out.println("Le score est de 0 !");
        } else {
            // Affiche le score évalué par la stratégie
            double score = this.strategy.evaluate(model, replica);
            System.out.println(score);
            if (this.show) {
                this.showScore(game);
            }
        }
    }

    /**
     * Affiche le score dans une fenêtre graphique pendant 5 secondes.
     * La fenêtre se ferme automatiquement après 5 secondes et réveille le thread de jeu.
     *
     * @param game L'instance du jeu qui est utilisée pour synchroniser l'attente du thread
     */
    private void showScore(MemoForm game) {
        JFrame frame = new JFrame("Score");
        double score = this.strategy.evaluate(game.getModel(), game.getReplica());
        JLabel label = new JLabel("Le score est : " + score);
        frame.add(label);

        // Paramètres de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        synchronized (game) {
            // Planifie la fermeture de la fenêtre après 5 secondes
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    frame.dispose(); // Ferme la fenêtre

                    synchronized (game) {
                        game.notify(); // Réveille le thread de jeu
                    }
                }
            }, 5 * 1000);

            synchronized(game) {
                try {
                    game.wait(); // Attend la fermeture de la fenêtre avant de continuer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("ShowContainerStep interrupted: " + e.getMessage());
                }
            }
        }
    }
}
