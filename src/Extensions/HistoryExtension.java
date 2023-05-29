/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: History extension for the game.
*/

package Extensions;

import ABC.BasicExtension;

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
import java.util.stream.Collectors;

public class HistoryExtension extends BasicExtension {
    private static HistoryExtension extension;

    private HistoryExtension() {
    }

    public static HistoryExtension getExtension() {
        if (extension == null) extension = new HistoryExtension();

        return extension;
    }

    @Override
    public void loadExtension() {
    }

    public void addHistory(String name, int score, Duration duration, String mapType, String mode, String status) {
        if (!getEnabled()) return;

        Path leaderboardFilePath = Paths.get("resources/history.txt");

        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            String formattedDate = myDateObj.format(myFormatObj);

            String content = new String(Files.readAllBytes(leaderboardFilePath), StandardCharsets.UTF_8);

            ArrayList<String> rankings = Arrays
                    .stream(content.split("\n"))
                    .filter(x -> x.trim().length() > 0).collect(Collectors.toCollection(ArrayList::new));

            String formattedElapsedTime = String.format("%02d:%02d:%02d", duration.toHours() % 24,
                    duration.toMinutes() % 60, duration.getSeconds() % 60).trim();

            rankings.add(String.format("%s,%s,%d,%s,%s,%s,%s", formattedDate, name, score, formattedElapsedTime, mapType, mode, status));

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
