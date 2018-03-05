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
import Utils.Message;
import Utils.Register;
import Utils.TimerSave;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
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
    File fisierAbonati = new File(String.format("src%sagenda.agd", File.separator));
    

    /**
     * Creates new form FereastraPrincipala
     */
    public FereastraPrincipala() {
                
        initComponents();

        //Verifica daca userul este inregistrat, daca nu este atunci pornim reclamele
        if (!Register.isRegistered()) {
            reclame = new TimerReclame(labelReclame);
            reclame.porneste();
            showRegisteredElements(false);
        } else {
            showRegisteredElements(true);
        }
        
        //daca fisierul cu abonati exista si poate fi citit, incarca datele din el
        try  {
            model = model.loadFromFile(fisierAbonati);
            Message.showMessage(labelInfo, "Datele au fost incarcate.");
        } catch (IOException | ClassNotFoundException | IllegalStateException e) {
            Message.showMessage(labelInfo, e.getMessage());
        }

        
        //setare model tabel cu abonati
        jTable.setModel(model);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Pentru sortare prin click pe cap tabel si cautare
        sorter = new TableRowSorter<>(jTable.getModel());
        jTable.setRowSorter(sorter);
        
        //popup menu tabel
        jMenuItemSterge.setText("Sterge");
        jMenuItemModifica.setText("Modifica");
        jPopupMenu.add(jMenuItemSterge);
        jPopupMenu.add(jMenuItemModifica);
        jTable.setComponentPopupMenu(jPopupMenu);
        
 
        //Combo box tip telefon
        DefaultComboBoxModel<String> tipuriTelefon = new DefaultComboBoxModel<>(TipTelefon.lista());
        jComboBoxPhoneType1.setModel(tipuriTelefon);
        jComboBoxPhoneType.setModel(tipuriTelefon);

        //Combo box - sortare dupa
        DefaultComboBoxModel<String> modelSortare = new DefaultComboBoxModel<>(SortareDupa.lista());
        jComboBoxSortare.setModel(modelSortare);

        //Combo box - directie sortare
        ComboBoxModel<String> modelDirectieSortare = new DefaultComboBoxModel<>(Directie.lista());
        jComboBoxDirectieSortare.setModel(modelDirectieSortare);
        
        //Filtru pentru FileChooser si CurrentDirectory
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("*.agd", "agd"));
        File workingDirectory = new File(System.getProperty("user.dir"));
        jFileChooser1.setCurrentDirectory(workingDirectory);
        
        //Salvare periodica a datelor - metoda 1 mai complexa
        TimerSave timerSave = new TimerSave(jTable, labelInfo, 1, fisierAbonati);
        timerSave.porneste();
        
        //Salvare periodica a datelor -  metoda 2 - mai simppla
        //salvarePeriodica(20000, 10000, labelInfo);
        
        //event listener pentru functia de cautare
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
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
           String text = tfSearch.getText();
            if (text.trim().length() == 0) {
              sorter.setRowFilter(null);
              labelSearch.setText("Puteti cauta abonati dupa nume, prenume, cnp, telefon sau tip telefon.");
            } else {
              sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
              labelSearch.setText("Puteti cauta abonati dupa nume, prenume, cnp, telefon sau tip telefon. Filtrul de cautare este activ.");
            }
        }
    });

    }


    /**
     * Salveaza agenda la fiecare X min - metoda mai simpla
     */
    private void salvarePeriodica(int SaveInterval, int delay, JLabel label) {
                
        Timer save = new Timer("Timer Salvare");
        save.schedule(new TimerTask() {
            @Override
            public void run() {
                salveazaAgenda();
            }	
        }, delay, SaveInterval);
    }

    
    private void showRegisteredElements(boolean isRegistered) {
        if (isRegistered) {
            JMenuOpen.setEnabled(true);
            JMenuSave.setEnabled(true);
            jMenuInregistrare.setEnabled(false);
            jMenuInregistrare.setVisible(false);
            jSeparator1.setVisible(false);
            labelReclame.setEnabled(false);
            labelReclame.setVisible(false); 
        } else {
            JMenuOpen.setEnabled(false);
            JMenuSave.setEnabled(false);
            jMenuInregistrare.setEnabled(true);
            jMenuInregistrare.setVisible(true);
            jSeparator1.setVisible(true);
            labelReclame.setEnabled(true);
            labelReclame.setVisible(true);
        }
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
        jPanelButoaneIesire = new javax.swing.JPanel();
        jButtonNu = new javax.swing.JButton();
        jButtonDa = new javax.swing.JButton();
        jPanelTextIesire = new javax.swing.JPanel();
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
        jPanelStergeButoane = new javax.swing.JPanel();
        jButtonNuSterge = new javax.swing.JButton();
        jButtonStergeAbonat = new javax.swing.JButton();
        jPanelStergeForm = new javax.swing.JPanel();
        jLabelSterge = new javax.swing.JLabel();
        jDialogSortare = new javax.swing.JDialog();
        jPanelSortareButoane = new javax.swing.JPanel();
        jButtonSorteazaInapoi = new javax.swing.JButton();
        jButtonSorteazaSorteaza = new javax.swing.JButton();
        jPanelSortareForm = new javax.swing.JPanel();
        jComboBoxDirectieSortare = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxSortare = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jDialogInregistrare = new javax.swing.JDialog();
        jPanelButoaneInregistrare = new javax.swing.JPanel();
        jButtonInapoi = new javax.swing.JButton();
        jButtonInregistreaza = new javax.swing.JButton();
        jPanelFormInregistrare = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldRegisterCode = new javax.swing.JTextField();
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
        Search = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        bClearSearch = new javax.swing.JButton();
        Left = new javax.swing.JPanel();
        bAdauga = new javax.swing.JButton();
        bSterge = new javax.swing.JButton();
        bModifica = new javax.swing.JButton();
        bSorteaza = new javax.swing.JButton();
        bIesire = new javax.swing.JButton();
        MainTable = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        labelReclame = new javax.swing.JLabel();
        Right = new javax.swing.JPanel();
        InfoBar = new javax.swing.JPanel();
        labelInfo = new javax.swing.JLabel();
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
        jDialogIesire.setBounds(new java.awt.Rectangle(0, 0, 300, 200));
        jDialogIesire.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogIesire.setModal(true);
        jDialogIesire.setResizable(false);
        jDialogIesire.setSize(new java.awt.Dimension(300, 200));
        jDialogIesire.getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonNu.setText("Nu");
        jButtonNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuActionPerformed(evt);
            }
        });
        jPanelButoaneIesire.add(jButtonNu);

        jButtonDa.setText("Da");
        jButtonDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDaActionPerformed(evt);
            }
        });
        jPanelButoaneIesire.add(jButtonDa);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jDialogIesire.getContentPane().add(jPanelButoaneIesire, gridBagConstraints);

        jLabelConfirmare.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConfirmare.setText("Sunteti siguri ca doriti sa iesiti?");
        jPanelTextIesire.add(jLabelConfirmare);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        jDialogIesire.getContentPane().add(jPanelTextIesire, gridBagConstraints);

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
        jFileChooser1.setApproveButtonToolTipText("");
        jFileChooser1.setCurrentDirectory(new java.io.File("/null"));
        jFileChooser1.setDialogTitle("");

        jDialogAdauga.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAdauga.setTitle("Adauga");
        jDialogAdauga.setBounds(new java.awt.Rectangle(0, 0, 400, 250));
        jDialogAdauga.setMinimumSize(new java.awt.Dimension(400, 250));
        jDialogAdauga.setModal(true);
        jDialogAdauga.setResizable(false);
        jDialogAdauga.setSize(new java.awt.Dimension(400, 250));
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

        jTextLastName.setToolTipText("Nume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextFirstName.setToolTipText("Prenume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextCnp.setToolTipText("CNP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextPhone.setToolTipText("Telefon");
        jTextPhone.setActionCommand("<Not Set>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm.add(jTextPhone, gridBagConstraints);

        jComboBoxPhoneType.setToolTipText("Tip Telefon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jDialogAdauga.getContentPane().add(jPanelButoane, gridBagConstraints);

        jDialogSterge.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogSterge.setTitle("Confirmare");
        jDialogSterge.setBounds(new java.awt.Rectangle(0, 0, 300, 200));
        jDialogSterge.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogSterge.setModal(true);
        jDialogSterge.setResizable(false);
        jDialogSterge.setSize(new java.awt.Dimension(300, 200));
        jDialogSterge.getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonNuSterge.setText("Nu");
        jButtonNuSterge.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNuSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuStergeActionPerformed(evt);
            }
        });
        jPanelStergeButoane.add(jButtonNuSterge);

        jButtonStergeAbonat.setText("Da");
        jButtonStergeAbonat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonStergeAbonat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStergeAbonatActionPerformed(evt);
            }
        });
        jPanelStergeButoane.add(jButtonStergeAbonat);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogSterge.getContentPane().add(jPanelStergeButoane, gridBagConstraints);

        jLabelSterge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSterge.setText("Sunteti sigur ca doriti sa stergeti abonatul?");
        jPanelStergeForm.add(jLabelSterge);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogSterge.getContentPane().add(jPanelStergeForm, gridBagConstraints);

        jDialogSortare.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogSortare.setTitle("Sortare");
        jDialogSortare.setBounds(new java.awt.Rectangle(0, 0, 300, 200));
        jDialogSortare.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogSortare.setModal(true);
        jDialogSortare.setResizable(false);
        jDialogSortare.setSize(new java.awt.Dimension(300, 200));
        jDialogSortare.getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonSorteazaInapoi.setText("Inapoi");
        jButtonSorteazaInapoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSorteazaInapoiActionPerformed(evt);
            }
        });
        jPanelSortareButoane.add(jButtonSorteazaInapoi);

        jButtonSorteazaSorteaza.setText("Sorteaza");
        jButtonSorteazaSorteaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSorteazaSorteazaActionPerformed(evt);
            }
        });
        jPanelSortareButoane.add(jButtonSorteazaSorteaza);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogSortare.getContentPane().add(jPanelSortareButoane, gridBagConstraints);

        jPanelSortareForm.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelSortareForm.add(jComboBoxDirectieSortare, gridBagConstraints);

        jLabel13.setText("Directie");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelSortareForm.add(jLabel13, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelSortareForm.add(jComboBoxSortare, gridBagConstraints);

        jLabel7.setText("Sorteaza dupa: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelSortareForm.add(jLabel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogSortare.getContentPane().add(jPanelSortareForm, gridBagConstraints);

        jDialogInregistrare.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogInregistrare.setTitle("Inregistrare");
        jDialogInregistrare.setBounds(new java.awt.Rectangle(0, 0, 300, 200));
        jDialogInregistrare.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialogInregistrare.setModal(true);
        jDialogInregistrare.setSize(new java.awt.Dimension(300, 200));
        jDialogInregistrare.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialogInregistrareWindowClosing(evt);
            }
        });
        jDialogInregistrare.getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonInapoi.setText("Inapoi");
        jButtonInapoi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonInapoi.setPreferredSize(new java.awt.Dimension(130, 29));
        jButtonInapoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInapoiActionPerformed(evt);
            }
        });
        jPanelButoaneInregistrare.add(jButtonInapoi);

        jButtonInregistreaza.setText("Inregistreaza");
        jButtonInregistreaza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonInregistreaza.setPreferredSize(new java.awt.Dimension(130, 29));
        jButtonInregistreaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInregistreazaActionPerformed(evt);
            }
        });
        jPanelButoaneInregistrare.add(jButtonInregistreaza);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jDialogInregistrare.getContentPane().add(jPanelButoaneInregistrare, gridBagConstraints);

        jPanelFormInregistrare.setLayout(new java.awt.GridLayout(2, 0));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Introduceti codul de inregistrare: ");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelFormInregistrare.add(jLabel8);

        jTextFieldRegisterCode.setPreferredSize(new java.awt.Dimension(260, 26));
        jPanelFormInregistrare.add(jTextFieldRegisterCode);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jDialogInregistrare.getContentPane().add(jPanelFormInregistrare, gridBagConstraints);

        jDialogModifica.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogModifica.setTitle("Modifica");
        jDialogModifica.setBounds(new java.awt.Rectangle(0, 0, 400, 250));
        jDialogModifica.setMinimumSize(new java.awt.Dimension(400, 250));
        jDialogModifica.setModal(true);
        jDialogModifica.setResizable(false);
        jDialogModifica.setSize(new java.awt.Dimension(400, 250));
        jDialogModifica.getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelForm1.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText("Nume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelForm1.add(jLabel9, gridBagConstraints);

        jTextLastName1.setToolTipText("Nume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextFirstName1.setToolTipText("Prenume");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextCnp1.setToolTipText("CNP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jTextPhone1.setToolTipText("Telefon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm1.add(jTextPhone1, gridBagConstraints);

        jComboBoxPhoneType1.setToolTipText("Tip Telefon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanelForm1.add(jComboBoxPhoneType1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialogModifica.getContentPane().add(jPanelForm1, gridBagConstraints);

        jButtonInchide1.setText("Inapoi");
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
        setTitle("Agenda");
        setBounds(new java.awt.Rectangle(0, 0, 800, 650));
        setMinimumSize(new java.awt.Dimension(800, 650));
        setName("MainWindow"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 650));
        setSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Search.setMinimumSize(new java.awt.Dimension(800, 70));
        Search.setPreferredSize(new java.awt.Dimension(800, 70));
        Search.setSize(new java.awt.Dimension(800, 70));

        labelSearch.setText("Puteti cauta abonati dupa nume, prenume, cnp, telefon sau tip telefon.");

        tfSearch.setToolTipText("Cauta");

        bClearSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/clear.png"))); // NOI18N
        bClearSearch.setToolTipText("Clear");
        bClearSearch.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bClearSearch.setMaximumSize(new java.awt.Dimension(30, 30));
        bClearSearch.setMinimumSize(new java.awt.Dimension(30, 30));
        bClearSearch.setPreferredSize(new java.awt.Dimension(30, 30));
        bClearSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SearchLayout = new javax.swing.GroupLayout(Search);
        Search.setLayout(SearchLayout);
        SearchLayout.setHorizontalGroup(
            SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSearch)
                    .addGroup(SearchLayout.createSequentialGroup()
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        SearchLayout.setVerticalGroup(
            SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bClearSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfSearch))
                .addContainerGap())
        );

        getContentPane().add(Search, java.awt.BorderLayout.PAGE_START);

        Left.setMinimumSize(new java.awt.Dimension(60, 500));
        Left.setPreferredSize(new java.awt.Dimension(60, 500));

        bAdauga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/add_user.png"))); // NOI18N
        bAdauga.setToolTipText("Adauga user");
        bAdauga.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bAdauga.setMaximumSize(new java.awt.Dimension(40, 40));
        bAdauga.setMinimumSize(new java.awt.Dimension(40, 40));
        bAdauga.setPreferredSize(new java.awt.Dimension(40, 40));
        bAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaActionPerformed(evt);
            }
        });
        Left.add(bAdauga);

        bSterge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/remove_user.png"))); // NOI18N
        bSterge.setToolTipText("Sterge user");
        bSterge.setMaximumSize(new java.awt.Dimension(40, 40));
        bSterge.setMinimumSize(new java.awt.Dimension(40, 40));
        bSterge.setPreferredSize(new java.awt.Dimension(40, 40));
        bSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStergeActionPerformed(evt);
            }
        });
        Left.add(bSterge);

        bModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/edit.png"))); // NOI18N
        bModifica.setToolTipText("Modifica user");
        bModifica.setMaximumSize(new java.awt.Dimension(40, 40));
        bModifica.setMinimumSize(new java.awt.Dimension(40, 40));
        bModifica.setPreferredSize(new java.awt.Dimension(40, 40));
        bModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificaActionPerformed(evt);
            }
        });
        Left.add(bModifica);

        bSorteaza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/sort.png"))); // NOI18N
        bSorteaza.setToolTipText("Sorteaza");
        bSorteaza.setMaximumSize(new java.awt.Dimension(40, 40));
        bSorteaza.setMinimumSize(new java.awt.Dimension(40, 40));
        bSorteaza.setPreferredSize(new java.awt.Dimension(40, 40));
        bSorteaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSorteazaActionPerformed(evt);
            }
        });
        Left.add(bSorteaza);

        bIesire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poze/Icons/exit.png"))); // NOI18N
        bIesire.setToolTipText("Iesire");
        bIesire.setMaximumSize(new java.awt.Dimension(40, 40));
        bIesire.setMinimumSize(new java.awt.Dimension(40, 40));
        bIesire.setPreferredSize(new java.awt.Dimension(40, 40));
        bIesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIesireActionPerformed(evt);
            }
        });
        Left.add(bIesire);

        getContentPane().add(Left, java.awt.BorderLayout.LINE_START);

        MainTable.setAlignmentX(0.0F);
        MainTable.setAlignmentY(0.0F);
        MainTable.setLayout(new java.awt.BorderLayout());

        jTable.setShowGrid(false);
        jScrollPane.setViewportView(jTable);

        MainTable.add(jScrollPane, java.awt.BorderLayout.CENTER);

        labelReclame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelReclame.setAlignmentY(0.0F);
        labelReclame.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelReclame.setMaximumSize(new java.awt.Dimension(800, 100));
        labelReclame.setPreferredSize(new java.awt.Dimension(800, 100));
        MainTable.add(labelReclame, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(MainTable, java.awt.BorderLayout.CENTER);

        Right.setMaximumSize(new java.awt.Dimension(30, 500));
        Right.setPreferredSize(new java.awt.Dimension(30, 500));

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        getContentPane().add(Right, java.awt.BorderLayout.LINE_END);

        InfoBar.setMinimumSize(new java.awt.Dimension(800, 30));
        InfoBar.setPreferredSize(new java.awt.Dimension(800, 30));
        InfoBar.setSize(new java.awt.Dimension(800, 30));

        labelInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo.setToolTipText("");
        labelInfo.setAlignmentY(0.0F);
        labelInfo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        labelInfo.setMaximumSize(new java.awt.Dimension(700, 20));
        labelInfo.setMinimumSize(new java.awt.Dimension(700, 20));
        labelInfo.setPreferredSize(new java.awt.Dimension(700, 20));
        labelInfo.setSize(new java.awt.Dimension(700, 20));

        javax.swing.GroupLayout InfoBarLayout = new javax.swing.GroupLayout(InfoBar);
        InfoBar.setLayout(InfoBarLayout);
        InfoBarLayout.setHorizontalGroup(
            InfoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoBarLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(labelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        InfoBarLayout.setVerticalGroup(
            InfoBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4, Short.MAX_VALUE))
        );

        getContentPane().add(InfoBar, java.awt.BorderLayout.PAGE_END);

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

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIesireActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogIesire.setVisible(true);
    }//GEN-LAST:event_bIesireActionPerformed

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
        try {
            if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fisierSelectat = jFileChooser1.getSelectedFile();

                model = model.loadFromFile(fisierSelectat);
                jTable.setModel(model);
                sorter = new TableRowSorter<>(jTable.getModel());
                jTable.setRowSorter(sorter);
                Message.showMessage(labelInfo, "Datele au fost incarcate.");
            }
        } catch (IOException | ClassNotFoundException | IllegalStateException e) {
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
            } catch (IOException | IllegalStateException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_JMenuSaveActionPerformed

    public void verificaTabel() {
        int selected = jTable.getSelectedRow();

        if (jTable.getRowCount() < 1) {
            throw new IllegalStateException("Lista nu contine abonati!");
        }

        if (selected == -1 || jTable.getColumnCount() == 0) {
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

            String cnp = jTable.getValueAt(jTable.getSelectedRow(), 2).toString();
            abonatDeModificat = model.getAbonatByCnp(cnp);            
            
            String nume = abonatDeModificat.getNume();
            String prenume = abonatDeModificat.getPrenume();
            
            String confirmare = String.format("Sterg abonatul %s %s?", nume, prenume);
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
            
            String cnp = jTable.getValueAt(jTable.getSelectedRow(), 2).toString();
            abonatDeModificat = model.getAbonatByCnp(cnp); 
            
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
        if (jTable.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Lista de abonati este goala. Nu aveti ce sorta!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            jDialogSortare.setLocationRelativeTo(null);
            jDialogSortare.setVisible(true);
        }
    }
    
    public void salveazaAgenda() {
        try {
            model.saveToFile(fisierAbonati);
            Message.showMessage(labelInfo, "Datele din agenda au fost salvate.");
        } catch (IOException | IllegalStateException e) {
            Message.showMessage(labelInfo, e.getMessage());
        }
    }
    
    private void jMenuAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAdaugaActionPerformed
        adauga();
    }//GEN-LAST:event_jMenuAdaugaActionPerformed

    private void bAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaActionPerformed
        adauga();
    }//GEN-LAST:event_bAdaugaActionPerformed

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
        salveazaAgenda();  
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
            Message.showMessage(labelInfo, String.format("Abonatul %s %s a fost adaugat.", abonat.getNume(), abonat.getPrenume()));
        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAdaugaAbonatActionPerformed

    private void jButtonNuStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuStergeActionPerformed
        jDialogSterge.dispose();
    }//GEN-LAST:event_jButtonNuStergeActionPerformed

    private void jButtonStergeAbonatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStergeAbonatActionPerformed
        //model.stergeAbonat(model.getElementAt(jTable1.getSelectedRow()));
        
        String nume = abonatDeModificat.getNume();
        String prenume = abonatDeModificat.getPrenume();
        model.stergeAbonat(abonatDeModificat);
        Message.showMessage(labelInfo, String.format("Abonatul %s %s a fost sters.", nume, prenume));
        jDialogSterge.dispose();
    }//GEN-LAST:event_jButtonStergeAbonatActionPerformed
    
    private void bStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStergeActionPerformed
        sterge();
    }//GEN-LAST:event_bStergeActionPerformed

    private void jMenuStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuStergeActionPerformed
        sterge();
    }//GEN-LAST:event_jMenuStergeActionPerformed

    private void jButtonSorteazaInapoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSorteazaInapoiActionPerformed
        jDialogSortare.dispose();
    }//GEN-LAST:event_jButtonSorteazaInapoiActionPerformed

    private void bSorteazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSorteazaActionPerformed
        sorteaza();
    }//GEN-LAST:event_bSorteazaActionPerformed

    private void jButtonSorteazaSorteazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSorteazaSorteazaActionPerformed
        int coloana = SortareDupa.valueOf(jComboBoxSortare.getSelectedItem().toString()).getIndex();
        SortOrder directie = SortOrder.valueOf(jComboBoxDirectieSortare.getSelectedItem().toString());

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(coloana, directie));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        
        jDialogSortare.setVisible(false);
    }//GEN-LAST:event_jButtonSorteazaSorteazaActionPerformed

    private void jButtonInapoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInapoiActionPerformed
        jTextFieldRegisterCode.setText("");
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
            Message.showMessage(labelInfo, String.format("Abonatul %s %s a fost modificat.", nume, prenume));
        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButtonModificaAbonatActionPerformed
    
    private void bModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificaActionPerformed
        modifica();
    }//GEN-LAST:event_bModificaActionPerformed

    private void jMenuModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuModificaActionPerformed
        modifica();
    }//GEN-LAST:event_jMenuModificaActionPerformed

    private void jButtonInregistreazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInregistreazaActionPerformed
        //Verificam codul si daca este corect, oprim reclamele
        String registerCode = jTextFieldRegisterCode.getText();
        
        if (!registerCode.equals(Register.getRegistrationCode())) {
            JOptionPane.showMessageDialog(this, "Codul de inregistrare nu este corect!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Register.register();
            if (Register.isRegistered()) {
                reclame.opreste();
                showRegisteredElements(true);
                jDialogInregistrare.dispose(); 
                jTextFieldRegisterCode.setText("");
            }  
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }          
    }//GEN-LAST:event_jButtonInregistreazaActionPerformed

    private void jMenuItemStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStergeActionPerformed
        sterge();
    }//GEN-LAST:event_jMenuItemStergeActionPerformed

    private void jMenuItemModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificaActionPerformed
        modifica();
    }//GEN-LAST:event_jMenuItemModificaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        sorteaza();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jDialogInregistrareWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialogInregistrareWindowClosing
        jTextFieldRegisterCode.setText("");
    }//GEN-LAST:event_jDialogInregistrareWindowClosing

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salveazaAgenda(); 
    }//GEN-LAST:event_formWindowClosing

    private void bClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearSearchActionPerformed
        tfSearch.setText("");
    }//GEN-LAST:event_bClearSearchActionPerformed

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
            @Override
            public void run() {
                new FereastraPrincipala().setVisible(true);  
            }
        });
    }
    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoBar;
    private javax.swing.JMenuItem JMenuIesire;
    private javax.swing.JMenuItem JMenuOpen;
    private javax.swing.JMenuItem JMenuSave;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel MainTable;
    private javax.swing.JMenu MeniuAbonati;
    private javax.swing.JMenu MeniuFile;
    private javax.swing.JMenu MeniuHelp;
    private javax.swing.JMenuBar MeniuPrincipal;
    private javax.swing.JPanel Right;
    private javax.swing.JPanel Search;
    private javax.swing.JPopupMenu.Separator Separator;
    private javax.swing.JButton bAdauga;
    private javax.swing.JButton bClearSearch;
    private javax.swing.JButton bIesire;
    private javax.swing.JButton bModifica;
    private javax.swing.JButton bSorteaza;
    private javax.swing.JButton bSterge;
    private javax.swing.JButton jButtonAdaugaAbonat;
    private javax.swing.JButton jButtonDa;
    private javax.swing.JButton jButtonInapoi;
    private javax.swing.JButton jButtonInchide;
    private javax.swing.JButton jButtonInchide1;
    private javax.swing.JButton jButtonInregistreaza;
    private javax.swing.JButton jButtonModificaAbonat;
    private javax.swing.JButton jButtonNu;
    private javax.swing.JButton jButtonNuSterge;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonSorteazaInapoi;
    private javax.swing.JButton jButtonSorteazaSorteaza;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAgenda;
    private javax.swing.JLabel jLabelConfirmare;
    private javax.swing.JLabel jLabelNume;
    private javax.swing.JLabel jLabelSterge;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuItem jMenuAdauga;
    private javax.swing.JMenuItem jMenuInregistrare;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemModifica;
    private javax.swing.JMenuItem jMenuItemSterge;
    private javax.swing.JMenuItem jMenuModifica;
    private javax.swing.JMenuItem jMenuSterge;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelButoane;
    private javax.swing.JPanel jPanelButoane1;
    private javax.swing.JPanel jPanelButoaneIesire;
    private javax.swing.JPanel jPanelButoaneInregistrare;
    private javax.swing.JPanel jPanelForm;
    private javax.swing.JPanel jPanelForm1;
    private javax.swing.JPanel jPanelFormInregistrare;
    private javax.swing.JPanel jPanelSortareButoane;
    private javax.swing.JPanel jPanelSortareForm;
    private javax.swing.JPanel jPanelStergeButoane;
    private javax.swing.JPanel jPanelStergeForm;
    private javax.swing.JPanel jPanelTextIesire;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField jTextCnp;
    private javax.swing.JTextField jTextCnp1;
    private javax.swing.JTextField jTextFieldRegisterCode;
    private javax.swing.JTextField jTextFirstName;
    private javax.swing.JTextField jTextFirstName1;
    private javax.swing.JTextField jTextLastName;
    private javax.swing.JTextField jTextLastName1;
    private javax.swing.JTextField jTextPhone;
    private javax.swing.JTextField jTextPhone1;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelReclame;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}


