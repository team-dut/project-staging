package Extensions;

import ABC.BasicExtension;
import Background.DiscordNativeFilesDownloader;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class DiscordExtension extends BasicExtension {
    private static DiscordExtension extension;

    private DiscordExtension() {
    }

    public static DiscordExtension getExtension() {
        if (extension == null) extension = new DiscordExtension();
        return extension;
    }

    @Override
    public void loadExtension() throws IOException {
        if (!getEnabled()) return;

        File discordLibrary = DiscordNativeFilesDownloader.downloadDiscordLibrary();

        if (discordLibrary == null) {
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }

        Core.init(discordLibrary);

        // Set parameters for the Core
        try (CreateParams params = new CreateParams()) {
            params.setClientID(974846248605581314L);
            params.setFlags(CreateParams.getDefaultFlags());

            // Create the Core
            try (Core core = new Core(params)) {
                // Create the Activity
                try (Activity activity = new Activity()) {
                    activity.setDetails("Playing a game");
                    activity.setState("Probably having fun?");

                    activity.timestamps().setStart(Instant.now());

                    core.activityManager().updateActivity(activity);
                }

                // Run callbacks forever
                while (true) {
                    core.runCallbacks();
                    try {
                        System.out.println("This work?");
                        // Sleep a bit to save CPU
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
