package com.github.reline.javaassembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControlUnit {

    // int myInt = (myBoolean) ? 1 : 0;

    public static boolean running;

    public ControlUnit() {
        running = true;

        System.out.println("Welcome to the Assembler Emulator!");
        System.out.println("Type 'help' for commands.");

        while(running) {
            System.out.print("$ ");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            try {
                input = br.readLine();
                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                } else if (input.equalsIgnoreCase("help")) {
                    showHelp();
                }
                // todo: instruction checks
                else {
                    System.out.println("'" + input + "' is not a command. See 'help'.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("    ADD [destination], [source]     [destination] := [destination] + [source]");
        System.out.println("    SUB [destination], [source]     [destination] := [destination] - [source]");
        System.out.println("    MUL [value]     [value]=byte: AX := AL*[value]\n" +
                           "                    [value]=word: DX:AX := AX*[value]\n" +
                           "                    [value]=doublew: EDX:EAX := EAX*[value]");
        System.out.println("    DIV [value]     [value]=byte: AL := AX / [value]\n" +
                           "                    [value]=word: AX := DX:AX / [value]\n" +
                           "                    [value]=doublew: EAX := EDX:EAX / [value]");
        System.out.println("    INC [source]    [source] := [source] + 1");
        System.out.println("    DEC [source]    [source] := [source] - 1");
        System.out.println("    CMP [arg1], [arg1]      [arg1] - [arg2]");
        System.out.println("    AND [destination], [source]     [destination] := [destination] " + (char)8744 + " [source]");
        System.out.println("    OR [destination], [source]      [destination] := [destination] " + (char)8743 + " [source]");
        System.out.println("    NOT [destination], [source]     [destination] := [destination] " + (char)172 + " [source]");
        System.out.println("    help    Show this message");
        System.out.println("    exit    Quit the emulator");
    }
}
