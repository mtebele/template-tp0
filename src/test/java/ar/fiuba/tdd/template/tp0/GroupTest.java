package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by mtebele on 20/3/16.
 */
public class GroupTest {

    private static final int MAX_LENGTH = 10;
    private static final int NUMBER_RESULTS = 100;

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(MAX_LENGTH);
        List<String> results = generator.generate(regEx, numberOfResults);
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
    public void testCharsInGroup() {
        assertTrue(validate("[abc]", NUMBER_RESULTS));
    }

    @Test
    public void testEscapedInGroup() {
        assertTrue(validate("[\\[]", NUMBER_RESULTS));
    }

    @Test
    public void testEscapedOpenBracket() {
        assertTrue(validate("\\[", NUMBER_RESULTS));
    }

    @Test
    public void testEscapedQuantifiersInGroup() {
        assertTrue(validate("[\\*]", NUMBER_RESULTS));
    }

    @Test
    public void testOddEscapedBrackets() {
        assertTrue(validate("abc[def]\\]", NUMBER_RESULTS));
        assertTrue(validate("[\\?\\*\\+\\\\\\.\\[\\]]*", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnEscapedClosedBracket() {
        assertTrue(validate("[\\]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnEmptyGroup() {
        assertTrue(validate("[]", NUMBER_RESULTS));
    }

    @Test
    public void testShouldFailOnSpecialCharsInGroup() {
        assertFailsWithException("[[]");
        assertFailsWithException("[]]");
    }

    @Test
    public void testShouldFailOnQuantifiersInGroup() {
        assertFailsWithException("[.]");
        assertFailsWithException("[+]");
        assertFailsWithException("[*]");
        assertFailsWithException("[?]");
        assertFailsWithException("[+abc]");
        assertFailsWithException("[abc*]");
        assertFailsWithException("[RTY+]");
        assertFailsWithException("[0K5?]");
        assertFailsWithException("[mN4.]");
        assertFailsWithException("[abc\\+.abc]");
        assertFailsWithException("[abc\\?qw*e\\+]");
    }

    @Test
    public void testShouldFailOnOddBrackets() {
        assertFailsWithException("[");
        assertFailsWithException("]");
        assertFailsWithException("abc[def]]");
        assertFailsWithException("[abc");
        assertFailsWithException("abc]");
        assertFailsWithException("[abc\\]");
        assertFailsWithException("\\[123]");
        assertFailsWithException("[ABC.\\]");
        assertFailsWithException("[mjT?\\]*");
    }

    private void assertFailsWithException(String regEx) {
        try {
            validate(regEx, NUMBER_RESULTS);
            fail("Should not pass " + regEx);
        } catch (InvalidRegexException ex) {
            assertTrue(true);
        }
    }
}
