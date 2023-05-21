import ABC.BaseExtension;
import UI.StartWindow;

import java.io.IOException;
import java.util.ArrayList;

public class GameBuilder {
    private final ArrayList<BaseExtension> extensions;

    public GameBuilder() {
        this.extensions = new ArrayList<>();
    }

    public ArrayList<BaseExtension> getExtensions() {
        return extensions;
    }

    public GameBuilder addExtension(BaseExtension extension) {
        getExtensions().add(extension);
        return this;
    }

    public void start() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.xrender", "True");

        for (BaseExtension ext: getExtensions()) {
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
