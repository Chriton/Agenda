/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;


import Agenda.Enums.TipTelefon;

import java.io.Serializable;

/**
 * @author dorumuntean
 */
public class NrMobil extends NrTel implements Serializable {

    public NrMobil(String numar) {
        super(numar, TipTelefon.MOBIL);
        valideazaNumar(numar); //TODO fix overridable method in constructor message - set private?
    }
}