package Reclame;

import java.util.Timer;
import javax.swing.JLabel;

/**
 * @author dorumuntean
 */
public class TimerReclame extends Timer {

    private JLabel label;
    private int delay;
    private int interval;

    public TimerReclame(JLabel label) {
        this(label, 0, 3000);
    }

    public TimerReclame(JLabel label, int delay, int interval) {
        super("Timer Reclame");
        this.label = label;
        this.delay = delay;
        this.interval = interval;
    }

    public void porneste() {
        this.schedule(new TaskReclame(label), delay, interval);
    }

    public void opreste() {
        this.cancel();
    }
}