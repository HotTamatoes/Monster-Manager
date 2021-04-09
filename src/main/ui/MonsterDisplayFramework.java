package ui;

import model.Monster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ui.MonsterDisplayFrame.FRAME_HEIGHT;
import static ui.MonsterDisplayFrame.FRAME_WIDTH;

// Represents the window that holds the visual for a monster
public class MonsterDisplayFramework extends WindowAdapter {
    private MonsterListFrame caller;
    private MonsterDisplayFrame frame;
    private Monster monster;
    private Point frameMiddle;

    // EFFECTS: creates a new window in the middle of the User's screen
    public MonsterDisplayFramework(Monster monster, MonsterListFrame overlord) {
        caller = overlord;
        this.monster = monster;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new MonsterDisplayFrame(this, monster);
        frameMiddle = new Point((screenSize.width / 2) - (FRAME_WIDTH / 2),
                (screenSize.height / 2) - (FRAME_HEIGHT / 2));
        frame.setLocation(frameMiddle);
        frame.setVisible(true);
    }

    public void windowClosing(WindowEvent e) {
        frame.giveInfo();
    }

    // EFFECTS: calls a method in the MonsterListFrame that updates the monster
    public void giveInfo(int str, int dex, int con, int intel, int wis, int cha, int level, int armor, int hp) {
        caller.updateMonster(monster, str, dex, con, intel, wis, cha, level, armor, hp);
    }
}
