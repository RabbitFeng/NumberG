package com.rabbit.mylibrary;

import androidx.annotation.NonNull;

/**
 * Created by DongZF on 2022/5/17
 */
public class CellViewBind {
    @NonNull
    private CellView cellView;
    @NonNull
    private Record record;

    public CellViewBind(@NonNull CellView cellView, @NonNull Record record) {
        this.cellView = cellView;
        this.record = record;
    }

    @NonNull
    public CellView getCellView() {
        return cellView;
    }

    @NonNull
    public Record getRecord() {
        return record;
    }
}
