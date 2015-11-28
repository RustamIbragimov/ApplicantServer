package model;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ribra on 11/10/2015.
 */
public class ServerProtocol {

    final private static byte SEARCH_BY_NUMBER = 1;
    final private static byte ATTENDANCE = 2;


    public ServerProtocol() {}

    public byte[] processInput(byte[] bytes) {
        byte first = bytes[0];
        byte[] message = new byte[bytes.length - 1];
        for (int i = 1; i < bytes.length; i++) {
            message[i - 1] = bytes[i];
        }
        if (first == SEARCH_BY_NUMBER) {
            return getByNumber(message);
        }
        else if (first == ATTENDANCE) {
            setAttended(message);
            return new byte[]{1};
        }
        return null;
    }

    private void setAttended(byte[] bytes) {
        try {
            String phoneNumber = (String) Serializer.deserialize(bytes);
            JDBCDriver.getInstance().setAttended(phoneNumber);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getByNumber(byte[] bytes) {
        byte[] result = null;
        try {
            String number = (String) Serializer.deserialize(bytes);
            List<Person> list = JDBCDriver.getInstance().findByNumber(number);
            result = Serializer.serialize(list);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
