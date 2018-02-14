/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author dorumuntean
 */
public abstract class NrTel implements Serializable, Comparator {

    private String numar;
    private TipTelefon tipTelefon;

    public NrTel(String numar, TipTelefon tipTelefon) {
        this.numar = numar;
        this.tipTelefon = tipTelefon;
    }

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
//        if (!(o1 instanceof NrTel) && !(o2 instanceof NrTel)) {
//            throw new IllegalStateException("Something went wrong.");
//        }

        NrTel numarTelefon1 = (NrTel) o1;
        NrTel numarTelefon2 = (NrTel) o2;

        return numarTelefon1.getNumar().compareTo(numarTelefon2.getNumar());
    }

    //public abstract boolean valideazaNumar(String numar);
    public void valideazaNumar(String numar) {
        if (!numar.matches(tipTelefon.getRegex())) {
            throw new IllegalStateException(String.format("Numarul de telefon %s nu este valid", tipTelefon.name().toLowerCase()));
        }
    }
}
