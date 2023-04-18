package Entities;

import ABC.BaseGhost;
import Core.BFSFinder;
import Core.PacBoard;
import Enums.MoveType;
import Helpers.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RedGhost extends BaseGhost {

    BFSFinder bfs;
    MoveType pendMove = MoveType.UP;

    public RedGhost(int x, int y, PacBoard pb) {
        super(x, y, pb, 12);
    }

    @Override
    public void loadImages() {
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/1.png"));
            ghostR[1] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/3.png"));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/1.png")));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/3.png")));
            ghostU[0] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/4.png"));
            ghostU[1] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/5.png"));
            ghostD[0] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/6.png"));
            ghostD[1] = ImageIO.read(this.getClass().getResource("../resources/images/ghost/red/7.png"));
        } catch (IOException e) {
            System.err.println("Cannot Read Images !");
        }
    }

    //find closest path using BFS
    @Override
    public MoveType getMoveAI() {
        if (isPending) {
            if (isStuck) {
                if (pendMove == MoveType.UP) {
                    pendMove = MoveType.DOWN;
                } else if (pendMove == MoveType.DOWN) {
                    pendMove = MoveType.UP;
                }
                return pendMove;
            } else {
                return pendMove;
            }
        }
        if (bfs == null)
            bfs = new BFSFinder(parentBoard);
        if (isDead) {
            return baseReturner.getMove(logicalPosition.x, logicalPosition.y, parentBoard.ghostBase.x, parentBoard.ghostBase.y);
        } else {
            return bfs.getMove(logicalPosition.x, logicalPosition.y, parentBoard.pacman.logicalPosition.x, parentBoard.pacman.logicalPosition.y);
        }
    }
}
