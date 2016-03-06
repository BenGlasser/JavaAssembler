package com.github.reline.javaassembler;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;

// arithmetic and logic instructions
public class ALU {

    // TODO: 3/6/2016 ADC, SBB, IDIV
    // TODO: 3/6/2016 SAL, SAR, RCL, RCR, ROL, ROR
    // TODO: 3/6/2016 NEG, XOR, SHL, SHR

    // add
    public void ADD(String destination, String source) {
        // Dest = Dest + Source
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) + parseInt(source)));
    }

    // subtract
    public void SUB(String destination, String source) {
        // Dest = Dest - Source
        if (isRegister(destination) && isNumber(source))
            utilMOV(destination, Integer.toBinaryString(getReg(destination) - parseInt(source)));
    }

    // multiply (unsigned)
    public void MUL(String arg) {
        if (isNumber(arg)) {

            int number = parseInt(arg);

            // if arg == byte: AX = AL * arg
            if (number <= 255) {
                utilMOV("AX", Integer.toBinaryString(getReg("AL") * number));
            }

            // if arg == word: DX:AX = AX * arg
            else if (number <= 65535) {
                String word = Integer.toBinaryString(getReg("AX") * number);
                word = fillZeros(word, 32);
                String DX = word.substring(0, 15);
                String AX = word.substring(16);
                utilMOV("DX", DX);
                utilMOV("AX", AX);
            }

            // if arg == doublew: EDX:EAX = EAX * arg
            else {
                String doublew = Integer.toBinaryString(getReg("EAX") * number);
                doublew = fillZeros(doublew, 64);
                String EDX = doublew.substring(0, 31);
                String EAX = doublew.substring(32);
                utilMOV("EDX", EDX);
                utilMOV("EAX", EAX);
            }
        }
    }

    // divide (unsigned)
    public void DIV(String arg) {
        if (isNumber(arg)) {

            int number = parseInt(arg);

            // if arg == byte: AL = AX / arg
            if (number <= 255) {
                utilMOV("AL", Integer.toBinaryString(getReg("AX") / number));
            }

            // if arg == word: AX = DX:AX / arg
            else if (number <= 65535) {
                String word = Integer.toBinaryString(getRegs("DX", "AX") / number);
                word = fillZeros(word, 16);
                utilMOV("AX", word);
            }

            // if arg == doublew: EAX = EDX:EAX / arg
            else {
                String doublew = Integer.toBinaryString(getRegs("EDX", "EAX") / number);
                doublew = fillZeros(doublew, 32);
                utilMOV("EAX", doublew);
            }
        }
    }

    // increment
    public void INC(String arg) {
        // arg += 1
        if (isRegister(arg))
            utilMOV(arg, Integer.toBinaryString(getReg(arg) + 1));
    }

    // decrement
    public void DEC(String arg) {
        // arg += 1
        if (isRegister(arg))
            utilMOV(arg, Integer.toBinaryString(getReg(arg) - 1));
    }

    // compare
    public void CMP(String arg1, String arg2) {
        // set flags but don't change registers; arg1 - arg2
        // cmp 1, 2;  CF=1  SF=1  ZF=0  OF=0  AF=1  PF=1
        // cmp 2, 1;  CF=0  SF=0  ZF=0  OF=0  AF=0  PF=0
        // cmp 1, 1;  CF=0  SF=0  ZF=1  OF=0  AF=0  PF=1
        // cmp 0, 0;  CF=0  SF=0  ZF=1  OF=0  AF=0  PF=1
        // cmp -1, 1; CF=0  SF=1  ZF=0  OF=0  AF=0  PF=0
        // cmp -1, 0; CF=0  SF=1  ZF=0  OF=0  AF=0  PF=1
        // cmp 0, -1; CF=1  SF=0  ZF=0  OF=0  AF=1  PF=0

        if (isRegister(arg1) && isRegister(arg2)) {

            if ((arg1.contains("E") && arg2.contains("E")) || (arg1.contains("X") && arg2.contains("X")) ||
                    (arg1.contains("H") || arg2.contains("L") && (arg1.contains("H") || arg2.contains("L")))) {

                int result = getReg(arg1) - getReg(arg2);

                // if (arg1 - arg2) = 0, ZF=1
                if (result == 0)
                    CPU.FLAGS.put("ZF", "1");
                else if (result < 0) { // if (arg1 - arg2) < 0 SF=1
                    CPU.FLAGS.put("SF", "1");
                    // if (arg1 - arg2) >= -255 && < 0, AF=1
                    if (result >= -255)
                        CPU.FLAGS.put("AF", "1");
                }

                // if (arg1 - arg2) has even number of set bits, PF=1
                String s = Integer.toBinaryString(result);
                int setBits = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '1') {
                        setBits++;
                    }
                }
                if (setBits % 2 == 0)
                    CPU.FLAGS.put("PF", "1");

                // (unsigned) if (arg1 - arg2) is too large or < 0, CF=1
                // (signed) if (arg1 - arg2) is > 2147483647 or < 0, OF=1
                if (result < 0) {
                    CPU.FLAGS.put("CF", "1");
                    CPU.FLAGS.put("OF", "1");
                } else if (arg1.toUpperCase().contains("X") && result > 65535) { // 16-bit register
                    CPU.FLAGS.put("CF", "1");
                    CPU.FLAGS.put("OF", "1");
                } else if (arg1.contains("H") || arg2.contains("L") && (arg1.contains("H") || arg2.contains("L"))
                        && result > 255) { // 8-bit register
                    CPU.FLAGS.put("CF", "1");
                    CPU.FLAGS.put("OF", "1");
                }

            } else {
                System.out.println("Registers '" + arg1 + "' and '" + arg2 + "' are not of the same size.");
            }
        }
    }

    // logical and
    public void AND(String destination, String source) {
        // Dest = Dest & Source
        String dest = destination.toUpperCase();
        String src = source.toUpperCase();

        if (isRegister(dest) && isRegister(src)) {
            if ((dest.contains("E") && src.contains("E")) || (dest.contains("X") && src.contains("X")) ||
                    (dest.contains("H") || dest.contains("L") && (src.contains("H") || src.contains("L")))) {
                utilMOV(destination, Integer.toBinaryString(getReg(destination) & getReg(source)));
            } else {
                System.out.println("Registers '" + dest + "' and '" + src + "' are not of the same size.");
            }
        }
    }

    // logical or
    public void OR(String destination, String source) {
        // Dest = Dest | Source
        String dest = destination.toUpperCase();
        String src = source.toUpperCase();

        if (isRegister(dest) && isRegister(src)) {
            if ((dest.contains("E") && src.contains("E")) || (dest.contains("X") && src.contains("X")) ||
                    (dest.contains("H") || dest.contains("L") && (src.contains("H") || src.contains("L")))) {
                utilMOV(destination, Integer.toBinaryString(getReg(destination) | getReg(source)));
            } else {
                System.out.println("Registers '" + dest + "' and '" + src + "' are not of the same size.");
            }
        }
    }

    // invert each bit
    public void NOT(String arg) {
        // invert each bit
        if (isRegister(arg)) {
            if (arg.toUpperCase().contains("E")) { // 32-bit register
                utilMOV(arg, Integer.toBinaryString(~getReg(arg)));
            } else if (arg.toUpperCase().contains("X")) { // 16-bit register
                utilMOV(arg, Integer.toBinaryString(~getReg(arg)).substring(16));
            } else { // 8-bit register
                utilMOV(arg, Integer.toBinaryString(~getReg(arg)).substring(24));
            }
        }
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
        if (dest.length() == 3) { // 32-bit register
            set32Bit(dest, fillZeros(source, 32));
        } else {
            if (dest.toUpperCase().contains("X")) { // 16-bit register
                set16Bit(dest, fillZeros(source, 16));
            } else if (dest.toUpperCase().contains("H")) { // upper 8-bit register
                setUpper8Bit(dest, fillZeros(source, 8));
            } else { // lower 8-bit register
                setLower8Bit(dest, fillZeros(source, 8));
            }
        }
    }

    public void set32Bit(String register, String value) {
        CPU.REGISTERS.put(register.toUpperCase(), value);
        String XRegister = register.toUpperCase().substring(1);
        set16Bit(XRegister, value.substring(16));
    }

    public void set16Bit(String register, String value) {
        CPU.REGISTERS.put(register.toUpperCase(), value);
        String UpperRegister = register.toUpperCase().substring(0,1) + "H";
        String LowerRegister = register.toUpperCase().substring(0,1) + "L";
        setUpper8Bit(UpperRegister, value.substring(0, 8));
        setLower8Bit(LowerRegister, value.substring(8));
    }

    public void setUpper8Bit(String register, String value) {
        String XRegister = register.toUpperCase().substring(0,1) + "X";
        String ERegister = "E" + XRegister.toUpperCase();

        CPU.REGISTERS.put(register.toUpperCase(), value);
        CPU.REGISTERS.put(XRegister,  value + CPU.REGISTERS.get(XRegister).substring(8));
        CPU.REGISTERS.put(ERegister, CPU.REGISTERS.get(ERegister).substring(0, 16) + value + CPU.REGISTERS.get(ERegister).substring(23));
    }

    public void setLower8Bit(String register, String value) {
        String XRegister = register.toUpperCase().substring(0,1) + "X";
        String ERegister = "E" + XRegister.toUpperCase();

        CPU.REGISTERS.put(register.toUpperCase(), value);
        CPU.REGISTERS.put(XRegister, CPU.REGISTERS.get(XRegister).substring(0, 8) + value);
        CPU.REGISTERS.put(ERegister, CPU.REGISTERS.get(ERegister).substring(0, 24) + value);
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
