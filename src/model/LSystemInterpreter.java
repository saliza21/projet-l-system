package model;

import utils.AbstrcatModeleEcoutable;

import javax.management.JMException;
import javax.management.JMRuntimeException;
import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class LSystemInterpreter extends AbstrcatModeleEcoutable {
    private LSystem lSystem;

    public LSystemInterpreter(LSystem lSystem) {
        this.lSystem = lSystem;
    }

    public String applyRules(String input){
        StringBuilder result = new StringBuilder(); // result sequence after applying LSystem rule to the current sequence {input}. input == axiom  in the first iteration

        List<ILSystemRule> rules = lSystem.getRules();
        List<ILSystemRule> stockastiquesRules = lSystem.getRules().stream()
                .filter(rule -> rule instanceof LSystemStochastiqueRule)
                .collect(Collectors.toList());

        Stack<LSystemStochastiqueRule> appliedStockastiqueRules = new Stack<>();
        for (char symbol : input.toCharArray()) {  // parse each character from the current sequence
            boolean symbolFound = false;

            for (ILSystemRule rule : rules) {   // iterate throw rules

                if (rule.getSymbol() == symbol) {
                    if(rule instanceof LSystemRule && !(rule instanceof LSystemContextuelRule) && !(rule instanceof LSystemStochastiqueRule)){
                        result.append(rule.getReplacement());
                    }else if(rule instanceof  LSystemContextuelRule){
                        LSystemContextuelRule stochasticRule = (LSystemContextuelRule) rule;
                        Map<String, Object> ruleElements = LSystemContextuelRule.getRuleElements(stochasticRule.getContext());
                        StringBuilder contextPattern = getContextPattern(ruleElements);
                        int contextIndex = contextPattern.indexOf(ruleElements.get("context").toString());
                        int contextPatternSize = contextPattern.length();

                        int startIndex = input.indexOf(ruleElements.get("context").toString()) - contextIndex;
                        int endIndex = startIndex + contextPatternSize;
                        if (startIndex >= 0 && endIndex <= input.length()) {
                                result.append(rule.getReplacement());
                        }
                    } else if (rule instanceof  LSystemStochastiqueRule) {
                        LSystemStochastiqueRule stochasticRule = (LSystemStochastiqueRule) rule;
                        boolean choiceApplicationRule = getRandomBooleanWithDistribution(stochasticRule.getPercent());
                        appliedStockastiqueRules.add((LSystemStochastiqueRule) rule);
                        if(choiceApplicationRule)
                            result.append(rule.getReplacement());
                    }
                    symbolFound = true;
                }
            }

            if (!symbolFound) {
                result.append(symbol);
            }
        }

        return result.toString();
    }

    public String generateStructure(int iterations) {
        String currentSequence = lSystem.getAxiom();  // axiom state
        List<ILSystemRule> rules = lSystem.getRules();
        List<ILSystemRule> stockastiquesRules = lSystem.getRules().stream()
                .filter(rule -> rule instanceof LSystemStochastiqueRule)
                .collect(Collectors.toList());

        // Extract unique symbols using a Set
        Set<Character> uniqueSymbols = stockastiquesRules.stream()
                .map(ILSystemRule::getSymbol)
                .collect(Collectors.toSet());

        Map<Character, List<LSystemStochastiqueRule>> symbolRulesMap = uniqueSymbols.stream()
                .collect(Collectors.toMap(
                        symbol -> symbol,
                        symbol -> stockastiquesRules.stream()
                                .filter(rule -> rule instanceof LSystemStochastiqueRule && rule.getSymbol() == symbol)
                                .map(rule -> (LSystemStochastiqueRule) rule)
                                .collect(Collectors.toList())
                ));
        Map<String,Boolean> rulesValidMap = new HashMap<>();
        // Check if the sum of percentages for each symbol equals 1
        symbolRulesMap.forEach((symbol, stockRules) -> {
            float sumOfPercentages = (float) stockRules.stream()
                    .mapToDouble(LSystemStochastiqueRule::getPercent)
                    .sum();

            if (sumOfPercentages == 1.0) {
                rulesValidMap.put(symbol.toString(),Boolean.TRUE);
            } else {
                rulesValidMap.put(symbol.toString(),Boolean.FALSE);
            }
        });

        if(rulesValidMap.values().contains(Boolean.FALSE)){
            JOptionPane.showMessageDialog(null, "La somme des pourcentages n'est pas égale à 1.", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        for (int i = 0; i < iterations; i++) {
            currentSequence = applyRules(currentSequence); // New sequence generated after applying rules to the axion
        }
        fireChangement();
        return currentSequence;
    }

    public void setlSystem(LSystem lSystem) {
        this.lSystem = lSystem;
    }

    public static boolean getRandomBooleanWithDistribution(double trueProbability) {
        if (trueProbability < 0 || trueProbability > 1) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }

        Random random = new Random();
        return random.nextDouble() < trueProbability;
    }
    private static StringBuilder getContextPattern(Map<String, Object> ruleElements) {
        StringBuilder rslt = new StringBuilder();
        if(ruleElements.get("precontext")!=null)
            rslt.append(ruleElements.get("precontext").toString());
        if(ruleElements.get("context")!=null)
            rslt.append(ruleElements.get("context").toString());
        if(ruleElements.get("postcontext")!=null)
            rslt.append(ruleElements.get("postcontext").toString());
        return rslt;
    }

}

