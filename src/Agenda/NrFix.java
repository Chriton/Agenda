package Agenda;

import Agenda.Enums.TipTelefon;

/**
 * @author dorumuntean
 */
public class NrFix extends NrTel {

    public NrFix(String numar) {
        super(numar, TipTelefon.FIX);
    }
}