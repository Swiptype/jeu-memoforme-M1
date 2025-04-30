package com.memoform.view.action_listener.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.memoform.model.command.CommandHandler;

/**
 * Écouteur d'action pour l'opération "Redo" (Refaire).
 * 
 * Cette classe implémente l'interface {@link ActionListener} et est utilisée
 * pour déclencher l'action de refaire une commande via un {@link CommandHandler}.
 * Elle est généralement associée à un bouton ou un menu permettant à l'utilisateur
 * de rejouer la dernière commande annulée, si cela est possible.
 * 
 * @see CommandHandler
 */
public class RedoActList implements ActionListener {

    /** Référence au gestionnaire de commandes permettant de gérer l'historique des actions. */
    private CommandHandler handler;

    /**
     * Construit un nouvel écouteur pour l'action "Refaire".
     *
     * @param handler le gestionnaire de commandes à utiliser
     */
    public RedoActList(CommandHandler handler) {
        this.handler = handler;
    }

    /**
     * Méthode appelée lors de l'activation de l'événement (ex. : clic sur un bouton).
     * Elle vérifie si une action peut être refaite, et si oui, elle l'exécute.
     *
     * @param e l'événement déclenché
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.handler.canRedo()) {
            this.handler.redo();
        }
    }
}
