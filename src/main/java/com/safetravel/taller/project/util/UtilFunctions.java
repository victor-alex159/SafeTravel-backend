package com.safetravel.taller.project.util;

import java.util.Objects;

public class UtilFunctions {

    public static boolean esNulo(Integer value) {
        return (value == null || value.intValue() < 1);
    }

    public static boolean esVacio(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean esNulo(Object obj) {
        return Objects.isNull(obj) || obj == null;
    }

    public static boolean noEsNulo(Object obj) {
        return !UtilFunctions.esNulo(obj);
    }

}
