package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.models.GroupType;
import ar.fiuba.tdd.template.tp0.models.QuantifierType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mtebele on 19/3/16.
 */
public class RegExUtils {

    private static final List<Character> SPECIAL_CHARS = Collections.unmodifiableList(Arrays.asList('.', '[', ']', '?',
            '*', '+'));

    public static boolean isGroup(char token) {
        return Arrays.stream(GroupType.values()).anyMatch(e -> e.getValue() == token);
    }

    public static boolean isQuantifier(char token) {
        return Arrays.stream(QuantifierType.values()).anyMatch(e -> e.getValue() == token);
    }

    public static boolean isEscaped(char token) {
        return token == '\\';
    }

    public static boolean isDot(char token) {
        return token == '.';
    }

    public static List<Character> getListFromGroup(String token) {
        String extractedGroup = token.substring(
                token.indexOf(GroupType.BRACKET_OPEN.getValue()) + 1,
                token.indexOf(GroupType.BRACKET_CLOSE.getValue()));

        return extractedGroup.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    public static List<Character> getSpecialChars() {
        return SPECIAL_CHARS;
    }
}
