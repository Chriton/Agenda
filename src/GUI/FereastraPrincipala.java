/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Agenda.Abonat;
import Agenda.CarteDeTelefon;
import Agenda.Enums.Directie;
import Agenda.NrFix;
import Agenda.NrMobil;
import Agenda.NrTel;
import Agenda.Enums.SortareDupa;

import Agenda.Enums.TipTelefon;
import Reclame.TimerReclame;
import Utils.Register;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 * @author dorumuntean
 * daca vrem sa folosim clasa SplashScreen -> Issue with Netbeans not showing the popup:
 * In netbeans -> Open Project Properties -> Run -> VM and set the location of the image file like: -splash:src/splash/splash.png
 * https://stackoverflow.com/questions/23273267/changing-splashscreen-image-with-splashscreen-setimageurllink
 * http://wiki.netbeans.org/Splash_Screen_Beginner_Tutorial
 */
public class FereastraPrincipala extends javax.swing.JFrame {


    private CarteDeTelefon model = new CarteDeTelefon();
    private TimerReclame reclame;
    private Abonat abonatDeModificat;
    private TableRowSorter<TableModel> sorter;
    

    /**
     * Creates new form FereastraPrincipala
     */
    public FereastraPrincipala() {
        initComponents();

        //TODO check if the user is registered, if not start the commercials
        if (!Register.isRegistered()) {
            reclame = new TimerReclame(jLabelReclame);
            reclame.porneste();
            isRegistered(false);
        } else {
            isRegistered(true);
        }

        //TODO incarcare date automat din fisier default

        //setare model tabel cu abonati
        jTable1.setModel(model);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Combo box tip telefon
        DefaultComboBoxModel tipuriTelefon = new DefaultComboBoxModel(TipTelefon.lista());
        jComboBoxPhoneType1.setModel(tipuriTelefon);
        jComboBoxPhoneType.setModel(tipuriTelefon);

        //Combo box - sortare dupa
        DefaultComboBoxModel modelSortare = new DefaultComboBoxModel(SortareDupa.lista());
        jComboBoxSortare.setModel(modelSortare);

        //Combo box - directie sortare
        DefaultComboBoxModel modelDirectieSortare = new DefaultComboBoxModel(Directie.lista());
        jComboBoxDirectieSortare.setModel(modelDirectieSortare);

        //popup menu tabel
        jMenuItemSterge.setText("Sterge");
        jMenuItemModifica.setText("Modifica");
        jPopupMenu.add(jMenuItemSterge);
        jPopupMenu.add(jMenuItemModifica);
        jTable1.setComponentPopupMenu(jPopupMenu);
        
        //Filtru pentru FileChooser
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("*.agd", "agd"));
        
        //TODO - 5min save
        fiveMinSave();
        
        //Pentru sortare prin click pe cap tabel si cautare
        sorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(sorter);
        
            
        //event listener pentru functia de cautare
        jTextFieldCauta.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void changedUpdate(DocumentEvent e) {
          //warn();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
          warn();
        }
            @Override
            public void insertUpdate(DocumentEvent e) {
            warn();
        }

        public void warn() {
           //jTable1.clearSelection();

           String text = jTextFieldCauta.getText();
            if (text.trim().length() == 0) {
              sorter.setRowFilter(null);
            } else {
              sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        }
    });

    }

    public void isRegistered(boolean isRegistered) {
        if (isRegistered) {
            JMenuOpen.setEnabled(true);
            JMenuSave.setEnabled(true);
            jMenuInregistrare.setEnabled(false);
            jMenuInregistrare.setVisible(false);
            jSeparator1.setVisible(false);
            jLabelReclame.setEnabled(false);
            jLabelReclame.setVisible(false); 
        } else {
            JMenuOpen.setEnabled(false);
            JMenuSave.setEnabled(false);
            jMenuInregistrare.setEnabled(true);
            jMenuInregistrare.setVisible(true);
            jSeparator1.setVisible(true);
            jLabelReclame.setEnabled(true);
            jLabelReclame.setVisible(true);
        }
    }
    
    /**
     * Salveaza agenda la fiecare 5 min
     */
    public void fiveMinSave() {

        Timer timerS = new Timer();
        timerS.schedule(new TimerTask() {
            @Override
            public void run() {
                //int x = m.getRowCount();
                //if(x != 0){ //verificare daca agenda este goala
                //salvareInfoAuto(agenda);
                jLabelSalvare.setText("Datele au fost salvate");
                Timer timerS2 = new Timer();
                timerS2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        jLabelSalvare.setText("");
                    }
                }, 5000);
            }
//		};	
        }, 10000, 60000);
    }


    /**
     * Reseteaza campurile din fereastra Adauga abonat
     */
    public void resetAddFields() {
        jTextFirstName.setText("");
        jTextLastName.setText("");
        jTextCnp.setText("");
        jTextPhone.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialogIesire = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jButtonNu = new javax.swing.JButton();
        jButtonDa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabelConfirmare = new javax.swing.JLabel();
        jDialogAbout = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabelAgenda = new javax.swing.JLabel();
        jLabelNume = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        jDialogAdauga = new javax.swing.JDialog();
        jPanelForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextLastName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFirstName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextCnp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextPhone = new javax.swing.JTextField();
        jComboBoxPhoneType = new javax.swing.JComboBox<>();
        jPanelButoane = new javax.swing.JPanel();
        jButtonInchide = new javax.swing.JButton();
        jButtonAdaugaAbonat = new javax.swing.JButton();
        jDialogSterge = new javax.swing.JDialog();
        jButtonNuSterge = new javax.swing.JButton();
        jButtonStergeAbonat = new javax.swing.JButton();
        jLabelSterge = new javax.swing.JLabel();
        jFrameSplashScreen = new javax.swing.JFrame();
        jLabel6 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jDialogSortare = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxSortare = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBoxDirectieSortare = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jDialogInregistrare = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldRegisterCode = new javax.swing.JTextField();
        jButtonInregistreaza = new javax.swing.JButton();
        jButtonInapoi = new javax.swing.JButton();
        jDialogModifica = new javax.swing.JDialog();
        jPanelForm1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextLastName1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFirstName1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextCnp1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextPhone1 = new javax.swing.JTextField();
        jComboBoxPhoneType1 = new javax.swing.JComboBox<>();
        jPanelButoane1 = new javax.swing.JPanel();
        jButtonInchide1 = new javax.swing.JButton();
        jButtonModificaAbonat = new javax.swing.JButton();
        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuItemSterge = new javax.swing.JMenuItem();
        jMenuItemModifica = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAdauga = new javax.swing.JButton();
        jButtonSterge = new javax.swing.JButton();
        jButtonModifica = new javax.swing.JButton();
        jButtonSorteaza = new javax.swing.JButton();
        jButtonIesire = new javax.swing.JButton();
        jLabelReclame = new javax.swing.JLabel();
        jLabelCauta = new javax.swing.JLabel();
        jTextFieldCauta = new javax.swing.JTextField();
        jLabelSalvare = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        MeniuPrincipal = new javax.swing.JMenuBar();
        MeniuFile = new javax.swing.JMenu();
        JMenuOpen = new javax.swing.JMenuItem();
        JMenuSave = new javax.swing.JMenuItem();
        Separator = new javax.swing.JPopupMenu.Separator();
        JMenuIesire = new javax.swing.JMenuItem();
        MeniuAbonati = new javax.swing.JMenu();
        jMenuAdauga = new javax.swing.JMenuItem();
        jMenuSterge = new javax.swing.JMenuItem();
        jMenuModifica = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        MeniuHelp = new javax.swing.JMenu();
        jMenuInregistrare = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuAbout = new javax.swing.JMenuItem();

        jDialogIesire.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogIesire.setTitle("Confirmare");
        jDialogIesire.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogIesire.setModal(true);
        jDialogIesire.setResizable(false);
        jDialogIesire.setSize(new java.awt.Dimension(300, 200));
        jDialogIesire.getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonNu.setText("NU");
        jButtonNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNu);

        jButtonDa.setText("DA");
        jButtonDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDa);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogIesire.getContentPane().add(jPanel1, gridBagConstraints);

        jLabelConfirmare.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConfirmare.setText("Sunteti siguri ca doriti sa iesiti?");
        jPanel4.add(jLabelConfirmare);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        jDialogIesire.getContentPane().add(jPanel4, gridBagConstraints);

        jDialogAbout.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAbout.setTitle("About");
        jDialogAbout.setLocation(new java.awt.Point(0, 0));
        jDialogAbout.setMinimumSize(new java.awt.Dimension(300, 300));
        jDialogAbout.setModal(true);
        jDialogAbout.setResizable(false);
        jDialogAbout.setSize(new java.awt.Dimension(300, 300));
        jDialogAbout.getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.BorderLayout(0, 30));

        jLabelAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAgenda.setText("Agenda Telefonica");
        jPanel2.add(jLabelAgenda, java.awt.BorderLayout.CENTER);

        jLabelNume.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNume.setText("Muntean Doru");
        jPanel2.add(jLabelNume, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogAbout.getContentPane().add(jPanel2, gridBagConstraints);

        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 20, Short.MAX_VALUE)
                    .addComponent(jButtonOk)
                    .addGap(0, 21, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 35, Short.MAX_VALUE)
                    .addComponent(jButtonOk)
                    .addGap(0, 36, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogAbout.getContentPane().add(jPanel3, gridBagConstraints);

        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.setDialogTitle("");

        jDialogAdauga.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAdauga.setTitle("Adauga");
        jDialogAdauga.setMinimumSize(new java.awt.Dimension(400, 300));
        jDialogAdauga.setModal(true);
        jDialogAdauga.setResizable(false);
        jDialogAdauga.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialogAdaugaWindowClosing(evt);
            }
        });
        jDialogAdauga.getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelForm.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Nume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm.add(jLabel1, gridBagConstraints);

        jTextLastName.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm.add(jTextLastName, gridBagConstraints);

        jLabel2.setText("Prenume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm.add(jLabel2, gridBagConstraints);

        jTextFirstName.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm.add(jTextFirstName, gridBagConstraints);

        jLabel3.setText("CNP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm.add(jLabel3, gridBagConstraints);

        jTextCnp.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm.add(jTextCnp, gridBagConstraints);

        jLabel4.setText("Telefon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm.add(jLabel4, gridBagConstraints);

        jTextPhone.setToolTipText("");
        jTextPhone.setActionCommand("<Not Set>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm.add(jTextPhone, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm.add(jComboBoxPhoneType, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogAdauga.getContentPane().add(jPanelForm, gridBagConstraints);

        jButtonInchide.setText("Inchide");
        jButtonInchide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInchideActionPerformed(evt);
            }
        });
        jPanelButoane.add(jButtonInchide);

        jButtonAdaugaAbonat.setText("Adauga");
        jButtonAdaugaAbonat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdaugaAbonatActionPerformed(evt);
            }
        });
        jPanelButoane.add(jButtonAdaugaAbonat);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogAdauga.getContentPane().add(jPanelButoane, gridBagConstraints);

        jDialogSterge.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogSterge.setTitle("Confirmare");
        jDialogSterge.setModal(true);
        jDialogSterge.setResizable(false);
        jDialogSterge.setSize(new java.awt.Dimension(350, 200));

        jButtonNuSterge.setText("Nu");
        jButtonNuSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuStergeActionPerformed(evt);
            }
        });

        jButtonStergeAbonat.setText("Da");
        jButtonStergeAbonat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStergeAbonatActionPerformed(evt);
            }
        });

        jLabelSterge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSterge.setText("Sunteti sigur ca doriti sa stergeti abonatul?");

        javax.swing.GroupLayout jDialogStergeLayout = new javax.swing.GroupLayout(jDialogSterge.getContentPane());
        jDialogSterge.getContentPane().setLayout(jDialogStergeLayout);
        jDialogStergeLayout.setHorizontalGroup(
            jDialogStergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogStergeLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButtonNuSterge, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(jButtonStergeAbonat, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jDialogStergeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSterge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialogStergeLayout.setVerticalGroup(
            jDialogStergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogStergeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSterge, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialogStergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNuSterge)
                    .addComponent(jButtonStergeAbonat)))
        );

        jFrameSplashScreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrameSplashScreen.setMinimumSize(new java.awt.Dimension(300, 300));
        jFrameSplashScreen.setUndecorated(true);
        jFrameSplashScreen.setResizable(false);

        jLabel6.setText("Doru Muntean");

        javax.swing.GroupLayout jFrameSplashScreenLayout = new javax.swing.GroupLayout(jFrameSplashScreen.getContentPane());
        jFrameSplashScreen.getContentPane().setLayout(jFrameSplashScreenLayout);
        jFrameSplashScreenLayout.setHorizontalGroup(
            jFrameSplashScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameSplashScreenLayout.createSequentialGroup()
                .addGroup(jFrameSplashScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrameSplashScreenLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel6))
                    .addGroup(jFrameSplashScreenLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jFrameSplashScreenLayout.setVerticalGroup(
            jFrameSplashScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameSplashScreenLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        jDialogSortare.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogSortare.setTitle("Sortare");
        jDialogSortare.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogSortare.setModal(true);
        jDialogSortare.setResizable(false);
        jDialogSortare.setSize(new java.awt.Dimension(300, 200));

        jLabel7.setText("Sorteaza dupa: ");

        jButton1.setText("Inapoi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sorteaza");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setText("Directie");

        javax.swing.GroupLayout jDialogSortareLayout = new javax.swing.GroupLayout(jDialogSortare.getContentPane());
        jDialogSortare.getContentPane().setLayout(jDialogSortareLayout);
        jDialogSortareLayout.setHorizontalGroup(
            jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogSortareLayout.createSequentialGroup()
                .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogSortareLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jDialogSortareLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxSortare, 0, 131, Short.MAX_VALUE)
                            .addComponent(jComboBoxDirectieSortare, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        jDialogSortareLayout.setVerticalGroup(
            jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogSortareLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxSortare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxDirectieSortare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(jDialogSortareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(101, 101, 101))
        );

        jDialogInregistrare.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogInregistrare.setTitle("Inregistrare");
        jDialogInregistrare.setModal(true);
        jDialogInregistrare.setSize(new java.awt.Dimension(300, 200));

        jLabel8.setText("Introduceti codul de inregistrare: ");

        jButtonInregistreaza.setText("Inregistreaza");
        jButtonInregistreaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInregistreazaActionPerformed(evt);
            }
        });

        jButtonInapoi.setText("Inapoi");
        jButtonInapoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInapoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogInregistrareLayout = new javax.swing.GroupLayout(jDialogInregistrare.getContentPane());
        jDialogInregistrare.getContentPane().setLayout(jDialogInregistrareLayout);
        jDialogInregistrareLayout.setHorizontalGroup(
            jDialogInregistrareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogInregistrareLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jDialogInregistrareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogInregistrareLayout.createSequentialGroup()
                        .addComponent(jButtonInapoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(jButtonInregistreaza))
                    .addGroup(jDialogInregistrareLayout.createSequentialGroup()
                        .addGroup(jDialogInregistrareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldRegisterCode, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialogInregistrareLayout.setVerticalGroup(
            jDialogInregistrareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogInregistrareLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldRegisterCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jDialogInregistrareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInregistreaza)
                    .addComponent(jButtonInapoi))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jDialogModifica.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogModifica.setTitle("Modifica");
        jDialogModifica.setMinimumSize(new java.awt.Dimension(400, 300));
        jDialogModifica.setModal(true);
        jDialogModifica.setResizable(false);
        jDialogModifica.getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelForm1.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText("Nume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm1.add(jLabel9, gridBagConstraints);

        jTextLastName1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm1.add(jTextLastName1, gridBagConstraints);

        jLabel10.setText("Prenume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm1.add(jLabel10, gridBagConstraints);

        jTextFirstName1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm1.add(jTextFirstName1, gridBagConstraints);

        jLabel11.setText("CNP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm1.add(jLabel11, gridBagConstraints);

        jTextCnp1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        jPanelForm1.add(jTextCnp1, gridBagConstraints);

        jLabel12.setText("Telefon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm1.add(jLabel12, gridBagConstraints);

        jTextPhone1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm1.add(jTextPhone1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm1.add(jComboBoxPhoneType1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogModifica.getContentPane().add(jPanelForm1, gridBagConstraints);

        jButtonInchide1.setText("Inchide");
        jButtonInchide1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInchide1ActionPerformed(evt);
            }
        });
        jPanelButoane1.add(jButtonInchide1);

        jButtonModificaAbonat.setText("Modifica");
        jButtonModificaAbonat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificaAbonatActionPerformed(evt);
            }
        });
        jPanelButoane1.add(jButtonModificaAbonat);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogModifica.getContentPane().add(jPanelButoane1, gridBagConstraints);

        jMenuItemSterge.setText("jMenuItem1");
        jMenuItemSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStergeActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemSterge);

        jMenuItemModifica.setText("jMenuItem2");
        jMenuItemModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModificaActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItemModifica);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(jTable1);

        jButtonAdauga.setText("Adauga");
        jButtonAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdaugaActionPerformed(evt);
            }
        });

        jButtonSterge.setText("Sterge");
        jButtonSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStergeActionPerformed(evt);
            }
        });

        jButtonModifica.setText("Modifica");
        jButtonModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificaActionPerformed(evt);
            }
        });

        jButtonSorteaza.setText("Sorteaza");
        jButtonSorteaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSorteazaActionPerformed(evt);
            }
        });

        jButtonIesire.setText("Iesire");
        jButtonIesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIesireActionPerformed(evt);
            }
        });

        jLabelReclame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabelCauta.setText("Puteti cauta abonati dupa nume, prenume, cnp, telefon sau tip telefon.");

        jTextFieldCauta.setToolTipText("Cauta");

        jButton5.setText("X");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        MeniuPrincipal.setName(""); // NOI18N

        MeniuFile.setMnemonic('F');
        MeniuFile.setText("File");

        JMenuOpen.setMnemonic('O');
        JMenuOpen.setText("Open");
        JMenuOpen.setToolTipText("");
        JMenuOpen.setEnabled(false);
        JMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuOpenActionPerformed(evt);
            }
        });
        MeniuFile.add(JMenuOpen);

        JMenuSave.setMnemonic('S');
        JMenuSave.setText("Save");
        JMenuSave.setEnabled(false);
        JMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuSaveActionPerformed(evt);
            }
        });
        MeniuFile.add(JMenuSave);
        MeniuFile.add(Separator);

        JMenuIesire.setMnemonic('I');
        JMenuIesire.setText("Iesire");
        JMenuIesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuIesireActionPerformed(evt);
            }
        });
        MeniuFile.add(JMenuIesire);

        MeniuPrincipal.add(MeniuFile);

        MeniuAbonati.setMnemonic('A');
        MeniuAbonati.setText("Abonati");

        jMenuAdauga.setMnemonic('A');
        jMenuAdauga.setText("Adauga...");
        jMenuAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAdaugaActionPerformed(evt);
            }
        });
        MeniuAbonati.add(jMenuAdauga);

        jMenuSterge.setMnemonic('S');
        jMenuSterge.setText("Sterge...");
        jMenuSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuStergeActionPerformed(evt);
            }
        });
        MeniuAbonati.add(jMenuSterge);

        jMenuModifica.setMnemonic('M');
        jMenuModifica.setText("Modifica...");
        jMenuModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuModificaActionPerformed(evt);
            }
        });
        MeniuAbonati.add(jMenuModifica);

        jMenuItem1.setMnemonic('o');
        jMenuItem1.setText("Sorteaza");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MeniuAbonati.add(jMenuItem1);

        MeniuPrincipal.add(MeniuAbonati);

        MeniuHelp.setMnemonic('H');
        MeniuHelp.setText("Help");

        jMenuInregistrare.setMnemonic('I');
        jMenuInregistrare.setText("Inregistrare");
        jMenuInregistrare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuInregistrareActionPerformed(evt);
            }
        });
        MeniuHelp.add(jMenuInregistrare);
        MeniuHelp.add(jSeparator1);

        jMenuAbout.setMnemonic('A');
        jMenuAbout.setText("About");
        jMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutActionPerformed(evt);
            }
        });
        MeniuHelp.add(jMenuAbout);

        MeniuPrincipal.add(MeniuHelp);

        setJMenuBar(MeniuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelReclame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonModifica)
                                    .addComponent(jButtonAdauga)
                                    .addComponent(jButtonSterge)
                                    .addComponent(jButtonSorteaza)
                                    .addComponent(jButtonIesire))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelSalvare, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabelCauta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jTextFieldCauta))
                                .addGap(1, 1, 1)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAdauga, jButtonIesire, jButtonModifica, jButtonSorteaza, jButtonSterge});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAdauga)
                    .addComponent(jLabelCauta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSterge)
                    .addComponent(jTextFieldCauta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonModifica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSorteaza)
                        .addGap(113, 113, 113)
                        .addComponent(jLabelSalvare, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonIesire))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelReclame, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAdauga, jButtonIesire, jButtonModifica, jButtonSorteaza, jButtonSterge});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIesireActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogIesire.setVisible(true);
    }//GEN-LAST:event_jButtonIesireActionPerformed

    private void JMenuIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuIesireActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogIesire.setVisible(true);
    }//GEN-LAST:event_JMenuIesireActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        jDialogAbout.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutActionPerformed
        jDialogAbout.setLocationRelativeTo(null);
        jDialogAbout.setVisible(true);
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void JMenuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuOpenActionPerformed
        //TODO atentie ca se poate deschide fereastra doar apasand pe O - fix it
        try {
            if (jFileChooser1.showOpenDialog(this) == jFileChooser1.APPROVE_OPTION) {
                File fisierSelectat = jFileChooser1.getSelectedFile();

                model = model.loadFromFile(fisierSelectat);  //load(fisierSelectat);
                jTable1.setModel(model);
                sorter = new TableRowSorter<>(jTable1.getModel());
                jTable1.setRowSorter(sorter);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_JMenuOpenActionPerformed

    private void JMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuSaveActionPerformed
        if (jFileChooser1.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                
                File fisierSelectat = jFileChooser1.getSelectedFile();
                String fileName = fisierSelectat.getPath();
                if(!fileName.endsWith(".agd")){
                    fileName+=".agd";
                    fisierSelectat = new File(fileName);
                }

                model.saveToFile(fisierSelectat);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_JMenuSaveActionPerformed

    public void verificaTabel() {
        int selected = jTable1.getSelectedRow();

        if (jTable1.getRowCount() < 1) {
            throw new IllegalStateException("Lista nu contine abonati!");
        }

        if (selected == -1 || jTable1.getColumnCount() == 0) {
            throw new IllegalStateException("Selectati un abonat din tabel!");
        }
    }
    
    public void adauga() {
        resetAddFields();
        jDialogAdauga.setLocationRelativeTo(null);
        jDialogAdauga.setVisible(true); 
    }
    
    public void sterge() {
        try {
            verificaTabel();

            String cnp = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
            abonatDeModificat = model.getAbonatByCnp(cnp);            
            
            String nume = abonatDeModificat.getNume();
            String prenume = abonatDeModificat.getPrenume();
            
            String confirmare = String.format("Sunteti sigur ca doriti sa stergeti abonatul %s %s?", nume, prenume);
            jLabelSterge.setText(confirmare);
            jDialogSterge.setLocationRelativeTo(null);
            jDialogSterge.setVisible(true);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
        }

    }
        
    public void modifica() {
         try {
            verificaTabel();
            
            String cnp = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
            abonatDeModificat = model.getAbonatByCnp(cnp); 
            
            
            //abonatDeModificat = model.getElementAt(jTable1.getSelectedRow());
            jTextLastName1.setText(abonatDeModificat.getNume());
            jTextFirstName1.setText(abonatDeModificat.getPrenume());
            jTextCnp1.setText(abonatDeModificat.getCnp());
            jTextPhone1.setText(abonatDeModificat.getTelefon().toString());
            jComboBoxPhoneType1.setSelectedItem(abonatDeModificat.getTipTelefon().name());
        
            jDialogModifica.setLocationRelativeTo(null);
            jDialogModifica.setVisible(true);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void sorteaza() {
        if (jTable1.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Lista de abonati este goala. Nu aveti ce sorta!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            jDialogSortare.setLocationRelativeTo(null);
            jDialogSortare.setVisible(true);
        }
    }
    
    private void jMenuAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAdaugaActionPerformed
        adauga();
    }//GEN-LAST:event_jMenuAdaugaActionPerformed

    private void jButtonAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdaugaActionPerformed
        adauga();
    }//GEN-LAST:event_jButtonAdaugaActionPerformed

    private void jButtonInchideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInchideActionPerformed
        jDialogAdauga.dispose();
    }//GEN-LAST:event_jButtonInchideActionPerformed

    private void jDialogAdaugaWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogAdaugaWindowClosing
        resetAddFields();
    }//GEN-LAST:event_jDialogAdaugaWindowClosing

    private void jButtonNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuActionPerformed
        jDialogIesire.dispose();
    }//GEN-LAST:event_jButtonNuActionPerformed

    private void jButtonDaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDaActionPerformed
        //TODO - salveaza date
        System.exit(0);
    }//GEN-LAST:event_jButtonDaActionPerformed

    private void jButtonAdaugaAbonatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdaugaAbonatActionPerformed
        try {
            String nume = jTextLastName.getText();
            String prenume = jTextFirstName.getText();
            String cnp = jTextCnp.getText();
            String telefon = jTextPhone.getText();
            TipTelefon tipTelefon = TipTelefon.valueOf(jComboBoxPhoneType.getSelectedItem().toString());
            NrTel nrTel = (tipTelefon == TipTelefon.MOBIL) ? new NrMobil(telefon) : new NrFix(telefon);
                  
            //arunca IllegalArgumentException daca parametri nu sunt ok sau daca cnp-ul exista deja in lista  
            Abonat abonat = new Abonat(nume, prenume, cnp, nrTel, tipTelefon);
            model.adaugaAbonat(abonat);
            
            jDialogAdauga.dispose();
  
        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAdaugaAbonatActionPerformed

    private void jButtonNuStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuStergeActionPerformed
        jDialogSterge.dispose();
    }//GEN-LAST:event_jButtonNuStergeActionPerformed

    private void jButtonStergeAbonatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStergeAbonatActionPerformed
        //model.stergeAbonat(model.getElementAt(jTable1.getSelectedRow()));
        
        model.stergeAbonat(abonatDeModificat);
        
        jDialogSterge.dispose();
    }//GEN-LAST:event_jButtonStergeAbonatActionPerformed
    
    private void jButtonStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStergeActionPerformed
        sterge();
    }//GEN-LAST:event_jButtonStergeActionPerformed

    private void jMenuStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuStergeActionPerformed
        sterge();
    }//GEN-LAST:event_jMenuStergeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialogSortare.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonSorteazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSorteazaActionPerformed
        sorteaza();
    }//GEN-LAST:event_jButtonSorteazaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int coloana = SortareDupa.valueOf(jComboBoxSortare.getSelectedItem().toString()).getIndex();
        SortOrder directie = SortOrder.valueOf(jComboBoxDirectieSortare.getSelectedItem().toString());

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(coloana, directie));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        
        jDialogSortare.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonInapoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInapoiActionPerformed
        jDialogInregistrare.dispose();
    }//GEN-LAST:event_jButtonInapoiActionPerformed

    private void jMenuInregistrareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuInregistrareActionPerformed
        jDialogInregistrare.setLocationRelativeTo(null);
        jDialogInregistrare.setVisible(true);
    }//GEN-LAST:event_jMenuInregistrareActionPerformed

    private void jButtonInchide1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInchide1ActionPerformed
        jDialogModifica.setVisible(false);
    }//GEN-LAST:event_jButtonInchide1ActionPerformed

    private void jButtonModificaAbonatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificaAbonatActionPerformed
        try {
                
            String nume = jTextLastName1.getText();
            String prenume = jTextFirstName1.getText();
            String cnp = jTextCnp1.getText();
            String telefon = jTextPhone1.getText();
            TipTelefon tipTelefon = TipTelefon.valueOf(jComboBoxPhoneType1.getSelectedItem().toString());
            NrTel nrTel = (tipTelefon == TipTelefon.MOBIL) ? new NrMobil(telefon) : new NrFix(telefon);

            //facem o copie si verificam ca noul cnp sa nu fie prezent
            CarteDeTelefon copie = model.dublura();
            copie.stergeAbonat(abonatDeModificat);
            if (copie.isAbonatPresent(cnp)) {
                throw new IllegalArgumentException("Exista deja un abonat cu acest cnp!");
            }

            Abonat abonatDateNoi = new Abonat(nume, prenume, cnp, nrTel, tipTelefon);
            model.modificaAbonat(abonatDeModificat.getCnp(), abonatDateNoi);
            
            jDialogModifica.dispose();

        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButtonModificaAbonatActionPerformed
    
    private void jButtonModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificaActionPerformed
        modifica();
    }//GEN-LAST:event_jButtonModificaActionPerformed

    private void jMenuModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModificaActionPerformed
        modifica();
    }//GEN-LAST:event_jMenuModificaActionPerformed

    private void jButtonInregistreazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInregistreazaActionPerformed
        //Check if the code is correct then stop the commercials
        String registerCode = jTextFieldRegisterCode.getText();
        
        if (!registerCode.equals(Register.getRegistrationCode())) {
            JOptionPane.showMessageDialog(this, "Codul de inregistrare nu este corect!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Register.register();
            if (Register.isRegistered()) {
                reclame.opreste();
                isRegistered(true);
                jDialogInregistrare.dispose(); 
            }  
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }          
    }//GEN-LAST:event_jButtonInregistreazaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextFieldCauta.setText("");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItemStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStergeActionPerformed
        sterge();
    }//GEN-LAST:event_jMenuItemStergeActionPerformed

    private void jMenuItemModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificaActionPerformed
        modifica();
    }//GEN-LAST:event_jMenuItemModificaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        sorteaza();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
              
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FereastraPrincipala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FereastraPrincipala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FereastraPrincipala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FereastraPrincipala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    
        //Splash screen
        Splash mySplash = new Splash();
        mySplash.showSplash();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FereastraPrincipala().setVisible(true);  
            }
        });
    }
    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMenuIesire;
    private javax.swing.JMenuItem JMenuOpen;
    private javax.swing.JMenuItem JMenuSave;
    private javax.swing.JMenu MeniuAbonati;
    private javax.swing.JMenu MeniuFile;
    private javax.swing.JMenu MeniuHelp;
    private javax.swing.JMenuBar MeniuPrincipal;
    private javax.swing.JPopupMenu.Separator Separator;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAdauga;
    private javax.swing.JButton jButtonAdaugaAbonat;
    private javax.swing.JButton jButtonDa;
    private javax.swing.JButton jButtonIesire;
    private javax.swing.JButton jButtonInapoi;
    private javax.swing.JButton jButtonInchide;
    private javax.swing.JButton jButtonInchide1;
    private javax.swing.JButton jButtonInregistreaza;
    private javax.swing.JButton jButtonModifica;
    private javax.swing.JButton jButtonModificaAbonat;
    private javax.swing.JButton jButtonNu;
    private javax.swing.JButton jButtonNuSterge;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonSorteaza;
    private javax.swing.JButton jButtonSterge;
    private javax.swing.JButton jButtonStergeAbonat;
    private javax.swing.JComboBox<String> jComboBoxDirectieSortare;
    private javax.swing.JComboBox<String> jComboBoxPhoneType;
    private javax.swing.JComboBox<String> jComboBoxPhoneType1;
    private javax.swing.JComboBox<String> jComboBoxSortare;
    private javax.swing.JDialog jDialogAbout;
    private javax.swing.JDialog jDialogAdauga;
    private javax.swing.JDialog jDialogIesire;
    private javax.swing.JDialog jDialogInregistrare;
    private javax.swing.JDialog jDialogModifica;
    private javax.swing.JDialog jDialogSortare;
    private javax.swing.JDialog jDialogSterge;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrameSplashScreen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAgenda;
    private javax.swing.JLabel jLabelCauta;
    private javax.swing.JLabel jLabelConfirmare;
    private javax.swing.JLabel jLabelNume;
    private javax.swing.JLabel jLabelReclame;
    private javax.swing.JLabel jLabelSalvare;
    private javax.swing.JLabel jLabelSterge;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuItem jMenuAdauga;
    private javax.swing.JMenuItem jMenuInregistrare;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemModifica;
    private javax.swing.JMenuItem jMenuItemSterge;
    private javax.swing.JMenuItem jMenuModifica;
    private javax.swing.JMenuItem jMenuSterge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelButoane;
    private javax.swing.JPanel jPanelButoane1;
    private javax.swing.JPanel jPanelForm;
    private javax.swing.JPanel jPanelForm1;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextCnp;
    private javax.swing.JTextField jTextCnp1;
    private javax.swing.JTextField jTextFieldCauta;
    private javax.swing.JTextField jTextFieldRegisterCode;
    private javax.swing.JTextField jTextFirstName;
    private javax.swing.JTextField jTextFirstName1;
    private javax.swing.JTextField jTextLastName;
    private javax.swing.JTextField jTextLastName1;
    private javax.swing.JTextField jTextPhone;
    private javax.swing.JTextField jTextPhone1;
    // End of variables declaration//GEN-END:variables
}


