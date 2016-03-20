package ar.fiuba.tdd.template.tp0.models;

/**
 * Created by mtebele on 20/3/16.
 */
public enum QuantifierType {
    ZERO_OR_ONE('?'), ZERO_TO_MANY('*'), ONE_TO_MANY('+');

    private final char value;

    QuantifierType(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}