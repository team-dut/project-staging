/*
    Name: Group 15 from NH4-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Class for normal food
*/

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
