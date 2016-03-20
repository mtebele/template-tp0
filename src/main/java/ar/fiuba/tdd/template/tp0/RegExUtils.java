package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.GroupType;
import ar.fiuba.tdd.template.tp0.models.QuantifierType;

import java.util.Arrays;

/**
 * Created by mtebele on 19/3/16.
 */
public class RegExUtils {

    public static boolean isGroup(char token) {
        return Arrays.stream(GroupType.values()).anyMatch(e -> e.name().equals(token));
    }

    public static boolean isQuantifier(char token) {
        return Arrays.stream(QuantifierType.values()).anyMatch(e -> e.name().equals(token));
    }

    public static boolean isEscaped(char token) {
        return token == '\\';
    }

    public static boolean isDot(char token) {
        return token == '.';
    }
}
