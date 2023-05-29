/*
    Name: Group 15 from NH4-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: A class for power-up food, extending normal Food
*/

package Entities;

public class PowerUpFood extends Food {
    private final int type;

    public PowerUpFood(int x, int y, int type) {
        super(x, y);
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
