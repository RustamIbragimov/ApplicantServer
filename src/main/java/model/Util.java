package model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jdk.internal.util.xml.impl.Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ribra on 12/4/2015.
 */
public class Util {
    public final static Object NULL_OBJECT = new Object();
    public final static Object EXIT_OBJECT = new Object();


    public static byte[] imgToBytes(Image img) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", baos);
        byte[] res = baos.toByteArray();
        return res;
    }

    public static SerializableImage bytesToImage(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) return  null;
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bImage = ImageIO.read(in);
        Image img = SwingFXUtils.toFXImage(bImage, null);
        SerializableImage sImage = new SerializableImage(img);
        return sImage;
    }
}
