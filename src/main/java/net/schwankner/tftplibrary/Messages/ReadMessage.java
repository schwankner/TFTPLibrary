package net.schwankner.tftplibrary.Messages;

/**
 * Created by JH on 15.01.18.
 */
public class ReadMessage extends AbstractInitiationMessage {
    public ReadMessage(String fileName) {
        super(fileName);
        this.opCode = OpCode.RRQ;
    }

    public ReadMessage(byte[] blob){
        super(blob);
    }
}
