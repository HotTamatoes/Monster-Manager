package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Developed from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextInputDemoProject/src/
// components/TextInputDemo.java
// Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.

// This class represents the window that the user uses to add a monster
public class AddFrame extends JFrame implements ActionListener, FocusListener {
    AddFramework framework;
    public static final int FRAME_WIDTH = 325;
    public static final int FRAME_HEIGHT = 200;
    protected Dimension defaultSize = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private JPanel panel;

    JTextField levelField;
    JTextField typeField;
    JTextField numberField;
    JSpinner numberSpinner;

    // MODIFIES: This
    // EFFECTS: Creates a new Add Frame
    public AddFrame(AddFramework controller) {
        super("Add a Monster");
        framework = controller;
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,
                BoxLayout.Y_AXIS));
        panel.add(createEntryFields());
        panel.add(createButtons());

        // Image source: https://gilkalai.files.wordpress.com/2017/09/dice.png
        this.setIconImage(new ImageIcon("./data/dice.png").getImage());

        add(panel);

        setSize(defaultSize);
    }

    // EFFECTS: creates the buttons for generating and clearing the text fields
    protected JComponent createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton button = new JButton("Generate");
        button.addActionListener(this);
        buttonPanel.add(button);

        button = new JButton("Clear");
        button.addActionListener(this);
        button.setActionCommand("clear");
        buttonPanel.add(button);

        return buttonPanel;
    }

    /**
     * Called when the user clicks the button or presses
     * Enter in a text field.
     */
    // EFFECTS: if e is "clear", resets the fields.
    //          gives the value in the fields to AddFramework and quits otherwise if possible
    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            levelField.setText("");
            typeField.setText("");
            numberSpinner.setValue(0);
        } else {
            try {
                this.setVisible(false);
                framework.getInfo(Integer.parseInt(levelField.getText()), typeField.getText(),
                        Integer.parseInt(numberField.getText()));
                //System.out.println("Dying!");
                this.dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showInputDialog(null, "Error! Not a number");
            }
        }
    }

    /**
     * Called when one of the fields gets the focus so that
     * we can select the focused field.
     */
    public void focusGained(FocusEvent e) {
        ((JTextField)e.getComponent()).selectAll();
    }

    public void focusLost(FocusEvent e) { } //ignore

    // EFFECTS: creates the entry fields that the user will type into
    protected JComponent createEntryFields() {
        JPanel entryPanel = new JPanel();

        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));

        levelField = new JTextField();
        levelField.setColumns(20);
        levelField.addActionListener(this);
        levelField.addFocusListener(this);
        entryPanel.add(new JLabel("Level: (0 = random)"));
        entryPanel.add(levelField);

        typeField = new JTextField();
        typeField.setColumns(20);
        typeField.addActionListener(this);
        typeField.addFocusListener(this);
        entryPanel.add(new JLabel("Type: (For Single) "));
        entryPanel.add(typeField);

        numberSpinner = new JSpinner(new SpinnerNumberModel());
        numberField = getTextField(numberSpinner);
        numberField.setColumns(20);
        numberField.addActionListener(this);
        numberField.addFocusListener(this);
        entryPanel.add(new JLabel("Number: (For Multiple)"));
        entryPanel.add(numberSpinner);

        return entryPanel;
    }

    // EFFECTS: returns a text field associated with a JSpinner
    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        return ((JSpinner.DefaultEditor)editor).getTextField();
    }
}

