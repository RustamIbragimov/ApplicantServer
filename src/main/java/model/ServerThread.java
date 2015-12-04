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


    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try  {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            ServerProtocol sp = new ServerProtocol();

            while (true) {
                byte type = in.readByte();
                Object obj = in.readObject();
                Object result = sp.processInput(type, obj);
                if (result == Util.EXIT_OBJECT) {
                    in.close();
                    out.close();
                    break;
                }
                else if (result != Util.NULL_OBJECT) out.writeObject(result);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
