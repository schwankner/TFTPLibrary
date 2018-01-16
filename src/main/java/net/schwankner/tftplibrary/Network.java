package net.schwankner.tftplibrary;


import net.schwankner.tftplibrary.Messages.OpCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Alexander Schwankner on 13.01.18.
 */
public class Network {

    private int port;
    private int retries;
    private int timeout;
    private DatagramSocket socket;

    public Network(int port, int timeout, int retries) {
        this.port = port;
        this.retries = retries;
        this.timeout = timeout;
    }

    public void connect(boolean portBind) {
        try {
            if (portBind) {
                socket = new DatagramSocket(port);
            } else {
                socket = new DatagramSocket();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

    public void sendPacket(byte[] message, InetAddress remoteHost, boolean awaitAck) {
        sendPacket(message, remoteHost, this.port, awaitAck);
    }

    public void sendPacket(byte[] message, InetAddress remoteHost, int port, boolean awaitAck) {
        boolean gotAck = false;
        int i = 0;

        if (awaitAck) {
            while (!gotAck) {
                if (i < retries) {
                    //send message
                    try {
                        socket.send(new DatagramPacket(message, message.length, remoteHost, port));
                        i++;
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    //receive response
                    try {
                        byte[] response = receivePacket().getData();
                        if (Utils.binToShort(Utils.getSnippet(response, 0, 1)) == OpCode.ACK.getNumeral()) {
                            gotAck = true;
                        }
                    } catch (TimeoutException e) {
                        System.err.println("Response timed out. Restart transmission.");
                    }
                } else {
                    break;
                }
            }
        } else {
            try {
                socket.send(new DatagramPacket(message, message.length, remoteHost, port));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public DatagramPacket receivePacket() throws TimeoutException {
        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(receivePacket);
            byte[] data = new byte[receivePacket.getLength()];
            System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
            receivePacket.setData(data);
            return receivePacket;
        } catch (IOException e) {
            throw new TimeoutException();

        }
    }
}