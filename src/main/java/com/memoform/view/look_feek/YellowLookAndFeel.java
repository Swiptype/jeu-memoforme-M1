package com.memoform.view.look_feek;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Implémentation d'un look-and-feel avec un bouton de fond jaune.
 * 
 * Cette classe crée des boutons avec un fond jaune et un texte par défaut.
 * 
 * @see MFLookAndFeel
 */
public class YellowLookAndFeel implements MFLookAndFeel {

    /**
     * Crée un bouton avec un fond jaune.
     *
     * @param name Le texte à afficher sur le bouton.
     * @return Le bouton créé avec un fond jaune.
     */
    @Override
    public JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(Color.YELLOW);
        return button;
    }
}
