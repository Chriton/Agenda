package Agenda;

import Agenda.Enums.TipTelefon;

/**
 * @author dorumuntean
 */
public class NrMobil extends NrTel {

    public NrMobil(String numar) {
        super(numar, TipTelefon.MOBIL);
    }
}