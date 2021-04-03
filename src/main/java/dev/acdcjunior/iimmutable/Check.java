package dev.acdcjunior.iimmutable;


public class Check {

    public static void notNull(Object o, String npeMsg) {
        if (o == null) {
            throw new NullPointerException(npeMsg);
        }
    }

}
