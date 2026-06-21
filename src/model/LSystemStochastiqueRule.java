package model;

public class LSystemStochastiqueRule  extends LSystemRule implements ILSystemRule{

    private Float percent;

    public LSystemStochastiqueRule(String symbol, String replacement) {
        super(symbol.charAt(0), replacement);
        this.percent = 1.0F;
    }
    public LSystemStochastiqueRule(String symbol, String replacement,Float percent) {
        super(symbol.charAt(0), replacement);
        this.percent = percent;
    }

    public Float getPercent() {
        return percent;
    }
    public void setPercent(Float percent) {
        this.percent = percent;
    }

}
