package com.incra.tutorial;

import com.incra.tutorial.CommonProtos.*;
import com.incra.tutorial.TrackingProtos.*;

import java.io.*;

/**
 * @author Jeff Risberg
 * @since 01/07/16
 */
class CreateTracking {

    // Main function:  Reads the entire tracking from a file, updates, then writes it back
    // out to the same file.
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:  CreateTracking TRACKING_FILE");
            System.exit(-1);
        }

        Tracking.Builder tracking = Tracking.newBuilder();

        // Read the existing tracking.
        try {
            FileInputStream input = new FileInputStream(args[0]);
            try {
                tracking.mergeFrom(input);
            } finally {
                try {
                    input.close();
                } catch (Throwable ignore) {
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }

        Activity activity = Activity.newBuilder().setId(4).setName("Hiking").setUom("Miles").build();

        Player.Builder player = Player.newBuilder();
        player.setId(6);
        player.setName("Jeff Risberg");

        tracking.setActivity(activity);
        tracking.setPlayer(player.build());
        tracking.setAmount(245);
        tracking.setTrackingDate(System.currentTimeMillis());

        // Write the new tracking back to disk.
        FileOutputStream output = new FileOutputStream(args[0]);
        try {
            tracking.build().writeTo(output);
        } finally {
            output.close();
        }
    }
}
