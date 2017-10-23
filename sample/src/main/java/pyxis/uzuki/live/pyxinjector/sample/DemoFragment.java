package pyxis.uzuki.live.pyxinjector.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import pyxis.uzuki.live.pyxinjector.annotation.Argument;
import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.base.InjectFragment;

/**
 * PyxInjector
 * Class: MainActivity
 * Created by Pyxis on 2017-10-23.
 */
public class DemoFragment extends InjectFragment {
    private View rootView;
    private @Argument String name = "";
    private @Argument("age") String age = "";

    private @BindView TextView mTxtName; // resource id != field name with BindViewPrefix.PREFIX_M
    private @BindView TextView txtName2; // resource id == field name
    private @BindView(resource = R.id.txtName3) TextView txtName3; // explicit statement

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        return rootView;
    }

    @NotNull
    @Override
    public View getCreatedView() {
        return rootView;
    }

    @Override
    public void onViewCreated(@org.jetbrains.annotations.Nullable View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTxtName.setText(String.format("Name is %s", name));
        txtName2.setText(String.format("age is %s", age));
    }
}
