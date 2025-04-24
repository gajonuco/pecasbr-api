package com.gabriel_nunez.oficina_mecanica.util;

public class PlacaUtils {

    private PlacaUtils() {

    }

    public static String formatarPlaca(String placa) {
        return placa == null ? null : placa.replaceAll("\\s+", "").toUpperCase();
    }
}