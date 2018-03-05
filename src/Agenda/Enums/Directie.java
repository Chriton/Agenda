package Agenda.Enums;

import java.util.stream.Stream;

/**
 * @author dorumuntean
 */
public enum Directie {
    ASCENDING(1),
    DESCENDING(2);

    private final int id;

    Directie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static String[] lista() {
        return Stream.of(Directie.values()).map(Directie::name).toArray(String[]::new);
    }
}