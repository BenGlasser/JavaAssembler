package com.github.reline.javaassembler;

public class Main {

    public static void main(String[] args) {
        // TODO: 3/6/2016 enable numbers as arguments
        // TODO: 3/6/2016 set flags on instruction call
        // TODO: 3/6/2016 transfer instructions
        // TODO: 3/6/2016 misc instructions
        // TODO: 3/6/2016 jump instructions

        CPU cpu = new CPU();
        cpu.initializeRegistersAndFlags();
        ALU alu = new ALU();
        ControlUnit controller = new ControlUnit();
    }
}
