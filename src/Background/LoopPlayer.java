package Background;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoopPlayer {
    Clip clip;
    AudioInputStream inputStream;

    public LoopPlayer(String soundname) {
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Files.newInputStream(Paths.get("resources/sounds/" + soundname))));
            clip.open(inputStream);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void start() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void stop() {
        try {
            clip.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
