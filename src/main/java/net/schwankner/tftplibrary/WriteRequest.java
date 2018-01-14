package net.schwankner.tftplibrary;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public class WriteRequest extends Message {
    private String fileName, mode;

    public WriteRequest(String fileName){
        this.fileName=fileName;
        this.mode="octet";
        this.opCode=OpCode.WRQ;
    }

    public byte[] buildMessage(){
        int length = 2+fileName.length()+1+mode.length()+1; //RFC1350 TFTP Page 6
        byte[] binMessage =new byte[length];
        byte[] opcode = this.opCode.getBinOpCode();

        binMessage[0]=opcode[0];
        binMessage[1]=opcode[1];

        for (int i=0;i<fileName.length();i++){
            binMessage[i+2]=fileName.getBytes()[i];
        }

        for (int i=0;i<mode.length();i++){
            binMessage[i+3+fileName.length()]=mode.getBytes()[i];
        }

        return binMessage;
    }
}
