package net.schwankner.tftplibrary.Messages;

import net.schwankner.tftplibrary.Utils;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class AcknowledgementMessage extends AbstractMessage {
    private short packetNumber;

    public AcknowledgementMessage(short packetNumber) {
        this.opCode = OpCode.ACK;
        this.packetNumber = packetNumber;
    }

    public AcknowledgementMessage(byte[] blob){
        this.opCode = OpCode.ACK;
        readBlob(blob);
    }

    public byte[] buildBlob() {
        byte[] binMessage = new byte[4];
        byte[] binOpCode = this.opCode.getBinOpCode();
        byte[] binPacketNumber = Utils.shortToBin(this.packetNumber);

        binMessage[0] = binOpCode[0];
        binMessage[1] = binOpCode[1];
        binMessage[2] = binPacketNumber[0];
        binMessage[3] = binPacketNumber[1];

        return binMessage;
    }

    private void readBlob(byte[] blob){
        packetNumber= Utils.binToShort(new byte[]{blob[2],blob[3]});
    }

    public short getPacketNumber(){
        return packetNumber;
    }

}
