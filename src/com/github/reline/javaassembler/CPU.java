package com.github.reline.javaassembler;

import java.util.HashMap;
import java.util.Map;

public class CPU {
    
    static Map<String, String> REGISTERS = new HashMap<>();
    static Map<String, String> FLAGS = new HashMap<>();

    public void initializeRegistersAndFlags() {

        // 32-bit registers
        REGISTERS.put("EAX", "00000000000000000000000000000000");
        REGISTERS.put("EBX", "00000000000000000000000000000000");
        REGISTERS.put("ECX", "00000000000000000000000000000000");
        REGISTERS.put("EDX", "00000000000000000000000000000000");

        // 16-bit registers
        REGISTERS.put("AX", "0000000000000000");
        REGISTERS.put("BX", "0000000000000000");
        REGISTERS.put("CX", "0000000000000000");
        REGISTERS.put("DX", "0000000000000000");

        // upper 8-bit registers
        REGISTERS.put("AH", "00000000");
        REGISTERS.put("BH", "00000000");
        REGISTERS.put("CH", "00000000");
        REGISTERS.put("DH", "00000000");

        // lower 8-bit registers
        REGISTERS.put("AL", "00000000");
        REGISTERS.put("BL", "00000000");
        REGISTERS.put("CL", "00000000");
        REGISTERS.put("DL", "00000000");

        // FLAGS
        FLAGS.put("DF", "0"); // Direction; 1 = string op's process down from high to low address
        FLAGS.put("IF", "0"); // Interrupt; whether interrupts can occur. 1 = enabled
        FLAGS.put("TF", "0"); // Trap; single step debugging

        FLAGS.put("SF", "0"); // Sign; sign of result. Reasonable for Integer only. 1 = neg. / false = pos
        FLAGS.put("ZF", "0"); //Zero; result of operation is zero. 1 = zero
        FLAGS.put("AF", "0"); // Aux. carry; similar to Carry but restricted to the low nibble only
        FLAGS.put("PF", "0"); // Parity; 1 = result has even number of set bits
        FLAGS.put("CF", "0"); // Carry; result of unsigned op. is too large or below zero. 1 = carry/borrow
        FLAGS.put("OF", "0"); // Overflow; result of signed op. is too large or small. 1 = carry/underflow
    }
}
