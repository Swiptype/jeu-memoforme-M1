package com.memoform.game;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.view.look_feek.DefaultLookAndFeel;
import com.memoform.view.look_feek.MFLookAndFeel;

/**
 * Classe principale du jeu MemoForm. Elle gère les étapes du jeu, les modèles et répliques,
 * ainsi que l'apparence de l'interface.
 * 
 * @see StepLink
 * @see MFShapeContainer
 * @see MFLookAndFeel
 */
public class MemoForm implements Runnable {

    /**
     * Enumération des conteneurs possibles (modèle ou réplique) pour le jeu.
     */
    public static enum MFContainer {
        REPLICA, MODEL
    }

    // Premier élément de la chaîne de responsabilité
    private StepLink headLink;  

    // Modèle du jeu
    private MFShapeContainer model = null;  
    // Réplique du jeu
    private MFShapeContainer replica = null;  

    // Apparence de l'interface
    private MFLookAndFeel look = new DefaultLookAndFeel();  

    /**
     * Définit l'apparence de l'interface du jeu.
     *
     * @param look L'apparence à appliquer.
     */
    public void setLook(MFLookAndFeel look) {
        this.look = look;
    }

    /**
     * Retourne l'apparence actuelle de l'interface.
     *
     * @return L'apparence du jeu.
     */
    public MFLookAndFeel getLook() {
        return this.look;
    }

    /**
     * Définit le modèle du jeu.
     *
     * @param model Le modèle à définir.
     */
    public void setModel(MFShapeContainer model) {
        this.model = model;
    }

    /**
     * Définit la réplique du jeu.
     *
     * @param replica La réplique à définir.
     */
    public void setReplica(MFShapeContainer replica) {
        this.replica = replica;
    }

    /**
     * Retourne le modèle du jeu.
     *
     * @return Le modèle actuel du jeu.
     */
    public MFShapeContainer getModel() {
        return this.model;
    }

    /**
     * Retourne la réplique du jeu.
     *
     * @return La réplique actuelle du jeu.
     */
    public MFShapeContainer getReplica() {
        return this.replica;
    }
    
    /**
     * Lance le jeu en démarrant un thread qui exécute la première étape.
     *
     * @param headLink Le premier élément de la chaîne de responsabilité des étapes du jeu.
     */
    public void launch(StepLink headLink) {
        this.headLink = headLink;
        new Thread(this).start();
    }

    /**
     * Méthode qui est exécutée dans le thread. Elle commence l'exécution de la chaîne d'étapes.
     */
    @Override
    public void run() {
        try {
            this.headLink.action(this);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution :");
            e.printStackTrace();
        } finally {
            // Force l'arrêt du programme (ferme toutes les fenêtres, tue les threads)
            System.exit(0);
        }
    }
}
