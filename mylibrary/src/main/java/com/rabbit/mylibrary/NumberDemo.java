package com.rabbit.mylibrary;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class NumberDemo {
    private int[][] chest = new int[9][9];// 原盘
    private int[][] positionHolder = new int[9][9];// 占位
    private int[][] result = new int[9][9];

    private Record[][] possibleRecord = new Record[9][9];

    public void init(@NonNull int[][] newChest) {
        this.chest = newChest;
        for (int row = 0; row < chest.length; row++) {
            for (int col = 0; col < chest[0].length; col++) {
                if (chest[row][col] != 0) {
                    result[row][col] = chest[row][col];
                }
            }
        }
        for (int row = 0; row < possibleRecord.length; row++) {
            for (int col = 0; col < possibleRecord[row].length; col++) {
                possibleRecord[row][col] = new Record();
                try {
                    recordPossibleNumber(row, col);
                } catch (NumberException.RowBoundsException | NumberException.ColBoundsException | NumberException.AreaBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /******** Judge ********/
    public boolean judgeAll() throws NumberException {
        return judgeForRow(-1) && judgeForCol(-1) && judgeForArea(-1);
    }

    /**
     * 判定行
     *
     * @param row from 0 to 8, or -1 to judge all rows
     * @return
     * @throws NumberException.RowBoundsException
     */
    public boolean judgeForRow(int row) throws NumberException.RowBoundsException {
        if (row == -1) {
            // TODO:判定全部行
            for (int i = 0; i < 9; i++) {
                if (!judgeForRow(i)) {
                    return false;
                }
            }
            return true;
        }
        checkRow(row);
        // TODO:判定单行
        int total = 0;
        for (int i = 0; i < result[row].length; i++) {
            if (result[row][i] == 0) {
                return false;
            } else {
                total += result[row][i];
            }
        }
        if (total != 45) {
            return false;
        }
        for (int i = 0; i < result[row].length - 1; i++) {
            for (int j = i + 1; j < result[row].length; j++) {
                if (result[row][i] == result[row][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param col col from 0 to 8, or -1 to judge all cols
     * @return
     * @throws NumberException.ColBoundsException
     */
    public boolean judgeForCol(int col) throws NumberException.ColBoundsException {
        if (col == -1) {
            // TODO:判定全部列
            for (int i = 0; i < 9; i++) {
                if (!judgeForCol(i)) {
                    return false;
                }
            }
            return true;
        }
        checkCol(col);
        // TODO:判定单列
        int total = 0;
        for (int i = 0; i < result[col].length; i++) {
            if (result[i][col] == 0) {
                return false;
            } else {
                total += result[i][col];
            }
        }
        if (total != 45) {
            return false;
        }
        for (int i = 0; i < result[col].length - 1; i++) {
            for (int j = i + 1; j < result[col].length; j++) {
                if (result[i][col] == result[j][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public int getAreaNumForPosition(int row, int col) {
        int rowWeight = row / 3;
        int colWeight = col / 3;
        return (rowWeight) * 3 + (colWeight + 1);
    }

    /**
     * 获取指定区的起始位置
     */
    public int[] getStartPositionForArea(int area) {
        int startRow = ((area - 1) / 3) * 3;
        int startCol = ((area - 1) % 3) * 3;
        return new int[]{startRow, startCol};
    }

    /**
     * 判定坐标点所在区域是否正确
     *
     * @param row
     * @param col
     * @return
     * @throws NumberException.AreaBoundsException
     */
    public boolean judgeForArea(int row, int col) throws NumberException.AreaBoundsException {
        return judgeForArea(getAreaNumForPosition(row, col));
    }

    /**
     * 判定指定区域是否正确
     *
     * @param areaNum
     * @return
     * @throws NumberException.AreaBoundsException
     */
    public boolean judgeForArea(int areaNum) throws NumberException.AreaBoundsException {
        if (areaNum == -1) {
            for (int i = 1; i <= 9; i++) {
                if (!judgeForArea(i)) {
                    return false;
                }
            }
            return true;
        }
        checkArea(areaNum);
        int[] startPositionForArea = getStartPositionForArea(areaNum);
        int rowStart = startPositionForArea[0];
        int colStart = startPositionForArea[1];
        int total = 0;
        int[] oneRow = new int[9];

        for (int row = rowStart; row < rowStart + 3; row++) {
            for (int col = colStart; col < colStart + 3; col++) {
                if (result[row][col] == 0) {
                    return false;
                } else {
                    total += result[row][col];
                    oneRow[row * 3 + col] = result[row][col];
                }
            }
        }
        if (total != 45) {
            return false;
        }
        for (int i = 0; i < oneRow.length - 1; i++) {
            for (int j = i + 1; j < oneRow.length; j++) {
                if (oneRow[i] == oneRow[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /******** Check ********/
    private boolean isRowInvalid(int row) {
        return row >= 0 && row <= 8;
    }

    private void checkRow(int row) throws NumberException.RowBoundsException {
        if (!isRowInvalid(row)) {
            throw new NumberException.RowBoundsException();
        }
    }

    private boolean isColInvalid(int col) {
        return col >= 0 && col <= 8;
    }

    private void checkCol(int col) throws NumberException.ColBoundsException {
        if (!isColInvalid(col)) {
            throw new NumberException.ColBoundsException();
        }
    }

    private boolean isAreaInvalid(int area) {
        return area >= 1 && area <= 9;
    }

    private void checkArea(int area) throws NumberException.AreaBoundsException {
        if (!isAreaInvalid(area)) {
            throw new NumberException.AreaBoundsException();
        }
    }

    /**
     * 数字异常
     */
    public static class NumberException extends Exception {
        public NumberException() {
        }

        public NumberException(String message) {
            super(message);
        }

        /**
         * 分区号超出边界异常
         */
        static class AreaBoundsException extends NumberException {
            public AreaBoundsException() {
                this("Area number is out of bounds. Area number => [1, 9]");
            }

            public AreaBoundsException(String message) {
                super(message);
            }
        }

        /**
         * 行号超出边界异常
         */
        static class RowBoundsException extends NumberException {
            public RowBoundsException() {
                this("Row number is out of bounds. Row number => [0,8]");
            }

            public RowBoundsException(String message) {
                super(message);
            }
        }

        /**
         * 列号超出边界异常
         */
        static class ColBoundsException extends NumberException {
            public ColBoundsException() {
                this("Col number is out of bounds. Col number => [0,8]");
            }

            public ColBoundsException(String message) {
                super(message);
            }
        }
    }

    public void recordPossibleNumber(int row, int col) throws NumberException.RowBoundsException, NumberException.ColBoundsException, NumberException.AreaBoundsException {
        checkRow(row);
        checkCol(col);
        Record record = new Record();
        possibleRecord[row][col] = record;
        if (chest[row][col] == 0) {
            ArrayList<Integer> list = (ArrayList<Integer>) ALL.clone();
            list.retainAll(obtainPossibleNumberForRow(row));
            list.retainAll(obtainPossibleNumberForCol(col));
            list.retainAll(obtainPossibleNumberForArea(getAreaNumForPosition(row, col)));
            record.setPossibleArray(list);
        } else {
            record.setHolder();
        }
    }

    private static final ArrayList<Integer> ALL = new ArrayList<Integer>(9) {
        {
            for (int i = 0; i < 9; i++) {
                add(i + 1);
            }
        }
    };

    private static final HashMap<Integer, ArrayList<Integer>> POSSIBLE_ROW_MAP = new HashMap<>();
    private static final HashMap<Integer, ArrayList<Integer>> POSSIBLE_COL_MAP = new HashMap<>();
    private static final HashMap<Integer, ArrayList<Integer>> POSSIBLE_AREA_MAP = new HashMap<>();

    public ArrayList<Integer> obtainPossibleNumberForRow(int row) throws NumberException.RowBoundsException {
        checkRow(row);
        if (POSSIBLE_ROW_MAP.containsKey(row)) {
            return POSSIBLE_ROW_MAP.get(row);
        }
        ArrayList<Integer> curRow = new ArrayList<>();
        for (int i : chest[row]) {
            if (i != 0) {
                curRow.add(i);
            }
        }
        ArrayList<Integer> clone = (ArrayList<Integer>) ALL.clone();
        clone.removeAll(curRow);
        POSSIBLE_ROW_MAP.put(row, clone);
        return clone;
    }

    public ArrayList<Integer> obtainPossibleNumberForCol(int col) throws NumberException.ColBoundsException {
        checkCol(col);
        if (POSSIBLE_COL_MAP.containsKey(col)) {
            return POSSIBLE_COL_MAP.get(col);
        }
        ArrayList<Integer> curCol = new ArrayList<>();
        for (int i = 0; i < chest.length; i++) {
            if (chest[i][col] != 0) {
                curCol.add(chest[i][col]);
            }
        }
        ArrayList<Integer> clone = (ArrayList<Integer>) ALL.clone();
        clone.removeAll(curCol);
        POSSIBLE_COL_MAP.put(col, clone);
        return clone;
    }

    public ArrayList<Integer> obtainPossibleNumberForArea(int area) throws NumberException.AreaBoundsException {
        checkArea(area);
        if (POSSIBLE_AREA_MAP.containsKey(area)) {
            return POSSIBLE_AREA_MAP.get(area);
        }
        int[] startPositionForArea = getStartPositionForArea(area);
        int rowStart = startPositionForArea[0];
        int colStart = startPositionForArea[1];
        ArrayList<Integer> curArea = new ArrayList<>();
        for (int row = rowStart; row < rowStart + 3; row++) {
            for (int col = colStart; col < colStart + 3; col++) {
                if (chest[row][col] != 0) {
                    curArea.add(chest[row][col]);
                }
            }
        }
        ArrayList<Integer> clone = (ArrayList<Integer>) ALL.clone();
        clone.removeAll(curArea);
        POSSIBLE_AREA_MAP.put(area, clone);
        return clone;
    }

    public void printPossibleRecord() {
        for (int row = 0; row < possibleRecord.length; row++) {
            for (int col = 0; col < possibleRecord[row].length; col++) {
                System.out.println("PossibleRecord:[" + row + ", " + col + "] ==> " + possibleRecord[row][col].toString());
            }
        }
    }
}
