package com.rabbit.numberg.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rabbit.mylibrary.dialog.PickNumberView;
import com.rabbit.numberg.databinding.FragmentPickNumberBinding;

/**
 * Created by DongZF on 2022/5/17
 */
public class PickNumberFragment extends Fragment {
    private FragmentPickNumberBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPickNumberBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ZFLog", "DemoFragment.onClick: ");
                if (v instanceof PickNumberView) {
                    Log.d("ZFLog", "DemoFragment.onClick: " + ((PickNumberView) v).getTouchedArea());
                }
            }
        });
    }
}
