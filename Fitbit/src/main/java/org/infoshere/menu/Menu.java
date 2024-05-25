package org.infoshere.menu;

import java.io.IOException;

public interface Menu {

    void printMenu();

    void selectOption(int optionNumber) throws IOException;
}
