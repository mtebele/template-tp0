package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exceptions.InvalidRegexException;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

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

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup() {
        assertTrue(validate("[+]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup2() {
        assertTrue(validate("[*]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup3() {
        assertTrue(validate("[.]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup4() {
        assertTrue(validate("[?]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup5() {
        assertTrue(validate("[[]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup6() {
        assertTrue(validate("[]]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup7() {
        assertTrue(validate("[+abc]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup8() {
        assertTrue(validate("[abc*]", NUMBER_RESULTS));
    }

    @Test
    public void testEscapedQuantifiersInGroup() {
        assertTrue(validate("[\\*]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup10() {
        assertTrue(validate("[abc\\+.abc]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnQuantifiersInGroup11() {
        assertTrue(validate("[abc\\?qw*e\\+]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnOddBrackets() {
        assertTrue(validate("[", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnOddBrackets2() {
        assertTrue(validate("]", NUMBER_RESULTS));
    }

    @Test(expected = InvalidRegexException.class)
    public void testShouldFailOnOddBrackets3() {
        assertTrue(validate("abc[def]]", NUMBER_RESULTS));
    }

    @Test
    public void testOddEscapedBrackets() {
        assertTrue(validate("abc[def]\\]", NUMBER_RESULTS));
    }
}
