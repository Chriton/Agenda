package Reclame;

import java.util.List;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * @author dorumuntean
 */
public class TaskReclame extends TimerTask {

    private final JLabel label;
    private int index = 0;
    private final List<ImageIcon> colectiePoze;

    public TaskReclame(JLabel label) {
        this.label = label;
        Poze poze = new Poze();
        colectiePoze = poze.getPoze();
    }

    @Override
    public void run() {
        if (index >= colectiePoze.size()) {
            index = 0;
        }
        label.setIcon(colectiePoze.get(index));
        index++;
    }
}