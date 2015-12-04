package model;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ribra on 11/10/2015.
 */
public class ServerProtocol {

    final private static byte EXIT = 0;
    final private static byte SEARCH_BY_NUMBER = 1;
    final private static byte ATTENDANCE = 2;
    final private static byte PHOTO = 3;


    public ServerProtocol() {}

    public Object processInput(byte type, Object obj) {
        if (type == EXIT) {
            return Util.EXIT_OBJECT;
        }
        else if (type == SEARCH_BY_NUMBER) {
            return getByNumber(obj);
        }
        else if (type == ATTENDANCE) {
            setAttended(obj);
            return Util.NULL_OBJECT;
        }
        else if (type == PHOTO) {
            updatePhoto(obj);
            return Util.NULL_OBJECT;
        }
        return null;
    }

    private void setAttended(Object obj) {
        String phoneNumber = (String) obj;
        JDBCDriver.getInstance().setAttended(phoneNumber);
    }

    public void updatePhoto(Object obj) {
        Person person = (Person) obj;
        JDBCDriver.getInstance().updatePhoto(person);
    }

    private Object getByNumber(Object obj) {
        String number = (String) obj;
        List<Person> list = JDBCDriver.getInstance().findByNumber(number);
        return list;
    }
}
