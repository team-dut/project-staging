import Extensions.DiscordExtension;

public class Main {
    public static void main(String[] args) {
        System.err.printf("Current working directory: %s\n", System.getProperty("user.dir"));

        new GameBuilder()
                .addExtension(DiscordExtension.getExtension())
                .start();
    }
}
