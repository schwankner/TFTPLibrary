package net.schwankner.tftplibrary;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class DataMessage extends Message {
    private short packetNumber;

    public DataMessage(short packetNumber, byte[] payload) {
        this.opCode = OpCode.DATA;
        this.packetNumber = packetNumber;
        this.payload = payload;
    }

    public byte[] buildMessage() {
        byte[] binMessage = new byte[4 + payload.length];
        byte[] opcode = this.opCode.getBinOpCode();
        byte[] packetNumber = Utils.shortToBin(this.packetNumber);

        binMessage[0] = opcode[0];
        binMessage[1] = opcode[1];
        binMessage[2] = packetNumber[0];
        binMessage[3] = packetNumber[1];

        for (int i = 0; i < payload.length; i++) {
            binMessage[i + 4] = payload[i];
        }
        return binMessage;
    }
}
