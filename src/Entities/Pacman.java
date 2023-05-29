package Entities;

import Core.GameMessage;
import Core.PacBoard;
import Enums.MoveType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pacman implements KeyListener {
    private final PacBoard board;
    private final Image[] pac = {
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac0.png"))),
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac1.png"))),
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac2.png"))),
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac3.png"))),
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac4.png")))
    };
    private final Point pixelPosition;
    private Point logicalPosition;
    private final Timer moveTimer;
    private final Timer animateTimer;
    private MoveType todoMove;
    boolean isStuck = true;
    private int activeImage = 0;
    private int addFactor = 1;
    private MoveType activeMove;

    public Pacman(int x, int y, PacBoard pb) throws IOException {
        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        board = pb;

        activeMove = MoveType.NONE;
        todoMove = MoveType.NONE;

        //animation timer
        ActionListener animAL = evt -> {
            activeImage = activeImage + addFactor;
            if (activeImage == 4 || activeImage == 0) {
                addFactor *= -1;
            }
        };
        animateTimer = new Timer(40, animAL);
        animateTimer.start();

        //update logical position
        //send update message
        ActionListener moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //update logical position
                if ((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)) {
                    if (!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++;
                                break;
                            case LEFT:
                                logicalPosition.x--;
                                break;
                            case UP:
                                logicalPosition.y--;
                                break;
                            case DOWN:
                                logicalPosition.y++;
                                break;
                        }
                        //send update message
                        board.dispatchEvent(new ActionEvent(this, GameMessage.UPDATE, null));
                    }
                    isStuck = true;
                    animateTimer.stop();

                    if (todoMove != MoveType.NONE && isPossibleMove(todoMove)) {
                        activeMove = todoMove;
                        todoMove = MoveType.NONE;
                    }
                } else {
                    isStuck = false;
                    animateTimer.start();
                }

                switch (activeMove) {
                    case RIGHT:
                        if ((pixelPosition.x >= (board.getMaxX() - 1) * 28) && board.getCustom()) {
                            return;
                        }

                        if (
                                getLogicalPosition().x >= 0 &&
                                        getLogicalPosition().x < board.getMaxX() - 1 &&
                                        getLogicalPosition().y >= 0 &&
                                        getLogicalPosition().y < board.getMaxY() - 1
                        ) {
                            if (board.getMap()[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT:
                        if ((pixelPosition.x <= 0) && board.getCustom()) {
                            return;
                        }

                        if (
                                logicalPosition.x > 0 &&
                                        logicalPosition.x < board.getMaxX() - 1 &&
                                        logicalPosition.y >= 0 &&
                                        logicalPosition.y < board.getMaxY() - 1) {
                            if (board.getMap()[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP:
                        if ((pixelPosition.y <= 0) && board.getCustom()) {
                            return;
                        }

                        if (
                                logicalPosition.x >= 0 &&
                                        logicalPosition.x < board.getMaxX() - 1 &&
                                        logicalPosition.y >= 0 &&
                                        logicalPosition.y < board.getMaxY() - 1
                        ) {
                            if (board.getMap()[logicalPosition.x][logicalPosition.y - 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if ((pixelPosition.y >= (board.getMaxY() - 1) * 28) && board.getCustom()) {
                            return;
                        }

                        if (
                                logicalPosition.x >= 0 &&
                                        logicalPosition.x < board.getMaxX() - 1 &&
                                        logicalPosition.y >= 0 &&
                                        logicalPosition.y < board.getMaxY() - 1) {
                            if (board.getMap()[logicalPosition.x][logicalPosition.y + 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y++;
                        break;
                }

                board.dispatchEvent(new ActionEvent(this, GameMessage.COLLISION_TEST, null));

            }
        };
        moveTimer = new Timer(9, moveAL);
        moveTimer.start();
    }

    public Timer getMoveTimer() {
        return moveTimer;
    }

    public Timer getAnimateTimer() {
        return animateTimer;
    }

    public Point getPixelPosition() {
        return pixelPosition;
    }

    public MoveType getActiveMove() {
        return activeMove;
    }

    public boolean isPossibleMove(MoveType move) {
        Point logicalPosition = getLogicalPosition();
        PacBoard board = getBoard();

        int x_pos = (int) logicalPosition.getX(), y_pos = (int) logicalPosition.getY();

        if (
                x_pos >= 0
                        && x_pos < board.getMaxX() - 1
                        && y_pos >= 0
                        && y_pos < board.getMaxY() - 1
        ) {
            switch (move) {
                case RIGHT:
                    return !(board.getMap()[x_pos + 1][y_pos] > 0);
                case LEFT:
                    return !(board.getMap()[x_pos - 1][y_pos] > 0);
                case UP:
                    return !(board.getMap()[x_pos][y_pos - 1] > 0);
                case DOWN:
                    return !(board.getMap()[x_pos][y_pos + 1] > 0);
            }
        }

        return false;
    }

    public PacBoard getBoard() {
        return board;
    }

    public Image[] getPac() {
        return pac;
    }

    public int getActiveImage() {
        return activeImage;
    }

    public Image getPacmanImage() {
        return getPac()[getActiveImage()];
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 37:
            case 65:
                setTodoMove(MoveType.LEFT);
                break;
            case 38:
            case 87:
                setTodoMove(MoveType.UP);
                break;
            case 39:
            case 68:
                setTodoMove(MoveType.RIGHT);
                break;
            case 40:
            case 83:
                setTodoMove(MoveType.DOWN);
                break;
            case 81:
                if (!getBoard().getCustom())
                    getBoard().getWindowParent().dispatchEvent(
                            new WindowEvent(
                                    getBoard().getWindowParent(),
                                    WindowEvent.WINDOW_CLOSING
                            )
                    );
                break;
            case 82:
                if (!getBoard().getCustom())
                    try {
                        getBoard().restart();
                    } catch (IOException | FontFormatException e) {
                        throw new RuntimeException(e);
                    }
                break;
        }
    }

    public MoveType getTodoMove() {
        return todoMove;
    }

    public void setTodoMove(MoveType todoMove) {
        this.todoMove = todoMove;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    public Point getLogicalPosition() {
        return logicalPosition;
    }

    public void setLogicalPosition(Point logicalPosition) {
        this.logicalPosition = logicalPosition;
    }
}
