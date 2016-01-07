package com.incra.tutorial;

import com.incra.tutorial.LeaderboardProtos.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Jeff Risberg
 * @since 01/04/16
 */
class AddPlayerScore {
    static PlayerScore PromptForPlayerScore(BufferedReader stdin,
                                            PrintStream stdout) throws IOException {
        Player.Builder player = Player.newBuilder();

        stdout.print("Enter person ID: ");
        player.setId(Integer.valueOf(stdin.readLine()));

        stdout.print("Enter name: ");
        player.setName(stdin.readLine());

        PlayerScore.Builder playerScore = PlayerScore.newBuilder();
        playerScore.setPlayer(player);

        stdout.print("Enter score: ");
        playerScore.setScore(0);

        String scoreStr = stdin.readLine();
        if (scoreStr.length() > 0) {
            playerScore.setScore(Integer.parseInt(scoreStr));
        }

        return playerScore.build();
    }

    // Main function:  Reads the entire leaderboard from a file, adds one playerscore based on
    // user input, then writes it back out to the same file.
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:  AddPlayerScore LEADERBOARD_FILE");
            System.exit(-1);
        }

        Leaderboard.Builder leaderboard = Leaderboard.newBuilder();

        // Read the existing leaderboard.
        try {
            FileInputStream input = new FileInputStream(args[0]);
            try {
                leaderboard.mergeFrom(input);
            } finally {
                try {
                    input.close();
                } catch (Throwable ignore) {
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }

        if (leaderboard.hasGame() == false) {
            Game.Builder game = Game.newBuilder();
            game.setId(1);
            game.setName("Africa Swing");

            leaderboard.setGame(game);
        }

        // Add a playerScore.
        leaderboard.addPlayerScore(
                PromptForPlayerScore(new BufferedReader(new InputStreamReader(System.in)),
                        System.out));

        // Write the new leaderboard back to disk.
        FileOutputStream output = new FileOutputStream(args[0]);
        try {
            leaderboard.build().writeTo(output);
        } finally {
            output.close();
        }
    }
}
