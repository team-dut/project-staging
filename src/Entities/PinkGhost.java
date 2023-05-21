package Entities;

import ABC.BaseGhost;
import Core.PacBoard;
import Enums.MoveType;
import Helpers.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class PinkGhost extends BaseGhost {
    public PinkGhost(int x, int y, PacBoard pb) throws IOException {
        super(x, y, pb, 6);
    }

    @Override
    public void loadImages() {
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/1.png")));
            ghostR[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/3.png")));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/1.png"))));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/3.png"))));
            ghostU[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/4.png")));
            ghostU[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/5.png")));
            ghostD[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/6.png")));
            ghostD[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/pink/7.png")));
        } catch (IOException e) {
            System.err.println("Cannot Read Images !");
        }
    }
}
