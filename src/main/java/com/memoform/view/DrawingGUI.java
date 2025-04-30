package com.memoform.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.memoform.model.command.CommandHandler;
import com.memoform.model.container.MFShapeContainer;
import com.memoform.model.shape.Circle;
import com.memoform.model.shape.MFPolygon;
import com.memoform.model.shape.MFShape;
import com.memoform.model.shape.Rectangle;
import com.memoform.view.action_listener.handler.RedoActList;
import com.memoform.view.action_listener.handler.UndoActList;
import com.memoform.view.action_listener.state.ToCreationStateActList;
import com.memoform.view.action_listener.state.ToMovementStateActList;
import com.memoform.view.action_listener.state.ToRemoveStateActList;
import com.memoform.view.action_listener.state.ToReshapeStateActList;
import com.memoform.view.look_feek.LightLookAndFeel;
import com.memoform.view.look_feek.MFLookAndFeel;

/**
 * Fenêtre principale de l'application de dessin. Cette classe permet de créer une interface graphique pour dessiner,
 * déplacer, supprimer et redimensionner des formes géométriques telles que des rectangles, cercles, triangles, etc.
 * Elle permet également de gérer les actions de l'utilisateur en fonction de différents modes (éditeur ou lecteur).
 * <p>
 * Cette classe utilise le modèle de conception MVC avec un contrôleur {@link StateController} pour gérer les actions
 * et un {@link CommandHandler} pour gérer les commandes.
 * </p>
 * 
 * @see CommandHandler
 * @see MFShapeContainer
 * @see MFShape
 * @see MFLookAndFeel
 */
public class DrawingGUI extends JFrame {

    /**
     * Enumération représentant les modes de l'application.
     * Le mode {@code EDITOR_MODE} permet d'ajouter, déplacer et modifier des formes.
     * Le mode {@code READER_MODE} est utilisé pour visualiser les formes sans les modifier.
     */
    public static enum MODE {
        EDITOR_MODE, READER_MODE
    }

    /** Apparence de l'application (style visuel). */
    public static MFLookAndFeel LOOK = new LightLookAndFeel();

    /** Liste des formes disponibles à créer. */
    public List<String> shapes = new ArrayList<>();

    /** Carte associant une forme à son action de création correspondante. */
    public Map<String, Runnable> shapeAction = new HashMap<>();

    /** Gestionnaire des commandes effectuées. */
    private CommandHandler handler = new CommandHandler();

    /** Conteneur des formes dessinées. */
    private MFShapeContainer shapeContainer;

    /** Panneau affichant les formes. */
    private ShapeContainerJPanel shapePanel;

    /** Contrôleur de l'état de l'application. */
    private StateController controller;

    /**
     * Constructeur par défaut créant une fenêtre avec un conteneur de formes vide et un gestionnaire de commandes par défaut.
     */
    public DrawingGUI() {
        this(new MFShapeContainer(), new CommandHandler());
    }

    /**
     * Constructeur avec un conteneur de formes.
     *
     * @param shapeContainer Le conteneur des formes à afficher.
     */
    public DrawingGUI(MFShapeContainer shapeContainer) {
        this(shapeContainer, null);
    }

    /**
     * Constructeur avec un conteneur de formes et un gestionnaire de commandes.
     *
     * @param shapeContainer Le conteneur des formes à afficher.
     * @param handler Le gestionnaire de commandes.
     */
    public DrawingGUI(MFShapeContainer shapeContainer, CommandHandler handler) {
        super("Drawing");
        this.shapeContainer = shapeContainer;
        this.handler = handler;
        this.shapePanel = new ShapeContainerJPanel(this.shapeContainer);
        this.controller = new StateController(this.shapeContainer, this.handler, this.shapePanel);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        this.setBasicShapes();
    }

    /**
     * Configure l'interface graphique en fonction du mode donné.
     * @param mode Le mode d'affichage de l'interface (éditeur ou lecteur).
     */
    public void setup(MODE mode) {        
        if (mode == MODE.EDITOR_MODE) {
            this.setupNorth(); 
            this.setupSouth();
        }
        this.setupCenter();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(600, 600));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Configure la section supérieure de la fenêtre (avec les options de création de formes).
     */
    private void setupNorth() {
        Container contentPane = this.getContentPane();
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(1, 7, 10, 0));
        contentPane.add(panel, BorderLayout.NORTH);

        JComboBox<String> creaComboBox = new JComboBox<>(); 
        creaComboBox.addActionListener(new ToCreationStateActList(this.controller));
        for (String shape : shapes) creaComboBox.addItem(shape);
        creaComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() != creaComboBox) return;
                shapeAction.get(creaComboBox.getSelectedItem()).run();
            }
        });

        JButton mvButton = DrawingGUI.LOOK.createButton("Move"); mvButton.addActionListener(new ToMovementStateActList(controller));
        JButton rmButton = DrawingGUI.LOOK.createButton("Remove"); rmButton.addActionListener(new ToRemoveStateActList(controller));
        JButton growButton = DrawingGUI.LOOK.createButton("Reschape"); growButton.addActionListener(new ToReshapeStateActList(controller));

        panel.add(creaComboBox);
        panel.add(mvButton); 
        panel.add(rmButton); 
        panel.add(growButton); 
    }

    /**
     * Configure la section centrale de la fenêtre (panneau d'affichage des formes).
     */
    private void setupCenter() {
        Container contentPane = this.getContentPane();
        this.shapePanel.setPreferredSize(new Dimension(1080, 720)); 
        this.shapePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        this.shapePanel.setBackground(new Color(245, 245, 245));
        contentPane.add(this.shapePanel, BorderLayout.CENTER);
    }

    /**
     * Configure la section inférieure de la fenêtre (avec les boutons de contrôle tels que "Undo", "Redo", etc.).
     */
    private void setupSouth() {
        Container contentPane = this.getContentPane();
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton undoButton = DrawingGUI.LOOK.createButton("Undo"); undoButton.addActionListener(new UndoActList(this.handler));
        JButton redoButton = DrawingGUI.LOOK.createButton("Redo"); redoButton.addActionListener(new RedoActList(this.handler));
        panel.add(undoButton); 
        panel.add(redoButton);  

        JButton validationButton = new JButton("Done");
        validationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {dispose();}
        });
        panel.add(validationButton);

        contentPane.add(panel, BorderLayout.SOUTH);
    }

    /**
     * Initialise les formes de base disponibles à créer dans l'application.
     */
    private void setBasicShapes() {
        shapes.add("Rectangle");    
        shapeAction.put(
            "Rectangle", 
            () -> this.controller.CS.setShapeSupplier(() -> new Rectangle())
        );
        
        shapes.add("Circle");       
        shapeAction.put(
            "Circle", 
            () -> this.controller.CS.setShapeSupplier(() -> new Circle())
        );
        
        shapes.add("Triangle");     
        shapeAction.put(
            "Triangle", 
            () -> this.controller.CS.setShapeSupplier(() -> new MFPolygon(3))
        );
        
        shapes.add("Pentagone");    
        shapeAction.put(
            "Pentagone", 
            () -> this.controller.CS.setShapeSupplier(() -> new MFPolygon(5))
        );
        
        shapes.add("Octagone");     
        shapeAction.put(
            "Octagone", 
            () -> this.controller.CS.setShapeSupplier(() -> new MFPolygon(8))
        );
    }


}
