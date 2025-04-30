package com.memoform.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.memoform.model.command.CommandHandler;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.view.control_state.ControlState;
import com.memoform.view.control_state.CreationState;
import com.memoform.view.control_state.ReshapeState;
import com.memoform.view.control_state.MovementState;
import com.memoform.view.control_state.RemoveState;

/**
 * Contrôleur d'état qui gère les différentes actions de manipulation des formes
 * dans l'interface graphique. Il gère les états du contrôle (création, déplacement, 
 * redimensionnement et suppression) et s'assure de la bonne transition entre ces états.
 * Ce contrôleur est attaché au panneau de formes et écoute les événements de la souris
 * pour exécuter les actions appropriées.
 */
public class StateController extends MouseAdapter {

    /** État pour le redimensionnement des formes */
    public ControlState RS = new ReshapeState(this);

    /** État pour le déplacement des formes */
    public ControlState MS = new MovementState(this);

    /** État pour la suppression des formes */
    public ControlState RMS = new RemoveState(this);

    /** État pour la création des formes */
    public CreationState CS = new CreationState(this);
    
    private MFShapeContainer container;
    private CommandHandler handler;
    private ShapeContainerJPanel panel;

    /** État actuel du contrôleur */
    private ControlState state;

    /**
     * Constructeur du contrôleur d'état.
     * @param container Le conteneur de formes à manipuler.
     * @param handler Le gestionnaire des commandes.
     * @param panel Le panneau contenant les formes.
     */
    public StateController(MFShapeContainer container, CommandHandler handler, ShapeContainerJPanel panel) {
        this.container = container;
        this.handler = handler;
        this.panel = panel;
        this.state = RS;  // L'état initial est celui de redimensionnement

        // Ajoute le contrôleur d'état comme écouteur des événements de souris
        this.panel.addMouseListener(this);
        this.panel.addMouseMotionListener(this);
    }

    /**
     * Active le mode de déplacement des formes.
     */
    public void movementMode() { this.state.movementMode(); }

    /**
     * Active le mode de suppression des formes.
     */
    public void removeMode() { this.state.removeMode(); }

    /**
     * Active le mode de redimensionnement des formes.
     */
    public void reshapeMode() { this.state.reshapeMode(); }

    /**
     * Active le mode de création des formes.
     */
    public void creationMode() { this.state.creationMode(); }

    /**
     * Permet de changer l'état actuel du contrôleur.
     * @param state Le nouvel état à définir.
     */
    public void switchState(ControlState state) {
        this.state = state;
    }

    /**
     * Récupère le conteneur de formes.
     * @return Le conteneur de formes.
     */
    public MFShapeContainer getContainer() { return this.container; }

    /**
     * Récupère le gestionnaire des commandes.
     * @return Le gestionnaire des commandes.
     */
    public CommandHandler getHandler() { return this.handler; }

    /**
     * Récupère le panneau contenant les formes.
     * @return Le panneau de formes.
     */
    public ShapeContainerJPanel getPanel() { return this.panel; }

    /**
     * Gère l'événement de clic de souris, délégant l'action à l'état actuel.
     * @param e L'événement de clic de souris.
     */
    @Override
    public void mouseClicked(MouseEvent e) { this.state.mouseClicked(e); }

    /**
     * Gère l'événement de pression de souris, délégant l'action à l'état actuel.
     * @param e L'événement de pression de souris.
     */
    @Override
    public void mousePressed(MouseEvent e) { this.state.mousePressed(e); }

    /**
     * Gère l'événement de relâchement de souris, délégant l'action à l'état actuel.
     * @param e L'événement de relâchement de souris.
     */
    @Override
    public void mouseReleased(MouseEvent e) { this.state.mouseReleased(e); }

    /**
     * Gère l'événement de glissement de souris, délégant l'action à l'état actuel.
     * @param e L'événement de glissement de souris.
     */
    @Override
    public void mouseDragged(MouseEvent e) { this.state.mouseDragged(e); }
}
