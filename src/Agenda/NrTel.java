/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author dorumuntean
 */
public abstract class NrTel implements Serializable, Comparator {

private String numar;

//TODO - convert this to enum mobil/fix
protected String tip;


public NrTel(String numar) {
    this.numar = numar;
}

public String toString() {
    return numar;
}
 
public String getNumar() {
    return numar;
}

//TODO return enum 
public String getTip() {
    return tip;
}

public abstract boolean valideazaNumar(String numar);

}
