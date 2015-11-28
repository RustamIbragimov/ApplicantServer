package model;

import java.io.*;

/**
 * Created by ribra on 11/11/2015.
 */
public class Serializer {

    private Serializer() {}

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(b);
        out.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte [] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) return null;
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(b);
        return in.readObject();
    }
}
