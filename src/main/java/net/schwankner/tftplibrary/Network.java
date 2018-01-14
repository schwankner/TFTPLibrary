package net.schwankner.tftplibrary;


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
    private InetAddress remoteHost;
    private int retries;
    private int timeout;
    private DatagramSocket socket;

    public Network(InetAddress remoteHost, int port, int timeout, int retries){
        this.remoteHost=remoteHost;
        this.port=port;
        this.retries = retries;
        this.timeout=timeout;
    }

    public void connect(){
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(byte[] message, boolean awaitAck){
        boolean gotAck=false;
        int i = 0;

        if(awaitAck) {
            while (!gotAck) {
                if(i<retries) {
                    //send message
                    try {
                        socket.send(new DatagramPacket(message, message.length, remoteHost, port));
                        i++;
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    //receive response
                    try {
                        byte[] response = receivePacket();
                        gotAck = true;
                    } catch (TimeoutException e) {
                        System.out.println("Response timed out");
                    }
                }else {
                    break;
                }
            }
        }else {
            try {
                socket.send(new DatagramPacket(message, message.length, remoteHost, port));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    public byte[] receivePacket() throws TimeoutException{
        byte[] data = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);

        try {
            socket.receive(receivePacket);
            return Utils.trim(receivePacket.getData());
        } catch (IOException e) {
            System.err.println("receive-method had a serverTimeout \nRESTART TRANSMISSION");
            //trying to synchronize the output, waits 1 sec after this output
            //Thread.sleep(1000);
            throw new TimeoutException();

        }
    }

}
