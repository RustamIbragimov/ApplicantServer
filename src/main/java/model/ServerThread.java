package model;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.logging.SocketHandler;

/**
 * Created by ribra on 11/10/2015.
 */


/**
 * add new applicant
 * get all applicants
 * find by digits
 * find by name
 * update applicant
 */
public class ServerThread implements Runnable {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }


    /**
     * need to send the size and the array itself
     * http://stackoverflow.com/questions/1176135/java-socket-send-receive-byte-array
     */
    public void run() {
        DataOutputStream out = null;
        DataInputStream in = null;
        try  {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            ServerProtocol sp = new ServerProtocol();

            while (true) {
                int length = in.readInt();
                if (length > 0) {
                    byte[] bytes = new byte[length];
                    in.readFully(bytes, 0, bytes.length);
                    byte[] result = sp.processInput(bytes);
                    if (result.length != 1 && result[0] != 1) {
                        out.writeInt(result.length);
                        out.write(result);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
