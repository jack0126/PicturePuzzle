package com.demo.example1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GameView extends JPanel implements GameModel.DataView {
    private URL mImageSource;
    private ImageView[] mImageViews;
    private Image[] mImages;
    private Image mCursorImage;

    private GameModel mGameModel;
    private int mRows;
    private int mColumns;

    public GameView() {
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public URL getImageSource() {
        return mImageSource;
    }

    public void setImageSource(URL url) {
        if (url != mImageSource) {
            mImageSource = url;
            mImages = null;
            if (mGameModel != null) {
                mGameModel.notifyChanged();
            }
        }
    }

    public void setCursorImage(Image image) {
        mCursorImage = image;
    }

    @Override
    public void onBind(GameModel model, int rows, int columns) {
        mGameModel = model;
        mRows = rows;
        mColumns = columns;
        updateView();
    }

    @Override
    public void onDataChanged(int position, int value, boolean cursor) {
        if (mImages == null) {
            mImages = new Image[mRows * mColumns];
            updateImageSource();
        }

        mImageViews[position].setImage(cursor ? mCursorImage : mImages[value]);
    }

    private void updateView() {
        removeAll();
        setLayout(new GridLayout(mRows, mColumns, 2, 2));
        mImageViews = new ImageView[mRows * mColumns];

        for (int i = 0; i < mImageViews.length; i++) {
            add(mImageViews[i] = new ImageView());
        }
    }

    private void updateImageSource() {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(mImageSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bufferedImage != null) {
            int subWidth = bufferedImage.getWidth() / mColumns;
            int subHeight = bufferedImage.getHeight() / mRows;

            for (int y = 0; y < mRows; y++) {
                for (int x = 0; x < mColumns; x++) {
                    int pos = y * mColumns + x;
                    int subX = x * subWidth;
                    int subY = y * subHeight;
                    mImages[pos] = bufferedImage.getSubimage(subX, subY, subWidth, subHeight);
                }
            }
        }// end if
    }

}
