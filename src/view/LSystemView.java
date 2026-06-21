package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import model.*;
import controller.LSystemController;
import utils.EcouteurModele;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class LSystemView extends JFrame implements EcouteurModele {

    private boolean is3D;
    private boolean isStandard;
    private String results;
    private final LSystemController controller;
    private  LSystem lSystem;
    private  LSystemInterpreter interpreter;


    // menuBar
    private JMenuBar menuBar;
    private JMenu interpretersMenu;
    private JMenuItem btn2D;
    private JMenuItem btn3D;

    // Input panel
    private JTextField axiomField;
    private JTextField angleField;
    private JTextArea rulesArea;
    private JTextField iterationsField;
    private JList<String> rulesList;
    private DefaultListModel<String> rulesListModel;
    private JButton generateButton;
    private JButton addRuleButton;

    //Graphic panels
    private CanvasPanel canvasPanel;
    private JFXPanel canvas3DPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private LSystelVisualInterpreter visualInterpreter;

    public LSystemView() {
        setTitle("L-System 2D Interpreter");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
        controller = new LSystemController(this, new LSystem(getAxiom()));
    }

    private void initComponents() {
        is3D = false;
        initMenuBar();
        initInputPanel();
        initCanvaPanels();
    }

    private void initCanvaPanels() {
        canvas3DPanel = new JFXPanel();
        canvasPanel = new CanvasPanel();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(canvas3DPanel, "3D");
        cardPanel.add(canvasPanel, "2D");

        add(cardPanel, BorderLayout.CENTER);
    }

    private void initInputPanel() {
        axiomField = new JTextField("F");
        angleField = new JTextField("45");
        rulesArea = new JTextArea("F=F[+F]F[-F]F");
        iterationsField = new JTextField("2");

        rulesListModel = new DefaultListModel<>();
        rulesList = new JList<>(rulesListModel);
        rulesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane rulesScrollPane = new JScrollPane(rulesList);
        rulesScrollPane.setPreferredSize(new Dimension(200, 100));

        generateButton = new JButton("Generate");
        addRuleButton = new JButton("Add Rule");

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        //row 1
        inputPanel.add(new JLabel("Axiom:"));
        inputPanel.add(axiomField);
        //row 2
        inputPanel.add(new JLabel("Rules:"));
        inputPanel.add(rulesArea);
        //row 3
        inputPanel.add(new JLabel("Angle:"));
        inputPanel.add(angleField);
        //row 4
        inputPanel.add(new JLabel("Iterations:"));
        inputPanel.add(iterationsField);
        //row 5
        inputPanel.add(addRuleButton);
        inputPanel.add(generateButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(inputPanel);
        leftPanel.add(rulesScrollPane);

        addRuleButton.setBackground(Color.ORANGE);
        addRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rule = rulesArea.getText().trim();
                if (!rule.isEmpty() && rule.contains("=")) {
                    rulesListModel.addElement(rule);
                    rulesArea.setText(""); // Clear the input area
                }
            }
        });
        generateButton.setBackground(Color.GREEN);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStandard = false;
                updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });

        add(leftPanel, BorderLayout.WEST);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        interpretersMenu = new JMenu("Interpreters");

        btn2D = new JMenuItem("2D");
        btn2D.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTitle("2D Interpreter selected");
                cardLayout.show(cardPanel, "2D");
                is3D = false;
            }
        });

        btn3D = new JMenuItem("3D");
        btn3D.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTitle("3D Interpreter selected");
                cardLayout.show(cardPanel, "3D");
                is3D = true;
            }
        });

        interpretersMenu.add(btn2D);
        interpretersMenu.addSeparator();
        interpretersMenu.add(btn3D);

        menuBar.add(interpretersMenu);

        JMenu standardExample = new JMenu("standards 2D");
        JMenuItem btna = new JMenuItem("a");
        JMenuItem btnb = new JMenuItem("b");
        JMenuItem btnc = new JMenuItem("c");
        JMenuItem btnd = new JMenuItem("d");
        JMenuItem btne = new JMenuItem("e");
        JMenuItem btnf = new JMenuItem("f");

        standardExample.add(btna);
        standardExample.add(btnb);
        standardExample.add(btnc);
        standardExample.add(btnd);
        standardExample.add(btne);
        standardExample.add(btnf);
        btna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });
        btnb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                  updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });
        btnc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                  updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });
        btnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                  updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });
        btne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                  updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });
        btnf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem clickedButton = (JMenuItem) e.getSource();
                controller.setModelToStandard(clickedButton.getText());
                isStandard = true;
                updateInterpreter();
                interpreter = controller.getInterpreter();
                modeleMisAJour(interpreter);
            }
        });

        menuBar.add(standardExample);
        this.setJMenuBar(menuBar);
    }


    // Methods for interacting with the GUI components
    public String getAxiom() {
        return axiomField.getText();
    }

    private void generateStructure() {
        try {

            if (is3D) {
                use3DInterpreter();
            } else {
                use2DInterpreter();
            }


        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid numeric value for iterations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateInterpreter() {
        if (!isStandard) {
            controller.setModel(controller.buildModel(getAxiom(), rulesList.getSelectedValuesList()));
            controller.setIterations(Integer.parseInt(iterationsField.getText()));
            controller.setAngle(Integer.parseInt(angleField.getText()));
        }

        results = controller.generateResults();

    }

    @Override
    public void modeleMisAJour(Object source) {
        generateStructure();
    }

    private Rectangle measureBounds(String result, int step, int adjustAngle) {
        int minX = 0, maxX = 0, minY = 0, maxY = 0;
        int x = 0, y = 0;
        double angle = 0;
        Stack<BranchState> branchStack = new Stack<>();

        for (char symbol : result.toCharArray()) {
            if (symbol == 'F') {
                int newX = x + (int) (step * Math.sin(Math.toRadians(angle)));
                int newY = y - (int) (step * Math.cos(Math.toRadians(angle)));
                minX = Math.min(minX, newX);
                maxX = Math.max(maxX, newX);
                minY = Math.min(minY, newY);
                maxY = Math.max(maxY, newY);
                x = newX;
                y = newY;
            } else if (symbol == '+') {
                angle += adjustAngle;
            } else if (symbol == '-') {
                angle -= adjustAngle;
            } else if (symbol == '[') {
                branchStack.push(new BranchState(x, y, angle));
            } else if (symbol == ']') {
                BranchState state = branchStack.pop();
                x = state.getX();
                y = state.getY();
                angle = state.getAngle();
            }
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    private void use2DInterpreter() {
        Graphics2D g2d = (Graphics2D) canvasPanel.getGraphics();
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, canvasPanel.getWidth(), canvasPanel.getHeight());

        Rectangle bounds = measureBounds(results, controller.getSteps(), controller.getAngle());
        double scale = 1.0;
        int translateX = 0;
        int translateY = 0;

        // Check if the drawing needs to be scaled down to fit on the canvas
        if (bounds.width > canvasPanel.getWidth() || bounds.height > canvasPanel.getHeight()) {
            double scaleX = (double) canvasPanel.getWidth() / (bounds.width + 10); // Padding
            double scaleY = (double) canvasPanel.getHeight() / (bounds.height + 10);
            scale = Math.min(scaleX, scaleY);

            translateX = (int) ((canvasPanel.getWidth() - (bounds.width * scale)) / 2 - (bounds.x * scale));
            translateY = (int) ((canvasPanel.getHeight() - (bounds.height * scale)) / 2 - (bounds.y * scale));
        } else {
            // Calculate translation to center the drawing on the canvas
            translateX = (canvasPanel.getWidth() - bounds.width) / 2 - bounds.x;
            translateY = (canvasPanel.getHeight() - bounds.height) / 2 - bounds.y;
        }

        // Apply scaling and translation to the Graphics2D object
        g2d.translate(translateX, translateY);
        g2d.scale(scale, scale);

        // Draw the L-system result
        visualInterpreter = new LSystem2DInterpreter(g2d, controller.getSteps(), controller.getAngle(), canvasPanel.getWidth(), canvasPanel.getHeight(), bounds, isStandard);
        visualInterpreter.drawResults(results);
        // Dispose of the graphics context to free up resources
        g2d.dispose();
    }

    private void use3DInterpreter() {
        // Ensure JavaFX runtime is initialized (can be placed in your application initialization)
        new JFXPanel();

        // Execute JavaFX operations on the JavaFX application thread
        Platform.runLater(() -> {

            visualInterpreter = new LSystem3DInterpreter(controller.getAngle());
            visualInterpreter.drawResults(results);
            Group resultGroup = ((LSystem3DInterpreter) visualInterpreter).getGroup();

            Rotate resulGroupRotate = new Rotate(0,0,0,1,Rotate.Y_AXIS);
            resultGroup.getTransforms().add(resulGroupRotate);

            // result group scale's calculations
            Bounds groupBounds = resultGroup.getBoundsInParent();
            double scaleFactorWidth = canvas3DPanel.getWidth() / groupBounds.getWidth();
            double scaleFactorHeight = canvas3DPanel.getHeight() / groupBounds.getHeight();
            // Use the smaller scale factor to fit the group within the panel
            double scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);
            Scale scale = new Scale(scaleFactor, scaleFactor, scaleFactor);
            // Apply the scale transformation to the group
            resultGroup.getTransforms().add(scale);

            // Center the resultGroup in the panel
            resultGroup.setTranslateX(canvas3DPanel.getWidth()/2);
            resultGroup.setTranslateY(canvas3DPanel.getHeight());

            PerspectiveCamera camera = new PerspectiveCamera(false);

            Timeline earthTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(resulGroupRotate.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(resulGroupRotate.angleProperty(), 360))
            );
            earthTimeline.setCycleCount(Timeline.INDEFINITE);
            earthTimeline.play();

            // goup all groups to a main one
            Group mainGroup = new Group();
            mainGroup.getChildren().add(resultGroup);

            Scene scene = new Scene(mainGroup, 500, 500);
            scene.setFill(javafx.scene.paint.Color.BLACK);
            scene.setCamera(camera);


            final Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
            final Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
            camera.getTransforms().addAll(xRotate, yRotate);

            final double[] anchorX = new double[1];
            final double[] anchorY = new double[1];

            scene.setOnMousePressed(event -> {
                anchorX[0] = event.getSceneX();
                anchorY[0] = event.getSceneY();
            });
            scene.setOnScroll(event -> {
                double delta = event.getDeltaY();
                camera.translateZProperty().set(camera.getTranslateZ() + delta);
            });
            scene.setOnMouseDragged(event -> {
                double deltaX = event.getSceneX() - anchorX[0];
                double deltaY = event.getSceneY() - anchorY[0];
                xRotate.setAngle(xRotate.getAngle() - (deltaY / 2));
                yRotate.setAngle(yRotate.getAngle() + (deltaX / 2));
                anchorX[0] = event.getSceneX();
                anchorY[0] = event.getSceneY();
            });

            // Since canvas3DPanel is a JFXPanel, setScene directly applies
            canvas3DPanel.setScene(scene);
        });
    }

}


