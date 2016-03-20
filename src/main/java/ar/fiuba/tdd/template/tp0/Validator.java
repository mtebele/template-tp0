package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mtebele on 20/3/16.
 */
public class Validator {

    private static final String PATTERN_START = "^";
    private static final String PATTERN_END = "$";
    private static final List<Character> SPECIAL_CHARS = Collections.unmodifiableList(Arrays.asList('.', '[', ']', '?',
            '*', '+'));

    public Validator() {

    }

    public void validateRegEx(String regEx) {
        validateEmptyGroup(regEx);
    }

    private void validateEmptyGroup(String regEx) {
        String emptyGroupPattern = ".*\\[\\].*";
        Pattern pattern = Pattern.compile(PATTERN_START + emptyGroupPattern + PATTERN_END);
        Matcher matcher = pattern.matcher(regEx);
        if (matcher.find()) {
            throw new InvalidRegexException("A group cannot be empty.");
        }
    }

    public void validateSpecialCharsInGroup(String token, List<Character> characters) {
        for (char specialChar : SPECIAL_CHARS) {
            if (characters.contains(specialChar)) {
                int index = token.indexOf(specialChar);
                if (index == 0 || token.charAt(index - 1) != '\\') {
                    throw new InvalidRegexException("Special characters in group must be escaped.");
                }
            }
        }
    }
}
