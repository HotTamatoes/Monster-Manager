package ui;

import javafx.scene.shape.Box;
import model.Monster;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents a window that display's a monster
public class MonsterDisplayFrame extends JFrame implements ActionListener, FocusListener {
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;
    private static final double IMAGE_FACTOR = 1.8;
    Monster mon;
    protected Dimension defaultSize = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    protected MonsterDisplayFramework framework;
    private JPanel panel;

    private JTextField strField;
    private JTextField dexField;
    private JTextField conField;
    private JTextField intField;
    private JTextField wisField;
    private JTextField chaField;
    private JTextField levelField;
    private JTextField hpField;
    private JTextField armorField;

    // EFFECTS: creates a new window of size defaultSize that shows the monster given as a parameter
    public MonsterDisplayFrame(MonsterDisplayFramework controller, Monster monster) {
        super(monster.toString());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        framework = controller;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        mon = monster;

        // Image source: https://gilkalai.files.wordpress.com/2017/09/dice.png
        this.setIconImage(new ImageIcon("./data/dice.png").getImage());
        addWindowListener(framework);

        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.X_AXIS));
        topHalf.add(strDexCon());
        topHalf.add(displayMonsterImage());
        topHalf.add(intWisCha());
        panel.add(topHalf);

        panel.add(makeBotHalf());

        this.add(panel);

        fillFields();
        this.setSize(defaultSize);
        this.setVisible(true);
    }

    // EFFECTS: gives all new values for the monster back to the Framework that created the instance on program close
    public void actionPerformed(ActionEvent e) {
        try {
            this.setVisible(false);
            giveInfo();
            //System.out.println("Dying!");
            this.dispose();
        } catch (NumberFormatException nfe) {
            JOptionPane.showInputDialog(null, "Error! Not a number");
        }
    }

    // EFFECTS: sends the monster's info to the controlling framework
    public void giveInfo() {
        framework.giveInfo(Integer.parseInt(strField.getText()),
                Integer.parseInt(dexField.getText()),
                Integer.parseInt(conField.getText()),
                Integer.parseInt(intField.getText()),
                Integer.parseInt(wisField.getText()),
                Integer.parseInt(chaField.getText()),
                Integer.parseInt(levelField.getText()),
                Integer.parseInt(armorField.getText()),
                Integer.parseInt(hpField.getText()));
    }

    public void focusGained(FocusEvent e) {
        ((JTextField)e.getComponent()).selectAll();
    }

    public void focusLost(FocusEvent e) { } //ignore

    // MODIFIES: this
    // EFFECTS: fills each of the fields with the monster's stats
    public void fillFields() {
        strField.setText("" + mon.getStr());
        dexField.setText("" + mon.getDex());
        conField.setText("" + mon.getCon());
        intField.setText("" + mon.getIntel());
        wisField.setText("" + mon.getWis());
        chaField.setText("" + mon.getCha());
        levelField.setText("" + mon.getLevel());
        armorField.setText("" + mon.getArmor());
        hpField.setText("" + mon.getHp());
    }

    // MODIFIES: this
    // EFFECTS: creates the panel that hold strength, dexterity, and constitution
    public JComponent strDexCon() {
        JPanel strDexCon = new JPanel();
        strDexCon.setLayout(new BoxLayout(strDexCon, BoxLayout.Y_AXIS));

        strDexCon.add(setStrField());
        strDexCon.add(setDexField());
        strDexCon.add(setConField());

        return strDexCon;
    }

    // MODIFIES: this
    // EFFECTS: creates the panel that contains the int, wis, and cha stats of the monster
    public JComponent intWisCha() {
        JPanel intWisCha = new JPanel();
        intWisCha.setLayout(new BoxLayout(intWisCha, BoxLayout.Y_AXIS));

        intWisCha.add(setIntField());
        intWisCha.add(setWisField());
        intWisCha.add(setChaField());

        return intWisCha;
    }

    // MODIFIES: this
    // EFFECTS: creates the panel that lies underneath all of the other panels
    public JComponent makeBotHalf() {
        JPanel botHalf = new JPanel();
        botHalf.setLayout(new BoxLayout(botHalf, BoxLayout.Y_AXIS));

        botHalf.add(setLevelField());
        botHalf.add(setArmorField());
        botHalf.add(setHpField());

        return botHalf;
    }

    // credit to Fred Haslam, https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
    // EFFECTS: displays the image that is associated with the monster's type. If a type is not recognized,
    // the image is a question mark.
    public JComponent displayMonsterImage() {
        BufferedImage myPicture = null;
        try {
            // All images are sourced from DNDBeyond.com
            myPicture = ImageIO.read(new File("./Data/" + mon.getType() + ".png"));
        } catch (IOException e) {
            try {
                // Source: https://www.pikpng.com/pngvi/mTmhih_white-question-mark-png-fa-question-circle-o-clipart/
                myPicture = ImageIO.read(new File("./Data/QuestionMark.png"));
            } catch (IOException e2) {
                System.out.println("Error finding the picture for this monster");
            }
        }
        if (myPicture != null) {
            Image scaledImage = myPicture.getScaledInstance((int)(myPicture.getWidth() / IMAGE_FACTOR),
                    (int)(myPicture.getHeight() / IMAGE_FACTOR), Image.SCALE_SMOOTH);
            JLabel returnLabel = new JLabel(new ImageIcon(scaledImage));
            returnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return returnLabel;
        } else {
            return null;
        }
    }

    // EFFECTS: creates and initializes the strField
    public JComponent setStrField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner str = new JSpinner(new SpinnerNumberModel());
        strField = getTextField(str);
        strField.setColumns(3);
        strField.addActionListener(this);
        strField.addFocusListener(this);
        panel1.add(new JLabel("Strength: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(str);
        return panel1;
    }

    // EFFECTS: creates and initializes the dexField
    public JComponent setDexField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner dex = new JSpinner(new SpinnerNumberModel());
        dexField = getTextField(dex);
        dexField.setColumns(3);
        dexField.addActionListener(this);
        dexField.addFocusListener(this);
        panel1.add(new JLabel("Dexterity: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(dex);
        return panel1;
    }

    // EFFECTS: creates and initializes the conField
    public JComponent setConField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner con = new JSpinner(new SpinnerNumberModel());
        conField = getTextField(con);
        conField.setColumns(3);
        conField.addActionListener(this);
        conField.addFocusListener(this);
        panel1.add(new JLabel("Constitution: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(con);
        return panel1;
    }

    // EFFECTS: creates and initializes the intField
    public JComponent setIntField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner intel = new JSpinner(new SpinnerNumberModel());
        intField = getTextField(intel);
        intField.setColumns(3);
        intField.addActionListener(this);
        intField.addFocusListener(this);
        panel1.add(new JLabel("Intelligence: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(intel);
        return panel1;
    }

    // EFFECTS: creates and initializes the wisField
    public JComponent setWisField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner wis = new JSpinner(new SpinnerNumberModel());
        wisField = getTextField(wis);
        wisField.setColumns(3);
        wisField.addActionListener(this);
        wisField.addFocusListener(this);
        panel1.add(new JLabel("Wisdom: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(wis);
        return panel1;
    }

    // EFFECTS: creates and initializes the chaField
    public JComponent setChaField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner cha = new JSpinner(new SpinnerNumberModel());
        chaField = getTextField(cha);
        chaField.setColumns(3);
        chaField.addActionListener(this);
        chaField.addFocusListener(this);
        panel1.add(new JLabel("Charisma: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(cha);
        return panel1;
    }

    // EFFECTS: creates and initializes the levelField
    public JComponent setLevelField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner level = new JSpinner(new SpinnerNumberModel());
        levelField = getTextField(level);
        levelField.setColumns(3);
        levelField.addActionListener(this);
        levelField.addFocusListener(this);
        panel1.add(new JLabel("Level: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(level);
        return panel1;
    }

    // EFFECTS: creates and initializes the armorField
    public JComponent setArmorField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner armor = new JSpinner(new SpinnerNumberModel());
        armorField = getTextField(armor);
        armorField.setColumns(3);
        armorField.addActionListener(this);
        armorField.addFocusListener(this);
        panel1.add(new JLabel("Armor Class: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(armor);
        return panel1;
    }

    // EFFECTS: creates and initializes the hpField
    public JComponent setHpField() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JSpinner hp = new JSpinner(new SpinnerNumberModel());
        hpField = getTextField(hp);
        hpField.setColumns(3);
        hpField.addActionListener(this);
        hpField.addFocusListener(this);
        panel1.add(new JLabel("Health: "));
        panel1.setPreferredSize(new Dimension(100, 10));
        panel1.add(hp);
        return panel1;
    }

    // EFFECTS: returns the text field associated with a given JSpinner
    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        return ((JSpinner.DefaultEditor)editor).getTextField();
    }
}
