package ar.fiuba.tdd.template.tp0.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

}
