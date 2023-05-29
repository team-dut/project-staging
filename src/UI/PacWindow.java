package UI;

import ABC.BaseGhost;
import Core.MapData;
import Core.PacBoard;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class PacWindow extends JFrame {
    private final JLabel timeStat;

    private final PacBoard innerGame;

    public PacWindow(MapData md) throws IOException, FontFormatException {
        if (md == null) {
            // load default map
            md = getMapFromResource("resources/maps/map_default.txt");
            md.setCustom(false);
        }

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(Color.black);

        setSize(1200, 884);
        setLocationRelativeTo(null);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get("resources/fonts/pixeloid_mono.ttf"))).deriveFont(20.5f);

        JLabel scoreboard = new JLabel(md.isCustom() ? "" : "Score: 0");
        scoreboard.setFont(customFont);
        scoreboard.setForeground(new Color(255, 243, 36));

        timeStat = new JLabel("Time: 00:00:00");
        timeStat.setFont(customFont);
        timeStat.setForeground(new Color(255, 243, 36));

        JLabel tooltip = new JLabel(md.isCustom() ? "" : "Q: Quit - R: Restart");
        tooltip.setFont(customFont);
        tooltip.setForeground(new Color(255, 243, 36));

        sidePanel.setAlignmentY(Component.RIGHT_ALIGNMENT);
        sidePanel.setBackground(Color.black);
        sidePanel.add(scoreboard);
        sidePanel.add(timeStat);

        this.innerGame = new PacBoard(scoreboard, md, this);
        this.innerGame.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLUE)));

        addKeyListener(this.innerGame.getPacman());

        this.getContentPane().add(sidePanel, BorderLayout.EAST);
        this.getContentPane().add(this.innerGame);
        this.getContentPane().add(tooltip, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JLabel getTimeStat() {
        return timeStat;
    }

    public PacBoard getInnerGame() {
        return innerGame;
    }

    public MapData getMapFromResource(String relPath) {
        String mapStr = "";
        try {
            Scanner scn = new Scanner(Files.newInputStream(Paths.get(relPath)));
            StringBuilder sb = new StringBuilder();
            String line;
            while (scn.hasNextLine()) {
                line = scn.nextLine();
                sb.append(line).append('\n');
            }
            mapStr = sb.toString();
        } catch (Exception e) {
            System.err.println("Error Reading Map File !");
        }
        if ("".equals(mapStr)) {
            System.err.println("Map is Empty !");
        }
        return MapEditor.compileMap(mapStr);
    }

    @Override
    public void dispose() {
        PacBoard inner = getInnerGame();

        // stop all timers
        inner.getTimeUpdateTimer().stop();
        inner.getRedrawTimer().stop();
        inner.getSiren().stop();
        inner.getPacmanSound().stop();

        for (BaseGhost ghost : inner.getGhosts()) {
            ghost.getMoveTimer().stop();
            ghost.getAnimationTimer().stop();
        }

        super.dispose();
    }
}