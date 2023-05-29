/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: A class for map metadata
*/

package Core;

import Entities.Food;
import Entities.PowerUpFood;

import java.awt.*;
import java.util.ArrayList;

public class MapData {
    private final ArrayList<Food> foods;
    private final ArrayList<PowerUpFood> powerUpFoods;
    private final ArrayList<GhostData> ghosts;
    private int x;
    private int y;
    private int[][] map;
    private Point pacmanPosition;
    private Point ghostBasePosition;
    private boolean isCustom;

    public MapData(int x, int y) {
        this.x = x;
        this.y = y;
        this.foods = new ArrayList<>();
        this.powerUpFoods = new ArrayList<>();
        this.ghosts = new ArrayList<>();
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

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Point pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public Point getGhostBasePosition() {
        return ghostBasePosition;
    }

    public void setGhostBasePosition(Point ghostBasePosition) {
        this.ghostBasePosition = ghostBasePosition;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<PowerUpFood> getPowerUpFoods() {
        return powerUpFoods;
    }

    public ArrayList<GhostData> getGhosts() {
        return ghosts;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
