package com.rabbit.numberg.repository;

/**
 * Created by DongZF on 2022/5/17
 */
public class InputRepository {
    private static final InputRepository INSTANCE = new InputRepository();

    public static InputRepository getInstance() {
        return INSTANCE;
    }
}
