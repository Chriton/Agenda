package Agenda;

import Agenda.Enums.TipTelefon;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author dorumuntean
 */
public abstract class NrTel implements Serializable, Comparator {

    private final String numar;
    private final TipTelefon tipTelefon;

    public NrTel(String numar, TipTelefon tipTelefon) {
        this.numar = numar;
        this.tipTelefon = tipTelefon;
        valideazaNumar(numar);
    }

    @Override
    public String toString() {
        return numar;
    }

    public String getNumar() {
        return numar;
    }

    public TipTelefon getTip() {
        return tipTelefon;
    }

    @Override
    public int compare(Object o1, Object o2) {
        NrTel numarTelefon1 = (NrTel) o1;
        NrTel numarTelefon2 = (NrTel) o2;

        return numarTelefon1.getNumar().compareTo(numarTelefon2.getNumar());
    }

    private void valideazaNumar(String numar) {
        if (!numar.matches(tipTelefon.getRegex())) {
            throw new IllegalArgumentException(String.format("Numarul de telefon %s nu este valid", tipTelefon.name().toLowerCase()));
        }
    }
}