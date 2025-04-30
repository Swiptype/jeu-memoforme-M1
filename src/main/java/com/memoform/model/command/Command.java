package com.memoform.model.command;

/**
 * Interface représentant une commande qui peut être exécutée et compensée.
 * Les classes implémentant cette interface devront définir la logique pour
 * l'exécution (opération) et la compensation (annulation) de la commande.
 */
public interface Command {

    /**
     * Effectue l'opération de la commande.
     * Cette méthode est appelée pour exécuter la commande.
     */
    void operate();

    /**
     * Compense l'opération effectuée par la commande.
     * Cette méthode est appelée pour annuler l'opération effectuée par {@link #operate()}.
     */
    void compensate();
}
