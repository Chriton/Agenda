/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclame;

import java.io.File;
import java.util.List;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 * @author dorumuntean
 */
public class TaskReclame extends TimerTask {

    private JLabel label;
    private int temporar = 1;
    //private Poze poze;
    private List<File> colectiePoze;

    public TaskReclame(JLabel label) {
        this.label = label;
        Poze poze = new Poze();
        poze.incarcaColectiePoze();
        colectiePoze = poze.getColectiePoze();
    }

    @Override
    public void run() {
        //cauta afiseazaPoza() si incarcaPoze()
        label.setText(Integer.toString(temporar));

        //label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reclame/Poze/reclama" + temporar + ".png")));
        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/me/myimageapp/newpackage/image.png"))); // NOI18N

        //System.out.println(colectiePoze.size());
        temporar++;
    }
}
