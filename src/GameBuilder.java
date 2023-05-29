/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Game builder using Builder design pattern.
*/

import ABC.BasicExtension;
import UI.StartWindow;

import java.io.IOException;
import java.util.ArrayList;

public class GameBuilder {
    private final ArrayList<BasicExtension> extensions;

    public GameBuilder() {
        this.extensions = new ArrayList<>();
    }

    public ArrayList<BasicExtension> getExtensions() {
        return extensions;
    }

    public GameBuilder addExtension(BasicExtension extension) {
        extension.setEnabled(true);
        getExtensions().add(extension);
        return this;
    }

    public void start() throws IOException {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.xrender", "True");

        for (BasicExtension ext : getExtensions()) {
            new Thread(() -> {
                try {
                    ext.loadExtension();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        StartWindow.getInstance().pop();
    }
}
