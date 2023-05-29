package ABC;

import Core.BFSFinder;
import Core.GameMessage;
import Core.PacBoard;
import Enums.MoveType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;


public abstract class BaseGhost {
    protected final int ghostWeakDelay = 30;
    private final Point pixelPosition;
    private final Point logicalPosition;
    private final Timer animationTimer;
    private final Timer moveTimer;
    private final Image[] ghostW = {
            ImageIO.read(Files.newInputStream(Paths.get(("resources/images/ghost/blue/1.png")))),
            ImageIO.read(Files.newInputStream(Paths.get(("resources/images/ghost/blue/3.png")))),
    };
    private final Image[] ghostWW = {
            ImageIO.read(Files.newInputStream(Paths.get(("resources/images/ghost/white/1.png")))),
            ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/white/3.png")))
    };
    private final Image ghostEye = ImageIO.read(Files.newInputStream(Paths.get("resources/images/eye.png")));
    protected boolean isStuck = true;
    protected boolean isPending = false;
    protected boolean isWeak = false;
    protected boolean isDead = false;
    protected Image[] ghostR;
    protected Image[] ghostL;
    protected Image[] ghostU;
    protected Image[] ghostD;
    protected BFSFinder baseReturner;
    protected PacBoard board;
    protected int ghostNormalDelay;
    private Timer pendingTimer;
    private Timer unWeakenTimer1;
    private Timer unWeakenTimer2;
    private int unweakenBlinks;
    private boolean isWhite = false;
    private BFSFinder bfsFinder;
    private MoveType pendingMove = MoveType.UP;
    private MoveType activeMove;
    private int activeImage = 0;

    public BaseGhost(int x, int y, PacBoard pb, int ghostDelay) throws IOException {
        logicalPosition = new Point(x, y);
        pixelPosition = new Point(28 * x, 28 * y);

        board = pb;

        activeMove = MoveType.RIGHT;

        ghostNormalDelay = ghostDelay;

        loadImages();

        ActionListener animationAL = evt -> activeImage = (activeImage + 1) % 2;
        animationTimer = new Timer(100, animationAL);
        animationTimer.start();

        // FIXME : fix ghost movements
        // TODO: cleanup code
        ActionListener moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

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
                        board.dispatchEvent(new ActionEvent(this, GameMessage.UPDATE, null));
                    }


                    activeMove = getMoveAI();
                    isStuck = true;

                } else {
                    isStuck = false;
                }

                // FIXME : fix ghost movements
                // TODO: cleanup code
                switch (activeMove) {
                    case RIGHT:
                        if (pixelPosition.x >= (board.getMaxX() - 1) * 28) {
                            return;
                        }
                        if (
                                (logicalPosition.x + 1 < board.getMaxX()) &&
                                        (board.getMap()[logicalPosition.x + 1][logicalPosition.y] > 0) &&
                                        ((board.getMap()[logicalPosition.x + 1][logicalPosition.y] < 26) || isPending)
                        ) {
                            return;
                        }

                        pixelPosition.x++;
                        break;
                    case LEFT:
                        if (pixelPosition.x <= 0) {
                            return;
                        }
                        if (
                                (logicalPosition.x - 1 >= 0) &&
                                        (board.getMap()[logicalPosition.x - 1][logicalPosition.y] > 0) &&
                                        ((board.getMap()[logicalPosition.x - 1][logicalPosition.y] < 26) || isPending)
                        ) {
                            return;
                        }
                        pixelPosition.x--;
                        break;
                    case UP:
                        if (pixelPosition.y <= 0) {
                            return;
                        }
                        if ((logicalPosition.y - 1 >= 0) &&
                                (board.getMap()[logicalPosition.x][logicalPosition.y - 1] > 0) &&
                                ((board.getMap()[logicalPosition.x][logicalPosition.y - 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if (pixelPosition.y >= (board.getMaxY() - 1) * 28) {
                            return;
                        }
                        if ((logicalPosition.y + 1 < board.getMaxY()) &&
                                (board.getMap()[logicalPosition.x][logicalPosition.y + 1] > 0) &&
                                ((board.getMap()[logicalPosition.x][logicalPosition.y + 1] < 26) || isPending)) {
                            return;
                        }
                        pixelPosition.y++;
                        break;
                }

                board.dispatchEvent(new ActionEvent(this, GameMessage.COLLISION_TEST, null));
            }
        };

        moveTimer = new Timer(ghostDelay, moveAL);
        moveTimer.start();

        ActionListener unweak1 = e -> {
            unWeakenTimer2.start();
            unWeakenTimer1.stop();
        };
        unWeakenTimer1 = new Timer(7000, unweak1);

        ActionListener unweak2 = e -> {
            if (unweakenBlinks == 10) {
                recoverFromWeakState();
                unWeakenTimer2.stop();
            }
            isWhite = unweakenBlinks % 2 == 0;
            unweakenBlinks++;
        };
        unWeakenTimer2 = new Timer(250, unweak2);

        ActionListener pendingAL = e -> {
            isPending = false;
            pendingTimer.stop();
        };

        pendingTimer = new Timer(7000, pendingAL);

        baseReturner = new BFSFinder(pb);

        activeMove = getMoveAI();
    }

    public Timer getAnimationTimer() {
        return animationTimer;
    }

    public abstract void loadImages();

    public MoveType getMoveAI() {
        if (isPending()) {
            if (isStuck()) {
                if (getPendingMove() == MoveType.UP) {
                    setPendingMove(MoveType.DOWN);
                } else if (getPendingMove() == MoveType.DOWN) {
                    setPendingMove(MoveType.UP);
                }
                return getPendingMove();
            } else {
                return getPendingMove();
            }
        }

        if (getBfsFinder() == null) setBfsFinder(new BFSFinder(getBoard()));

        if (getDead()) {
            return getBaseReturner().getMove(
                    (int) getLogicalPosition().getX(),
                    (int) getLogicalPosition().getY(),
                    (int) getBoard().getGhostBase().getX(),
                    (int) getBoard().getGhostBase().getY()
            );
        } else {
            return getBfsFinder()
                    .getMove(
                            (int) getLogicalPosition().getX(),
                            (int) getLogicalPosition().getY(),
                            (int) getBoard().getPacman().getLogicalPosition().getX(),
                            (int) getBoard().getPacman().getLogicalPosition().getY()
                    );
        }
    }

    public Image getGhostImage() {
        int activeImage = getActiveImage();
        Image[] ghostL = getGhostL(),
                ghostR = getGhostR(),
                ghostU = getGhostU(),
                ghostD = getGhostD(),
                ghostW = getGhostW(),
                ghostWW = getGhostWW();

        if (!getDead()) {
            if (!getWeak()) {
                switch (getActiveMove()) {
                    case RIGHT:
                        return ghostR[activeImage];
                    case LEFT:
                        return ghostL[activeImage];
                    case UP:
                        return ghostU[activeImage];
                    case DOWN:
                        return ghostD[activeImage];
                }
                return ghostR[activeImage];
            } else {
                if (isWhite()) {
                    return ghostWW[activeImage];
                } else {
                    return ghostW[activeImage];
                }
            }
        } else {
            return getGhostEye();
        }
    }

    public void weaken() {
        setWeak(true);
        getMoveTimer().setDelay(getGhostWeakDelay());
        setUnweakenBlinks(0);
        setWhite(false);

        if (getUnWeakenTimer1().isRunning()) {
            getUnWeakenTimer2().stop();
            getUnWeakenTimer1().restart();
            System.out.println("Eating another, restarting!");
        } else {
            getUnWeakenTimer1().start();
        }
    }

    public Timer getUnWeakenTimer2() {
        return unWeakenTimer2;
    }

    public Timer getUnWeakenTimer1() {
        return unWeakenTimer1;
    }

    public Image getGhostEye() {
        return ghostEye;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public Image[] getGhostL() {
        return ghostL;
    }

    public Image[] getGhostR() {
        return ghostR;
    }

    public Image[] getGhostU() {
        return ghostU;
    }

    public Image[] getGhostD() {
        return ghostD;
    }

    public Image[] getGhostW() {
        return ghostW;
    }

    public Image[] getGhostWW() {
        return ghostWW;
    }

    public int getActiveImage() {
        return activeImage;
    }

    public MoveType getActiveMove() {
        return activeMove;
    }

    public void setUnweakenBlinks(int unweakenBlinks) {
        this.unweakenBlinks = unweakenBlinks;
    }

    public int getGhostWeakDelay() {
        return ghostWeakDelay;
    }

    public void recoverFromWeakState() {
        setWeak(false);
        getMoveTimer().setDelay(getGhostNormalDelay());
    }

    public void die() {
        setDead(true);
        getMoveTimer().setDelay(getGhostNormalDelay());
    }

    public void revive() {
        int r = ThreadLocalRandom.current().nextInt(3);

        if (r == 1) {
            getLogicalPosition().translate(1, 0);
            getPixelPosition().translate(28, 0);
        }
        if (r == 2) {
            getLogicalPosition().translate(-1, 0);
            getPixelPosition().translate(-28, 0);
        }

        setPending(true);
        getPendingTimer().start();

        setDead(false);
        setWeak(false);

        getMoveTimer().setDelay(getGhostNormalDelay());
    }

    public Point getLogicalPosition() {
        return logicalPosition;
    }

    public Point getPixelPosition() {
        return pixelPosition;
    }

    public boolean getDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean getWeak() {
        return isWeak;
    }

    public void setWeak(boolean weak) {
        isWeak = weak;
    }

    public Timer getPendingTimer() {
        return pendingTimer;
    }

    public Timer getMoveTimer() {
        return moveTimer;
    }

    public int getGhostNormalDelay() {
        return ghostNormalDelay;
    }

    public BFSFinder getBfsFinder() {
        return bfsFinder;
    }

    public void setBfsFinder(BFSFinder bfsFinder) {
        this.bfsFinder = bfsFinder;
    }

    public PacBoard getBoard() {
        return board;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public boolean isStuck() {
        return isStuck;
    }

    public MoveType getPendingMove() {
        return pendingMove;
    }

    public void setPendingMove(MoveType pendingMove) {
        this.pendingMove = pendingMove;
    }

    public BFSFinder getBaseReturner() {
        return baseReturner;
    }
}
