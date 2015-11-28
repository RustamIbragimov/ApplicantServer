package model;

import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by ribra on 11/10/2015.
 */
public class Server {
    private static int portNumber;
    private static ServerSocket serverSocket;
    private static Server instance = new Server();
    private static Logger logger = Logger.getLogger(Server.class.getName());

    private Server() {}

    private void init(int portNumber) {
        this.portNumber = portNumber;
        try {
            serverSocket = new ServerSocket(portNumber);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launch(int portNumber) {
        try {
            init(portNumber);
            boolean listening = true;
            logger.info("Server is running...");
            while (listening) {
                Thread t = new Thread(new ServerThread(serverSocket.accept()));
                t.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance() {
        return instance;
    }

    public static boolean isPortAvailable(String portStr) {
        if (portStr.length() == 0) return false;
        int port = Integer.valueOf(portStr);
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                /* should not be thrown */
                }
            }
        }

        return false;
    }

    public static String getIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


}
