package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

/**
 * Created by mtebele on 19/3/16.
 */
public class TokenGenerator {

    private ArrayList<String> tokens;
    private StringBuilder builder;
    private boolean currentlyInGroup = false;
    private boolean currentlyEscaping = false;

    public TokenGenerator() {
        tokens = new ArrayList<>();
        builder = new StringBuilder();
    }

    public ArrayList<String> getTokens(String regEx) {
        for (int i = 0; i < regEx.length(); i++) {
            char charValue = regEx.charAt(i);
            builder.append(charValue);

            if (RegExUtils.isGroup(charValue) || currentlyInGroup || currentlyEscaping) {
                processGroups(charValue);
            } else if (RegExUtils.isQuantifier(charValue)) {
                processQuantifiers(charValue);
            } else if (RegExUtils.isEscaped(charValue)) {
                currentlyEscaping = true;
            } else {
                processLiterals();
            }
        }

        return tokens;
    }

    private void processLiterals() {
        tokens.add(builder.toString());
        builder.setLength(0);
    }

    private void processQuantifiers(char charValue) {
        // Adds quantifier to the last token
        tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1).concat(Character.toString(charValue)));
        builder.setLength(0);
    }

    private void processGroups(char charValue) {
        currentlyInGroup = true;
        if (charValue == ']' || currentlyEscaping) {
            tokens.add(builder.toString());
            builder.setLength(0);
            currentlyInGroup = false;
            currentlyEscaping = false;
        }
    }
}
