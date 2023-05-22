package Extensions;

import ABC.BaseExtension;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class HistoryExtension extends BaseExtension {
    private final boolean loaded;

    private static HistoryExtension extension;

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void loadExtension() {
    }

    public static HistoryExtension getExtension() {
        if (extension == null) extension = new HistoryExtension();

        return extension;
    }

    private HistoryExtension() { this.loaded = true; }

    public void addHistory(String name, Duration duration, String mapType, String mode, String status) {
        // no-op
        if (!isLoaded()) return;

        Path leaderboardFilePath = Paths.get("resources/history.txt");

        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            String formattedDate = myDateObj.format(myFormatObj);

            String content = new String(Files.readAllBytes(leaderboardFilePath), StandardCharsets.UTF_8);

            ArrayList<String> rankings = new ArrayList<>(Arrays.asList(content.split("\n")));

            String formattedElapsedTime = String.format("%02d:%02d:%02d", duration.toHours() % 24,
                    duration.toMinutes() % 60, duration.getSeconds() % 60).trim();

            rankings.add(String.format("%s,%s,%s,%s,%s,%s", formattedDate, name, formattedElapsedTime, mapType, mode, status));

            String contentToWrite = String.join("\n", rankings);

            FileWriter fw = new FileWriter(leaderboardFilePath.toString());
            BufferedWriter out = new BufferedWriter(fw);
            out.write(contentToWrite);
            out.close();
            fw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
