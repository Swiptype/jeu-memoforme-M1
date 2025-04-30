package com.memoform.view.look_feek;

import java.awt.Color;
import javax.swing.JButton;

/**
 * Implémentation du look-and-feel "Light" pour la création de boutons.
 *
 * Cette classe crée des boutons avec un fond clair et un texte noir.
 *
 * @see MFLookAndFeel
 */
public class LightLookAndFeel implements MFLookAndFeel {

    /**
     * Crée un bouton avec un fond clair et du texte noir.
     *
     * @param name Le texte à afficher sur le bouton.
     * @return Le bouton créé avec le style "Light".
     */
    @Override
    public JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(Color.LIGHT_GRAY);  // Fond clair
        button.setForeground(Color.BLACK);       // Texte noir
        return button;
    }
}
