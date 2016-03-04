package com.github.reline.javaassembler;

public class Main {

    public static void main(String[] args) {
        CPU cpu = new CPU();
        cpu.initializeRegistersAndFlags();
        ALU alu = new ALU();
        ControlUnit controller = new ControlUnit();
    }
}
