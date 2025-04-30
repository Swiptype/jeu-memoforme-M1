package com.memoform.view.shape_view.cast;

import java.lang.reflect.InvocationTargetException;

import com.memoform.model.shape.MFShape;
import com.memoform.view.shape_view.MFShapeView;

/**
 * Classe représentant un lien de casting non spécifique dans une chaîne de responsabilité.
 * 
 * Ce lien permet de gérer des formes génériques (non spécifiques) et leur conversion en vues
 * correspondantes, en utilisant la réflexion pour créer la vue associée à une forme.
 * 
 * @see MFShape
 * @see MFShapeView
 */
public class NonSpecificCaster extends CasterLink {

    private Class<? extends MFShape> clazz;
    private Class<? extends MFShapeView<?>> clazzView;

    /**
     * Constructeur qui initialise le lien avec une forme et une vue spécifiques.
     *
     * @param clazz La classe de la forme à gérer.
     * @param clazzView La classe de la vue correspondant à la forme.
     */
    public NonSpecificCaster(Class<? extends MFShape> clazz, Class<? extends MFShapeView<?>> clazzView) {
        this.clazz = clazz;
        this.clazzView = clazzView;
    }

    /**
     * Vérifie si la forme donnée est du type géré par ce lien.
     *
     * @param shape La forme à vérifier.
     * @return true si la forme est une instance du type géré, sinon false.
     */
    @Override
    protected boolean membership(MFShape shape) {
        return this.clazz.isInstance(shape);
    }

    /**
     * Crée la vue correspondante à la forme donnée en utilisant la réflexion pour instancier
     * la classe de vue avec la forme en paramètre.
     *
     * @param shape La forme à transformer en vue.
     * @return La vue correspondante à la forme, ou null en cas d'erreur.
     */
    @Override
    protected MFShapeView<?> getCorrespondingView(MFShape shape) {
        try {
            return this.clazzView.getConstructor(clazz).newInstance(shape);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
