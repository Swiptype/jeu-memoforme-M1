package com.memoform.view.look_feek;

import javax.swing.JButton;

/**
 * Implémentation du look-and-feel par défaut pour la création de boutons.
 * 
 * Cette classe crée des boutons sans appliquer de style particulier (style par défaut).
 * 
 * @see MFLookAndFeel
 */
public class DefaultLookAndFeel implements MFLookAndFeel {

    /**
     * Crée un bouton avec le style par défaut.
     *
     * @param name Le texte à afficher sur le bouton.
     * @return Le bouton créé avec le style par défaut.
     */
    @Override
    public JButton createButton(String name) {
        return new JButton(name);
    }
}
