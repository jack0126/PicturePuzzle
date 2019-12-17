package com.demo.example1;

import com.demo.example1.util.DateTimeUtils;
import com.demo.example1.util.LayoutUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameCommandPane extends JPanel {

    public static final String PICK_PICTURE_COMMAND = "Pick picture";
    public static final String UPSET_COMMAND = "Upset";
    public static final String START_COMMAND = "Start";
    public static final String STOP_COMMAND = "Stop";

    private Container mContainer;
    private ImageView mPreviewView;
    private JLabel mTime;
    private JLabel mStep;
    private JButton mPickPicture;
    private JButton mUpset;
    private JButton mStart;
    private JButton mStop;

    public GameCommandPane() {
        setBorder(BorderFactory.createTitledBorder("Command Pane"));
        add(mContainer = Box.createVerticalBox());
        initComponents();
    }

    private void initComponents() {
        mPreviewView = new ImageView();
        mPreviewView.setPreferredSize(new Dimension(120, 120));

        Box statePane = Box.createVerticalBox();
        statePane.add(mTime = new JLabel("Time: 00:00:00"));
        statePane.add(mStep = new JLabel("Step: 0"));

        // add components
        mContainer.add(mPreviewView);

        mContainer.add(Box.createVerticalStrut(10));
        mContainer.add(new JSeparator());
        mContainer.add(LayoutUtils.horizontalCenter(statePane));
        mContainer.add(new JSeparator());
        mContainer.add(Box.createVerticalStrut(10));

        mContainer.add(LayoutUtils.horizontalCenter(mPickPicture = new JButton(PICK_PICTURE_COMMAND)));
        mContainer.add(Box.createVerticalStrut(5));
        mContainer.add(LayoutUtils.horizontalCenter(mUpset = new JButton(UPSET_COMMAND)));
        mContainer.add(Box.createVerticalStrut(5));
        mContainer.add(LayoutUtils.horizontalCenter(mStart = new JButton(START_COMMAND)));
        mContainer.add(Box.createVerticalStrut(5));
        mContainer.add(LayoutUtils.horizontalCenter(mStop = new JButton(STOP_COMMAND)));
    }

    public void setPreviewImage(Image image) {
        mPreviewView.setImage(image);
    }

    public void setGameTime(long time) {
        mTime.setText("Time: " + DateTimeUtils.toTimeDescription(time));
    }

    public void setGameStep(int count) {
        mStep.setText("Step: " + count);
    }

    public void setClickForPickPicture(ActionListener l) {
        mPickPicture.addActionListener(l);
    }

    public void setClickForUpset(ActionListener l) {
        mUpset.addActionListener(l);
    }

    public void setClickForStart(ActionListener l) {
        mStart.addActionListener(l);
    }

    public void setClickForStop(ActionListener l) {
        mStop.addActionListener(l);
    }

}
