package com.github.reline.javaassembler;

import static java.lang.Integer.parseInt;

public class ALU {

    // change flags in here!

    public void ADD(String destination, String source) {
        // Dest = Dest + Source
        // TODO: 3/3/2016 implement registers as source arguments
        // TODO: 3/3/2016 set flags
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, getReg(destination) + parseInt(source));
    }

    public void SUB(String destination, String source) {
        // Dest = Dest - Source
        // TODO: 3/3/2016 implement registers as source arguments
        // TODO: 3/3/2016 set flags
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, getReg(destination) - parseInt(source));
    }

    public void MUL(String arg) {
        System.out.println("Fuck yeah multiply");
        /*if (isNumber(arg)) {
            // if arg == byte: AX = AL * arg
            if (Integer.parseInt(arg) <= 255) {
                utilMOV("AX", getReg("AL") + Integer.parseInt(arg));
            }

            // if arg == word: DX:AX = AX * arg
            // if arg == doublew: EDX:EAX = EAX * arg
        }*/

    }

    public void DIV(String arg) {
        // if arg == byte: AL = AX / arg
        // if arg == word: AX = DX:AX / arg
        // if arg == doublew: EAX = EDX:EAX / arg
        System.out.println("Fuck yeah divide");
    }

    public void INC(String arg) {
        // arg += 1
        System.out.println("Fuck yeah increment");
    }

    public void DEC(String arg) {
        // arg += 1
        System.out.println("Fuck yeah decrement");
    }

    public void CMP(String arg1, String arg2) {
        // set flags but don't change registers; arg1 - arg2
        System.out.println("Fuck yeah compare");
    }

    public void AND(String destination, String source) {
        // Dest = Dest ^ Source
        System.out.println("Fuck yeah AND");
    }

    public void OR(String destination, String source) {
        // Dest = Dest v Source
        System.out.println("Fuck yeah OR");
    }

    public void NOT(String arg) {
        // invert each bit
        System.out.println("Fuck yeah NOT");
    }

    private boolean isRegister(String arg) {
        return Registers.REGISTERS.containsKey(arg);
    }

    private boolean isNumber(String arg) {
        try {
            parseInt(arg)
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(arg + " is not a valid number");
            return false;
        }
    }

    private void utilMOV(String dest, int source) {
        Registers.REGISTERS.put(dest, source);
    }

    private int getReg(String dest) {
        return Registers.REGISTERS.get(dest);
    }
}
