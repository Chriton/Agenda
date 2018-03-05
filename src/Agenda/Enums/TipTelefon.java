package Agenda.Enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum TipTelefon implements Serializable {
    FIX("^0([2-3]){1}([0-9]){8}"),
    MOBIL("^0(7){1}([0-9]){8}");

    private final String regex;

    TipTelefon(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static String[] lista() {
        return Stream.of(TipTelefon.values()).map(TipTelefon::name).toArray(String[]::new);
    }
}