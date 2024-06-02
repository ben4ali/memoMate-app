package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.util.ArrayList;

public class SearchDialog extends JDialog {
    JTextField zoneRecherche;
    JLabel vide;
    JButton suivant;
    JButton annuler;
    String inputSh;
    int count = 0;
    int cmpt = 0;

    SearchDialog(DocumentContainer documentContainer){
        //Dialog box properties
        this.setTitle("Rechercher");
        this.setSize(400,120);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        addComponents();
        addListener(documentContainer);

        this.setVisible(true);

    }

    private void addComponents(){
        JPanel main = new JPanel(new BorderLayout());
        this.add(main, BorderLayout.CENTER);
        main.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Rechercher
        JPanel boxSh = new JPanel(new GridLayout(2,1));
        boxSh.add(new JLabel("Recherche : "));
        boxSh.add(vide = new JLabel(""));
        main.add(boxSh, BorderLayout.WEST);

        JPanel boxBtn_input = new JPanel(new GridLayout(2,1));
        JPanel boxBtn = new JPanel(new GridLayout(0,2));
        boxBtn.add(suivant = new JButton("Suivant"));
        boxBtn.add(annuler = new JButton("Annuler"));
        boxBtn_input.add(zoneRecherche = new JTextField(10));
        boxBtn_input.add(boxBtn);
        main.add(boxBtn_input,BorderLayout.CENTER);
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

    public void canSh(){
        suivant.setEnabled(!zoneRecherche.getText().isEmpty());
    }

    private void addListener(DocumentContainer documentContainer){
        canSh();
        // Variables
        String textAction = documentContainer.getContent().editorPane.getText();
        // Array of doc text
        String[] arrayWords = textAction.split(" ");
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> resultIndex = new ArrayList<Integer>();
        Document document = zoneRecherche.getDocument();


        document.addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                canSh();
                // Pas d'usage pour ce cas
                cmpt =0;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                canSh();
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
        // Annuler action
        annuler.addActionListener(e -> {
            documentContainer.getContent().unselect();
            dispose();
        });
        // suivant action
        suivant.addActionListener(e -> {
                    try {


                        cmpt++;
                        int index = 0;
                        inputSh = zoneRecherche.getText();
                        for (String word : arrayWords) {
                            if (inputSh.equals(word)) {
                                index = textAction.indexOf(word);
                                result.add(word);
                            }
                        }
                        while (index >= 0) {
                            resultIndex.add(index);

                            try {
                                index = textAction.indexOf(result.get(0), index + 1);
                            } catch (Exception e1) {
                                showErrorDialog("Impossible de trouver le mot ", inputSh);
                            }
                            count++;
                        }

                        int lon = result.get(0).length();

                        vide.setText(result.size() + " " + inputSh);

                        documentContainer.getContent().editorPane.select(resultIndex.get(cmpt - 1), resultIndex.get(cmpt - 1) + lon);

                        if (cmpt == resultIndex.size()) {
                            cmpt = 0;
                        }

                        resultIndex.clear();
                        result.clear();
                        count = 0;
                    }

                catch(Exception e0){

        }
    });

    }
}
