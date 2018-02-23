/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclame;

import java.util.TimerTask;
import javax.swing.JLabel;

/**
 * @author dorumuntean
 */
public class TaskReclame extends TimerTask {

    private JLabel label;
    private int temporar = 0;

    public TaskReclame(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        //cauta afiseazaPoza() si incarcaPoze()
        label.setText(Integer.toString(temporar));
        temporar++;
    }
}
