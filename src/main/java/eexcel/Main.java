package eexcel;

import javax.swing.*;

public class Main {


    private static EGui eGui;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                eGui = new EGui();
            }
        });
    }
}
