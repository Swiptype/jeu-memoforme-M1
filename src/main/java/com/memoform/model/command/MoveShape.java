package com.memoform.model.command;

import com.memoform.model.shape.MFShape;

/**
 * Commande pour déplacer une forme (MFShape) d'un certain vecteur (xV, yV).
 * Cette commande implémente l'interface {@link Command} et permet de déplacer une forme
 * tout en permettant l'annulation (compensation) de ce déplacement.
 * 
 * @see Command
 * 
 * @see MFShape
 */
public class MoveShape implements Command {

    private MFShape shape;  // La forme à déplacer
    private int xV, yV;     // Les valeurs de déplacement en X et Y

    /**
     * Constructeur de la commande MoveShape.
     * 
     * @param shape La forme à déplacer
     * @param xV Le déplacement en X
     * @param yV Le déplacement en Y
     */
    public MoveShape(MFShape shape, int xV, int yV) {
        this.shape = shape;
        this.xV = xV;
        this.yV = yV;
    }

    /**
     * Effectue l'opération de déplacement de la forme.
     * Cette méthode est appelée pour déplacer la forme selon les vecteurs xV et yV.
     */
    @Override
    public void operate() {
        this.shape.setX(this.shape.getX() + xV);
        this.shape.setY(this.shape.getY() + yV);
    }

    /**
     * Compense l'opération de déplacement en ramenant la forme à sa position initiale.
     * Cette méthode est appelée pour annuler le déplacement de la forme.
     */
    @Override
    public void compensate() {
        this.shape.setX(this.shape.getX() - xV);
        this.shape.setY(this.shape.getY() - yV);
    }

}
