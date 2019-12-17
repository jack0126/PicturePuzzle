package com.demo.example1;

import com.demo.example1.util.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class GameModel {
    private final int mFixedRows;
    private final int mFixedColumns;
    private final int mFixedSize;
    private final int mFixedCursor;
    private final int[] mData;

    private int mCursor;
    private DataView mDataView;
    private OnCompletedListener mOnCompletedListener;

    public interface DataView {
        /**
         * 仅在视图绑定到游戏模型时执行
         * @param rows 数据的行数
         * @param columns 数据的列数
         */
        void onBind(GameModel model, int rows, int columns);

        /**
         * 每当游戏数据改变时执行
         * @param position 数据的编号
         * @param value 新的数据
         * @param cursor 当前位置是否为光标
         */
        void onDataChanged(int position, int value, boolean cursor);
    }

    public interface OnCompletedListener {
        /**
         * 当拼图完成时调用
         */
        void onComplete();
    }

    public enum Direction {
        Left, Up, Right, Down
    }

    public GameModel(int rows, int columns) {
        mFixedRows = rows;
        mFixedColumns = columns;
        mFixedSize = mFixedRows * mFixedColumns;
        mFixedCursor = mFixedSize - 1;
        mData = new int[mFixedSize];
        reset();
    }

    public void setView(DataView view) {
        mDataView = view;
        if (mDataView != null) {
            view.onBind(this, mFixedRows, mFixedColumns);
            notifyChanged();
        }
    }

    public void setOnCompletedListener(OnCompletedListener l) {
        mOnCompletedListener = l;
    }

    public void reset() {
        for (int i = 0; i < mData.length; i++) {
            mData[i] = i;
        }
        mCursor = mFixedCursor;
    }

    public void upset() {
        Arrays.fill(mData, -1);
        Random r = new Random();

        int pos = 0;
        while (pos < mFixedSize) {
            int v = r.nextInt(mFixedSize);
            if (ArrayUtils.indexOf(mData, v) > -1) {
                continue;
            }
            mData[pos] = v;
            pos++;
        }

        mCursor = ArrayUtils.indexOf(mData, mFixedCursor);
    }

    public boolean move(Direction direction) {
        int x = mCursor % mFixedColumns;
        int y = mCursor / mFixedColumns;

        switch (direction) {
            case Left: x--; break;//左移光标
            case Up: y--; break;//上移光标
            case Right: x++; break;//右移光标
            case Down: y++; break;//下移光标
        }

        if (canMove(x, y)) {
            int cursor = mCursor;
            int next = x + y * mFixedColumns;

            int tmp = mData[next];
            mData[next] = mData[cursor];
            mData[cursor] = tmp;
            mCursor = next;

            notifyChanged(cursor);
            notifyChanged(next);
            checkComplete();
            return true;
        }
        return false;
    }

    private boolean canMove(int x, int y) {
        return x >= 0 && y >= 0 && x < mFixedColumns && y < mFixedRows;
    }

    private void notifyChanged(int pos) {
        if (mDataView != null) {
            int value = mData[pos];
            mDataView.onDataChanged(pos, value, value == mFixedCursor);
        }
    }

    public void notifyChanged() {
        if (mDataView != null) {
            for (int pos = 0; pos < mFixedSize; pos++) {
                int value = mData[pos];
                mDataView.onDataChanged(pos, value, value == mFixedCursor);
            }
        }
    }

    private void checkComplete() {
        for (int i = 0; i < mFixedSize; i++) {
            if (mData[i] != i) {
                return;
            }
        }

        if (mOnCompletedListener != null) {
            mOnCompletedListener.onComplete();
        }
    }
}
