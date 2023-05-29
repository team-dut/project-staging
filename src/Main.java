/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Main class to run the game.
*/

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
