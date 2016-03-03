package com.github.reline.javaassembler;

import java.util.HashMap;
import java.util.Map;

public class Registers {

    static Map<String, Integer> REGISTERS = new HashMap<>();

    public static void initialize() {

        REGISTERS.put("EAX", 0);
        REGISTERS.put("EBX", 0);
        REGISTERS.put("ECX", 0);
        REGISTERS.put("EDX", 0);

        REGISTERS.put("AX", 0);
        REGISTERS.put("BX", 0);
        REGISTERS.put("CX", 0);
        REGISTERS.put("DX", 0);

        REGISTERS.put("AH", 0);
        REGISTERS.put("BH", 0);
        REGISTERS.put("CH", 0);
        REGISTERS.put("DH", 0);

        REGISTERS.put("AL", 0);
        REGISTERS.put("BL", 0);
        REGISTERS.put("CL", 0);
        REGISTERS.put("DL", 0);

        REGISTERS.put("DF", 0); // Direction; 1 = string op's process down from high to low address
        REGISTERS.put("IF", 0); // Interrupt; whether interrupts can occur. 1 = enabled
        REGISTERS.put("TF", 0); // Trap; single step debugging

        REGISTERS.put("SF", 0); // Sign; sign of result. Reasonable for Integer only. 1 = neg. / 0 = pos
        REGISTERS.put("ZF", 0); //Zero; result of operation is zero. 1 = zero
        REGISTERS.put("AF", 0); // Aux. carry; similar to Carry but restricted to the low nibble only
        REGISTERS.put("PF", 0); // Parity; 1 = result has even number of set bits
        REGISTERS.put("CF", 0); // Carry; result of unsigned op. is too large or below zero. 1 = carry/borrow
        REGISTERS.put("OF", 0); // Overflow; result of signed op. is too large or small. 1 = carry/underflow
    }
}
