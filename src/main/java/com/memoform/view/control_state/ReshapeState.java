package com.memoform.view.control_state;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import com.memoform.model.command.Reschape;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;
import com.memoform.view.StateController;

/**
 * État de contrôle permettant de redimensionner ou modifier la forme sélectionnée.
 * 
 * Ce mode permet à l'utilisateur de cliquer et de redimensionner une forme 
 * (Rectangle, Cercle, Polygone) en fonction de l'endroit où il clique sur les bords de la forme.
 * 
 * @see AbstractControlState
 * 
 * @see MFShapeContainer
 * @see MFShape
 * @see StateController
 */
public class ReshapeState extends AbstractControlState {

    private MFShape selectedShape, clonedShape;
    private Point initialPoint;

    private Point startPoint;
    private Point endPoint;

    /**
     * Constructeur de l'état de redimensionnement.
     * 
     * @param controller Le contrôleur de l'état actuel.
     */
    public ReshapeState(StateController controller) {
        super(controller);
    }

    /**
     * Méthode redéfinie pour indiquer que cet état est en mode de redimensionnement.
     */
    @Override
    public void reshapeMode() {}

    /**
     * Lorsqu'une souris est pressée, cette méthode identifie si la forme
     * sur laquelle l'utilisateur clique est redimensionnable et la sélectionne.
     * 
     * @param me L'événement du clic de souris.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        MFShapeContainer container = this.controller.getContainer();
        this.selectedShape = null;

        this.startPoint = me.getPoint();

        for (int i = 0; i < container.getSize(); i++) {
            MFShape shape = container.getElementAt(i);
            if (!shape.onEdge(this.startPoint.x, this.startPoint.y)) continue;
            this.selectedShape = shape;  
            this.clonedShape = shape;
            this.initialPoint = new Point(this.selectedShape.getX(), this.selectedShape.getY());              
            break;
        }
    }

    /**
     * Lors du déplacement de la souris, cette méthode redimensionne la forme
     * sélectionnée en fonction du déplacement de la souris, tout en créant
     * une copie de la forme pour la mise à jour en temps réel.
     * 
     * @param e L'événement de déplacement de souris.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Si il n'y a pas de forme, on passe
        if (this.selectedShape == null) return;

        // On fait une copie de la forme selectionne
        MFShape newShape = this.selectedShape.clone();

        // On récupère les changements actuels
        this.endPoint = e.getPoint();
        newShape.reshape(getCurrentChanges());
        this.endPoint = null;

        // On échange la forme cloné avec une nouvelle forme cloné (avec les changements courrants)
        this.controller.getContainer().replace(this.clonedShape, newShape);
        this.clonedShape = newShape; // nouvelle ancienne forme cloné
    }

    /**
     * Lorsque le clic de souris est relâché, cette méthode applique les modifications
     * à la forme sélectionnée et enregistre cette action comme une commande pour pouvoir
     * être annulée ou refaite.
     * 
     * @param me L'événement de relâchement de souris.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        // Si il n'y a pas de forme, on passe
        if (this.selectedShape == null) return;

        // On récupère le dernier point
        this.endPoint = me.getPoint();

        // On récupère les changements
        Map<String,Integer> changes = this.getCurrentChanges();

        // On applique la commande
        Reschape reschape = new Reschape(selectedShape, changes);
        this.controller.getHandler().handle(reschape);

        // On remet la vraie forme
        this.controller.getContainer().replace(this.clonedShape, this.selectedShape);

        // On a effectue la modification donc on deselectionne la forme
        this.selectedShape = null;
    }

    /**
     * Récupère les changements actuels en fonction de la forme sélectionnée,
     * qu'il s'agisse d'un rectangle, cercle ou polygone.
     * 
     * @return Un map contenant les changements effectués (déplacement, redimensionnement).
     */
    private Map<String,Integer> getCurrentChanges() {
        Map<String,Integer> changes = new HashMap<>();
        this.putRectangleChanges(changes);
        this.putCircleChanges(changes);
        this.putPolygonChanges(changes);
        return changes;
    }

    /**
     * Calcule la direction du déplacement pour ajuster la forme en fonction du déplacement.
     * 
     * @param init La position initiale de la forme.
     * @param start La position de départ du déplacement.
     * @param end La position finale du déplacement.
     * @return Un code représentant la direction du déplacement.
     */
    private int moveCase(int init, int start, int end) {
        if (start < init) return (end < start) ? 1 : 2;
        if (start > init) return (end < start) ? 3 : 4;
        return -1;
    }
    
    /**
     * Met à jour les changements pour un rectangle en fonction du déplacement et redimensionnement.
     * 
     * @param changes La map contenant les changements à appliquer.
     */
    private void putRectangleChanges(Map<String, Integer> changes) {
        if (!(this.selectedShape instanceof Rectangle)) return;
        Rectangle rect = (Rectangle) this.selectedShape;
    
        int dx = this.endPoint.x - this.startPoint.x;
        int dy = this.endPoint.y - this.startPoint.y;
        int widthR = rect.getWidth();
        int heightR = rect.getHeight();
    
        if (dx != 0) {
            int caseX = moveCase(this.initialPoint.x, this.startPoint.x, this.endPoint.x);
            int x_move = 0, width_add = 0;
    
            switch (caseX) {
                case 1:
                    x_move = dx;
                    width_add = -dx;
                    break;
                case 2:
                    x_move = Math.min(dx, widthR);
                    width_add = (widthR == x_move) ? dx - 2 * widthR : -dx;
                    break;
                case 3:
                    x_move = (dx < -widthR) ? dx + widthR : 0;
                    width_add = (dx < -widthR) ? -2 * widthR - dx : dx;
                    break;
                case 4:
                    width_add = dx;
                    break;
            }
    
            if (caseX > 0) {
                changes.put("x_move", x_move);
                changes.put("width_add", width_add);
            }
        }
    
        if (dy != 0) {
            int caseY = moveCase(this.initialPoint.y, this.startPoint.y, this.endPoint.y);
            int y_move = 0, height_add = 0;
    
            switch (caseY) {
                case 1:
                    y_move = dy;
                    height_add = -dy;
                    break;
                case 2:
                    y_move = Math.min(dy, heightR);
                    height_add = (heightR == y_move) ? dy - 2 * heightR : -dy;
                    break;
                case 3:
                    y_move = (dy < -heightR) ? dy + heightR : 0;
                    height_add = (dy < -heightR) ? -2 * heightR - dy : dy;
                    break;
                case 4:
                    height_add = dy;
                    break;
            }
    
            if (caseY > 0) {
                changes.put("y_move", y_move);
                changes.put("height_add", height_add);
            }
        }
    }

    /**
     * Met à jour les changements pour un cercle en fonction du déplacement de la souris.
     * 
     * @param changes La map contenant les changements à appliquer.
     */
    private void putCircleChanges(Map<String,Integer> changes) {
        if (!(this.selectedShape instanceof Circle)) return;
        double addRadius = this.distEucli(this.startPoint, this.endPoint);

        double distInitEnd = this.distEucli(this.initialPoint, this.endPoint);
        double distInitStart = this.distEucli(this.initialPoint, this.startPoint);

        if (distInitStart < distInitEnd)
            changes.put("radius_add", (int) addRadius);
        else if (distInitStart > distInitEnd)
            changes.put("radius_add", (int) -addRadius);   
        
    }   

    /**
     * Calcule la distance nécessaire pour ajuster le côté d'un polygones.
     *
     * @param startPoint Le point de départ du déplacement de la souris.
     * @param endPoint Le point final du déplacement de la souris.
     * @return La différence de rayon calculée en fonction du déplacement.
     */
    private double distToRadiusDist(MFPolygon polygon, double dist) {
        return dist * (2 * Math.sin(Math.PI / polygon.getNbSides()));
    }

    /**
     * Met à jour les changements pour un polygone en fonction du déplacement de la souris.
     * Cette méthode ajustera les coordonnées des sommets du polygone en fonction
     * de l'endroit où l'utilisateur clique et déplace la souris.
     * 
     * @param changes La map contenant les changements à appliquer.
     */
    private void putPolygonChanges(Map<String,Integer> changes) {
        if (!(this.selectedShape instanceof MFPolygon)) return;
        MFPolygon polygon = (MFPolygon) this.selectedShape;
        double distStartEnd = this.distEucli(this.startPoint, this.endPoint);
        double addSideLength = this.distToRadiusDist(polygon, distStartEnd);

        double distInitEnd = this.distEucli(this.initialPoint, this.endPoint);
        double distInitStart = this.distEucli(this.initialPoint, this.startPoint);

        
        if (distInitStart < distInitEnd) {
            changes.put("side_length_add", (int) addSideLength);
        }
        else if (distInitStart > distInitEnd) {
            changes.put("side_length_add", (int) -addSideLength);
        }
    }

    /**
     * Calcule la distance euclidienne entre deux points.
     * 
     * @param p1 Le premier point.
     * @param p2 Le deuxième point.
     * @return La distance euclidienne entre les deux points.
     */
    private double distEucli(Point p1, Point p2) {
        return distEucli(p1.x, p1.y, p2.x, p2.y);
    }
    /**
     * Calcule la distance euclidienne entre deux points représentés par leurs coordonnées.
     * 
     * @param x1 La coordonnée x du premier point.
     * @param y1 La coordonnée y du premier point.
     * @param x2 La coordonnée x du deuxième point.
     * @param y2 La coordonnée y du deuxième point.
     * @return La distance euclidienne entre les deux points.
     */
    private double distEucli(int x1, int y1, int x2, int y2) {
        double xx = Math.pow(x1 - x2, 2);
        double yy = Math.pow(y1 - y2, 2);
        return Math.sqrt(xx + yy);
    }
}