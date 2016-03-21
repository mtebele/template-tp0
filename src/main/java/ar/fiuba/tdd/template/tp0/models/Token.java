package ar.fiuba.tdd.template.tp0.models;

/**
 * Created by mtebele on 20/3/16.
 */
public class Token {

    private String value;
    private TokenType tokenType;
    private Quantifier quantifier;

    public Token(String value, TokenType tokenType) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public boolean hasAlreadyQuantifier() {
        return quantifier != null;
    }

    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public int getQuantity(int maxLength) {
        if (quantifier == null) {
            return 1;
        }
        return quantifier.getQuantity(maxLength);
    }
}
