package Core;

import Enums.MoveType;

import java.awt.*;

public class BFSFinder {
    private final int[][] map;
    private final int mx;
    private final int my;

    public BFSFinder(PacBoard pb) {
        this.mx = pb.getMaxX();
        this.my = pb.getMaxY();
        this.map = new int[pb.getMaxX()][pb.getMaxY()];

        for (int ii = 0; ii < pb.getMaxY(); ii++) {
            for (int jj = 0; jj < pb.getMaxX(); jj++) {
                if (pb.getMap()[jj][ii] > 0 && pb.getMap()[jj][ii] < 26) {
                    map[jj][ii] = 1;
                } else {
                    map[jj][ii] = 0;
                }
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getMaxX() {
        return mx;
    }

    public int getMaxY() {
        return my;
    }

    private boolean isValid(int i, int j, boolean[][] markMat) {
        return (i >= 0 && i < getMaxX() && j >= 0 && j < getMaxY() && getMap()[i][j] == 0 && !markMat[i][j]);
    }

    public MoveType getMove(int x, int y, int tx, int ty) {
        // already marked
        if (x == tx && y == ty) {
            return MoveType.NONE;
        }

        int mx = getMaxX(), my = getMaxY();

        MazeCell[][] mazeCellTable = new MazeCell[mx][my];
        Point[][] parentTable = new Point[mx][my];
        boolean[][] markMat = new boolean[mx][my];

        for (int ii = 0; ii < mx; ii++) {
            for (int jj = 0; jj < my; jj++) {
                markMat[ii][jj] = false;
            }
        }

        MazeCell[] Q = new MazeCell[2000];
        int size = 1;

        MazeCell start = new MazeCell(x, y);
        mazeCellTable[x][y] = start;
        Q[0] = start;
        markMat[x][y] = true;

        for (int k = 0; k < size; k++) {
            int i = Q[k].getX();
            int j = Q[k].getY();

            //RIGHT
            if (isValid(i + 1, j, markMat)) {
                MazeCell m = new MazeCell(i + 1, j);
                mazeCellTable[i + 1][j] = m;
                Q[size] = m;
                size++;
                markMat[i + 1][j] = true;
                parentTable[i + 1][j] = new Point(i, j);
            }

            //LEFT
            if (isValid(i - 1, j, markMat)) {
                MazeCell m = new MazeCell(i - 1, j);
                mazeCellTable[i - 1][j] = m;
                Q[size] = m;
                size++;
                markMat[i - 1][j] = true;
                parentTable[i - 1][j] = new Point(i, j);
            }

            //UP
            if (isValid(i, j - 1, markMat)) {
                MazeCell m = new MazeCell(i, j - 1);
                mazeCellTable[i][j - 1] = m;
                Q[size] = m;
                size++;
                markMat[i][j - 1] = true;
                parentTable[i][j - 1] = new Point(i, j);
            }

            //DOWN
            if (isValid(i, j + 1, markMat)) {
                MazeCell m = new MazeCell(i, j + 1);
                mazeCellTable[i][j + 1] = m;
                Q[size] = m;
                size++;
                markMat[i][j + 1] = true;
                parentTable[i][j + 1] = new Point(i, j);
            }
        }

        int ttx = tx;
        int tty = ty;

        MazeCell t = mazeCellTable[ttx][tty];
        MazeCell tl = null;

        while (t != start) {
            Point tp = parentTable[ttx][tty];
            ttx = (int) tp.getX();
            tty = (int) tp.getY();
            tl = t;
            t = mazeCellTable[ttx][tty];
        }

        assert tl != null;
        int tlx = tl.getX();
        int tly = tl.getY();

        if (x == tlx - 1 && y == tly) {
            return MoveType.RIGHT;
        }
        if (x == tlx + 1 && y == tly) {
            return MoveType.LEFT;
        }
        if (x == tlx && y == tly - 1) {
            return MoveType.DOWN;
        }
        if (x == tlx && y == tly + 1) {
            return MoveType.UP;
        }

        return MoveType.NONE;
    }
}