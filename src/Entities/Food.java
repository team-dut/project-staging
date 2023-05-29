package Entities;

import java.awt.*;

public class Food {
    private final Point position;

    public Food(int x, int y) {
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }
}
