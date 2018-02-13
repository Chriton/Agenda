/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.Serializable;

/**
 *
 * @author dorumuntean
 */
//TODO check if needed to implement Serializable because the parent already does it
public class NrFix extends NrTel implements Serializable {

    public NrFix(String numar) {
        super(numar);
        tip = "fix";
    }

    @Override
    public boolean valideazaNumar(String numar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public int compare(Object o1, Object o2) {
        //TODO add is instanceof NrTel check here then do the comparation
        
        NrTel numarTelefon1 = (NrTel)o1;
        NrTel numarTelefon2 = (NrTel)o2;
        
        return numarTelefon1.getNumar().compareTo(numarTelefon2.getNumar());
    }   
}
