package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DocumentContainer extends JTabbedPane {

    DocumentContainer(){
        createNewDocument();
    }

    public void createNewDocument(){
        this.addTab("Untitled"+(this.getTabCount() + 1), new TextDocument() );
        this.setSelectedIndex(this.getTabCount() -1);
        this.getContent().registerEditlistener(this);
    }
    public void openDocument(){
        var filChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents", "txt");
        filChooser.setFileFilter(filter);
        var result = filChooser.showOpenDialog(this.getRootPane());
        if (result == 0){
            var file = filChooser.getSelectedFile();
            this.addTab(file.getName(), new TextDocument(file) );
            this.setSelectedIndex(this.getTabCount() -1);
        }
        this.getContent().registerEditlistener(this);
    }
    public int getselectedIndex(){
        return this.getSelectedIndex();
    }
    public void saveCurrentDocument(){
        getContent().save();
        this.setTitleAt(getselectedIndex(),getContent().localFile.getName());
    }
    public void saveAsCurrentDocument(){
        getContent().saveAs();
        if (!this.getContent().localFile.getName().equals("null")){
            this.setTitleAt(this.getselectedIndex(), getContent().localFile.getName());
        }
    }
    public boolean closeCurrentDocument(){
            if (getTitleAt(getselectedIndex()).endsWith("*")){
                int response = JOptionPane.showOptionDialog(getRootPane(),
                        "<html><b>Attention<b>\n Vous êtes sur le point de fermer l'onglet courant sans enregistrer.\n Voulez-vous enregistrer "+getTitleAt(getselectedIndex())+"?",
                        "Sauvegarder changements?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Enregistrer", "Ne pas enregistrer", "Annuler"},
                        "Enregistrer");
                if(response == JOptionPane.YES_OPTION){
                    saveAsCurrentDocument();
                    try {
                        removeTabAt(getselectedIndex());
                    }
                    catch (Exception e) {}
                    if (getTabCount() == 0) {
                        createNewDocument();
                    }
                }
                else if (response == JOptionPane.NO_OPTION){
                    try {removeTabAt(getselectedIndex());}
                    catch (Exception e) {}
                    if (getTabCount() == 0) {
                        createNewDocument();
                    }
                }
            }
            else
                try {removeTabAt(getselectedIndex());}
                catch (Exception e) {}
            if (getTabCount() == 0) {
                createNewDocument();
            }

        return getTabCount() == 0;
    }


    public boolean closeAllDocument(){
        for (int i = getTabCount()-1; i > 0;i--) {

            if (getTitleAt(i).endsWith("*")){
                int response = JOptionPane.showOptionDialog(getRootPane(),
                        "<html><b>Attention<b>\n Vous êtes sur le point de fermer l'onglet courant sans sauvegarder.\n Voulez-vous sauvegarder "+getTitleAt(i)+"?",
                        "Sauvegarder changements?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Enregistrer", "Ne pas enregistrer", "Annuler"},
                        "Enregistrer");
                if(response == JOptionPane.YES_OPTION){
                    saveAsCurrentDocument();
                    try {
                        removeTabAt(i);
                    }
                    catch (Exception e) {}
                    if (getTabCount() == 0) {
                        createNewDocument();
                    }
                }
                else if (response == JOptionPane.NO_OPTION){
                    try {removeTabAt(i);}
                    catch (Exception e) {}
                    if (getTabCount() == 0) {
                        createNewDocument();
                    }
                }
            }
            else
                try {removeTabAt(i);}
                catch (Exception e) {}
            if (getTabCount() == 0) {
                createNewDocument();
            }
        }
        return getTabCount() == 0;
    }

    public void undoCurrentDocument(){
        getContent().undo();
    }

    public void redoCurrentDocument(){
        getContent().redo();
    }

    public void copyInCurrentDocument(){
        getContent().copy();
    }

    public void cutInCurrentDocument(){
        getContent().cut();
    }

    public void pasteInCurrentDocument(){
        getContent().paste();
    }

    public TextDocument getContent(){
        return (TextDocument) this.getComponentAt(this.getSelectedIndex());
    }

}
