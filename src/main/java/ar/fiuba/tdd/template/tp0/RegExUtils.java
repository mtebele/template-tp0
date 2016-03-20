package ar.fiuba.tdd.template.tp0;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by mtebele on 19/3/16.
 */
public class RegExUtils {

    private static final List QUANTIFIERS = Collections.unmodifiableList(Arrays.asList(Quantifiers.ZERO_TO_ONE.getValue(),
            Quantifiers.ZERO_TO_MANY.getValue(), Quantifiers.ONE_TO_MANY.getValue()));
    private static final List GROUPS = Collections.unmodifiableList(Arrays.asList('[', ']'));

    public static boolean isGroup(char token) {
        return GROUPS.contains(token);
    }

    public static boolean isQuantifier(char token) {
        return QUANTIFIERS.contains(token);
    }

    public static boolean isEscaped(char token) {
        return token == '\\';
    }

    public enum Quantifiers {
        ZERO_TO_ONE('?'), ZERO_TO_MANY('*'), ONE_TO_MANY('+');

        private final char value;

        Quantifiers(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }
}
