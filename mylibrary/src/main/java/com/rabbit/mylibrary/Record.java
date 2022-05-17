package com.rabbit.mylibrary;

import androidx.annotation.NonNull;

import java.util.ArrayList;

// 三维链表
public class Record {
//    Record nextOnRow; // 同一行的下一个数据
//    Record nextOnCol; // 同一列的下一个数据
//    Record nextOnArea; // 同一区域的下一个数据

    private boolean holder = false;// 是否是占位数字

    private ArrayList<Integer> possibleArray = new ArrayList<>();

    private int index = -1;

    public boolean isHolder() {
        return holder;
    }

    public void setHolder() {
        this.holder = true;
    }

    public int next() {
        index++;
        if (index < possibleArray.size()) {
            return possibleArray.size();
        }
        return 0;
    }

    public void resetIndex() {
        index = -1;
    }

    public void setPossibleArray(@NonNull ArrayList<Integer> array) {
        possibleArray.clear();
        possibleArray.addAll(array);
    }

    @Override
    public String toString() {
        return "Record{" +
                "holder=" + holder +
                ", possibleArray=" + possibleArray +
                '}';
    }
}
