package net.schwankner.tftplibrary;

import net.schwankner.tftplibrary.Messages.OpCode;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class Utils {

    public static byte[] shortToBin(short x) {
        byte[] ret = new byte[2];

        ret[1] = (byte) (x & 0xff);
        ret[0] = (byte) ((x >> 8) & 0xff);

        return ret;
    }

    public static short binToShort(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getShort();
    }

    public static int findByte(byte[] haystack, byte needle, int start) {
        int i = start;
        boolean found = false;
        while (!found || i > haystack.length) {
            if (haystack[i] == needle) {
                found = true;
            }
            i++;
        }
        return i - 1;
    }

    public static byte[] getSnippet(byte[] array, int start, int end) {
        return Arrays.copyOfRange(array, start, end + 1);
    }

    public static OpCode getOpCode(byte[] blob) {
        short numeral = Utils.binToShort(Utils.getSnippet(blob, 0, 1));
        return OpCode.valueOf(numeral);
    }
}
