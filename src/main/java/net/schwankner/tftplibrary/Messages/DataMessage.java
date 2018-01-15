package net.schwankner.tftplibrary.Messages;

import net.schwankner.tftplibrary.Utils;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class DataMessage extends AbstractMessage {
    private short packetNumber;
    private byte[] payload;

    public DataMessage(short packetNumber, byte[] payload) {
        this.opCode = OpCode.DATA;
        this.packetNumber = packetNumber;
        this.payload = payload;
    }

    public DataMessage(byte[] blob){
        readBlob(blob);
    }

    public byte[] buildBlob() {
        byte[] binMessage = new byte[4 + payload.length];
        byte[] binOpCode = this.opCode.getBinOpCode();
        byte[] binPacketNumber = Utils.shortToBin(this.packetNumber);

        binMessage[0] = binOpCode[0];
        binMessage[1] = binOpCode[1];
        binMessage[2] = binPacketNumber[0];
        binMessage[3] = binPacketNumber[1];

        for (int i = 0; i < payload.length; i++) {
            binMessage[i + 4] = payload[i];
        }
        return binMessage;
    }

    private void readBlob(byte[] blob){
        this.opCode = OpCode.DATA;
        this.packetNumber=Utils.binToShort(Utils.getSnippet(blob,2,3));
        this.payload=Utils.getSnippet(blob,4,blob.length-1);
    }

    public short getPacketNumber(){
        return this.packetNumber;
    }

    public int getSize(){
        return this.payload.length;
    }

    public byte[] getPayload(){
        return this.payload;
    }
}
