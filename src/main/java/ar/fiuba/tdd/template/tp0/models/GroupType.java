package ar.fiuba.tdd.template.tp0.models;

/**
 * Created by mtebele on 20/3/16.
 */
public enum GroupType {
    BRACKET_OPEN('['), BRACKET_CLOSE(']');
    private final char value;

    GroupType(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
