package model;

import java.util.ArrayList;
import java.util.List;

public class LSystem {
    private String axiom;
    private List<ILSystemRule> rules;

    public LSystem(String axiom, List<ILSystemRule> rules) {
        this.axiom = axiom;
        this.rules = rules;
    }

    public LSystem(String axiom) {
        this.axiom = axiom;
        this.rules = new ArrayList<>();
    }

    public String getAxiom() {
        return axiom;
    }

    public List<ILSystemRule> getRules() {
        return rules;
    }

    public void addRule(ILSystemRule rule) {
        rules.add(rule);
    }
    public void setAxiom(String axiom) {
        this.axiom = axiom;
    }

    public void setRules(List<ILSystemRule> rules) {
        this.rules = rules;
    }

}
