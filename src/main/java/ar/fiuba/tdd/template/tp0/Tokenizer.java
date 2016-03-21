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
    private Validator validator;
    private boolean currentlyInGroup = false;
    private boolean currentlyEscaping = false;

    public Tokenizer(Validator validator) {
        this.tokens = new ArrayList<>();
        this.builder = new StringBuilder();
        this.validator = validator;
    }

    public ArrayList<Token> tokenize(String regEx) {
        for (int i = 0; i < regEx.length(); i++) {
            char charValue = regEx.charAt(i);
            builder.append(charValue);
            processToken(charValue);
        }
        return tokens;
    }

    private void processToken(char charValue) {
        if (currentlyEscaping) {
//            validator.validateNotReservedEscapedChars(charValue);
            processLiterals();
            currentlyEscaping = false;
        } else if (RegExUtils.isGroup(charValue) || currentlyInGroup) {
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

    private void processEscaped() {
        currentlyEscaping = true;
        builder.setLength(0);
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
        validator.validateQuantifierAtStart(tokens);
        validator.validateConsecutiveQuantifiers(tokens);

        // Adds quantifier to the last token
        Token lastToken = tokens.get(tokens.size() - 1);
        lastToken.setQuantifier(new Quantifier(charValue));
        builder.setLength(0);
    }

    private void processGroups(char charValue) {
        currentlyInGroup = true;
        if (charValue == ']') {
            tokens.add(new Token(builder.toString(), TokenType.GROUP));
            builder.setLength(0);
            currentlyInGroup = false;
        }
    }
}
