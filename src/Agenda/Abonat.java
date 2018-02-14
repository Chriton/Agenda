/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

/**
 * @author dorumuntean
 */
public class Abonat {

    private NrTel telefon;
    private String nume;
    private String prenume;
    private String cnp;

    //org.apache.commons.lang.StringUtils
    //StringUtils.capitalize(Str);


    public Abonat(NrTel telefon, String nume, String prenume, String cnp) {

        valideazaNumeSauPrenume(nume, Apelativ.NUME);
        valideazaNumeSauPrenume(prenume, Apelativ.PRENUME);

        this.telefon = telefon;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
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
            throw new IllegalArgumentException(String.format("Campul %s trebuie sa contina doar litere"));
        }
    }
}
