package com.incra.tutorial;

import com.incra.tutorial.CommonProtos.*;
import com.incra.tutorial.LeaderboardProtos.*;

import java.io.FileInputStream;

/**
 * @author Jeff Risberg
 * @since 01/04/16
 */
class DisplayLeaderboard {
    // Iterates though all people in the Leaderboard and prints info about them.
    static void print(Leaderboard leaderboard) {

        for (PlayerScore playerScore : leaderboard.getPlayerScoreList()) {
            Player player = playerScore.getPlayer();
            System.out.println("\nPlayer ID: " + player.getId());
            System.out.println("Name: " + player.getName());
            System.out.println("Score: " + playerScore.getScore());
        }
    }

    // Main function:  Reads the entire leaderboard from a file and prints all
    //   the information inside.
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:  DisplayLeaderboard LEADERBOARD_FILE");
            System.exit(-1);
        }

        // Read the existing leaderboard.
        Leaderboard leaderboard =
                Leaderboard.parseFrom(new FileInputStream(args[0]));

        Game game = leaderboard.getGame();
        System.out.println("For the " + game.getName() + " game:");

        print(leaderboard);
    }
}