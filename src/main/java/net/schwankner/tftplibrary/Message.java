package net.schwankner.tftplibrary;

/**
 * Created by Alexander Schwankner on 14.01.18.
 */
public abstract class Message{

    protected byte [] payload;
    protected OpCode opCode;

    public Message() {

    }

    public enum OpCode {
        RRQ("Read request", (short) 1),
        WRQ("Write request", (short) 2),
        DATA("Data", (short) 3),
        ACK("Acknowledgment", (short) 4),
        ERROR("Error", (short) 5);

        private String operation;
        private short numeral;


        OpCode(String operation, short numeral) {
            this.numeral = numeral;
        }

        public short getNumeral() {
            return this.numeral;
        }

        public String getOperation(){
            return this.operation;
        }

        public byte[] getBinOpCode(){
            return Utils.shortToBin(numeral);
        }

    }
}
