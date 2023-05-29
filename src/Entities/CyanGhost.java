/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Create ghost with cyan color.
*/

package Entities;

import ABC.BaseGhost;
import Core.PacBoard;
import Helpers.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CyanGhost extends BaseGhost {
    public CyanGhost(int x, int y, PacBoard pb) throws IOException {
        super(x, y, pb, 9);
    }

    @Override
    public void loadImages() {
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];

        try {
            ghostR[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/1.png")));
            ghostR[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/3.png")));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(Files.newInputStream(Paths.get(("resources/images/ghost/cyan/1.png")))));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(Files.newInputStream(Paths.get(("resources/images/ghost/cyan/3.png")))));
            ghostU[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/4.png")));
            ghostU[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/5.png")));
            ghostD[0] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/6.png")));
            ghostD[1] = ImageIO.read(Files.newInputStream(Paths.get("resources/images/ghost/cyan/7.png")));
        } catch (IOException e) {
            System.err.println("Cannot Read Images !");
        }
    }
}
