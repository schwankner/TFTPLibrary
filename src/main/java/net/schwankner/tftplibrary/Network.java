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

    public Network(int port) {
        this.port = port;
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
        sendPacket(message,remoteHost,this.port,awaitAck);
    }

    public void sendPacket(byte[] message, InetAddress remoteHost,int port,boolean awaitAck) {
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
                        if(Utils.binToShort(Utils.getSnippet(response,0,1))==OpCode.ACK.getNumeral()){
                            gotAck = true;
                        }
                    } catch (TimeoutException e) {
                        System.out.println("Response timed out");
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
        System.out.println("Session closed, server not reachable!");
        close();
        System.exit(1);
    }

    public DatagramPacket receivePacket() throws TimeoutException {
        byte[] data = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);

        try {
            socket.receive(receivePacket);
            Utils.trim(data);
            return receivePacket;
        } catch (IOException e) {
            System.err.println("receive-method had a serverTimeout \nRESTART TRANSMISSION");
            //trying to synchronize the output, waits 1 sec after this output
            //Thread.sleep(1000);
            throw new TimeoutException();

        }
    }

}
