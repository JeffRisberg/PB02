package com.incra.tutorial;

import com.incra.tutorial.LeaderboardProtos.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

class DisplayLeaderboard {
  // Iterates though all people in the AddressBook and prints info about them.
  static void Print(Leaderboard leaderboard) {

    for (PlayerScore playerScore: leaderboard.getPlayerScoreList()) {
      Player player = playerScore.getPlayer();
      System.out.println("Player ID: " + player.getId());
      System.out.println("Score: " + playerScore.getScore());
    }
  }

  // Main function:  Reads the entire leaderboard from a file and prints all
  //   the information inside.
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage:  DisplayLeaderboard FILE");
      System.exit(-1);
    }

    // Read the existing leaderboard.
    Leaderboard leaderboard =
            Leaderboard.parseFrom(new FileInputStream(args[0]));

    Print(leaderboard);
  }
}