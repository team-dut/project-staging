/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: A class for mazecell metadata
*/

package Core;

public class MazeCell {
    private final int x;
    private final int y;
    private boolean isVisited;

    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isVisited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}