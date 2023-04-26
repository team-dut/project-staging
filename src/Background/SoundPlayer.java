package Background;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SoundPlayer {
    public static void playAsync(final String name) {
        // The wrapper thread is unnecessary, unless it blocks on the
        // Clip finishing; see comments.
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        new BufferedInputStream(Files.newInputStream(Paths.get("resources/sounds/" + name))));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}