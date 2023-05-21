import Extensions.DiscordExtension;
import Extensions.HistoryExtension;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.err.printf("Current working directory: %s\n", System.getProperty("user.dir"));

        new GameBuilder()
                .addExtension(DiscordExtension.getExtension())
                .addExtension(HistoryExtension.getExtension())
                .start();
    }
}
