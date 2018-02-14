/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author dorumuntean
 */
public class FereastraPrincipala extends javax.swing.JFrame {

    /**
     * Creates new form FereastraPrincipala
     */
    public FereastraPrincipala() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogIesire = new javax.swing.JDialog();
        jButtonDa = new javax.swing.JButton();
        jButtonNu = new javax.swing.JButton();
        jLabelConfirmare = new javax.swing.JLabel();
        jDialogAbout = new javax.swing.JDialog();
        jLabelAgenda = new javax.swing.JLabel();
        jLabelNume = new javax.swing.JLabel();
        jButtonOk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAdauga = new javax.swing.JButton();
        jButtonSterge = new javax.swing.JButton();
        jButtonModifica = new javax.swing.JButton();
        jButtonSorteaza = new javax.swing.JButton();
        jButtonIesire = new javax.swing.JButton();
        jLabelReclame = new javax.swing.JLabel();
        jLabelCauta = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        MeniuPrincipal = new javax.swing.JMenuBar();
        MeniuFile = new javax.swing.JMenu();
        JMenuOpen = new javax.swing.JMenuItem();
        JMenuSave = new javax.swing.JMenuItem();
        Separator = new javax.swing.JPopupMenu.Separator();
        JMenuIesire = new javax.swing.JMenuItem();
        MeniuAbonati = new javax.swing.JMenu();
        jMenuAdauga = new javax.swing.JMenuItem();
        jMenuCauta = new javax.swing.JMenuItem();
        jMenuSterge = new javax.swing.JMenuItem();
        jMenuModifica = new javax.swing.JMenuItem();
        MeniuHelp = new javax.swing.JMenu();
        jMenuInregistrare = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuAbout = new javax.swing.JMenuItem();

        jDialogIesire.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogIesire.setTitle("Confirmare");
        jDialogIesire.setMinimumSize(new java.awt.Dimension(300, 100));
        jDialogIesire.setModal(true);
        jDialogIesire.setPreferredSize(new java.awt.Dimension(300, 100));
        jDialogIesire.setResizable(false);

        jButtonDa.setMnemonic('D');
        jButtonDa.setText("DA");
        jButtonDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDaActionPerformed(evt);
            }
        });

        jButtonNu.setMnemonic('N');
        jButtonNu.setText("Nu");
        jButtonNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuActionPerformed(evt);
            }
        });

        jLabelConfirmare.setText("Sunteti siguri ca doriti sa iesiti?");

        javax.swing.GroupLayout jDialogIesireLayout = new javax.swing.GroupLayout(jDialogIesire.getContentPane());
        jDialogIesire.getContentPane().setLayout(jDialogIesireLayout);
        jDialogIesireLayout.setHorizontalGroup(
            jDialogIesireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialogIesireLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jDialogIesireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelConfirmare)
                    .addGroup(jDialogIesireLayout.createSequentialGroup()
                        .addComponent(jButtonDa)
                        .addGap(30, 30, 30)
                        .addComponent(jButtonNu)))
                .addGap(51, 51, 51))
        );
        jDialogIesireLayout.setVerticalGroup(
            jDialogIesireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogIesireLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabelConfirmare)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialogIesireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNu)
                    .addComponent(jButtonDa))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jDialogAbout.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialogAbout.setTitle("About");
        jDialogAbout.setLocation(new java.awt.Point(0, 0));
        jDialogAbout.setMinimumSize(new java.awt.Dimension(200, 200));
        jDialogAbout.setPreferredSize(new java.awt.Dimension(200, 200));
        jDialogAbout.setResizable(false);

        jLabelAgenda.setText("Agenda Telefonica");

        jLabelNume.setText("Muntean Doru");

        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogAboutLayout = new javax.swing.GroupLayout(jDialogAbout.getContentPane());
        jDialogAbout.getContentPane().setLayout(jDialogAboutLayout);
        jDialogAboutLayout.setHorizontalGroup(
            jDialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogAboutLayout.createSequentialGroup()
                .addGroup(jDialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogAboutLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabelAgenda))
                    .addGroup(jDialogAboutLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(jDialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonOk)
                            .addComponent(jLabelNume))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jDialogAboutLayout.setVerticalGroup(
            jDialogAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogAboutLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelAgenda)
                .addGap(29, 29, 29)
                .addComponent(jLabelNume)
                .addGap(32, 32, 32)
                .addComponent(jButtonOk)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(jTable1);

        jButtonAdauga.setText("Adauga");

        jButtonSterge.setText("Sterge");

        jButtonModifica.setText("Modifica");

        jButtonSorteaza.setText("Sorteaza");

        jButtonIesire.setText("Iesire");
        jButtonIesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIesireActionPerformed(evt);
            }
        });

        jLabelReclame.setText("Reclame");

        jLabelCauta.setText("Cauta");

        jTextField1.setToolTipText("Cauta");

        MeniuPrincipal.setName(""); // NOI18N

        MeniuFile.setMnemonic('F');
        MeniuFile.setText("File");

        JMenuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, 0));
        JMenuOpen.setMnemonic('O');
        JMenuOpen.setText("Open");
        JMenuOpen.setToolTipText("");
        MeniuFile.add(JMenuOpen);

        JMenuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        JMenuSave.setMnemonic('S');
        JMenuSave.setText("Save");
        MeniuFile.add(JMenuSave);
        MeniuFile.add(Separator);

        JMenuIesire.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, 0));
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

        jMenuAdauga.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        jMenuAdauga.setMnemonic('A');
        jMenuAdauga.setText("Adauga...");
        MeniuAbonati.add(jMenuAdauga);

        jMenuCauta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, 0));
        jMenuCauta.setMnemonic('C');
        jMenuCauta.setText("Cauta...");
        MeniuAbonati.add(jMenuCauta);

        jMenuSterge.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        jMenuSterge.setMnemonic('S');
        jMenuSterge.setText("Sterge...");
        MeniuAbonati.add(jMenuSterge);

        jMenuModifica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, 0));
        jMenuModifica.setMnemonic('M');
        jMenuModifica.setText("Modifica...");
        MeniuAbonati.add(jMenuModifica);

        MeniuPrincipal.add(MeniuAbonati);

        MeniuHelp.setMnemonic('H');
        MeniuHelp.setText("Help");

        jMenuInregistrare.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, 0));
        jMenuInregistrare.setMnemonic('I');
        jMenuInregistrare.setText("Inregistrare");
        MeniuHelp.add(jMenuInregistrare);
        MeniuHelp.add(jSeparator1);

        jMenuAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonModifica)
                            .addComponent(jButtonAdauga)
                            .addComponent(jButtonSterge)
                            .addComponent(jButtonSorteaza)
                            .addComponent(jButtonIesire))
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelReclame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabelCauta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAdauga, jButtonIesire, jButtonModifica, jButtonSorteaza, jButtonSterge});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAdauga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSterge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonModifica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSorteaza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonIesire))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCauta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelReclame, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAdauga, jButtonIesire, jButtonModifica, jButtonSorteaza, jButtonSterge});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIesireActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogIesire.setVisible(true);
    }//GEN-LAST:event_jButtonIesireActionPerformed

    private void JMenuIesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuIesireActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogIesire.setVisible(true);
    }//GEN-LAST:event_JMenuIesireActionPerformed

    private void jButtonDaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDaActionPerformed
        //TODO - salveaza date
        System.exit(0);
    }//GEN-LAST:event_jButtonDaActionPerformed

    private void jButtonNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuActionPerformed
        jDialogIesire.dispose();
    }//GEN-LAST:event_jButtonNuActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        jDialogAbout.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutActionPerformed
        jDialogIesire.setLocationRelativeTo(null);
        jDialogAbout.setVisible(true);
    }//GEN-LAST:event_jMenuAboutActionPerformed

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
    private javax.swing.JButton jButtonAdauga;
    private javax.swing.JButton jButtonDa;
    private javax.swing.JButton jButtonIesire;
    private javax.swing.JButton jButtonModifica;
    private javax.swing.JButton jButtonNu;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonSorteaza;
    private javax.swing.JButton jButtonSterge;
    private javax.swing.JDialog jDialogAbout;
    private javax.swing.JDialog jDialogIesire;
    private javax.swing.JLabel jLabelAgenda;
    private javax.swing.JLabel jLabelCauta;
    private javax.swing.JLabel jLabelConfirmare;
    private javax.swing.JLabel jLabelNume;
    private javax.swing.JLabel jLabelReclame;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuItem jMenuAdauga;
    private javax.swing.JMenuItem jMenuCauta;
    private javax.swing.JMenuItem jMenuInregistrare;
    private javax.swing.JMenuItem jMenuModifica;
    private javax.swing.JMenuItem jMenuSterge;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}