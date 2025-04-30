package com.memoform.view;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.observer_pattern.AbstractModeleEcoutable;
import com.memoform.model.observer_pattern.container.MFShapeContainerListener;
import com.memoform.model.shape.*;
import com.memoform.view.shape_view.*;
import com.memoform.view.shape_view.cast.CasterLink;

/**
 * Conteneur de vues pour les formes du modèle.
 * Cette classe gère la visualisation des formes contenues dans un {@link MFShapeContainer}.
 * Elle permet de mettre à jour les vues lorsque des formes sont ajoutées, supprimées ou modifiées.
 * Elle utilise un mécanisme de chaînes (CasterLink) pour associer chaque forme à sa vue correspondante.
 * 
 * @see MFShapeContainerListener
 * @see AbstractModeleEcoutable
 * 
 * @see MFShapeContainer
 * @see MFShape
 * @see MFShapeView
 * @see CasterLink
 */
public class MFShapeViewContainer extends AbstractModeleEcoutable implements MFShapeContainerListener {

    /**
     * Lien de départ de la chaîne de convertisseurs de formes en vues.
     * Ce lien est utilisé pour obtenir la vue d'une forme donnée.
     */
    public final static CasterLink HEAD_LINK;
    
    static {
        // Initialisation des liens de conversion de forme en vue
        CasterLink linkRecta = RectangleView.createViewCaster(); 
        CasterLink linkCircl = CircleView.createViewCaster(); linkRecta.setNextLink(linkCircl);
        CasterLink linkPolyg = MFPolygonView.createViewCaster(); linkCircl.setNextLink(linkPolyg);
        HEAD_LINK = linkRecta;
    }
    
    private MFShapeContainer container;
    private Map<MFShape, MFShapeView<?>> modelToView;

    /**
     * Constructeur de {@link MFShapeViewContainer}.
     * Initialise le conteneur et enregistre l'écouteur pour les modifications des formes.
     * @param container Le conteneur contenant les formes à visualiser.
     */
    public MFShapeViewContainer(MFShapeContainer container) {
        this.container = container;
        this.container.addListener(this);
        this.modelToView = new HashMap<>();
        
        // Ajout des formes existantes dans le conteneur à la vue
        for (int i = 0; i < this.container.getSize(); i++) {
            MFShape shape = this.container.getElementAt(i);
            this.putModelToView(shape);
        }
    }

    /**
     * Associe une forme à sa vue correspondante.
     * @param shape La forme à associer.
     */
    private void putModelToView(MFShape shape) {
        MFShapeView<?> shapeView = HEAD_LINK.getShapeView(shape);
        if (shape != null) this.modelToView.put(shape, shapeView);
    }

    /**
     * Dessine les vues des formes dans le conteneur.
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void paint(Graphics g) {
        for (MFShapeView<?> shapeView : modelToView.values()) {
            shapeView.paint(g);
        }
    }

    /**
     * Méthode appelée lorsqu'une forme est ajoutée au conteneur.
     * @param shape La forme ajoutée.
     */
    @Override
    public void shapeAdded(MFShape shape) {
        this.putModelToView(shape);
        this.fireChangement();  // Notifie le changement
    }

    /**
     * Méthode appelée lorsqu'une forme est supprimée du conteneur.
     * @param shape La forme supprimée.
     */
    @Override
    public void shapeRemoved(MFShape shape) {
        this.modelToView.remove(shape);
        this.fireChangement();  // Notifie le changement
    }

    /**
     * Méthode appelée lorsqu'une forme est modifiée.
     * @param shape La forme modifiée.
     */
    @Override
    public void shapeChanged(MFShape shape) {
        this.fireChangement();  // Notifie le changement
    }
}
