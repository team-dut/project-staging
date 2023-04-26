package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartWindow extends JFrame {

    public StartWindow() {
        setSize(600, 300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(Files.newInputStream(Paths.get("resources/images/pacman_logo.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get("resources/fonts/crackman.ttf"))));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo), BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        buttonsC.setLayout(new BoxLayout(buttonsC, BoxLayout.Y_AXIS));

        TheButton startButton = new TheButton("Start Game");
        TheButton customButton = new TheButton("Custom Game");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            new PacWindow();
            dispose();
        });

        customButton.addActionListener(e -> {
            new MapEditor();
            dispose();
        });

        buttonsC.add(startButton);
        buttonsC.add(customButton);

        getContentPane().add(buttonsC);

        setVisible(true);
    }
}
