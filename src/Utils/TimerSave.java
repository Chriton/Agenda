/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.util.Timer;
import javax.swing.JTable;

/**
 *
 * @author dorumuntean
 */
public class TimerSave extends Timer {
    
    private final int intervalMinute;
    private final File file;
    //private final CarteDeTelefon carteDeTelefon;
    private JTable tabelAbonati;
    private final int delay = 0; 

    public TimerSave(JTable tabelAbonati, int intervalMinute, File file) {
        super("Timer Salvare");
        this.tabelAbonati = tabelAbonati;
        this.intervalMinute = intervalMinute;
        this.file = file;
    }
    
    public void porneste() {
       this.schedule(new TaskSave(tabelAbonati, file), delay, intervalMinute*15000); //todo restore to 60000 
    }
    
    public void opreste() {
       this.cancel(); 
    }
}
