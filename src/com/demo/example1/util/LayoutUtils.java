package com.demo.example1.util;

import javax.swing.*;

public class LayoutUtils {

    public static JComponent center(JComponent view) {
        return verticalCenter(horizontalCenter(view));
    }

    public static JComponent horizontalCenter(JComponent view) {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(view);
        box.add(Box.createHorizontalGlue());
        return box;
    }

    public static JComponent verticalCenter(JComponent view) {
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalGlue());
        box.add(view);
        box.add(Box.createVerticalGlue());
        return box;
    }
}
