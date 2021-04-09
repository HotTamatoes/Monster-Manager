package ui;

import model.Monster;

public class Main {

    private static void createAndShowGUI() {
        MonsterListFramework framework = new MonsterListFramework();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
