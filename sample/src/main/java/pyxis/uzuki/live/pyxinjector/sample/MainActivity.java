package pyxis.uzuki.live.pyxinjector.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.OnClicks;
import pyxis.uzuki.live.pyxinjector.annotation.OnSeekbarChange;
import pyxis.uzuki.live.pyxinjector.base.InjectActivity;

/**
 * PyxInjector
 * Class: MainActivity
 * Created by Pyxis on 2017-10-23.
 */

public class MainActivity extends InjectActivity {

    private @BindView TextView mTxtName; // resource id != field name with BindViewPrefix.PREFIX_M
    private @BindView TextView txtName2; // resource id == field name
    private @BindView(R.id.txtName3) TextView txtName3; // explicit statement

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
