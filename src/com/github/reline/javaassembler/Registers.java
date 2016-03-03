package com.github.reline.javaassembler;

public class Registers {

    protected static int EAX = 0;
    protected static int EBX = 0;
    protected static int ECX = 0;
    protected static int EDX = 0;

    protected static boolean DF = false; // Direction; 1 = string op's process down from high to low address
    protected static boolean IF = false; // Interrupt; whether interrupts can occur. 1 = enabled
    protected static boolean TF = false; // Trap; single step debugging

    protected static boolean SF = false; // Sign; sign of result. Reasonable for Integer only. 1 = neg. / 0 = pos
    protected static boolean ZF = false; //Zero; result of operation is zero. 1 = zero
    protected static boolean AF = false; // Aux. carry; similar to Carry but restricted to the low nibble only
    protected static boolean PF = false; // Parity; 1 = result has even number of set bits
    protected static boolean CF = false; // Carry; result of unsigned op. is too large or below zero. 1 = carry/borrow
    protected static boolean OF = false; // Overflow; result of signed op. is too large or small. 1 = carry/underflow
}
