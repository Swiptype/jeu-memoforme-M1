package com.memoform.view.look_feek;

import javax.swing.JButton;

/**
 * Interface pour la création de boutons avec un look-and-feel personnalisé.
 */
public interface MFLookAndFeel {

    /**
     * Crée un bouton avec le texte spécifié.
     *
     * @param name Le texte à afficher sur le bouton.
     * @return Le bouton créé.
     */
    JButton createButton(String name);
}
