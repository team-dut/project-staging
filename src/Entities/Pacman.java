package Entities;

import Core.GameMessage;
import Core.PacBoard;
import Enums.MoveType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pacman implements KeyListener {
    private final PacBoard parentBoard;
    public MoveType activeMove;
    public Point pixelPosition;
    public Point logicalPosition;
    //Move Vars
    public Timer moveTimer;
    //Animation Vars
    public Timer animTimer;
    ActionListener moveAL;
    MoveType todoMove;
    boolean isStuck = true;
    ActionListener animAL;
    Image[] pac;
    int activeImage = 0;
    int addFactor = 1;


    public Pacman(int x, int y, PacBoard pb) {

        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        parentBoard = pb;

        pac = new Image[5];

        activeMove = MoveType.NONE;
        todoMove = MoveType.NONE;

        try {
            pac[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac0.png")));
            pac[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac1.png")));
            pac[2] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac2.png")));
            pac[3] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac3.png")));
            pac[4] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/pac/pac4.png")));
        } catch (IOException e) {
            System.err.println("Cannot Read Images !");
        }

        //animation timer
        animAL = evt -> {
            activeImage = activeImage + addFactor;
            if (activeImage == 4 || activeImage == 0) {
                addFactor *= -1;
            }
        };
        animTimer = new Timer(40, animAL);
        animTimer.start();

        moveAL = new ActionListener() {
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
                        parentBoard.dispatchEvent(new ActionEvent(this, GameMessage.UPDATE, null));
                    }
                    isStuck = true;
                    animTimer.stop();

                    if (todoMove != MoveType.NONE && isPossibleMove(todoMove)) {
                        activeMove = todoMove;
                        todoMove = MoveType.NONE;
                    }
                } else {
                    isStuck = false;
                    animTimer.start();
                }

                switch (activeMove) {
                    case RIGHT:
                        if ((pixelPosition.x >= (parentBoard.m_x - 1) * 28) && parentBoard.isCustom) {
                            return;
                        }

                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x - 1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y - 1) {
                            if (parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x++;
                        break;
                    case LEFT:
                        if ((pixelPosition.x <= 0) && parentBoard.isCustom) {
                            return;
                        }

                        if (logicalPosition.x > 0 && logicalPosition.x < parentBoard.m_x - 1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y - 1) {
                            if (parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP:
                        if ((pixelPosition.y <= 0) && parentBoard.isCustom) {
                            return;
                        }

                        if (logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x - 1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y - 1) {
                            if (parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if ((pixelPosition.y >= (parentBoard.m_y - 1) * 28) && parentBoard.isCustom) {
                            return;
                        }

                        if (
                                logicalPosition.x >= 0 &&
                                        logicalPosition.x < parentBoard.m_x - 1 &&
                                        logicalPosition.y >= 0 &&
                                        logicalPosition.y < parentBoard.m_y - 1) {
                            if (parentBoard.map[logicalPosition.x][logicalPosition.y + 1] > 0) {
                                return;
                            }
                        }
                        pixelPosition.y++;
                        break;
                }

                parentBoard.dispatchEvent(new ActionEvent(this, GameMessage.COLTEST, null));

            }
        };
        moveTimer = new Timer(9, moveAL);
        moveTimer.start();
    }

    public boolean isPossibleMove(MoveType move) {
        if (
                logicalPosition.x >= 0
                && logicalPosition.x < parentBoard.m_x - 1
                && logicalPosition.y >= 0
                && logicalPosition.y < parentBoard.m_y - 1
        ) {
            switch (move) {
                case RIGHT:
                    return !(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y + 1] > 0);
            }
        }

        return false;
    }

    public Image getPacmanImage() {
        return pac[activeImage];
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 37:
            case 65:
                todoMove = MoveType.LEFT;
                break;
            case 38:
            case 87:
                todoMove = MoveType.UP;
                break;
            case 39:
            case 68:
                todoMove = MoveType.RIGHT;
                break;
            case 40:
            case 83:
                todoMove = MoveType.DOWN;
                break;
            case 82:
                parentBoard.dispatchEvent(new ActionEvent(this, GameMessage.RESET, null));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {}
}
