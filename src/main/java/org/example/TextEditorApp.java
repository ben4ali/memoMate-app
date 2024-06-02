package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TextEditorApp extends JFrame {
    private final Settings conf = new Settings();
    private final JLabel satutBar = new JLabel("Informations",JLabel.CENTER);

    private final DocumentContainer onglet = new DocumentContainer();
    TextEditorApp(){
        String os = System.getProperty("os.name");
        if (os.contains("Mac")){
            System.setProperty( "apple.laf.useScreenMenuBar", "true" ); // for apple
        }

        this.setSize(1000,750);
        this.setTitle("MemoMate");
        this.setLocationRelativeTo(null);
        this.setIconImage(conf.appIcon);
        // Default Methods
        addComponents();
        addMenus();
        this.add(satutBar, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void addComponents(){
        this.getContentPane().add(onglet);
        onglet.getContent().update(onglet.getContent().document,satutBar,onglet);
    }


    public void addMenus(){
        JMenuBar menuBar = new JMenuBar();
// Fichier -->
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem menuItemNouveau = new JMenuItem("Nouveau");
        menuFichier.add(menuItemNouveau);
        //Nouveau
        menuItemNouveau.addActionListener(e -> {
            onglet.createNewDocument();
        });
        //Ouvrir
        JMenuItem menuItemOuvrir = new JMenuItem("Ouvrir");
        menuFichier.add(menuItemOuvrir);
        menuItemOuvrir.addActionListener(e -> {
            onglet.openDocument();
        });
        //Sauvegarder
        JMenuItem menuItemSauvegarder = new JMenuItem("Sauvegarder");
        menuFichier.add(menuItemSauvegarder);
        menuItemSauvegarder.addActionListener(e -> {
            onglet.saveCurrentDocument();
        });
        //Sauvegarder sous
        JMenuItem menuItemSauvegarderSous = new JMenuItem("Sauvegarder sous");
        menuFichier.add(menuItemSauvegarderSous);
        menuItemSauvegarderSous.addActionListener(e -> {
            onglet.saveAsCurrentDocument();
        });
        //Fermer
        JMenuItem menuItemFermer = new JMenuItem("Fermer");
        menuFichier.add(menuItemFermer);
        menuItemFermer.addActionListener(e -> {
            boolean closed = onglet.closeCurrentDocument();
        });
        //Fermer tout
        JMenuItem menuItemFermerTout = new JMenuItem("Fermer tout");
        menuFichier.add(menuItemFermerTout);
        menuItemFermerTout.addActionListener(e -> {
            boolean closed = onglet.closeAllDocument();
        });

        //Quitter
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuFichier.add(menuItemQuitter);
        menuItemQuitter.addActionListener(e -> {
            System.exit(0);
        });


        //Racourccis
        menuItemNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menuItemOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menuItemSauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menuItemSauvegarderSous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
        menuItemFermer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        menuItemFermerTout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
        menuItemQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));

        // Edition -->
        JMenu menuEdition = new JMenu("Edition");
        JMenuItem jMenuItemUndo = new JMenuItem("Undo");
        menuEdition.add(jMenuItemUndo);
        jMenuItemUndo.addActionListener(e -> onglet.undoCurrentDocument());
        JMenuItem jMenuItemRedo = new JMenuItem("Redo");
        menuEdition.add(jMenuItemRedo);
        jMenuItemRedo.addActionListener(e -> onglet.redoCurrentDocument());
        JMenuItem jMenuItemCopy = new JMenuItem("Copy");
        menuEdition.add(jMenuItemCopy);
        jMenuItemCopy.addActionListener(e -> onglet.copyInCurrentDocument());
        JMenuItem jMenuItemCut = new JMenuItem("Cut");
        menuEdition.add(jMenuItemCut);
        jMenuItemCut.addActionListener(e -> onglet.cutInCurrentDocument());
        JMenuItem jMenuItemPaste = new JMenuItem("Paste");
        menuEdition.add(jMenuItemPaste);
        jMenuItemPaste.addActionListener(e -> onglet.pasteInCurrentDocument());

        //Raccourcis
        jMenuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        jMenuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        jMenuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        jMenuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        jMenuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
// Outils -->
        JMenu menuOutils = new JMenu("Outils");
        JMenuItem jMenuItemRechercher = new JMenuItem("Rechercher");
        menuOutils.add(jMenuItemRechercher);
        //recherche listener
        jMenuItemRechercher.addActionListener(e -> {
            new SearchDialog(onglet);
        });
        JMenuItem jMenuItemRemplacer = new JMenuItem("Remplacer");
        menuOutils.add(jMenuItemRemplacer);
        //remplacer listener
        jMenuItemRemplacer.addActionListener(e -> {
            new ReplaceDialog(onglet);
        });
        //Raccourcis
        jMenuItemRechercher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        jMenuItemRemplacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
// Aide -->
        JMenu menuAide = new JMenu("Aide");
        JMenuItem jMenuItemApropos = new JMenuItem("À propos ");
        menuAide.add(jMenuItemApropos);
        jMenuItemApropos.addActionListener(e -> {
            JOptionPane.showMessageDialog(super.rootPane,
                    "MemoMate\n" +
                            "Benkarrouch Ali\n"+
                            "2023 Version 1.0",
                    "À propos"
                    ,JOptionPane.INFORMATION_MESSAGE,conf.scaleImage(conf.incoInfo,20,25));
        });
        jMenuItemApropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.BUTTON1_DOWN_MASK));

        menuBar.add(menuFichier);
        menuBar.add(menuEdition);
        menuBar.add(menuOutils);
        menuBar.add(menuAide);

        this.setJMenuBar(menuBar);
// Bouton X windows

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean hasNotSavedDocument = false;
                for (int i = 0; i < onglet.getTabCount();i++) {
                    if (onglet.getTitleAt(i).endsWith("*")){
                        hasNotSavedDocument = true;
                    }
                }
                if (hasNotSavedDocument) {
                    int response = JOptionPane.showConfirmDialog(getRootPane(),
                            "<html><b>Attention!<br>\nUn ou plusieurs document(s) ne sont pas enregistré(s).\nÊtes-vous sûr de vouloir continuer ?",
                            "Avertissement", JOptionPane.YES_NO_OPTION);
                    if (response == 0) {
                        System.exit(0);
                    } else {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
                else
                    System.exit(0);
            }
        });
    }

    public DocumentContainer getOnglet() {
        return onglet;
    }
}
