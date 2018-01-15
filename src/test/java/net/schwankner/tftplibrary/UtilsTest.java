package net.schwankner.tftplibrary;

import net.schwankner.tftplibrary.Messages.OpCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Alexander Schwankner on 15.01.18.
 */
public class UtilsTest {

    @Test
    public void getSnippetStartFromZeroTest() {
        byte[] input = new byte[]{0x0, 0x2, 0x2F, 0x68};
        byte[] actual = Utils.getSnippet(input, 0, 1);
        byte[] expected = new byte[]{0x0, 0x2};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getSnippetTest() {
        byte[] input = new byte[]{0x0, 0x2, 0x2F, 0x68};
        byte[] actual = Utils.getSnippet(input, 2, 3);
        byte[] expected = new byte[]{0x2F, 0x68};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getSnippetLongTest() {
        byte[] input = new byte[]{0x0, 0x2, 0x2F, 0x68};
        byte[] actual = Utils.getSnippet(input, 1, 3);
        byte[] expected = new byte[]{0x2, 0x2F, 0x68};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getOpCodeTest() {
        byte[] input = new byte[]{0x0, 0x2, 0x2F, 0x68};

        assertEquals(OpCode.WRQ,Utils.getOpCode(input));
    }

    @Test
    public void trimTest(){
        byte[] input = new byte[]{0x0, 0x4, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};
        byte[] expected = new byte[]{0x0, 0x4, 0x0, 0x0};

        assertArrayEquals(expected, Utils.trimRawDataPackage(input));
    }

    @Test
    public void trimLongMessageTest(){
        byte[] input = new byte[]{0x0, 0x4, 0x0, 0x1, 0x1, 0x1, 0x1, 0x1, 0x0, 0x0, 0x0, 0x0};
        byte[] expected = new byte[]{0x0, 0x4, 0x0, 0x1, 0x1, 0x1, 0x1, 0x1};

        assertArrayEquals(expected, Utils.trimRawDataPackage(input));
    }

    @Test
    public void convertShortToBinAndRevert(){
        byte[] input = Utils.shortToBin((short)1900);
        assertEquals((short)1900,Utils.binToShort(input));
    }
}
