package UI;

import Core.GhostData;
import Core.MapData;
import Entities.Food;
import Entities.PowerUpFood;
import Enums.GhostColor;
import Helpers.StringHelper;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class MapEditor extends JFrame {
    private static MapEditor mapEditor;

    private MapEditor() {
    }

    public static MapEditor getInstance() {
        if (mapEditor == null) mapEditor = new MapEditor();

        return mapEditor;
    }

    public void pop() {
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.black);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());
        sideBar.setBackground(Color.black);

        JPanel ghostSelection = new JPanel();
        ghostSelection.setLayout(new BoxLayout(ghostSelection, BoxLayout.Y_AXIS));
        ghostSelection.setBackground(Color.black);

        JLabel l0 = new JLabel("= : Blank Space (without Food)");
        JLabel l1 = new JLabel("_ : Blank Space (with Food)");
        JLabel l2 = new JLabel("X : Wall");
        JLabel l3 = new JLabel("Y : Semi-Wall (Passable by Ghosts)");
        JLabel l4 = new JLabel("P : Pacman Start Position");
        JLabel l5 = new JLabel("1 : Red Ghost (Chaser)");
        JLabel l6 = new JLabel("2 : Pink Ghost (Traveler)");
        JLabel l7 = new JLabel("3 : Cyan Ghost (Patrol)");
        JLabel l8 = new JLabel("F : Fruit");
        JLabel l9 = new JLabel("B : Ghost Base");

        l0.setForeground(Color.yellow);
        l1.setForeground(Color.yellow);
        l2.setForeground(Color.yellow);
        l3.setForeground(Color.yellow);
        l4.setForeground(Color.yellow);
        l5.setForeground(Color.yellow);
        l6.setForeground(Color.yellow);
        l7.setForeground(Color.yellow);
        l8.setForeground(Color.yellow);
        l9.setForeground(Color.yellow);

        ghostSelection.add(l0);
        ghostSelection.add(l1);
        ghostSelection.add(l2);
        ghostSelection.add(l3);
        ghostSelection.add(l4);
        ghostSelection.add(l5);
        ghostSelection.add(l6);
        ghostSelection.add(l7);
        ghostSelection.add(l8);
        ghostSelection.add(l9);

        setLayout(new BorderLayout());
        sideBar.add(ghostSelection, BorderLayout.NORTH);
        getContentPane().add(sideBar, BorderLayout.EAST);

        JTextArea ta = new JTextArea();

        ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        ta.setBackground(Color.black);
        ta.setForeground(Color.yellow);
        ta.setCaretColor(Color.white);

        ta.setText(
                "XXXXXXXXXX\n" +
                        "XP_______X\n" +
                        "X________X\n" +
                        "X________X\n" +
                        "XXXXXXXXXX"
        );

        ta.setBorder(new CompoundBorder(
                        new CompoundBorder(
                                new EmptyBorder(20, 10, 20, 10),
                                new LineBorder(Color.yellow)
                        ),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        getContentPane().add(ta);

        TheButton startButton = new TheButton("Test!");
        startButton.addActionListener(e -> {
            try {
                PacWindow.getInstance().loadFromMap(compileMap(ta.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        TheButton openFileButton = new TheButton("Open file");
        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File selectedFile = fileChooser.getSelectedFile();

            try {
                String content = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())), StandardCharsets.UTF_8);
                ta.setText(content);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        TheButton saveToFileButton = new TheButton("Save to file");
        saveToFileButton.addActionListener(e -> {
            try {
                String content = ta.getText();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null);
                File selectedFile = fileChooser.getSelectedFile();

                FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());
                BufferedWriter out = new BufferedWriter(fw);
                out.write(content);
                out.close();
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel buttons = new JPanel();

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setBackground(Color.black);

        buttons.add(openFileButton);
        buttons.add(saveToFileButton);
        buttons.add(startButton);

        getContentPane().add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static MapData compileMap(String input) {
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);
        System.out.println("Making Map " + mx + "x" + my);

        MapData customMap = new MapData(mx, my);
        customMap.setCustom(true);
        int[][] map = new int[mx][my];

        int i = 0;
        int j = 0;
        for (char c : input.toCharArray()) {
            if (c == '1') {
                map[i][j] = 0;
                customMap.getGhosts().add(new GhostData(i, j, GhostColor.RED));
            }
            if (c == '2') {
                map[i][j] = 0;
                customMap.getGhosts().add(new GhostData(i, j, GhostColor.PINK));
            }
            if (c == '3') {
                map[i][j] = 0;
                customMap.getGhosts().add(new GhostData(i, j, GhostColor.CYAN));
            }
            if (c == 'P') {
                map[i][j] = 0;
                customMap.setPacmanPosition(new Point(i, j));
            }
            if (c == 'X') {
                map[i][j] = 23;
            }
            if (c == 'Y') {
                map[i][j] = 26;
            }
            if (c == '_') {
                map[i][j] = 0;
                customMap.getFoods().add(new Food(i, j));
            }
            if (c == '=') {
                map[i][j] = 0;
            }
            if (c == 'O') {
                map[i][j] = 0;
                customMap.getPowerUpFoods().add(new PowerUpFood(i, j, 0));
            }
            if (c == 'F') {
                map[i][j] = 0;
                customMap.getPowerUpFoods().add(new PowerUpFood(i, j, ThreadLocalRandom.current().nextInt(4) + 1));
            }
            if (c == 'B') {
                map[i][j] = 0;
                customMap.setGhostBasePosition(new Point(i, j));
            }
            i++;
            if (c == '\n') {
                j++;
                i = 0;
            }
        }

        customMap.setMap(map);
        customMap.setCustom(true);
        return customMap;
    }

    @Override
    public void dispose() {
        try {
            StartWindow.getInstance().pop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.dispose();
    }
}
