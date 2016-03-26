package com.incra.tutorial;

import com.incra.tutorial.CommonProtos.*;
import com.incra.tutorial.LeaderboardProtos.*;
import com.incra.tutorial.OperationProtos.*;

import java.io.*;

/**
 * @author Jeff Risberg
 * @since 01/07/16
 */
class CreateOperation {

    // Main function:  Reads the entire operation from a file, updates, then writes it back
    // out to the same file.
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage:  CreateOperation OPERATION_FILE");
            System.exit(-1);
        }

        Operation.Builder operation = Operation.newBuilder();

        // Read the existing operation.
        try {
            FileInputStream input = new FileInputStream(args[0]);
            try {
                operation.mergeFrom(input);
            } finally {
                try {
                    input.close();
                } catch (Throwable ignore) {
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }

        // Make changes here

        // Write the new operation back to disk.
        FileOutputStream output = new FileOutputStream(args[0]);
        try {
            operation.build().writeTo(output);
        } finally {
            output.close();
        }
    }
}
