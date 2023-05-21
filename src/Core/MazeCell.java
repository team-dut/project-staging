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