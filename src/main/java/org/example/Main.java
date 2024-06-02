package org.example;

import com.formdev.flatlaf.*;

public class Main {
    public static void main(String[] args) {
        //Set thème pour interface

        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Initialisation de l'application
        new TextEditorApp();
    }
}