package com.memoform.view.control_state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Interface représentant un état de contrôle dans l'application MemoForm.
 * 
 * Un état de contrôle définit le comportement de l'application en réponse
 * aux événements utilisateur (clics, mouvements de souris, etc.).
 * Chaque implémentation de cet état correspond à un mode particulier :
 * création, déplacement, suppression ou redimensionnement de formes.
 */
public interface ControlState {

    /**
     * Passe l'application en mode création.
     * Ce mode permet à l'utilisateur de dessiner de nouvelles formes.
     */
    void creationMode();

    /**
     * Passe l'application en mode déplacement.
     * Ce mode permet de déplacer des formes existantes.
     */
    void movementMode();

    /**
     * Passe l'application en mode suppression.
     * Ce mode permet de supprimer des formes de la zone de dessin.
     */
    void removeMode();

    /**
     * Passe l'application en mode redimensionnement.
     * Ce mode permet de modifier la taille des formes existantes.
     */
    void reshapeMode();

    /**
     * Gère l'événement de clic de souris.
     * 
     * @param e L'événement de souris.
     */
    void mouseClicked(MouseEvent e);

    /**
     * Gère l'événement de pression de la souris.
     * 
     * @param e L'événement de souris.
     */
    void mousePressed(MouseEvent e);

    /**
     * Gère l'événement de relâchement de la souris.
     * 
     * @param e L'événement de souris.
     */
    void mouseReleased(MouseEvent e);

    /**
     * Gère l'événement de glissement de la souris (drag).
     * 
     * @param e L'événement de souris.
     */
    void mouseDragged(MouseEvent e);

    /**
     * Dessine les éléments spécifiques à cet état.
     * Cette méthode est appelée à chaque rafraîchissement de l'interface graphique.
     * 
     * @param g Le contexte graphique utilisé pour dessiner.
     */
    void paint(Graphics g);
}
