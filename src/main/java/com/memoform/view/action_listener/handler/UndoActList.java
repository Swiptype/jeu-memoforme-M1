package com.memoform.view.action_listener.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.memoform.model.command.CommandHandler;

/**
 * Écouteur d'action pour l'opération "Annuler" (Undo).
 * 
 * Cette classe implémente {@link ActionListener} et permet de déclencher
 * l'annulation de la dernière commande effectuée, à l'aide d'un
 * {@link CommandHandler}, lorsque cela est possible.
 * Elle est typiquement utilisée dans une interface graphique (bouton "Undo").
 * 
 * @see CommandHandler
 */
public class UndoActList implements ActionListener {

    /** Gestionnaire des commandes permettant l'annulation. */
    private CommandHandler handler;

    /**
     * Construit un écouteur d'action pour l'annulation de commande.
     *
     * @param handler le gestionnaire de commandes responsable de l'annulation
     */
    public UndoActList(CommandHandler handler) {
        this.handler = handler;
    }

    /**
     * Appelé lorsque l'action est déclenchée (clic, raccourci, etc.).
     * Si une commande peut être annulée, exécute l'annulation.
     *
     * @param e l'événement utilisateur déclenchant cette action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.handler.canUndo()) {
            this.handler.undo();
        }
    }
}
