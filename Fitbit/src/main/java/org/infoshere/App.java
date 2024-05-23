package org.infoshere;

import org.infoshere.menu.MainMenu;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.run();
    }
}

