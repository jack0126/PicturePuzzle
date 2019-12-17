package com.demo.example1;

import javax.swing.*;
import java.awt.*;

public class ImageView extends JPanel {
    private Image mImage;

    public ImageView() {
        super(null);
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mImage !=  null) {
            g.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void setImage(Image image) {
        mImage = image;
        if (mImage != null) {
            prepareImage(mImage, null);
        }
        updateUI();
    }
}
