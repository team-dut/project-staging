package UI;

import Background.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartWindow extends JFrame {
    private static StartWindow startWindow;
    private final SoundPlayer titleMusicPlayer;

    private StartWindow() {
        titleMusicPlayer = new SoundPlayer("title_music.wav");
        titleMusicPlayer.start();
    }

    public SoundPlayer getTitleMusicPlayer() {
        return titleMusicPlayer;
    }

    public static StartWindow getInstance() {
        if (startWindow == null) startWindow = new StartWindow();

        return startWindow;
    }

    public void pop() throws IOException {
        setSize(new Dimension(728, 410));
        setContentPane(new JLabel(new ImageIcon("resources/images/banner.jpg")));
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(Files.newInputStream(Paths.get("resources/images/pacman_logo_new.png"))));

            Image image = logo.getImage();
            Image newImg = image.getScaledInstance(500, 121, java.awt.Image.SCALE_SMOOTH);
            logo = new ImageIcon(newImg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo), BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setOpaque(false);
        buttonsC.setBackground(Color.black);
        buttonsC.setLayout(new BoxLayout(buttonsC, BoxLayout.Y_AXIS));

        TheButton startButton = new TheButton("Start Game");
        TheButton customButton = new TheButton("Custom Game");
        TheButton playHistoryButton = new TheButton("Play History");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            try {
                getTitleMusicPlayer().stop();
                new PacWindow(null);
            } catch (IOException | FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        customButton.addActionListener(e -> {
            getTitleMusicPlayer().stop();
            MapEditor.getInstance().pop();
        });

        playHistoryButton.addActionListener(e -> {
            try {
                PlayHistory.getInstance().pop();
            } catch (IOException | FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonsC.add(startButton, BorderLayout.SOUTH);
        buttonsC.add(customButton, BorderLayout.SOUTH);
        buttonsC.add(playHistoryButton, BorderLayout.SOUTH);

        getContentPane().add(buttonsC, BorderLayout.SOUTH);

        setVisible(true);
    }
}
