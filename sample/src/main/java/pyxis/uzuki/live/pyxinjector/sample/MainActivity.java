package pyxis.uzuki.live.pyxinjector.sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pyxis.uzuki.live.pyxinjector.PyxInjector;
import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.EditTextChangeTrigger;
import pyxis.uzuki.live.pyxinjector.annotation.OnCheckChange;
import pyxis.uzuki.live.pyxinjector.annotation.OnClicks;
import pyxis.uzuki.live.pyxinjector.annotation.OnEditTextChange;
import pyxis.uzuki.live.pyxinjector.annotation.OnSeekbarChange;
import pyxis.uzuki.live.pyxinjector.base.InjectActivity;
import pyxis.uzuki.live.pyxinjector.utils.PyxUtils;

/**
 * PyxInjector
 * Class: MainActivity
 * Created by Pyxis on 2017-10-23.
 */

@SuppressLint({"DefaultLocale", "SetTextI18n"})
public class MainActivity extends InjectActivity {

    private @BindView TextView mTxtName; // resource id != field name with BindViewPrefix.PREFIX_M
    private TextView txtName2; // resource id == field name
    private @BindView(R.id.txtName3) TextView txtName3; // explicit statement

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName2 = PyxInjector.find(R.id.txtName2, PyxUtils.content(this));

        mTxtName.setText("resource id != field name with BindViewPrefix.PREFIX_M");
        txtName2.setText("resource id == field name");
        txtName3.setText("explicit statement");
    }

    @OnClicks({R.id.btnDo, R.id.btnDo2})
    private void clickDo(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name", "John");
        intent.putExtra("age", "17");
        startActivity(intent);
    }

    @OnSeekbarChange(R.id.seekBar)
    private void changeSeekbar(int progress, boolean fromUser) {
        txtName3.setText(String.format("changeSeekbar::progress = %d, fromUser = %s", progress, String.valueOf(fromUser)));
    }

    @OnEditTextChange(value = R.id.editText, trigger = EditTextChangeTrigger.AFTER)
    private void changeAfterEditText(EditText editText) {
        mTxtName.setText("changeAfterEditText::");
    }

    @OnEditTextChange(value = R.id.editText, trigger = EditTextChangeTrigger.BEFORE)
    private void changeBeforeEditText(EditText editText, CharSequence s, int start, int count, int after) {
        txtName2.setText(String.format("changeBeforeEditText:: s = %s, start = %d, count = %d, after = %d", s, start, count, after));
    }

    @OnEditTextChange(R.id.editText)
    private void changeTextEditText(EditText editText, CharSequence s, int start, int before, int count) {
        txtName3.setText(String.format("changeTextEditText:: s = %s, start = %d, before = %d, count = %d", s, start, before, count));
    }

    @OnCheckChange(R.id.checkBox)
    private void changeCheckBox(boolean isChecked) {
        txtName3.setText(String.format("changeCheckBox:: isChecked = %s", String.valueOf(isChecked)));
    }
}
