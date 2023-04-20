package Core;

import Enums.GhostColor;

public class GhostData {
    private int x;
    private int y;
    private GhostColor color;

    public GhostData(int x, int y, GhostColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GhostColor getType() {
        return this.color;
    }

    public void setType(GhostColor color) {
        this.color = color;
    }
}


