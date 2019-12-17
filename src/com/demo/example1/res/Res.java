package com.demo.example1.res;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Res {
    public static URL getResource(String name) {
        return Res.class.getResource(name);
    }

    public static InputStream getResourceAsStream(String name) {
        return Res.class.getResourceAsStream(name);
    }

    public static Image getImage(String name) {
        return Toolkit.getDefaultToolkit().getImage(getResource(name));
    }

    public static BufferedImage getBufferedImage(String name) {
        try {
            return ImageIO.read(getResource(name));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
