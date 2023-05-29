/*
    Name: Group 15 from NH4-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: A class for ghost metadata
*/

package Core;

import Enums.GhostColor;

public class GhostData {
    private final GhostColor color;
    private int x;
    private int y;

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

    public GhostColor getColor() {
        return this.color;
    }
}