package com.github.reline.javaassembler;

public class ALU {

    // change flags in here!

    public void ADD(String destination, String source) {
        // Dest = Dest + Source
    }

    public void SUB(String destination, String source) {
        // Dest = Dest - Source
    }

    public void MUL(String arg) {
        // if arg == byte: AX = AL * arg
        // if arg == word: DX:AX = AX * arg
        // if arg == doublew: EDX:EAX = EAX * arg
    }

    public void DIV(String arg) {
        // if arg == byte: AL = AX / arg
        // if arg == word: AX = DX:AX / arg
        // if arg == doublew: EAX = EDX:EAX / arg
    }

    public void INC(String arg) {
        // arg += 1
    }

    public void DEC(String arg) {
        // arg += 1
    }

    public void CMP(String arg1, String arg2) {
        // set flags but don't change registers; arg1 - arg2
    }

    public void AND(String destination, String source) {
        // Dest = Dest ^ Source
    }

    public void OR(String destination, String source) {
        // Dest = Dest v Source
    }

    public void NOT(String arg) {
        // invert each bit
    }
}
