package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.Quantifier;
import ar.fiuba.tdd.template.tp0.models.Token;
import ar.fiuba.tdd.template.tp0.models.TokenType;

import java.util.ArrayList;

/**
 * Created by mtebele on 19/3/16.
 */
public class Tokenizer {

    private ArrayList<Token> tokens;
    private StringBuilder builder;
    private boolean currentlyInGroup = false;
    private boolean currentlyEscaping = false;

    public Tokenizer() {
        tokens = new ArrayList<>();
        builder = new StringBuilder();
    }

    public ArrayList<Token> tokenize(String regEx) {
        for (int i = 0; i < regEx.length(); i++) {
            char charValue = regEx.charAt(i);
            builder.append(charValue);

            if (RegExUtils.isGroup(charValue) || currentlyInGroup || currentlyEscaping) {
                processGroups(charValue);
            } else if (RegExUtils.isQuantifier(charValue)) {
                processQuantifiers(charValue);
            } else if (RegExUtils.isEscaped(charValue)) {
                processEscaped();
            } else if (RegExUtils.isDot(charValue)) {
                processAnyChar();
            } else {
                processLiterals();
            }
        }
        return tokens;
    }

    private void processEscaped() {
        currentlyEscaping = true;
    }

    private void processAnyChar() {
        tokens.add(new Token(builder.toString(), TokenType.DOT));
        builder.setLength(0);
    }

    private void processLiterals() {
        tokens.add(new Token(builder.toString(), TokenType.LITERAL));
        builder.setLength(0);
    }

    private void processQuantifiers(char charValue) {
        // Adds quantifier to the last token
        Token lastToken = tokens.get(tokens.size() - 1);
        lastToken.setQuantifier(new Quantifier(charValue));
        builder.setLength(0);
    }

    private void processGroups(char charValue) {
        currentlyInGroup = true;
        if (charValue == ']' || currentlyEscaping) {
            tokens.add(new Token(builder.toString(), TokenType.GROUP));
            builder.setLength(0);
            currentlyInGroup = false;
            currentlyEscaping = false;
        }
    }
}
