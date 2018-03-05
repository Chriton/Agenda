package Utils;

import java.io.File;
import java.util.Timer;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author dorumuntean
 */
public class TimerSave extends Timer {
    
    private final int intervalMinute;
    private final File file;
    private final JTable tabelAbonati;
    private final JLabel label;
    private final int delay = 10000; 

    public TimerSave(JTable tabelAbonati, JLabel label, int intervalMinute, File file) {
        super("Timer Salvare");
        this.tabelAbonati = tabelAbonati;
        this.label = label;
        this.intervalMinute = intervalMinute;
        this.file = file;
    }
    
    /**
     * This will start a periodic save after 10 seconds
     */
    public void porneste() {
       this.schedule(new TaskSave(tabelAbonati, label, file), delay, intervalMinute*15000); //todo restore to 60000 
    }
    
    /**
     * Stop the periodic save
     */
    public void opreste() {
       this.cancel(); 
    }
}
