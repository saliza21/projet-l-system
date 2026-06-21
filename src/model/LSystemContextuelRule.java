package model;

import java.util.HashMap;
import java.util.Map;

public class LSystemContextuelRule extends LSystemRule implements ILSystemRule{
    private String context;

    public LSystemContextuelRule(String context, String replacement) {
        super(getRuleElements(context).get("context").toString().charAt(0), replacement);
        this.context =context;
    }
    public LSystemContextuelRule(String context, String replacement,Float percent) {
        super(getRuleElements(context).get("context").toString().charAt(0), replacement);
        this.context =context;
    }

    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }


    @Override
    public String toString() {
        return getContext() + "->" +getReplacement();
    }

    public static Map<String, Object> getRuleElements(String rule) {
        Map<String,Object> ruleElements = new HashMap<>();
        if(rule.contains("<")){
            ruleElements.put("precontext",rule.split("<")[0]);
            ruleElements.put("context",rule.split("<")[1].split(">")[0]);
        }
        if(rule.contains(">")){
            ruleElements.put("postcontext",rule.split(">")[1]);
            ruleElements.put("context",rule.split(">")[0].charAt(rule.split(">")[0].length()-1));
        }
        if(rule.length() == 1 )
            ruleElements.put("context",rule);
        return  ruleElements;
    }

}
