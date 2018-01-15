package net.schwankner.tftplibrary;

import net.schwankner.tftplibrary.Messages.AcknowledgementMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class AcknowledgementMessageTest {

    @Test
    public void ackMessageCreationTest() {
        AcknowledgementMessage acknowledgementMessage = new AcknowledgementMessage((short) 3);
        byte[] blob = acknowledgementMessage.buildBlob();
        byte[] expected = new byte[]{0x00, 0x04, 0x00, 0x03};
        assertArrayEquals(expected, blob);
    }

    @Test
    public void ackMessageReadBlobTest() {
        AcknowledgementMessage acknowledgementMessage = new AcknowledgementMessage(new byte[]{0x00, 0x04, 0x00, 0x03});
        assertEquals((short) 3, acknowledgementMessage.getPacketNumber());
    }
}
