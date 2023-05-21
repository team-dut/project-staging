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
