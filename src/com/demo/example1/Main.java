package com.demo.example1;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
	    // write your code here
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
