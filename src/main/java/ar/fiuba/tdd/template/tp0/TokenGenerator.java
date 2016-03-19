package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

/**
 * Created by mtebele on 19/3/16.
 */
public class TokenGenerator {

    static ArrayList<String> tokens = new ArrayList<>();
    static StringBuilder builder = new StringBuilder();
    static boolean currentlyInGroup = false;
    static boolean currentlyEscaping = false;

    public static ArrayList<String> getTokens(String regEx) {
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

    private static void processLiterals() {
        tokens.add(builder.toString());
        builder.setLength(0);
    }

    private static void processQuantifiers(char charValue) {
        tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1).concat(Character.toString(charValue)));
        builder.setLength(0);
    }

    private static void processGroups(char charValue) {
        currentlyInGroup = true;
        if (charValue == ']' || currentlyEscaping) {
            tokens.add(builder.toString());
            builder.setLength(0);
            currentlyInGroup = false;
            currentlyEscaping = false;
        }
    }
}
