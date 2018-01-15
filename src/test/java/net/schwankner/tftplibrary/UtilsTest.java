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
}
