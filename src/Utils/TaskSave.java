/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Agenda.CarteDeTelefon;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.swing.JTable;

/**
 *
 * @author dorumuntean
 */
public class TaskSave extends TimerTask {
    
    CarteDeTelefon carteDeTelefon;
    JTable tabelAbonati;
    File file;
    
    public TaskSave(JTable tabelAbonati, File file) {
        this.file = file;
        this.tabelAbonati = tabelAbonati;
    }
    
    @Override
    public void run() {
        try {
            
            //todo is instanceof check
            carteDeTelefon = (CarteDeTelefon)tabelAbonati.getModel();
            
            carteDeTelefon.saveToFile(file);
            System.out.println("Datele au fost salvate"); //TODO host to throw exception here? 
        } catch (IOException ex) {
            System.out.println("Nu am putut salva datele.");   
           // throw new IOException("Nu am putut salva datele");//fix
        }
    }
}
