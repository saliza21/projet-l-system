package controller;

import model.*;
import view.LSystemView;
import java.util.ArrayList;
import java.util.List;

public class LSystemController {
    private LSystemView view;

    private LSystemInterpreter interpreter;

    private LSystem model;
    private int angle;
    private int steps;
    private int iterations;


    public LSystemController(LSystemView view, LSystem model) {
        this.steps = 10;
        this.angle = 45;
        this.iterations = 1;
        this.view = view;
        this.model = model;
    }

    public LSystem buildModel(String axiom,List<String> rules){
        return new LSystem(axiom,this.parseRules(rules));
    }

    public LSystem getModel() {
        return model;
    }

    public void setModel(LSystem model) {
        this.model = model;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getIterations() {
        return iterations;
    }
    public LSystemInterpreter getInterpreter() {
        return interpreter;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public static List<ILSystemRule> parseRules(List<String> rulesStrList) {
        List<ILSystemRule> lSysRules = new ArrayList<>();
        if (!rulesStrList.isEmpty()) {
            rulesStrList.forEach(ruleStr -> {
                if(ruleStr.contains("<") || ruleStr.contains(">")){
                    String context = ruleStr.contains("=")?ruleStr.split("=")[0]:"";
                    String replacement = ruleStr.contains("=")?ruleStr.split("=")[1].split( ":")[0]:"";
                    lSysRules.add(new LSystemContextuelRule(context, replacement));
                }else if(ruleStr.contains(":")) {
                    Float percent = ruleStr.contains(":")?Float.parseFloat(ruleStr.split(":")[1]):1.0F;
                    String context = ruleStr.contains("=")?ruleStr.split("=")[0]:"";
                    String replacement = ruleStr.contains("=")?ruleStr.split("=")[1].split( ":")[0]:"";
                    lSysRules.add(new LSystemStochastiqueRule(context, replacement,percent));
                }else{
                    String[] keyValuePair = ruleStr.split("=");
                    if (keyValuePair.length == 2) { // Ensure we have a valid key-value pair
                        char key = keyValuePair[0].charAt(0);
                        String value = keyValuePair[1];
                        lSysRules.add(new LSystemRule(key, value));
                    }
                }
            });
        }
        return lSysRules;
    }

    public String generateResults(){
        interpreter = new LSystemInterpreter(model);
        return interpreter.generateStructure(iterations);
    }

    public void setModelToStandard(String buttonText) {
        setSteps(10);
        switch (buttonText) {
            case "a":
                setModel(buildModel("F-F-F-F",List.of("F=FF-F-F-F-F-F+F")));
                setAngle(90);
                setIterations(4);
                break;
            case "b":
                setModel(buildModel("F-F-F-F",List.of( "F=FF-F-F-F-FF")));
                setAngle(90);
                setIterations(4);
                break;
            case "c":
                setModel(buildModel("F-F-F-F",List.of("F=FF-F+F-F-FF")));
                setAngle(90);
                setIterations(3);
                break;
            case "d":
                setModel(buildModel("F-F-F-F",List.of("F=FF-F--F-F")));
                setAngle(90);
                setIterations(4);
                break;
            case "e":
                setModel(buildModel("F-F-F-F",List.of("F=F-FF--F-F")));
                setAngle(90);
                setIterations(5);
                break;
            case "f":
                setModel(buildModel("F-F-F-F",List.of("F=F-F+F-F-F")));
                setAngle(90);
                setIterations(4);
                break;
            default:
                break;
        }
    }

}
