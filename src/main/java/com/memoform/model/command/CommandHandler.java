package com.memoform.model.command;

import java.util.Stack;

/**
 * Cette classe gère les commandes dans un système de type undo/redo.
 * Elle permet de stocker les commandes effectuées pour pouvoir les annuler (undo)
 * ou les refaire (redo).
 * 
 * @see Command
 */
public class CommandHandler {

    private Stack<Command> pile_undo;  // Pile pour les commandes à annuler
    private Stack<Command> pile_redo;  // Pile pour les commandes à refaire

    /**
     * Constructeur de CommandHandler.
     * Initialise les piles pour les actions undo et redo.
     */
    public CommandHandler() {
        this.pile_redo = new Stack<>();
        this.pile_undo = new Stack<>();
    }

    /**
     * Gère l'exécution d'une commande. La commande est opérée et ajoutée à la pile d'undo.
     * La pile de redo est réinitialisée.
     *
     * @param command La commande à exécuter
     */
    public void handle(Command command) {
        command.operate();  // Exécute l'action de la commande
        this.pile_undo.add(command);  // Ajoute la commande à la pile d'undo
        this.pile_redo.clear();  // Réinitialise la pile de redo (on ne peut plus refaire après une nouvelle action)
    }

    /**
     * Refait la dernière commande annulée (redo).
     * Si la pile de redo est vide, aucune action n'est effectuée.
     */
    public void redo() {
        if (this.pile_redo.isEmpty()) return;  // Si rien à refaire, sortir de la méthode

        Command command = this.pile_redo.pop();  // Récupère la commande à refaire
        command.operate();  // Exécute l'action de la commande
        this.pile_undo.add(command);  // Ajoute la commande à la pile d'undo
    }

    /**
     * Annule la dernière commande effectuée (undo).
     * Si la pile d'undo est vide, aucune action n'est effectuée.
     */
    public void undo() {
        if (this.pile_undo.isEmpty()) return;  // Si rien à annuler, sortir de la méthode

        Command command = this.pile_undo.pop();  // Récupère la commande à annuler
        command.compensate();  // Annule l'effet de la commande
        this.pile_redo.add(command);  // Ajoute la commande à la pile de redo pour pouvoir la refaire
    }

    /**
     * Vérifie si une commande peut être annulée (undo).
     *
     * @return true si des commandes sont disponibles pour être annulées, false sinon
     */
    public boolean canUndo() {
        return !this.pile_undo.isEmpty();  // Retourne true si la pile d'undo contient des commandes
    }

    /**
     * Vérifie si une commande peut être refaite (redo).
     *
     * @return true si des commandes sont disponibles pour être refaites, false sinon
     */
    public boolean canRedo() {
        return !this.pile_redo.isEmpty();  // Retourne true si la pile de redo contient des commandes
    }
}
