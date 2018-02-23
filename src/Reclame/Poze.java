/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclame;


import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author dorumuntean
 */
public class Poze {

    private static final String LOCATIE_POZE = "";

    File directorPoze = new File(LOCATIE_POZE);
    //File[] poze;
    private List<File> colectiePoze = new ArrayList<File>();

    public List<File> getColectiePoze() {
        return colectiePoze;
    }

    public void incarcaColectiePoze() {
        if (!directorPoze.exists() || !directorPoze.isDirectory() || !directorPoze.canRead() || !directorPoze.canExecute()) {
            //creaza o lista cu numere in loc de poze;
        } else {
            FileFilter filtruPNG = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".png");
                }
            };
            File[] fisiere = directorPoze.listFiles(filtruPNG);
            colectiePoze = Arrays.asList(fisiere);
        }
    }

    //this should be a collection of pictures or if not found, some texts..

//        File f = poze.get(pozaCurenta);
//        ImageIcon i = new ImageIcon(f.getAbsolutePath());
//        jLabelShareware.setIcon(i);
//        jLabelShareware.setText("");


}
