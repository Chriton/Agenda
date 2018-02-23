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
public class Abonat implements Serializable /*, Comparator */ {

    private NrTel telefon;
    private String nume;
    private String prenume;
    private String cnp;

    //org.apache.commons.lang.StringUtils
    //StringUtils.capitalize(Str);


    public Abonat(String nume, String prenume, String cnp, NrTel telefon) {

        valideazaNumeSauPrenume(nume, Apelativ.NUME);
        valideazaNumeSauPrenume(prenume, Apelativ.PRENUME);

        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return String.format("Nume: %s Prenume: %s CNP: %s Telefon: %s", nume, prenume, cnp, telefon);
    }

    public NrTel getTelefon() {
        return telefon;
    }

    public void setTelefon(NrTel telefon) {
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void valideazaNumeSauPrenume(String numeSauPrenume, Apelativ apelativ) {
        if (numeSauPrenume == null || numeSauPrenume.length() < 3) {
            throw new IllegalArgumentException(String.format("Campul %s trebuie sa contina minim 3 litere", apelativ.name().toLowerCase()));
        }

        if (numeSauPrenume.length() > 30) {
            throw new IllegalArgumentException(String.format("Campul %s trebuie sa contina maxim 30 litere", apelativ.name().toLowerCase()));
        }

        if (numeSauPrenume.matches("([0-9])")) {
            throw new IllegalArgumentException(String.format("Campul %s trebuie sa contina doar litere", apelativ.name().toLowerCase()));
        }
    }

//    @Override
//    public int compare(Object o1, Object o2) {
//        //TODO
//    }


    public static Comparator<Abonat> dupaNume() {

        //return (Abonat a1, Abonat a2) -> a1.getNume().compareToIgnoreCase(a2.getNume());

        return new Comparator<Abonat>() {
            @Override
            public int compare(Abonat a1, Abonat a2) {
                return a1.getNume().compareToIgnoreCase(a2.getNume());
            }
        };
    }


    public static Comparator<Abonat> dupaPrenume() {

        return new Comparator<Abonat>() {
            @Override
            public int compare(Abonat a1, Abonat a2) {
                return a1.getPrenume().compareToIgnoreCase(a2.getPrenume());
            }
        };
    }

    public static Comparator<Abonat> dupaCnp() {

        return new Comparator<Abonat>() {
            @Override
            public int compare(Abonat a1, Abonat a2) {
                return a1.getCnp().compareToIgnoreCase(a2.getCnp());
            }
        };
    }

    public static Comparator<Abonat> dupaTelefon() {

        return new Comparator<Abonat>() {
            @Override
            public int compare(Abonat a1, Abonat a2) {
                return a1.getTelefon().toString().compareToIgnoreCase(a2.getTelefon().toString());
            }
        };
    }

}
