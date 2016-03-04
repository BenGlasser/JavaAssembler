package com.github.reline.javaassembler;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;

public class ALU {

    // change flags in here!

    public void ADD(String destination, String source) {
        // Dest = Dest + Source
        // TODO: 3/3/2016 implement registers as source arguments
        // TODO: 3/3/2016 set flags
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) + parseInt(source)));
    }

    public void SUB(String destination, String source) {
        // Dest = Dest - Source
        // TODO: 3/3/2016 implement registers as source arguments
        // TODO: 3/3/2016 set flags
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) - parseInt(source)));
    }

    public void MUL(String arg) {
        if (isNumber(arg)) {

            /*// if arg == byte: AX = AL * arg
            if (Integer.parseInt(arg) <= 255) {
                // utilMOV("AX", getReg("AL") + Integer.parseInt(arg));
            }

            // if arg == word: DX:AX = AX * arg
            else if (Integer.parseInt(arg) <= 65535) {

            }*/

            // if arg == doublew: EDX:EAX = EAX * arg
            if (Integer.parseInt(arg) <= 2147483647) {
                String doublew = Integer.toBinaryString(getReg("EAX") * parseInt(arg));
                doublew = fillZeros(doublew, 64);
                String EDX = doublew.substring(0, 31);
                String EAX = doublew.substring(32);
                utilMOV("EDX", EDX);
                utilMOV("EAX", EAX);
            }

            else {
                System.out.println("Your number is too big!");
            }
        }
    }

    public void DIV(String arg) {
        // if arg == byte: AL = AX / arg

        // if arg == word: AX = DX:AX / arg

        // if arg == doublew: EAX = EDX:EAX / arg
        String doublew = Integer.toBinaryString(getRegs("EDX", "EAX") / parseInt(arg));
        utilMOV("EAX", doublew);
    }

    public void INC(String arg) {
        // arg += 1
        if (isRegister(arg))
            utilMOV(arg, Integer.toBinaryString(getReg(arg) + 1));
    }

    public void DEC(String arg) {
        // arg += 1
        if (isRegister(arg))
            utilMOV(arg, Integer.toBinaryString(getReg(arg) - 1));
    }

    public void CMP(String arg1, String arg2) {
        // set flags but don't change registers; arg1 - arg2
    }

    public void AND(String destination, String source) {
        // Dest = Dest ^ Source
        if (isRegister(destination) && isRegister(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) & getReg(source)));
    }

    public void OR(String destination, String source) {
        // Dest = Dest v Source
        if (isRegister(destination) && isRegister(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) | getReg(source)));
    }

    public void NOT(String arg) {
        // invert each bit
        if (isRegister(arg))
            utilMOV(arg, Integer.toBinaryString(~getReg(arg)));
    }

    public void PRINT(String arg) {
        if (CPU.FLAGS.containsKey(arg.toUpperCase()))
            System.out.println(arg.toUpperCase() + " = " + CPU.FLAGS.get(arg.toUpperCase()));
        else if (isRegister(arg))
            System.out.println(arg.toUpperCase() + " = " + CPU.REGISTERS.get(arg.toUpperCase()));
    }

    private boolean isRegister(String arg) {
        if (CPU.REGISTERS.containsKey(arg.toUpperCase())) {
            return true;
        } else {
            System.out.println("'" + arg + "' is not a valid register.");
            return false;
        }
    }

    private boolean isNumber(String arg) {
        try {
            parseInt(arg);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(arg + " is not a valid number");
            return false;
        }
    }

    private void utilMOV(String dest, String source) {
        CPU.REGISTERS.put(dest.toUpperCase(), fillZeros(source, 32)); // make dynamic for non-32-bit registers later
    }

    private int getReg(String dest) {
        String upper = dest.toUpperCase();
        String regVal = CPU.REGISTERS.get(upper);
        return new BigInteger(regVal, 2).intValue();
    }

    private int getRegs(String dest1, String dest2) {
        return new BigInteger(CPU.REGISTERS.get(dest1.toUpperCase()) + CPU.REGISTERS.get(dest2.toUpperCase()), 2).intValue();
    }

    @NotNull
    private String fillZeros(String arg, int size) {
        StringBuilder builder = new StringBuilder(arg);
        while (builder.length() < size) {
            builder.insert(0, '0');
        }
        return builder.toString();
    }
}
