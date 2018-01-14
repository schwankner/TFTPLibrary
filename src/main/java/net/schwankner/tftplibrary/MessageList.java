package net.schwankner.tftplibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class MessageList {

    private List<DataMessage> messageCollection = new ArrayList<>();

    public MessageList(){

    }

    public void addDataMessage(byte[] payload){
        DataMessage message = new DataMessage(
                (short) messageCollection.size(),
                payload
                );
        messageCollection.add(message);
    }

    public void createMessageListFromBin(byte[] data){

        for (int i = 0; i < data.length; i = i + 512) {
            if (data.length - i < 512) {
                addDataMessage(Arrays.copyOfRange(data, i, data.length));
            } else {
                addDataMessage(Arrays.copyOfRange(data, i, i+512));
            }
        }
        if(data.length%512==0){
            addDataMessage(new byte[0]);
        }
    }

    public List<DataMessage> getMessageCollection(){
        return messageCollection;
    }
}
