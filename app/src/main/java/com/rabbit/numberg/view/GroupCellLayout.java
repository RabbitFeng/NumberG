package com.rabbit.numberg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rabbit.mylibrary.CellView;
import com.rabbit.numberg.databinding.ViewGroupCellBinding;

/**
 * Created by DongZF on 2022/5/17
 */
public class GroupCellLayout extends FrameLayout {
    public GroupCellLayout(@NonNull Context context) {
        this(context, null);
    }

    public GroupCellLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupCellLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private ViewGroupCellBinding binding;

    CellView[] cellViews;

    private void init(Context context) {
        binding = ViewGroupCellBinding.inflate(LayoutInflater.from(context), this, true);

        cellViews = new CellView[]{
                binding.cv1, binding.cv2, binding.cv3,
                binding.cv4, binding.cv5, binding.cv6,
                binding.cv7, binding.cv8, binding.cv9,
        };
    }

    public CellView[] getCellViewArray() {
        return cellViews;
    }
}
