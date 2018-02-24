/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda.Enums;

import java.util.stream.Stream;

/**
 * @author dorumuntean
 */
public enum SortareDupa {
    NUME(0),
    PRENUME(1),
    CNP(2),
    TELEFON(3),
    TIP(4);

    private int index;

    SortareDupa(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static String[] lista() {
        return Stream.of(SortareDupa.values()).map(SortareDupa::name).toArray(String[]::new);
    }
}






