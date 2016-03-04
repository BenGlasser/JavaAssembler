package com.github.reline.javaassembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControlUnit {

    // int flag = (myBoolean) ? 1 : 0;

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
                } else if (input.equalsIgnoreCase("DumpRegs")) {
                    DumpRegs();
                } else {
                    parseInstruction(input);
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
        System.out.println("    DumpRegs    Displays registers");
        System.out.println("    help    Show this message");
        System.out.println("    exit    Quit the emulator");
    }

    private void DumpRegs() {
        // TODO: 3/3/2016 order registers and flags
        System.out.println(CPU.REGISTERS);
        System.out.println(CPU.FLAGS);
    }

    private void parseInstruction(String instruction) {
        // POSSIBLE TODO: 3/3/2016 use strict MASM syntax and require commas between args
        instruction = instruction.replaceAll(",", " "); // replace commas with spaces
        instruction = instruction.replaceAll("[ ]{2,}", " "); // replace areas with more than one space with only one space
        String[] instructions = instruction.split(" "); // separate arguments by spaces

        try {
            if (instructions.length == 3) {
                Method method = ALU.class.getMethod(instructions[0].toUpperCase(), String.class, String.class);
                method.invoke(new ALU(), instructions[1], instructions[2]);
            } else if (instructions.length == 2) {
                Method method = ALU.class.getMethod(instructions[0].toUpperCase(), String.class);
                method.invoke(new ALU(), instructions[1]);
            } else {
                System.out.println("There is no such method '" + instructions[0] + "' that takes " + (instructions.length - 1) +
                        " arguments. See 'help'.");
            }
        } catch (SecurityException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("There is no such method '" + instructions[0] + "' that takes " + (instructions.length - 1) +
                    " arguments. See 'help'.");
        }
    }
}
