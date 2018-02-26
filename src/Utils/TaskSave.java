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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dorumuntean
 */
public class TaskSave extends TimerTask {
    
    CarteDeTelefon carteDeTelefon;
    File file;
    
    public TaskSave(CarteDeTelefon carteDeTelefon, File file) {
        this.carteDeTelefon = carteDeTelefon;
        this.file = file;
    }
    
    @Override
    public void run() {
        try {
            carteDeTelefon.saveToFile(file);
            System.out.println("Datele au fost salvate");
        } catch (IOException ex) {
            System.out.println("Nu am putut salva datele.");   
           // throw new IOException("Nu am putut salva datele");//fix
        }
    }
}
