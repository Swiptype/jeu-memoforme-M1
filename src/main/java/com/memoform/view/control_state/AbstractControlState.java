package com.memoform.view.control_state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.memoform.view.StateController;

/**
 * Classe abstraite représentant un état de contrôle générique dans l'application.
 * Elle implémente l'interface {@link ControlState} et fournit des implémentations par défaut
 * pour la gestion des événements de souris et des transitions entre états.
 * 
 * Les sous-classes peuvent surcharger les méthodes nécessaires pour définir un comportement spécifique
 * (par exemple, déplacement, création, suppression ou redimensionnement de formes).
 * 
 * @see ControlState
 * 
 * @see StateController
 */
public abstract class AbstractControlState implements ControlState {

    /**
     * Le contrôleur principal qui gère les états.
     */
    protected StateController controller;

    /**
     * Constructeur de l'état de contrôle abstrait.
     * 
     * @param controller Le contrôleur responsable des transitions d'état.
     */
    public AbstractControlState(StateController controller) {
        this.controller = controller;
    }

    /**
     * Passe en mode de création (CreationState).
     */
    public void creationMode() {
        this.controller.switchState(this.controller.CS);
    }

    /**
     * Passe en mode de déplacement (MovementState).
     */
    public void movementMode() {
        this.controller.switchState(this.controller.MS);
    }

    /**
     * Passe en mode de suppression (RemoveState).
     */
    public void removeMode() {
        this.controller.switchState(this.controller.RMS);
    }

    /**
     * Passe en mode de redimensionnement (ReshapeState).
     */
    public void reshapeMode() {
        this.controller.switchState(this.controller.RS);
    }

    /**
     * Gère l'événement de clic de souris. Par défaut, cette méthode ne fait rien.
     * 
     * @param e L'événement de souris.
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * Gère l'événement de pression de souris. Par défaut, cette méthode ne fait rien.
     * 
     * @param e L'événement de souris.
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * Gère l'événement de relâchement de souris. Par défaut, cette méthode ne fait rien.
     * 
     * @param e L'événement de souris.
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * Gère l'événement de glissement de souris. Par défaut, cette méthode ne fait rien.
     * 
     * @param e L'événement de souris.
     */
    public void mouseDragged(MouseEvent e) {}

    /**
     * Dessine les éléments spécifiques à cet état. Par défaut, cette méthode ne fait rien.
     * 
     * @param g Le contexte graphique utilisé pour le rendu.
     */
    public void paint(Graphics g) {}

    /**
     * Retourne le nom simple de la classe, utilisé pour identifier l'état courant.
     * 
     * @return Le nom de la classe d'état sans le package.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
