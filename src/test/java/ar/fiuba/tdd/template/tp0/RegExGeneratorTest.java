package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private static final int MAX_LENGTH = 10;
    private static final int NUMBER_RESULTS = 1000;

    private boolean validate(String regEx) {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate(regEx);
        return validateResults(results, regEx);
    }

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate(regEx, numberOfResults);
        return validateResults(results, regEx);
    }

    private boolean validateResults(List<String> results, String regEx) {
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", NUMBER_RESULTS));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", NUMBER_RESULTS));
    }

    @Test
    public void testDotWithQuantifiers() {
        assertTrue(validate("..?.*.+.", NUMBER_RESULTS));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\[\\.J\\]\\@z\\+\\?\\*M", NUMBER_RESULTS));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", NUMBER_RESULTS));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", NUMBER_RESULTS));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", NUMBER_RESULTS));
    }

    @Test
    public void testStatementExample() {
        assertTrue(validate("..+[ab]*d?c", NUMBER_RESULTS));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnUnescapedChar() {
        assertTrue(validate("abc\\", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnUnescapedCharOnly() {
        assertTrue(validate("\\", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnEmptyRegex() {
        assertTrue(validate("", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnNullRegex() {
        assertTrue(validate(null, NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnZeroNumber() {
        assertTrue(validate("[abc]+", 0));
    }

    @Test
    public void testGenerateWithoutNumberOfResults() {
        assertTrue(validate("[abc]+"));
    }

    @Test
    public void testComplexRegex2() {
        assertTrue(validate("\\*N?z+.?\\-*[442]", NUMBER_RESULTS));
    }

    @Test
    public void testComplexRegex3() {
        assertTrue(validate("x\\+L*.*\\}?[hu\\]\\[9]", NUMBER_RESULTS));
    }

    @Test
    public void testComplexRegex4() {
        assertTrue(validate("\\?\\**[G\\+\\?\\*\\\\\\]\\[,]", NUMBER_RESULTS));
    }
}
