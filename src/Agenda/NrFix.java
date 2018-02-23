/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;


        import java.io.Serializable;

/**
 * @author dorumuntean
 */
//TODO check if needed to implement Serializable because the parent already does it
public class NrFix extends NrTel implements Serializable {

    public NrFix(String numar) {
        super(numar, TipTelefon.FIX);
        valideazaNumar(numar);
    }
}