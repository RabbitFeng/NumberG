package com.rabbit.numberg.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rabbit.mylibrary.CellView;
import com.rabbit.mylibrary.NumberUtil;
import com.rabbit.numberg.databinding.FragmentInputBinding;
import com.rabbit.numberg.view.GroupCellLayout;

/**
 * Created by DongZF on 2022/5/17
 */
public class InputFragment extends Fragment implements View.OnClickListener {

    private FragmentInputBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInputBinding.inflate(inflater, container, false);
        initCellViewMap();
        return binding.getRoot();
    }

    //    private CellViewBind[][] cellViewBindMap = new CellViewBind[9][9];
    private CellView[][] cellViewMap = new CellView[9][9];

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 初始化cellViewMap
     */
    private void initCellViewMap() {
        GroupCellLayout[] groupCellLayouts = new GroupCellLayout[]{
                binding.fcl1, binding.fcl2, binding.fcl3,
                binding.fcl4, binding.fcl5, binding.fcl6,
                binding.fcl7, binding.fcl8, binding.fcl9,
        };

        int rowStart;
        int colStart;
        for (int i = 0; i < groupCellLayouts.length; i++) {
            int index = 0;
            CellView[] cellViewArray = groupCellLayouts[i].getCellViewArray();
            int[] startPositionForArea = NumberUtil.getStartPositionForArea(i + 1);
            rowStart = startPositionForArea[0];
            colStart = startPositionForArea[1];
            for (int row = rowStart; row < rowStart + 3; row++) {
                for (int col = colStart; col < colStart + 3; col++) {
                    Log.d("ZFLog", "InputFragment.initCellViewMap: fill [" + row + ", " + col + "]");
                    if (index < cellViewArray.length) {
                        CellView cellView = cellViewArray[index++];
                        cellView.setPosition(row, col);
                        cellView.setOnClickListener(this);
                        cellViewMap[row][col] = cellView;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CellView) {
            CellView cellView = (CellView) v;
            int row = cellView.getRow();
            int col = cellView.getCol();
            Log.d("ZFLog", "InputFragment.onClick: [" + row + ", " + col + "]");



        }
    }
}
