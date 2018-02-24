/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        this(label, 2000, 1000);
    }

    public TimerReclame(JLabel label, int delay, int interval) {
        this.label = label;
        this.delay = delay;
        this.interval = interval;
    }

    public void pornesteReclame() {
        this.schedule(new TaskReclame(label), delay, interval);
    }

    public void opresteReclame() {
        this.cancel();
    }
}
