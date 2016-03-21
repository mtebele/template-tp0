package ar.fiuba.tdd.template.tp0.helpers;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import ar.fiuba.tdd.template.tp0.models.Token;
import ar.fiuba.tdd.template.tp0.utils.StringUtils;
import ar.fiuba.tdd.template.tp0.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtebele on 20/3/16.
 */
public class Validator {

    public Validator() {
    }

    public void validateRegEx(String regEx, int numberOfResults) {
        validateEmptyRegex(regEx);
        validateEmptyNumberOfResults(numberOfResults);
        validateOddBrackets(regEx);
        validateEmptyGroup(regEx);
        validateEndingSlash(regEx);
    }

    /** Validates that the regEx is not empty. */
    private void validateEmptyRegex(String regEx) {
        if (regEx == null || regEx.isEmpty()) {
            throw new InvalidRegexException("RegEx cannot be empty.");
        }
    }

    /** Validates that the number of results is greater than zero. */
    private void validateEmptyNumberOfResults(int numberOfResults) {
        if (numberOfResults < 1) {
            throw new InvalidRegexException("Number of results cannot be less than one.");
        }
    }

    /** Validates that the regEx has equal number of opened and closed brackets (unescaped). */
    private void validateOddBrackets(String regEx) {
        String openedBracketPattern = "\\[";
        String openedEscapedBracketPattern = "\\\\\\[";
        String closedBracketPattern = "\\]";
        String closedEscapedBracketPattern = "\\\\]";

        int openedCount = StringUtils.getMatchesCount(openedBracketPattern, regEx)
                - StringUtils.getMatchesCount(openedEscapedBracketPattern, regEx);
        int closedCount = StringUtils.getMatchesCount(closedBracketPattern, regEx)
                - StringUtils.getMatchesCount(closedEscapedBracketPattern, regEx);

        if (openedCount != closedCount) {
            throw new InvalidRegexException("Opened and closed brackets doesn't match.");
        }
    }

    /** Validates that the regEx has content inside a group. */
    private void validateEmptyGroup(String regEx) {
        int openIndex = regEx.indexOf('[');
        int closeIndex = regEx.indexOf(']');
        while (openIndex >= 0 && closeIndex >= 0) {
            if (closeIndex - openIndex == 1) {
                throw new InvalidRegexException("A group cannot be empty.");
            }
            openIndex = regEx.indexOf('[', openIndex + 1);
            closeIndex = regEx.indexOf(']', closeIndex + 1);
        }
    }

    /** Validates that the regEx doesn't end with a slash character. */
    private void validateEndingSlash(String regEx) {
        if (regEx.charAt(regEx.length() - 1) == '\\') {
            throw new InvalidRegexException("Unescaped character detected.");
        }
    }

    /** Validates that the regEx has (if any) valid character inside a group. */
    public void validateSpecialCharsInGroup(Token token) {
        String tokenValue = TokenUtils.getStringFromGroup(token.getValue());
        List<Character> characters = TokenUtils.getListFromGroup(token.getValue());

        for (char specialChar : TokenUtils.getSpecialChars()) {
            if (characters.contains(specialChar)) {
                int index = tokenValue.indexOf(specialChar);
                if (index == 0 || tokenValue.charAt(index - 1) != '\\') {
                    throw new InvalidRegexException("Special characters in group must be escaped.");
                }
            }
        }
    }

    /** Validates that the regEx doesn't start with a quantifier. */
    public void validateQuantifierAtStart(ArrayList<Token> tokens) {
        if (tokens.isEmpty()) {
            throw new InvalidRegexException("RegEx cannot start with a quantifier.");
        }
    }

    /** Validates that the regEx doesn't have two or more consecutive quantifiers (unescaped). */
    public void validateConsecutiveQuantifiers(ArrayList<Token> tokens) {
        Token lastToken = tokens.get(tokens.size() - 1);
        if (lastToken.hasAlreadyQuantifier()) {
            throw new InvalidRegexException("Consecutive quantifiers are not allowed.");
        }
    }
}
