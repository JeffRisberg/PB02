package com.incra.tutorial;

import com.incra.tutorial.LeaderboardProtos.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;

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

    // Main function:  Reads the entire address book from a file, adds one person based on
    // user input, then writes it back out to the same file.
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:  AddPlayerScore ADDRESS_BOOK_FILE");
            System.exit(-1);
        }

        Leaderboard.Builder leaderboard = Leaderboard.newBuilder();

        // Read the existing address book.
        try {
            FileInputStream input = new FileInputStream(args[0]);
            try {
                leaderboard.mergeFrom(input);
            } finally {
                try { input.close(); } catch (Throwable ignore) {}
            }
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }

        // Add a playerscore.
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