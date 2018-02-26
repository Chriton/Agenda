/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Agenda.CarteDeTelefon;
import java.io.File;
import java.util.Timer;

/**
 *
 * @author dorumuntean
 */
public class TimerSave extends Timer {
    
    private final int intervalMinute;
    private final File file;
    private final CarteDeTelefon carteDeTelefon;
    private final int delay = 0; 

    public TimerSave(CarteDeTelefon carteDeTelefon, int intervalMinute, File file) {
        this.carteDeTelefon = carteDeTelefon;
        this.intervalMinute = intervalMinute;
        this.file = file;
    }
    
    public void porneste() {
       this.schedule(new TaskSave(carteDeTelefon, file), delay, intervalMinute*15000); 
    }
    
    public void opreste() {
       this.cancel(); 
    }
}
