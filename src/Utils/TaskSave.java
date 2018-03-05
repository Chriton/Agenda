package Utils;

import Agenda.CarteDeTelefon;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author dorumuntean
 */
public class TaskSave extends TimerTask {
    
    CarteDeTelefon carteDeTelefon;
    JTable tabelAbonati;
    JLabel label;
    File file;
    
    public TaskSave(JTable tabelAbonati, JLabel label, File file) {
        this.tabelAbonati = tabelAbonati;
        this.label = label;
        this.file = file;
    }
    
    @Override
    public void run() {
        try {
            
            carteDeTelefon = (CarteDeTelefon)tabelAbonati.getModel();
            carteDeTelefon.saveToFile(file);
            Message.showMessage(label, "Datele au fost salvate.");
            
        } catch (IOException ex) {
            Message.showMessage(label, "Nu am putut salva datele.");
        }
    }
}
