/*
    Name: Group 15 from NH4-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Containing utilities for image processing
*/

package Helpers;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageHelper {

    public static Image rotate90(Image i) {
        BufferedImage bi = (BufferedImage) i;
        AffineTransform tx = new AffineTransform();
        tx.rotate(0.5 * Math.PI, (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(bi, null);
    }

    public static Image flipHor(Image i) {
        BufferedImage bi = (BufferedImage) i;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-i.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bi, null);
    }

    public static Image flipVer(Image i) {
        BufferedImage bi = (BufferedImage) i;
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -i.getWidth(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(bi, null);
    }
}
