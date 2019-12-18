package com.demo.example1;

import com.demo.example1.res.Res;
import com.demo.example1.util.DateTimeUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

public class MainFrame extends JFrame implements ActionListener, KeyListener, GameModel.OnCompletedListener {
    private JPanel mContentPane;
    private GameCommandPane mGameCommandPane;
    private GameModel mGameModel;
    private GameView mGameView;
    private java.util.Timer mTimer;
    private long mTimeCount;
    private int mStepCount;
    private volatile boolean mIsGameOver;

    public MainFrame() {
        super("拼图游戏");
        setIconImage(Res.getImage("icon.png"));
        setSize(640, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        setContentPane(mContentPane = new JPanel(null));
        initComponents();

        mIsGameOver = true;
        mTimer = new java.util.Timer();
        startTimer();
    }

    private void initComponents() {
        mGameView = new GameView();
        mGameView.setImageSource(Res.getResource("timg.jpg"));
        mGameView.setCursorImage(Res.getImage("cursor.png"));

        mGameModel = new GameModel(3, 3);
        mGameModel.setView(mGameView);
        mGameModel.setOnCompletedListener(this);

        mGameCommandPane = new GameCommandPane();
        mGameCommandPane.setPreviewImage(Toolkit.getDefaultToolkit().getImage(mGameView.getImageSource()));
        mGameCommandPane.setGameTime(mTimeCount = DateTimeUtils.parseTimeDescription("00:00:00"));
        mGameCommandPane.setGameStep(mStepCount = 0);
        mGameCommandPane.setClickForPickPicture(this);
        mGameCommandPane.setClickForUpset(this);
        mGameCommandPane.setClickForStart(this);
        mGameCommandPane.setClickForStop(this);

        mContentPane.setLayout(new BorderLayout());
        mContentPane.setBorder(BorderFactory.createRaisedBevelBorder());
        mContentPane.add(mGameCommandPane, BorderLayout.EAST);
        mContentPane.add(mGameView, BorderLayout.CENTER);
    }

    private void startTimer() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!mIsGameOver) {
                    mGameCommandPane.setGameTime(mTimeCount += 1000);
                }
            }
        }, 0, 1000);
    }

    private void onPickPicture() {
        onStop();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName();
                return name.endsWith(".jpg") ||
                        name.endsWith(".jpeg") ||
                        name.endsWith(".bmp");
            }

            @Override
            public String getDescription() {
                return "图片(*.bmp,*.jpeg,*.jpg)";
            }
        });
        fileChooser.showOpenDialog(this);

        File file =  fileChooser.getSelectedFile();
        if (file != null && file.exists()) {
            try {
                URL url = file.toURI().toURL();
                mGameView.setImageSource(url);
                mGameCommandPane.setPreviewImage(Toolkit.getDefaultToolkit().getImage(url));
            } catch (MalformedURLException ignore) {
            }
        }
    }// end onPickPicture

    private void onUpset() {
        mGameModel.upset();
        mGameModel.notifyChanged();
        mGameCommandPane.setGameTime(mTimeCount = DateTimeUtils.parseTimeDescription("00:00:00"));
        mGameCommandPane.setGameStep(mStepCount = 0);
        onStart();
    }

    private void onStart() {
        mIsGameOver = false;
        requestFocus();
    }

    private void onStop() {
        mIsGameOver = true;
    }

    @Override
    public void onComplete() {
        onStop();
        JOptionPane.showMessageDialog(this, "Game over!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (GameCommandPane.PICK_PICTURE_COMMAND.equals(command)) onPickPicture();
        else if (GameCommandPane.UPSET_COMMAND.equals(command)) onUpset();
        else if (GameCommandPane.START_COMMAND.equals(command)) onStart();
        else if (GameCommandPane.STOP_COMMAND.equals(command)) onStop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (mIsGameOver) {
            return;
        }

        boolean success = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                success = mGameModel.move(GameModel.Direction.Left);
                break;
            case KeyEvent.VK_UP:
                success = mGameModel.move(GameModel.Direction.Up);
                break;
            case KeyEvent.VK_RIGHT:
                success = mGameModel.move(GameModel.Direction.Right);
                break;
            case KeyEvent.VK_DOWN:
                success = mGameModel.move(GameModel.Direction.Down);
            break;
        }

        if (success) {
            mGameCommandPane.setGameStep(++mStepCount);
        }
    }
}
