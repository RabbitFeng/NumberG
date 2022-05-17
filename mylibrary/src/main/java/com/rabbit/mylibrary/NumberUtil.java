package com.rabbit.mylibrary;

/**
 * Created by DongZF on 2022/5/17
 */
public class NumberUtil {
    /**
     * @param row
     * @param col
     * @return
     */
    public static int getAreaNumForPosition(int row, int col) {
        int rowWeight = row / 3;
        int colWeight = col / 3;
        return (rowWeight) * 3 + (colWeight + 1);
    }

    /**
     * 获取指定区的起始位置
     */
    public static int[] getStartPositionForArea(int area) {
        int startRow = ((area - 1) / 3) * 3;
        int startCol = ((area - 1) % 3) * 3;
        return new int[]{startRow, startCol};
    }
}
