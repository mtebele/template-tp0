package ar.fiuba.tdd.template.tp0.exceptions;

/**
 * Created by mtebele on 20/3/16.
 */
public class InvalidRegexException extends RuntimeException {
    public InvalidRegexException(String message) {
        super(message);
    }
}
