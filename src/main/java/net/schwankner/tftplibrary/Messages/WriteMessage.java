package net.schwankner.tftplibrary.Messages;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class WriteMessage extends AbstractInitiationMessage {
    public WriteMessage(String fileName) {
        super(fileName);
        this.opCode = OpCode.WRQ;
    }

    public WriteMessage(byte[] blob){
        super(blob);
    }
}
