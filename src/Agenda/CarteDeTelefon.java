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

    public Abonat cautaDupaNume(String nume) {
        for (int i = 0; i < abonati.size(); i++) {
            if (abonati.get(i).getNume().equalsIgnoreCase(nume)) {
                return abonati.get(i);
            }
        }
        throw new IllegalArgumentException("Abonatul nu a fost gasit in lista!");
    }

    public Abonat cautaDupaPrenume(String prenume) {
        for (int i = 0; i < abonati.size(); i++) {
            if (abonati.get(i).getPrenume().equalsIgnoreCase(prenume)) {
                return abonati.get(i);
            }
        }
        throw new IllegalArgumentException("Abonatul nu a fost gasit in lista!");
    }

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

    /**
     * Checks if the user is present.
     * @param cnp - the CNP of the user
     * @return true if the user is present, false otherwise
     */
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

    /**
     * Sort a ArrayList<Abonat> by the provided comparator and direction
     * @param c - the Comparator<Abonat> to use
     * @param directie - direction of the sorting. Can be ASCENDING or DESCENDING
     */
    public void sortBy(Comparator<Abonat> c, Directie directie) {
        if (directie == Directie.ASCENDING) {
            sort(c);
        } else if (directie == Directie.DESCENDING) {
            sortRev(c);
        }
    }

    /**
     * Sort ascending a a ArrayList<Abonat> list
     * @param c - the Comparator<Abonat> to use
     */
    public void sort(Comparator<Abonat> c) {
        Collections.sort(abonati, c);
        //abonati.sort(c);
        fireTableDataChanged();
    }

    /**
     * Sort descending a a ArrayList<Abonat> list
     * @param c - the Comparator<Abonat> to use
     */
    public void sortRev(Comparator<Abonat> c) {
        Collections.sort(abonati, Collections.reverseOrder(c));
        //abonati.sort(Collections.reverseOrder(c));
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

    /**
     * Creates and returns a copy of a CarteDeTelefon object
     * @return a copy of the CarteDeTelefon object
     */
    public CarteDeTelefon dublura() {
        CarteDeTelefon dublura = new CarteDeTelefon();
        for (int i = 0; i < abonati.size(); i++) {
            dublura.adaugaAbonat(abonati.get(i));
        }
        return dublura;
    }
          
    /**
     * Saves a CarteDeTelefon object in the provided location
     * @param file - the File to save the data to
     * @throws IOException
     * @throws IllegalStateException
     */
    public void saveToFile(File file) throws IOException, IllegalStateException {
        try {

            if (file.exists() && !file.canWrite()) {
                throw new IllegalStateException("Nu exista permisiuni de scriere in locatia aleasa.");
            }
            
            try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(this.dublura());
            }
        } catch (IOException se) {
            throw new IOException("Fisierul nu a putut fi salvat.");
        }
    }
    
    /**
     * Loads and returns a CarteDeTelefon object from the provided location
     * @param file - the File to load the data from
     * @return CarteDeTelefon object
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalStateException
     */
    public CarteDeTelefon loadFromFile(File file) throws IOException, ClassNotFoundException, IllegalStateException {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalStateException("Fisierul default cu abonati nu exista.");
        }
        
        if (!file.canRead()) {
            throw new IllegalStateException("Fisierul cu abonati exista, insa nu poate fi citit.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {
           
            CarteDeTelefon carte = (CarteDeTelefon) ois.readObject();
            return carte;  

        } catch (IOException e) {
            throw new IOException("Nu am putut sa citesc fisierul.");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Nu am putut sa citesc fisierul.");
        }
    }
}