package pyxis.uzuki.live.pyxinjector.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.OnClick;
import pyxis.uzuki.live.pyxinjector.base.InjectorActivity;

/**
 * PyxInjector
 * Class: MainActivity
 * Created by Pyxis on 2017-10-23.
 */

public class MainActivity extends InjectorActivity {

    private @BindView(resource = R.id.txtName) TextView txtName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName.setText("Hello, world!");
    }

    @OnClick(resource = R.id.btnDo)
    private void clickDo(View v) {
        Toast.makeText(this, "Clicked button!", Toast.LENGTH_SHORT).show();
    }
}
