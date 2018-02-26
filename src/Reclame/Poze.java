/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclame;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;


/**
 * @author dorumuntean
 */
public class Poze {

    private List<ImageIcon> colectiePoze = new ArrayList<>();
    private static String LOCATIE_POZE_INTERNE = "/Poze/reclama%s.png";
    private static String LOCATIE_POZE_EXTERNE = String.format("src%spoze", File.separator); 

    
    public Poze() {
        //LOCATIE_POZE_EXTERNE = String.format(LOCATIE_POZE_EXTERNE, File.separator);
        colectiePoze = existaPozeExterne() ? incarcaPozeExterne() : incarcaPozeInterne();
    }

    public List<ImageIcon> getPoze() {
        return colectiePoze;
    }

    public boolean existaFolderPozeExterne() {
        File folderPoze = new File(LOCATIE_POZE_EXTERNE);
        return !(!folderPoze.exists() || !folderPoze.isDirectory() || !folderPoze.canRead() || !folderPoze.canExecute());
    }
    
    public boolean existaPozeExterne() {
            
        if (!existaFolderPozeExterne()) {
            return false;
        }
            
        File folderPoze = new File(LOCATIE_POZE_EXTERNE);
        File[] fisiere = folderPoze.listFiles(filtruPoze(".png"));   
        return fisiere.length != 0;
    }
    
    public List<ImageIcon> incarcaPozeInterne() {   
        
       List<ImageIcon> poze = new ArrayList<>();
       poze.add(new ImageIcon(getClass().getResource(String.format(LOCATIE_POZE_INTERNE, "1"))));
       poze.add(new ImageIcon(getClass().getResource(String.format(LOCATIE_POZE_INTERNE, "2"))));
       poze.add(new ImageIcon(getClass().getResource(String.format(LOCATIE_POZE_INTERNE, "3"))));

       return poze;
    }
    
    public List<ImageIcon> incarcaPozeExterne() {
 
            if (!existaPozeExterne()) {
                //daca apare o eroare intoarcem colectia cu pozele interne
                return incarcaPozeInterne();
            } 
            
            File folderPoze = new File(LOCATIE_POZE_EXTERNE);
            FileFilter filtruPng =  filtruPoze(".png");
            File[] fisiere = folderPoze.listFiles(filtruPng);

            List<ImageIcon> colectieIcon = new ArrayList<>();
                  
            for (File fisier : fisiere) {
                colectieIcon.add(new ImageIcon(fisier.getAbsolutePath()));
            }
                  
            return colectieIcon;
        }
    
    public FileFilter filtruPoze(String extensie) {
        return new FileFilter(){
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(extensie);
                }  
            }; 
    }
}