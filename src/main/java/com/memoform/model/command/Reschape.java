package com.memoform.model.command;

import java.util.HashMap;
import java.util.Map;

import com.memoform.model.shape.MFShape;

/**
 * Commande pour redimensionner ou modifier une forme (MFShape) avec des changements spécifiques.
 * Cette commande implémente l'interface {@link Command} et permet de modifier une forme
 * en appliquant des changements, tout en permettant l'annulation (compensation) de ces changements.
 * 
 * @see Command
 * 
 * @see MFShape
 */
public class Reschape implements Command {
    
    private MFShape shape;                  // La forme à redimensionner ou modifier
    private Map<String,Integer> changes;    // Les changements à appliquer à la forme

    /**
     * Constructeur de la commande Reschape.
     * 
     * @param shape La forme à redimensionner ou modifier
     * @param changes Les changements à appliquer à la forme, sous forme de clé/valeur
     */
    public Reschape(MFShape shape, Map<String,Integer> changes) {
        this.shape = shape;
        this.changes = changes;
    }
    
    /**
     * Applique les changements spécifiés à la forme.
     * Cette méthode est appelée pour effectuer les modifications sur la forme.
     */
    @Override
    public void operate() {
        this.shape.reshape(this.changes);
    }
    
    /**
     * Compense les changements effectués sur la forme en annulant les modifications.
     * Cette méthode est appelée pour annuler les changements précédemment appliqués.
     */
    @Override
    public void compensate() {
        Map<String,Integer> invChanges = new HashMap<>();
        
        // Annule les changements spécifiques selon le type de forme
        this.reverseRectangleChanges(this.changes, invChanges);
        this.reverseCircleChanges(this.changes, invChanges);
        this.reversePolygoneChanges(this.changes, invChanges);

        // Applique les changements inversés pour revenir à l'état initial
        this.shape.reshape(invChanges);
    }

    /**
     * Annule les changements spécifiques aux rectangles.
     * 
     * @param changes Les changements appliqués
     * @param invChanges Les changements inversés (annulés)
     */
    private void reverseRectangleChanges(Map<String,Integer> changes, Map<String,Integer> invChanges) {
        takeTheOpposite(changes, invChanges, "width_add");
        takeTheOpposite(changes, invChanges, "height_add");
        takeTheOpposite(changes, invChanges, "x_move");
        takeTheOpposite(changes, invChanges, "y_move");
    }

    /**
     * Annule les changements spécifiques aux cercles.
     * 
     * @param changes Les changements appliqués
     * @param invChanges Les changements inversés (annulés)
     */
    private void reverseCircleChanges(Map<String,Integer> changes, Map<String,Integer> invChanges) {
        takeTheOpposite(changes, invChanges, "radius_add");
    }

    /**
     * Annule les changements spécifiques aux polygones.
     * 
     * @param changes Les changements appliqués
     * @param invChanges Les changements inversés (annulés)
     */
    private void reversePolygoneChanges(Map<String,Integer> changes, Map<String,Integer> invChanges) {
        takeTheOpposite(changes, invChanges, "side_length_add");
    }

    /**
     * Inverse un changement spécifique.
     * 
     * @param changes Les changements appliqués
     * @param invChanges Les changements inversés
     * @param key La clé du changement à inverser
     */
    private void takeTheOpposite(Map<String,Integer> changes, Map<String,Integer> invChanges, String key) {
        if (changes.containsKey(key)) {
            invChanges.put(key, -changes.get(key));  // Inverse la valeur du changement
        }
    }
}
