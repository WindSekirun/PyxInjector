package pyxis.uzuki.live.pyxinjector.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.Extra;
import pyxis.uzuki.live.pyxinjector.base.InjectorActivity;

/**
 * PyxInjector
 * Class: ${FILE_NAME}
 * Created by Pyxis on 2017-10-23.
 */

public class SecondActivity extends InjectorActivity {
    private @Extra("name") String name = ""; // explicit statement
    private @Extra String age = ""; // extra name = field name

    private @BindView TextView mTxtName;
    private @BindView TextView txtName2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtName.setText(String.format("Name is %s", name));
        txtName2.setText(String.format("Age is %s", age));
    }
}
