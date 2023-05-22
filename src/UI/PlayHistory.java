package UI;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayHistory extends JFrame {
    private static PlayHistory playHistory;

    private PlayHistory() {}

    public static PlayHistory getInstance() {
        if (playHistory == null) playHistory = new PlayHistory();

        return playHistory;
    }

    public void pop() throws IOException, FontFormatException {
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel statTitle = new JLabel("History");
        statTitle.setForeground(Color.yellow);
        statTitle.setHorizontalAlignment(SwingConstants.CENTER);
        Path fontPath = Paths.get("resources/fonts/pixeloid_mono.ttf");
        statTitle.setFont(Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(fontPath)).deriveFont(20.5f));

        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(Color.black);
        historyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));

        getContentPane().add(statTitle, BorderLayout.NORTH);

        Path leaderboardFilePath = Paths.get("resources/history.txt");

        try {
            String content = new String(Files.readAllBytes(leaderboardFilePath), StandardCharsets.UTF_8);

            ArrayList<String> rankings = new ArrayList<>(Arrays.asList(content.split("\n")));
            Collections.reverse(rankings);
            List<String> history = rankings.subList(0, Math.min(10, rankings.size()));

            for (String ranking: history) {
                String[] elements = ranking.split(",");

                String date = elements[0];
                String name = elements[1];
                String score = elements[2];
                String duration = elements[3];
                String mapType = elements[4];
                String gameMode = elements[5];
                String gameResult = elements[6];

                JLabel label = new JLabel(String.format(
                        "[%s] -- Name: %s -- Score %s -- Total time: %s -- Map: %s -- Mode: %s -- Result: %s",
                        date,
                        name,
                        score,
                        duration,
                        mapType,
                        gameMode,
                        gameResult
                ));

                label.setForeground(Color.yellow);
                label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

                historyPanel.add(label);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        getContentPane().add(historyPanel);

        setVisible(true);
    }
}

