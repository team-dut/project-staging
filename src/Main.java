import UI.StartWindow;

public class Main {
    public static void main(String[] args) {
        System.err.printf("Current working directory: %s\n", System.getProperty("user.dir"));

        System.setProperty("sun.java2d.opengl", "True");

        new StartWindow();
    }
}
