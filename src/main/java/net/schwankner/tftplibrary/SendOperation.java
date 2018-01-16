package net.schwankner.tftplibrary;

import net.schwankner.tftplibrary.Messages.DataMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class SendOperation {

    private byte[] data;
    private int lastSendMessage = 0;

    private List<DataMessage> messageCollection = new ArrayList<>();

    private void addDataMessage(byte[] payload) {
        DataMessage message = new DataMessage(
                (short) (messageCollection.size() + 1),
                payload
        );
        messageCollection.add(message);
    }

    public void createMessageListFromBin(byte[] data) {
        this.data = data;
        for (int i = 0; i < data.length; i = i + 512) {
            if (data.length - i < 512) {
                addDataMessage(Arrays.copyOfRange(data, i, data.length));
            } else {
                addDataMessage(Arrays.copyOfRange(data, i, i + 512));
            }
        }
        if (data.length % 512 == 0) {
            addDataMessage(new byte[]{});
        }
    }

    public List<DataMessage> getMessageCollection() {
        return messageCollection;
    }

    public DataMessage getMessageForSending() throws Exception {
        if (lastSendMessage <= messageCollection.size()) {
            this.lastSendMessage++;
            return messageCollection.get(lastSendMessage - 1);
        } else {
            throw new Exception("Last packet has been send, no further packets available!");
        }
    }

    public int getLastSendMessage() {
        return lastSendMessage;
    }

    public int getDataSize() {
        return data.length;
    }

    public int getMessageListSize() {
        return messageCollection.size();
    }
}
