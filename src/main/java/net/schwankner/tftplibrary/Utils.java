package net.schwankner.tftplibrary;

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

    public static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }

        return Arrays.copyOf(bytes, i + 1);
    }
}
