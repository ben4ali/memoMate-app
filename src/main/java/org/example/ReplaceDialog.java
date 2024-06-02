package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;


public class ReplaceDialog extends JDialog {
    JTextField zoneRecherche;
    JTextField zoneRemplacer;
    JLabel vide;
    JButton suivant;
    JButton remplacer;
    JButton remplacerTout;
    JButton annuler;
    String inputSh;
    int count = 0;
    int cmpt = 0;


    ReplaceDialog(DocumentContainer documentContainer){
        this.setTitle("Remplacer");
        this.setLayout(new GridLayout(4,4));
        this.setResizable(false);
        this.setSize(400,150);
        this.setLocationRelativeTo(null);


        addComponents();
        addListener(documentContainer);

        this.setVisible(true);
    }

    private void addComponents(){
        //Rechercher
        JLabel messageRecherche = new JLabel();
        messageRecherche.setText("   Rechercher:");

        zoneRecherche = new JTextField();
        zoneRecherche.setSize(50,50);

        //Remplacer par
        JLabel messageRemplacer = new JLabel();
        messageRemplacer.setText("   Remplacer:");

        zoneRemplacer = new JTextField();
        zoneRemplacer.setSize(50,50);

        //Boutons
        suivant = new JButton();
        suivant.setText("Suivant");
        suivant.setBounds(0,0,10,10);

        remplacer = new JButton();
        remplacer.setText("Remplacer");
        remplacer.setBounds(10,10,10,10);

        remplacerTout = new JButton();
        remplacerTout.setText("Remplacer Tout");
        remplacerTout.setBounds(20,20,10,10);

        annuler = new JButton();
        annuler.setText("Annuler");
        annuler.setBounds(30,30,10,10);

        //Add les composants au JDialog
        add(messageRecherche);
        add(zoneRecherche);

        add(messageRemplacer);
        add(zoneRemplacer);

        add(suivant);
        add(remplacer);
        add(remplacerTout);
        add(annuler);

    }
    public void showErrorDialog(String message, String occurence) {
        dispose();
        JDialog dialog = new JDialog();
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Mot introuvable");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel(message+occurence);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.getContentPane().add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        this.getContentPane().add(dialog);
    }
    public void canShRecherche(){
        suivant.setEnabled(!zoneRecherche.getText().isEmpty());
    }
    public void canShRemplacer(){
        remplacer.setEnabled(!zoneRemplacer.getText().isEmpty());
    }
    public void canShRemplacerTout(){
        remplacerTout.setEnabled(!zoneRemplacer.getText().isEmpty());
    }
    private void addListener(DocumentContainer documentContainer){
        canShRecherche();
        canShRemplacer();
        canShRemplacerTout();
        // Variables
        final String[] textAction = {documentContainer.getContent().editorPane.getText()};
        // Array of doc text
        final String[][] arrayWords = {textAction[0].split(" ")};
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> resultIndex = new ArrayList<Integer>();
        Document document = zoneRecherche.getDocument();
        Document document0 = zoneRemplacer.getDocument();

        document0.addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                canShRemplacer();
                canShRemplacerTout();
                // Pas d'usage pour ce cas
                cmpt =0;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                canShRemplacer();
                canShRemplacerTout();
                documentContainer.getContent().unselect();
                // Réinisialisation de tous pour récuperer d'autre selection
                resultIndex.clear();
                result.clear();
                count =0;
                cmpt =0;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Pas d'usage pour ce cas
            }
        });

        document.addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                canShRecherche();
                // Pas d'usage pour ce cas
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                canShRecherche();
                documentContainer.getContent().unselect();
                // Réinisialisation de tous pour récuperer d'autre selection
                resultIndex.clear();
                result.clear();
                count =0;
                cmpt =0;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        suivant.addActionListener(e ->
        {
            try {
                cmpt++;

                int index = 0;
                inputSh = zoneRecherche.getText();
                for (String word : arrayWords[0]) {
                    if (inputSh.equals(word)) {
                        index = textAction[0].indexOf(word);
                        result.add(word);
                    }
                }
                while (index >= 0) {
                    resultIndex.add(index);
                    try {
                        index = textAction[0].indexOf(result.get(0), index + 1);
                    } catch (Exception e1) {
                        showErrorDialog("Impossible de trouver le mot ", inputSh);
                    }
                    count++;
                }

                int lon = result.get(0).length();

                documentContainer.getContent().editorPane.select(resultIndex.get(cmpt - 1), resultIndex.get(cmpt - 1) + lon);
                while (documentContainer.getContent().editorPane.getSelectedText() == null) {
                    count++;
                }

                if (cmpt == resultIndex.size()) {
                    cmpt = 0;
                }
                resultIndex.clear();
                result.clear();
                count = 0;
            }
            catch (Exception e0) {

            }
        });
        remplacer.addActionListener(e -> {
            if (documentContainer.getContent().editorPane.getSelectedText() != null) {
                String remplacement = zoneRemplacer.getText();
                String inputSh = zoneRecherche.getText();
                int startIndex = documentContainer.getContent().editorPane.getSelectionStart();
                int lengthDiff = remplacement.length() - inputSh.length();

                documentContainer.getContent().editorPane.replaceSelection(remplacement);
                documentContainer.getContent().unselect();

                result.clear();
                resultIndex.clear();
                int index = 0;
                for (String word : arrayWords[0]) {
                    if (inputSh.equals(word)) {
                        index = textAction[0].indexOf(word, index);
                        result.add(word);
                        resultIndex.add(index);
                    }
                    index += word.length() + 1;
                }

                for (int i = 0; i < resultIndex.size(); i++) {
                    index = resultIndex.get(i);
                    if (index >= startIndex) {
                        index += lengthDiff;
                        resultIndex.set(i, index);
                    }
                }

                textAction[0] = documentContainer.getContent().editorPane.getText();
                arrayWords[0] = textAction[0].split(" ");
            }
        });
        remplacerTout.addActionListener(e -> {
            String inputSh = zoneRecherche.getText();
            String remplacement = zoneRemplacer.getText();
            String text = documentContainer.getContent().editorPane.getText();
            String newText = text.replace(inputSh, remplacement);
            documentContainer.getContent().editorPane.setText(newText);
            documentContainer.getContent().unselect();
        });
        annuler.addActionListener(e -> {
            dispose();
        });
    }
}
