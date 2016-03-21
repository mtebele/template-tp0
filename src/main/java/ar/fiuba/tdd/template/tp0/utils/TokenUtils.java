package ar.fiuba.tdd.template.tp0.utils;

import ar.fiuba.tdd.template.tp0.models.GroupType;
import ar.fiuba.tdd.template.tp0.models.QuantifierType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mtebele on 19/3/16.
 */
public class TokenUtils {

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

    /** Returns the string contained in the group. */
    public static String getStringFromGroup(String token) {
        return token.substring(
                token.indexOf(GroupType.BRACKET_OPEN.getValue()) + 1,
                token.lastIndexOf(GroupType.BRACKET_CLOSE.getValue()));
    }

    /** Returns a list of characters belonging to the string contained in the group. */
    public static List<Character> getListFromGroup(String token) {
        String extractedGroup = getStringFromGroup(token);
        return extractedGroup.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    /** Returns a list of special characters. */
    public static List<Character> getSpecialChars() {
        return SPECIAL_CHARS;
    }
}
