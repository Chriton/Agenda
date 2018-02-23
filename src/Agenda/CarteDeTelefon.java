/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

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
        abonati.add(abonat);
        fireTableDataChanged();
    }

    public void stergeAbonat(Abonat abonat) {
        abonati.remove(abonat);
        fireTableDataChanged();
    }

    public void modificaAbonat(Abonat abonat) {
        //TODO
    }

    public Abonat cautaAbonat(String nume) {
        //TODO
        return null;
    }

    public Abonat cautaAbonatDupaCNP(String CNP) {
        //TODO
        return null;
    }

    //TODO - salvare si incarcare date hdd

    //ordonare abonati dupa oricare din criteriile posibile

    public void sortare(int x, char directie) {
        if (directie == 'a') {
            switch (x) {
                case 1:
                    sort(Abonat.dupaNume());
                    break;
                case 2:
                    sort(Abonat.dupaPrenume());
                    break;
                case 3:
                    sort(Abonat.dupaCnp());
                    break;
                case 4:
                    sort(Abonat.dupaTelefon());
                    break;
                default:
                    throw new IllegalArgumentException("Doar valori intre 1-4 pentru sortare");
            }
        } else if (directie == 'd') {
            switch (x) {
                case 1:
                    sortRev(Abonat.dupaNume());
                    break;
                case 2:
                    sortRev(Abonat.dupaPrenume());
                    break;
                case 3:
                    sortRev(Abonat.dupaCnp());
                    break;
                case 4:
                    sortRev(Abonat.dupaTelefon());
                    break;
                default:
                    throw new IllegalArgumentException("Doar valori intre 1-4 pentru sortare");
            }
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
        return "Agenda contine: " + abonati.size() + " inregistrari.";
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
        return 4;
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
            default:
                return null;
        }
    }
}
