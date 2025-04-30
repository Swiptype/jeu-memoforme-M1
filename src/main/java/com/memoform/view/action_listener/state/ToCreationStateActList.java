package com.memoform.view.action_listener.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.memoform.view.StateController;

/**
 * Écouteur d'action permettant de passer à l'état de création.
 * 
 * Lorsqu'une action est déclenchée (par exemple un clic sur un bouton "Créer"),
 * ce gestionnaire utilise le {@link StateController} pour basculer
 * l'application en mode création.
 * 
 * @see StateController
 */
public class ToCreationStateActList implements ActionListener {

    /** Contrôleur d'état de l'application. */
    private StateController controller;

    /**
     * Construit un écouteur d'action pour passer en mode création.
     *
     * @param controller le contrôleur d'état à utiliser pour changer de mode
     */
    public ToCreationStateActList(StateController controller) {
        this.controller = controller;
    }

    /**
     * Appelé lorsque l'action est déclenchée.
     * Fait passer le contrôleur en mode création.
     *
     * @param e l'événement utilisateur déclenchant cette action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.controller.creationMode();
    }
}
