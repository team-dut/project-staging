package UI;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JComponent {
    private final Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
