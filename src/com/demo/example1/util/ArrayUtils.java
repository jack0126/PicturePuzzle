package com.demo.example1.util;

public class ArrayUtils {
    public static int indexOf(int[] arr, int key) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
}
