package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ui.MonsterListFrame.FRAME_HEIGHT;
import static ui.MonsterListFrame.FRAME_WIDTH;

// Represents the base framework for the project
public class MonsterListFramework extends WindowAdapter {
    private Point frameMiddle;

    // MODIFIES: this
    // EFFECTS: initializes the framework at the center of the user's screen
    public MonsterListFramework() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new MonsterListFrame(this);
        frameMiddle = new Point((screenSize.width / 2) - (FRAME_WIDTH / 2),
                (screenSize.height / 2) - (FRAME_HEIGHT / 2));
        frame.setLocation(frameMiddle);
        frame.setVisible(true);
    }

    // EFFECTS: shut the program down when the main window is closed
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }
}