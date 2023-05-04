package Entities;

import java.awt.*;

public class PowerUpFood {
    public Point position;
    public int type;

    public PowerUpFood(int x, int y, int type) {
        position = new Point(x, y);
        this.type = type;
    }
}
