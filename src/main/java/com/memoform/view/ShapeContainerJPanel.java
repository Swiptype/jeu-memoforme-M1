package com.memoform.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.observer_pattern.EcouteurModele;

/**
 * Panneau personnalisé qui affiche les formes contenues dans un {@link MFShapeContainer}.
 * Cette classe étend {@link JPanel} et utilise un {@link MFShapeViewContainer} pour dessiner les formes sur le panneau.
 * Elle met à jour l'affichage lorsque le modèle sous-jacent est modifié, grâce à l'interface {@link EcouteurModele}.
 * 
 * @see EcouteurModele
 * 
 * @see MFShapeContainer
 * @see MFShapeViewContainer
 */
public class ShapeContainerJPanel extends JPanel implements EcouteurModele {

    private MFShapeViewContainer mfwContainer;

    /**
     * Constructeur de {@link ShapeContainerJPanel}.
     * Crée un conteneur de vues pour les formes du modèle et l'ajoute comme écouteur du modèle.
     * @param container Le conteneur de formes à afficher.
     */
    public ShapeContainerJPanel(MFShapeContainer container) {
        this.mfwContainer = new MFShapeViewContainer(container);
        this.mfwContainer.ajoutEcouteur(this);  // Ajoute le panneau comme écouteur du modèle
        this.setPreferredSize(new Dimension(400, 400));  // Définit la taille préférée du panneau
    }

    /**
     * Redéfinit la méthode {@link paintComponent} pour dessiner les vues des formes sur le panneau.
     * @param g L'objet Graphics utilisé pour dessiner sur le panneau.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Appelle la méthode de la classe parente pour la gestion de l'affichage
        this.mfwContainer.paint(g);  // Dessine les formes via le conteneur de vues
    }

    /**
     * Méthode appelée lorsque le modèle est mis à jour.
     * Redessine le panneau pour refléter les changements dans le modèle.
     * @param source L'objet source de la mise à jour (peut être le conteneur de formes).
     */
    @Override
    public void modeleMAJ(Object source) {
        if (this.getGraphics() != null) {
            this.repaint();  // Demande un nouveau dessin du panneau
        }
    }
}
