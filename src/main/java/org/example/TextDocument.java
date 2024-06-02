package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.Scanner;


public class TextDocument extends JScrollPane {
    JEditorPane editorPane ;
    UndoManager undoManager = new UndoManager();
    Document document;
    File localFile = new File("/Users/null");


    TextDocument(){
        editorPane = new JEditorPane();
        undoManager.setLimit(-1);
        this.getViewport().setView(editorPane);

        document = editorPane.getDocument();
        document.addUndoableEditListener(undoManager);
    }
    TextDocument(File file){
        editorPane = new JEditorPane();
        setViewportView(editorPane);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            StringBuilder contenu = new StringBuilder();
            while ((line = reader.readLine()) != null){
                contenu.append(line).append("\n");
            }
            reader.close();
            fileReader.close();
            editorPane.setText(contenu.toString());

        }
        catch (IOException e){
            System.err.println("Erreur de chargement du fichier");
            e.printStackTrace();
        }

    }

    public void registerEditlistener(DocumentContainer modif){
        document = this.editorPane.getDocument();
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String title  =  modif.getTitleAt(modif.getselectedIndex());
                if (!title.contains("*")){
                    modif.setTitleAt(modif.getselectedIndex(),
                            title+"*");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String title  =  modif.getTitleAt(modif.getselectedIndex());
                if (!title.contains("*")){
                    modif.setTitleAt(modif.getselectedIndex(),
                            title+"*");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String title  =  modif.getTitleAt(modif.getselectedIndex());
                if (!title.contains("*")){
                    modif.setTitleAt(modif.getselectedIndex(),
                            title+"*");
                }
            }
        });

    }
    public void copy() {
        String selectedText = editorPane.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            StringSelection text = new StringSelection(selectedText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(text, null);
        }
    }

    public void cut(){
        editorPane.cut();
    }

    public void paste(){
        editorPane.paste();
    }

    public void save(){
        if(this.localFile.getName().equals("null")){
            saveAs();
        }else {
            try {
                FileWriter fw = new FileWriter(localFile);
                fw.write(editorPane.getText());
                fw.close();
            }catch (Exception e){
                System.out.println("Erreur d'écriture");
            }
        }
    }

    public void saveAs(){
        JFileChooser filChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents", "txt");
        filChooser.setFileFilter(filter);
        filChooser.setVisible(true);
        var result = filChooser.showSaveDialog(editorPane);
        if(result == 0){
            this.localFile = filChooser.getSelectedFile();
        }

        try {
            FileWriter fw = new FileWriter(localFile);
            fw.write(editorPane.getText());
            fw.close();
        }catch (Exception e){
            System.out.println("Erreur d'écriture");
        }
    }

    public void undo(){
        int numEdits = 1;
        int delay = 2;

        for (int i = 0; i < numEdits && undoManager.canUndo(); i++) {
            undoManager.undo();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void redo(){
        int numEdits = 1;
        int delay = 2;
        for (int i = 0; i < numEdits && undoManager.canRedo(); i++) {
            undoManager.redo();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void unselect(){
        int caretPosition = editorPane.getCaretPosition();
        editorPane.setSelectionStart(caretPosition);
        editorPane.setSelectionEnd(caretPosition);
    }

    public void update(Document doc, JLabel satut, DocumentContainer onglet){

        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                satut.setText(info(onglet));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                satut.setText(info(onglet));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                satut.setText(info(onglet));
            }
        });

        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                satut.setText(info(onglet));
            }
        });

        onglet.addChangeListener(e -> {
            satut.setText(info(onglet));
            System.out.println(onglet.getselectedIndex());
        });
    }

    public String info(DocumentContainer onglet){
        String information = String.format("Nombre de caractères : %s  Selection : %s Nombre de fenêtres %s",
                onglet.getContent().editorPane.getText()
                        .replace(" ", "")
                        .replace("\n", "")
                        .replace("\t", "")
                        .length(),
                onglet.getTitleAt(onglet.getselectedIndex()), onglet.getTabCount());

        return information;
    }

}
