package cs455.overlay.transport;

//This is the client program.
import cs455.overlay.node.*;

import java.io.*;
import java.net.*;

public class TCPReceiverThread implements Runnable{
    private Socket           socketToTheServer;
    private DataInputStream  din;
    private Node             Node;

    public void run(){
        int dataLength;
        while (!socketToTheServer.isClosed()) {
            try {
                if(din != null) {
                    //receive and record message
                    dataLength = din.readInt();
                    byte[] data = new byte[dataLength];
                    din.readFully(data, 0, dataLength);

                    //send bytes for Unmarshalling and handling
                    //The current node sent for debug/data access
                    Registry.Factory.newEvent(data, this.Node);

                }
            } catch (IOException ioe) {
                System.out.println("TCPReceiverThread::IOException ioe: a connetcion dropped?");
                break;
            }
        }
    }

    public TCPReceiverThread(Socket socket, Node node) throws IOException {
        this.socketToTheServer = socket;
        this.Node = node;
        this.din = new DataInputStream(socket.getInputStream());
    }
}
