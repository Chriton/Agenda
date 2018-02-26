/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclame;

//import java.io.File;

import java.util.List;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author dorumuntean
 */
public class TaskReclame extends TimerTask {

    private JLabel label;
    private int index = 0;
    private List<ImageIcon> colectiePoze;

    public TaskReclame(JLabel label) {
        this.label = label;
        //label.setSize(1024, 768); //TODO ?
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