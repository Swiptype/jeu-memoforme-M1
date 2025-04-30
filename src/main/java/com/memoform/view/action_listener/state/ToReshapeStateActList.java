package com.memoform.view.action_listener.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.memoform.view.StateController;

/**
 * Écouteur d'action permettant de passer à l'état de redimensionnement (reshape).
 *
 * Lorsqu'une action utilisateur est déclenchée (comme un clic sur un bouton),
 * cet écouteur demande au {@link StateController} de basculer en mode
 * redimensionnement des formes.
 * 
 * @see StateController
 */
public class ToReshapeStateActList implements ActionListener {

    /** Contrôleur d'état de l'application. */
    private StateController controller;

    /**
     * Construit un écouteur d'action pour activer le mode redimensionnement.
     *
     * @param controller le contrôleur d'état utilisé pour changer de mode
     */
    public ToReshapeStateActList(StateController controller) {
        this.controller = controller;
    }

    /**
     * Appelé lors du déclenchement de l'action.
     * Active le mode redimensionnement via le contrôleur.
     *
     * @param e l'événement utilisateur déclencheur
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.controller.reshapeMode();
    }
}
