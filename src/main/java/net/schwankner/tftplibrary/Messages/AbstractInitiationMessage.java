package net.schwankner.tftplibrary.Messages;

import net.schwankner.tftplibrary.Utils;

/**
 * Created by JH on 15.01.18.
 */
public abstract class AbstractInitiationMessage extends AbstractMessage {
    protected String fileName, mode;

    public AbstractInitiationMessage(String fileName) {
        this.fileName = fileName;
        this.mode = "octet";
    }

    public AbstractInitiationMessage(byte[] binMessage) {
        parseBlob(binMessage);
    }

    public byte[] buildBlob() {
        int length = 2 + fileName.length() + 1 + mode.length() + 1; //RFC1350 TFTP Page 6
        byte[] binMessage = new byte[length];
        byte[] opcode = this.opCode.getBinOpCode();

        binMessage[0] = opcode[0];
        binMessage[1] = opcode[1];

        for (int i = 0; i < fileName.length(); i++) {
            binMessage[i + 2] = fileName.getBytes()[i];
        }

        for (int i = 0; i < mode.length(); i++) {
            binMessage[i + 3 + fileName.length()] = mode.getBytes()[i];
        }

        return binMessage;
    }

    private void parseBlob(byte[] blob) {
        this.opCode = OpCode.WRQ;

        int endOfPath = Utils.findByte(blob, (byte) 0, 2);
        this.fileName = new String(Utils.getSnippet(blob, 2, endOfPath-1));

        this.mode = new String(Utils.getSnippet(blob, endOfPath + 1, blob.length-1));
    }

    public String getFileName() {
        return this.fileName;
    }
}
