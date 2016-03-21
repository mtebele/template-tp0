package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import ar.fiuba.tdd.template.tp0.models.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mtebele on 20/3/16.
 */
public class Validator {

    private static final String PATTERN_START = "^";
    private static final String PATTERN_END = "$";

    public Validator() {
    }

    public void validateRegEx(String regEx, int numberOfResults) {
        validateEmptyRegex(regEx);
        validateEmptyNumberOfResults(numberOfResults);
        validateEmptyGroup(regEx);
        validateOddBrackets(regEx);
        validateEndingSlash(regEx);
    }

    private void validateEmptyRegex(String regEx) {
        if (regEx == null || regEx.isEmpty()) {
            throw new InvalidRegexException("RegEx cannot be empty.");
        }
    }

    private void validateEmptyNumberOfResults(int numberOfResults) {
        if (numberOfResults < 1) {
            throw new InvalidRegexException("Number of results cannot be less than one.");
        }
    }

    private void validateEmptyGroup(String regEx) {
        String emptyGroupPattern = ".*\\[\\].*";
        Pattern pattern = Pattern.compile(PATTERN_START + emptyGroupPattern + PATTERN_END);
        Matcher matcher = pattern.matcher(regEx);
        if (matcher.find()) {
            throw new InvalidRegexException("A group cannot be empty.");
        }
    }

    private void validateOddBrackets(String regEx) {
        String openedBracketPattern = "\\[";
        String openedEscapedBracketPattern = "\\\\\\[";
        String closedBracketPattern = "\\]";
        String closedEscapedBracketPattern = "\\\\]";

        int openedCount = getBracketsCount(openedBracketPattern, openedEscapedBracketPattern, regEx);
        int closedCount = getBracketsCount(closedBracketPattern, closedEscapedBracketPattern, regEx);

        if (openedCount != closedCount) {
            throw new InvalidRegexException("Opened and closed brackets doesn't match.");
        }
    }

    private void validateEndingSlash(String regEx) {
        if (regEx.charAt(regEx.length() - 1) == '\\') {
            throw new InvalidRegexException("Unescaped character detected.");
        }
    }

    private int getBracketsCount(String firstPattern, String secondPattern, String regEx) {
        Pattern pattern = Pattern.compile(firstPattern);
        Matcher matcher = pattern.matcher(regEx);
        int totalCount = countMatches(matcher);

        pattern = Pattern.compile(secondPattern);
        matcher = pattern.matcher(regEx);
        int escapedCount = countMatches(matcher);

        return totalCount - escapedCount;
    }

    private int countMatches(Matcher matcher) {
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public void validateSpecialCharsInGroup(String token, List<Character> characters) {
        for (char specialChar : RegExUtils.getSpecialChars()) {
            if (characters.contains(specialChar)) {
                int index = token.indexOf(specialChar);
                if (index == 0 || token.charAt(index - 1) != '\\') {
                    throw new InvalidRegexException("Special characters in group must be escaped.");
                }
            }
        }
    }

//    public void validateNotReservedEscapedChars(char charValue) {
//        if (!RegExUtils.getSpecialChars().contains(charValue)) {
//            throw new InvalidRegexException("Not special character has been escaped.");
//        }
//    }

    public void validateQuantifierAtStart(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            throw new InvalidRegexException("RegEx cannot start with a quantifier.");
        }
    }

    public void validateConsecutiveQuantifiers(ArrayList<Token> tokens) {
        Token lastToken = tokens.get(tokens.size() - 1);
        if (lastToken.hasAlreadyQuantifier()) {
            throw new InvalidRegexException("Consecutive quantifiers are not allowed.");
        }
    }
}
