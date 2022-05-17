package com.rabbit.numberg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rabbit.numberg.fragment.InputFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, new InputFragment())
                .commitNow();
    }
}