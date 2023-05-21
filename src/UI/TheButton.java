package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TheButton extends JLabel implements MouseListener {

    ActionListener myAL;

    public TheButton(String str) {
        super(str);
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get("resources/fonts/pixeloid_mono.ttf"))).deriveFont(30f);
            this.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        this.setForeground(Color.yellow);
        this.setOpaque(false);
        this.addMouseListener(this);
    }

    public void addActionListener(ActionListener al) {
        myAL = al;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        myAL.actionPerformed(new ActionEvent(this, 501, ""));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(new Color(243, 105, 66));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(Color.yellow);
    }
}
