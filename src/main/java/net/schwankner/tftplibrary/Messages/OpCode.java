package net.schwankner.tftplibrary.Messages;

import net.schwankner.tftplibrary.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public enum OpCode {
    RRQ((short) 1),
    WRQ((short) 2),
    DATA((short) 3),
    ACK((short) 4),
    ERROR((short) 5);

    private static Map map = new HashMap<>();

    static {
        for (OpCode opCode : OpCode.values()) {
            map.put(opCode.numeral, opCode);
        }
    }

    private short numeral;

    OpCode(short numeral) {
        this.numeral = numeral;
    }

    public static OpCode valueOf(short opCode) {
        return (OpCode) map.get(opCode);
    }

    public short getNumeral() {
        return this.numeral;
    }


    public byte[] getBinOpCode() {
        return Utils.shortToBin(numeral);
    }

}