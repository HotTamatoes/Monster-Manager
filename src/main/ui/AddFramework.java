package ui;

import java.awt.*;
import java.awt.event.*;

import static ui.AddFrame.FRAME_HEIGHT;
import static ui.AddFrame.FRAME_WIDTH;

// Represents a box that the user inputs the parameters for a new Monster
public class AddFramework extends WindowAdapter {
    MonsterListFrame controller;
    boolean multiple;
    private Point frameMiddle;
    AddFrame frame;

    // EFFECTS: creates a new AddFrame window that is in the center of the user's screen
    public AddFramework(MonsterListFrame overlord, boolean multiple) {
        controller = overlord;
        this.multiple = multiple;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new AddFrame(this);
        frameMiddle = new Point((screenSize.width / 2) - (FRAME_WIDTH / 2),
                (screenSize.height / 2) - (FRAME_HEIGHT / 2));
        frame.setLocation(frameMiddle);
        frame.setVisible(true);
    }

    // EFFECTS: passes the user's info to the MonsterListFrame that created this framework
    public void getInfo(int level, String type, int number) {
        //System.out.println("we made it!");
        controller.addMonster(level, type, number, multiple);
    }
}
