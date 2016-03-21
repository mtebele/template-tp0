package ar.fiuba.tdd.template.tp0.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mtebele on 21/3/16.
 */
public class StringUtils {

    private static final int MIN_ASCII_CODE = 32;
    private static final int MAX_ASCII_CODE = 127;
    private static final List<Integer> FORBIDDEN_ASCII_CODES = new ArrayList<Integer>() {
        {
            add(10);
            add(13);
            add(127);
            add(129);
            add(141);
            add(143);
            add(144);
            add(157);
        }
    };

    public static int getValidRandomAsciiCode() {
        Random random = new Random();
        int ascii = (MIN_ASCII_CODE + random.nextInt(MAX_ASCII_CODE - MIN_ASCII_CODE));
        while (FORBIDDEN_ASCII_CODES.contains(ascii)) {
            ascii = (MIN_ASCII_CODE + random.nextInt(MAX_ASCII_CODE - MIN_ASCII_CODE));
        }
        return ascii;
    }

    public static int getMatchesCount(String strPattern, String regEx) {
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(regEx);
        return countMatches(matcher);
    }

    private static int countMatches(Matcher matcher) {
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
