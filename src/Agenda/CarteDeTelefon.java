/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import Agenda.Enums.Directie;
import Agenda.Enums.SortareDupa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.table.AbstractTableModel;


/**
 * @author dorumuntean
 */
public class CarteDeTelefon extends AbstractTableModel implements Serializable {

    private ArrayList<Abonat> abonati = new ArrayList<>();

    public void adaugaAbonat(Abonat abonat) {
        if (isAbonatPresent(abonat.getCnp())) {
            throw new IllegalArgumentException("Exista deja un abonat cu acest cnp");
        }
        
        abonati.add(abonat);
        fireTableDataChanged();
    }

    public void stergeAbonat(Abonat abonat) {
        abonati.remove(abonat);
        fireTableDataChanged();
    }

    public void modificaAbonat(String cnp, Abonat abonat) {

        int indexAbonat = getAbonatIndexByCnp(cnp);
        
        Abonat modificaAbonat = abonati.get(indexAbonat);
        modificaAbonat.setNume(abonat.getNume());
        modificaAbonat.setPrenume(abonat.getPrenume());
        modificaAbonat.setTelefon(abonat.getTelefon());
        modificaAbonat.setTipTelefon(abonat.getTipTelefon());
        modificaAbonat.setCnp(abonat.getCnp());  
        
        fireTableDataChanged();
    }

//    public Abonat cautaDupaNume(String nume) {
//        //TODO ??
//        return null;
//    }

    public Abonat getAbonatByCnp(String cnp) {
        for (int i = 0; i < abonati.size(); i++) {
            if (abonati.get(i).getCnp().equals(cnp)) {
                return abonati.get(i);
            }
        }
        throw new IllegalArgumentException("Abonatul nu a fost gasit in lista!");
    }
    
    
    public int getAbonatIndexByCnp(String cnp) {
        for (int i = 0; i < abonati.size(); i++) {
            if (abonati.get(i).getCnp().equals(cnp)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Abonatul nu a fost gasit in lista!");
    }

    public boolean isAbonatPresent(String cnp) {
        for (int i = 0; i < abonati.size(); i++) {
            if (abonati.get(i).getCnp().equals(cnp)) {
                return true;
            }
        }
        return false;
    }
    
    public void sortare(SortareDupa sortareDupa, Directie directie) {

        switch (sortareDupa) {
            case NUME:
                sortBy(Abonat.dupaNume(), directie);
                break;
            case PRENUME:
                sortBy(Abonat.dupaPrenume(), directie);
                break;
            case CNP:
                sortBy(Abonat.dupaCnp(), directie);
                break;
            case TELEFON:
                sortBy(Abonat.dupaTelefon(), directie);
                break;
            case TIP:
                sortBy(Abonat.dupaTipTelefon(), directie);
                break;
            default:
                throw new IllegalArgumentException(String.format("Sortarea dupa %s nu a fost implementata momentan.", sortareDupa.name()));
        }
    }

    public void sortBy(Comparator c, Directie directie) {
        if (directie == Directie.ASCENDING) {
            sort(c);
        } else if (directie == Directie.DESCENDING) {
            sortRev(c);
        }
    }

    public void sort(Comparator c) {
        Collections.sort(abonati, c);
        fireTableDataChanged();
    }

    public void sortRev(Comparator c) {
        Collections.sort(abonati, Collections.reverseOrder(c));
        fireTableDataChanged();
    }

    @Override
    public String toString() {
        return "Agenda contine " + abonati.size() +  (abonati.size() == 1 ? " inregistrare." : " inregistrari.");
    }

    public Abonat getElementAt(int index) {
        return abonati.get(index);
    }

    @Override
    public int getRowCount() {
        return abonati.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Abonat abonat = abonati.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return abonat.getNume();
            case 1:
                return abonat.getPrenume();
            case 2:
                return abonat.getCnp();
            case 3:
                return abonat.getTelefon();
            case 4:
                return abonat.getTipTelefon();
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Nume";
            case 1:
                return "Prenume";
            case 2:
                return "CNP";
            case 3:
                return "Telefon";
            case 4:
                return "Tip";
            default:
                throw new IndexOutOfBoundsException();
        }
    }
    
    public CarteDeTelefon dublura() {
        CarteDeTelefon dublura = new CarteDeTelefon();
        for (int i = 0; i < abonati.size(); i++) {
            dublura.adaugaAbonat(abonati.get(i));
        }
        return dublura;
    }
          
    /**
     * Salveaza obiectul CarteDeTelefon in locatia indicata
     * @param fisier
     * @throws IOException 
     */
    public void saveToFile(File fisier) throws IOException {
        try {

            if (fisier.exists() && !fisier.canWrite()) {
                throw new IOException("Nu exista permisiuni de scriere in locatia aleasa!");
            }
            try (FileOutputStream fos = new FileOutputStream(fisier); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(this.dublura());
            }
        } catch (IOException se) {
            throw new IOException("Fisierul nu a putut fi salvat!");
        }
    }
    
    /**
     * Incarca si returneaza un obiect CarteDeTelefon din locatia indicata
     * @param fisier
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public CarteDeTelefon loadFromFile(File fisier) throws IOException, ClassNotFoundException {
        if (!fisier.exists() || !fisier.isFile() || !fisier.canRead()) {
            throw new IOException("Nu am putut sa citesc fisierul!");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fisier));) {
          
            CarteDeTelefon carte = (CarteDeTelefon) ois.readObject();
            return carte;

        } catch (IOException e) {
            throw new IOException("Nu am putut sa citesc fisierul");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Nu am putut sa citesc fisierul");
        }
    }
}