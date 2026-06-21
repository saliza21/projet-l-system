package model;

public class LSystemRule implements ILSystemRule{
    private char symbol;
    private String replacement;

    public LSystemRule(char symbol, String replacement) {
        this.symbol = symbol;
        this.replacement = replacement;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getReplacement() {
        return replacement;
    }

    @Override
    public String toString() {
        return getSymbol() + "->" +getReplacement();
    }
}

