package ui;

import model.Monster;
import model.NoFavouriteMonstersException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

// Represents the User interface window for the monster list. Most of the application
// functionality starts here
public class MonsterListFrame extends JFrame {
    public static final int FRAME_WIDTH = 420;
    public static final int FRAME_HEIGHT = 720;
    protected Dimension defaultSize = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    protected MonsterListFramework framework;
    protected MonsterGroupInterfaceHelper monsterHelper;
    private ArrayList<Monster> selectedMonsters;

    // MODIFIES: this
    // EFFECTS: Creates a new MonsterListFrame, preparing for user input
    public MonsterListFrame(MonsterListFramework controller) {
        super("Monster Manager");
        framework = controller;
        monsterHelper = new MonsterGroupInterfaceHelper();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        selectedMonsters = new ArrayList<Monster>();

        // Image source: https://gilkalai.files.wordpress.com/2017/09/dice.png
        this.setIconImage(new ImageIcon("./data/dice.png").getImage());
        addWindowListener(framework);

        setUpMenu();

        setUpPanel();
    }

    // MODIFIES: this
    // EFFECTS: adds all the menu options to the menu
    private void setUpMenu() {
        JMenu menu = new JMenu("Window");
        menu.setMnemonic(KeyEvent.VK_W);
        menu.add(allMonstersItem());
        menu.add(favouritesOnlyItem());
        menu.add(saveItem());
        menu.add(saveFavItem());
        menu.add(loadItem());
        menu.add(deleteAllItem());
        menu.add(closeItem());

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JMenu addMenu = new JMenu("Add");
        addMenu.setMnemonic(KeyEvent.VK_ADD);
        menuBar.add(addMenu);
        addMenu.add(addSingle());
        addMenu.add(addMultiple());

        JMenu selectMenu = new JMenu("Selected");
        selectMenu.setMnemonic(KeyEvent.VK_S);
        menuBar.add(selectMenu);
        selectMenu.add(deleteItem());
        selectMenu.add(favouriteItem());

        setSize(defaultSize);
    }

    // MODIFIES: this
    // EFFECTS: initial blank panel is displayed
    private void setUpPanel() {
        JPanel panel = new JPanel();
        this.setContentPane(panel);
    }

    // EFFECTS: creates the close button on the menu
    private JMenuItem closeItem() {
        JMenuItem item = new JMenuItem("Close");
        item.setMnemonic(KeyEvent.VK_C);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MonsterListFrame.this.setVisible(false);
                MonsterListFrame.this.dispose();
            }
        });
        return item;
    }

    // EFFECTS: creates the save button on the menu
    private JMenuItem saveItem() {
        JMenuItem item = new JMenuItem("Save");
        item.setMnemonic(KeyEvent.VK_S);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.saveMonsters();
            }
        });
        return item;
    }

    // EFFECTS: creates the save favourites button on the menu
    private JMenuItem saveFavItem() {
        JMenuItem item = new JMenuItem("Save Favourites");
        item.setMnemonic(KeyEvent.VK_F);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.saveFavouriteMonsters();
            }
        });
        return item;
    }

    // EFFECTS: creates the load button on the menu
    private JMenuItem loadItem() {
        JMenuItem item = new JMenuItem("Load");
        item.setMnemonic(KeyEvent.VK_L);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.loadMonsters();
                displayMonsterList(monsterHelper.getMonsters());
            }
        });
        return item;
    }

    // EFFECTS: creates the favourites only button on the menu (shows only favourite monsters)
    private JMenuItem favouritesOnlyItem() {
        JMenuItem item = new JMenuItem("Favourites Only");
        item.setMnemonic(KeyEvent.VK_L);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    displayMonsterList(monsterHelper.getFavouriteMonsters());
                } catch (NoFavouriteMonstersException nfm) {
                    JPanel emptyPanel = new JPanel();
                    JLabel label = new JLabel();
                    label.setText("No Favourite Monsters!");
                    emptyPanel.add(label);
                    MonsterListFrame.this.setContentPane(emptyPanel);
                    MonsterListFrame.this.pack();
                }
            }
        });
        return item;
    }

    // EFFECTS: creates the all monsters button on the menu (shows all monsters)
    private JMenuItem allMonstersItem() {
        JMenuItem item = new JMenuItem("All Monsters");
        item.setMnemonic(KeyEvent.VK_R);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayMonsterList(monsterHelper.getMonsters());
            }
        });
        return item;
    }

    // EFFECTS: creates the single button on the menu under the add tab
    private JMenuItem addSingle() {
        JMenuItem item = new JMenuItem("Single");
        item.setMnemonic(KeyEvent.VK_A);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFramework(MonsterListFrame.this, false);
            }
        });
        return item;
    }

    // EFFECTS: creates the multiple button on the menu under the add tab
    private JMenuItem addMultiple() {
        JMenuItem item = new JMenuItem("Multiple");
        item.setMnemonic(KeyEvent.VK_M);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFramework(MonsterListFrame.this, true);
            }
        });
        return item;
    }

    // EFFECTS: creates the delete button on the menu under the select tab
    private JMenuItem deleteItem() {
        JMenuItem item = new JMenuItem("Delete");
        item.setMnemonic(KeyEvent.VK_D);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.delete(selectedMonsters);
                selectedMonsters = new ArrayList<Monster>();
                displayMonsterList(monsterHelper.getMonsters());
            }
        });
        return item;
    }

    // EFFECTS: creates the menu item that deletes all the monsters from the Monster Group
    private JMenuItem deleteAllItem() {
        JMenuItem item = new JMenuItem("Delete All");
        item.setMnemonic(KeyEvent.VK_Q);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.deleteAll();
                displayMonsterList(monsterHelper.getMonsters());
            }
        });
        return item;
    }

    // EFFECTS: creates the menu item under the selected tab that toggles if a monster is favourite
    private JMenuItem favouriteItem() {
        JMenuItem item = new JMenuItem("Favourite");
        item.setMnemonic(KeyEvent.VK_F);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monsterHelper.favourite(selectedMonsters);
                displayMonsterList(monsterHelper.getMonsters());
            }
        });
        return item;
    }

    // MODIFIES: this
    // EFFECTS: display all the monsters in the MonsterListFrame as a scrollable panel
    private void displayMonsterList(LinkedList<Monster> lom) {
        JPanel newPanel = new JPanel();
        JPanel monsterPanel = new JPanel();
        monsterPanel.setLayout(new GridLayout(lom.size(), 1));
        for (Monster m : lom) {
            if (m != null) {
                JPanel monsterBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JCheckBox checkBox = new JCheckBox();
                checkBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        selectMonster(m);
                    }
                });
                monsterBox.add(checkBox);
                monsterBox.add(monsterButton(m));
                monsterPanel.add(monsterBox);
            }
        }

        JScrollPane scroll = new JScrollPane(monsterPanel);
        scroll.setPreferredSize(defaultSize);
        newPanel.add(scroll);

        this.setContentPane(newPanel);
        this.pack();
    }

    // MODIFIES: this
    // EFFECTS: creates a button that relates to a monster that can be clicked to open the monster
    // If a monster is a favourite monster, this button will be pink
    private JButton monsterButton(Monster m) {
        JButton but = new JButton(m.toString());
        if (m.isFavourite()) {
            but.setBackground(Color.PINK);
        }
        but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MonsterDisplayFramework(m, MonsterListFrame.this);
            }
        });
        return but;
    }

    // MODIFIES: this
    // EFFECTS: adds new monsters to the MonsterGroup by calling a method in Monster Helper
    public void addMonster(int level, String type, int number, boolean multiple) {
        if (multiple) {
            if (number > 0) {
                monsterHelper.addMultiple(number, level);
            }
        } else {
            monsterHelper.addSingle(level, type);
        }
        displayMonsterList(monsterHelper.getMonsters());
    }

    // MODIFIES: this
    // EFFECTS: updates the monster in monster group to match any user changes while viewing.
    // Called from MonsterDisplayFramework
    public void updateMonster(Monster monster, int str, int dex, int con, int intel, int wis,
                              int cha, int level, int armor, int hp) {
        monster.setStr(str);
        monster.setDex(dex);
        monster.setStr(con);
        monster.setIntel(intel);
        monster.setWis(wis);
        monster.setCha(cha);
        monster.setArmor(armor);
        monster.setHp(hp);
        monster.setLevel(level);
        displayMonsterList(monsterHelper.getMonsters());
    }

    // MODIFIES: this
    // EFFECTS: adds a monster to the selected monster list if unselected
    // otherwise removes a monster from the selected monster list (if selected)
    private void selectMonster(Monster monster) {
        if (selectedMonsters.contains(monster)) {
            selectedMonsters.remove(monster);
        } else {
            selectedMonsters.add(monster);
        }
    }
}
